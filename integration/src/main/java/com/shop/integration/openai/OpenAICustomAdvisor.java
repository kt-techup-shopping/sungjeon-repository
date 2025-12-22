package com.shop.integration.openai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.stereotype.Component;

import com.shop.integration.openai.request.OpenAIRequestVectorSearch;
import com.shop.integration.openai.response.OpenAIResponseSearchData;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OpenAICustomAdvisor implements BaseAdvisor {
	private final OpenAIClient openAIClient;
	private final OpenAIProperties openAIProperties;

	@NotNull
	@Override
	public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
		var prompt = chatClientRequest.prompt();
		var message = prompt.getUserMessage().getText();
		var candidateAnswers = new ArrayList<OpenAIResponseSearchData>();

		var parsing = message.split(":");

		var request = new OpenAIRequestVectorSearch(parsing[1]);

		var ids = parsing[0].split(",");

		Arrays.stream(ids).forEach(id -> {
			var response = openAIClient.search(parsing[0], String.format("Bearer %s", openAIProperties.apiKey()), request);

			var searchData = response.data().stream().max(Comparator.comparingDouble(OpenAIResponseSearchData::score)).orElse(
				new OpenAIResponseSearchData("", "", 0.0, null, null)
			);

			candidateAnswers.add(searchData);
		});

		var topScoreSearchData = candidateAnswers.stream()
			.max(Comparator.comparingDouble(OpenAIResponseSearchData::score))
			.orElse(
				new OpenAIResponseSearchData("", "", 0.0, null, null)
			);

		var newPrompt = prompt.augmentSystemMessage(topScoreSearchData.contents().toString());

		return chatClientRequest.mutate()
			.prompt(newPrompt)
			.build();
	}

	@Override
	public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
		return chatClientResponse;
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
