package com.shop.support;

import com.shop.exception.CustomException;
import com.shop.exception.ErrorCode;

public class Preconditions {
	public static void validate(boolean expression, ErrorCode errorCode) {
		if (!expression) {
			throw new CustomException(errorCode);
		}
	}
}
