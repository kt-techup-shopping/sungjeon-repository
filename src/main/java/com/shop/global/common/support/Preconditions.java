package com.shop.global.common.support;

import com.shop.global.common.exception.CustomException;
import com.shop.global.common.exception.ErrorCode;

public class Preconditions {
	public static void validate(boolean expression, ErrorCode errorCode) {
		if (!expression) {
			throw new CustomException(errorCode);
		}
	}
}
