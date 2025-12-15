package com.shop.dto;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
