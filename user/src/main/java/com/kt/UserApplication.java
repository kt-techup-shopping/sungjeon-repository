package com.kt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import com.kt.common.support.Message;

import lombok.RequiredArgsConstructor;

// @ServletComponentScan
// @EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class UserApplication {
	private final ApplicationEventPublisher applicationEventPublisher;

	@EventListener(ApplicationReadyEvent.class)
	public void started() {
		applicationEventPublisher.publishEvent(new Message("Shopping Application Started"));
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
