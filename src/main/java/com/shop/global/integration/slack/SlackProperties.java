package com.shop.global.integration.slack;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slack")
public record SlackProperties(
	String botToken,
	String logChannel
) {

}

