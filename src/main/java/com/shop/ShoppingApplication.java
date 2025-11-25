package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.shop.global.integration.slack.SlackApi;

import lombok.RequiredArgsConstructor;

// @ServletComponentScan
@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class ShoppingApplication {
	private final SlackApi slackApi;

	// @EventListener(ApplicationReadyEvent.class)
	// public void started() {
	// 	slackApi.notify("Shopping Application Started");
	// }

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

}
