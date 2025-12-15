package com.kt.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;

import lombok.val;

@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {
	@Override
	public Executor getAsyncExecutor() {
		val executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		executor.initialize();

		return new DelegatingSecurityContextExecutor(executor);
	}
}
