package com.shop.common.support;

public record VisitorEvent(
	String ip,
	String userAgent,
	Long userId
) {
}
