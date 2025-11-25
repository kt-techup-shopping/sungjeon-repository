package com.shop.global.integration.slack;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.slack.api.methods.MethodsClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackApi {
	private final MethodsClient methodsClient;
	private final SlackProperties slackProperties;
	private final Environment environment;

	public void notify(String message) {
		try {
			methodsClient.chatPostMessage(request -> {
				request.username("spring-Bot")
					.channel(slackProperties.logChannel())
					.text(String.format("```%s - shopping - %s```", message, getActiveProfile()))
					.build();

				return request;
			});
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private String getActiveProfile() {
		return Arrays.stream(environment.getActiveProfiles()).findFirst().orElse("local");
	}

}
