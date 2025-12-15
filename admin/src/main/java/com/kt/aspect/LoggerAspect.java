package com.kt.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.kt.common.support.TechUpLogger;
import com.kt.security.CurrentUser;
import com.kt.service.HistoryService;

import lombok.RequiredArgsConstructor;

//todo 로그 남기는 부분 ELK로 변경
@Aspect
@Component
@RequiredArgsConstructor
public class LoggerAspect {
	private final HistoryService historyService;

	@Around("@annotation(com.kt.common.support.TechUpLogger) && @annotation(techUpLogger)")
	public Object techUpLogger(ProceedingJoinPoint joinPoint, TechUpLogger techUpLogger) throws Throwable {
		var principal = (CurrentUser)Arrays.stream(joinPoint.getArgs())
			.filter(arg -> arg instanceof CurrentUser)
			.findFirst().orElse(null);

		var userId = principal != null ? principal.getId() : null;

		var type = techUpLogger.type();
		var content = techUpLogger.content();

		historyService.create(type, content, userId);

		return joinPoint.proceed();
	}
}
