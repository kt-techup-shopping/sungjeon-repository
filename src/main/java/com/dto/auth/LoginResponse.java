package com.dto.auth;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
