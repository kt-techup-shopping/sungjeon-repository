package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class SpringOpenAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOpenAiApplication.class, args);
	}

}
