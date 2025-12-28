package com.shop.integration.openai;

import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import com.shop.integration.openai.request.OpenAIRequestVectorCreate;
import com.shop.integration.openai.request.OpenAIRequestVectorUploadFile;
import com.shop.profile.AppProfile;
import com.shop.profile.DevProfile;
import com.shop.profile.LocalProfile;
import com.shop.vector.VectorApi;

import lombok.RequiredArgsConstructor;

@Component
@DevProfile
@AppProfile
@LocalProfile
@RequiredArgsConstructor
public class DefaultVectorApi implements VectorApi {
	private final OpenAIClient openAIClient;
	private final OpenAIProperties openAIProperties;

	private final String token = "Bearer " + openAIProperties.apiKey();

	@Override

	public String create(String name, String description) {
		var response = openAIClient.create(
			token,
			new OpenAIRequestVectorCreate(name, description)
		);
		return response.id();
	}

	@Override
	public String uploadFile(String vectorStoreId, byte[] json) {
		var map = new LinkedMultiValueMap<String, Object>();

		var fileResource = new ByteArrayResource(
			json
		) {
			@Override
			public String getFilename() {
				return String.format("%s.json", UUID.randomUUID());
			}
		};

		map.add("purpose", "assistants");
		map.add("file", fileResource);

		var response = openAIClient.upload(
			token,
			map
		);

		openAIClient.uploadVectorStore(
			vectorStoreId,
			token,
			new OpenAIRequestVectorUploadFile(response.id())
		);

		return response.id();
	}

	@Override
	public void delete(String vectorStoreId, String fileId) {
		openAIClient.delete(
			vectorStoreId,
			fileId,
			token
		);

		openAIClient.deleteFile(
			fileId,
			token
		);
	}
}
