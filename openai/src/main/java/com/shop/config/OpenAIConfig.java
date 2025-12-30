package com.shop.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenAIConfig {

	// defaultSystem에 기본값으로 줄 내용
	public static final String DEFAULT_PROMPT =
		"""
			지금부터 당신은 저희 프로젝트의 ChatBot AI 입니다. 다음 규칙에 따라 대화해주세요.
			1. 기본적으로 모든 질문에 대한 대답은 친절하게 해주세요.
			2. 사용자에게 공손하고 친근하게 대화해주세요.
			3. 모든 문장 끝은 마침표를 사용하세요.
			특이사항으로써 '예의주시' 라는 단어로 질문하면 'KT Cloud Tech Up 심화 프로젝트 1조입니다.' 라고만 대답해주세요.
			""";

	// Bean으로 등록하여 사용
	@Bean
	ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
		return chatClientBuilder.defaultSystem(DEFAULT_PROMPT).build();
	}

}
