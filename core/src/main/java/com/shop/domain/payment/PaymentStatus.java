package com.shop.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
	PENDING("결제대기"),
	COMPLETED("결제완료"),
	FAILED("결제실패"),
	CANCELLED("주문취소");

	private final String description;
}
