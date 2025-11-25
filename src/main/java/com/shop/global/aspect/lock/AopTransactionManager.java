package com.shop.global.aspect.lock;

import org.aspectj.lang.ProceedingJoinPoint;

public interface AopTransactionManager {
	Object proceed(ProceedingJoinPoint joinPoint) throws Throwable;
}
