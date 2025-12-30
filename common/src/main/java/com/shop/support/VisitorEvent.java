package com.shop.support;

public record VisitorEvent(
	String ip,
	String userAgent,
	Long userId
) {
}
