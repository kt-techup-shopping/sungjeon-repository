package com.kt.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "테크업 쇼핑몰",
		version = "0.0.2",
		description = "테크업 쇼핑몰 API 명세서"
	)
)
@RequiredArgsConstructor
public class SwaggerConfiguration {
	private final Environment environment;

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.servers(
				List.of(new Server().url(getServerUrl())
				)
			)
			.components(
				new Components()
					.addSecuritySchemes(
						"Bearer Authentication",
						new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")
					)
			);
	}

	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder()
			.group("User API")
			.pathsToExclude("/admin/**")
			.build();
	}

	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi.builder()
			.group("Admin API")
			.pathsToMatch("/admin/**", "/auth/**")
			.build();
	}

	private String getServerUrl() {
		try {
			String[] activeProfiles = environment.getActiveProfiles();

			// 활성화된 프로파일이 없으면 기본값 반환
			if (activeProfiles.length == 0) {
				return "http://localhost:8080";
			}

			String profile = activeProfiles[0];

			return switch (profile) {
				case "dev" -> "https://dev.ktechup.com";
				case "prod" -> "https://ktechup.com";
				default -> "http://localhost:8080";
			};
		} catch (Exception e) {
			// 예외 발생 시 기본값 반환
			System.err.println("프로파일 설정 오류: " + e.getMessage());
			return "http://localhost:8080";
		}
	}
}
