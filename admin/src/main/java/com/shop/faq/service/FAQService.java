package com.shop.faq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.domain.faq.FAQ;
import com.shop.domain.vector.VectorType;
import com.shop.exception.CustomException;
import com.shop.exception.ErrorCode;
import com.shop.faq.request.FAQRequestCreate;
import com.shop.faq.request.FAQRequestSearch;
import com.shop.faq.response.FAQResponseSearch;
import com.shop.faq.response.FAQResponseSearchData;
import com.shop.integration.openai.response.OpenAIResponseContent;
import com.shop.integration.openai.response.OpenAIResponseSearch;
import com.shop.repository.faq.FAQRepository;
import com.shop.repository.vector.VectorRepository;
import com.shop.vector.VectorApi;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FAQService {
	private final FAQRepository faqRepository;
	private final VectorRepository vectorRepository;
	private final VectorApi vectorApi;
	private final ObjectMapper objectMapper;

	public void create(FAQRequestCreate request) throws Exception {
		var faq = faqRepository.save(
			new FAQ(
				request.title(),
				request.content(),
				request.category()
			)
		);

		var vector = vectorRepository.findByType(VectorType.FAQ)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VECTOR_STORE));

		var fileId = vectorApi.uploadFile(vector.getStoredId(), objectMapper.writeValueAsBytes(faq));

		faq.updateFileId(fileId);
	}

	public void delete(Long id) {
		var faq = faqRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND_FAQ)
		);
		var vector = vectorRepository.findByType(VectorType.FAQ)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VECTOR_STORE));

		vectorApi.delete(vector.getStoredId(), faq.getFileId());

		faqRepository.delete(faq);
	}
	
	// 데이터 변환
	public FAQResponseSearch search(FAQRequestSearch request) {
		var vector = vectorRepository.findByType(VectorType.FAQ)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VECTOR_STORE));

		// OpenAI API 호출
		var aiResponse = vectorApi.search(vector.getStoredId(), request.query());

		OpenAIResponseSearch searchResponse;
		try {
			searchResponse = objectMapper.convertValue(aiResponse, OpenAIResponseSearch.class);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.ERROR_VECTOR_SEARCH);
		}

		List<FAQResponseSearchData> data = searchResponse.data().stream()
			.map(it -> {
				String textContent = it.content().stream()
					.filter(content -> "text".equals(content.type()))
					.map(OpenAIResponseContent::text)
					.findFirst()
					.orElse("");

				return FAQResponseSearchData.of(
					it.fileId(),
					it.filename(),
					it.score(),
					it.attributes(),
					textContent
				);
			}).toList();

		return FAQResponseSearch.of(request.query(), data);
	}
}
