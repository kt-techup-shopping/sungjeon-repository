package com.shop.service.vector;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.vector.VectorApi;
import com.shop.domain.vector.Vector;
import com.shop.domain.vector.VectorType;
import com.shop.repository.vector.VectorRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VectorService {
	private final VectorRepository vectorRepository;
	private final VectorApi vectorApi;

	@PostConstruct
	void init() {
		if (!vectorRepository.existsByType(VectorType.FAQ)) {
			create(
				"faq_vector_store",
				"FAQ 벡터 스토어",
				"FAQ 벡터 스토어 입니다",
				VectorType.FAQ
			);
		}

	}

	public void create(String storedId, String name, String description, VectorType type) {
		vectorRepository.save(new Vector(
				type,
				storedId,
				description,
				name
			)
		);
	}
}
