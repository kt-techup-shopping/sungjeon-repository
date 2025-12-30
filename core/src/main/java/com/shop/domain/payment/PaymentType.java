package com.shop.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
	CARD("카드"),
	ACCOUNT_TRANSFER("계좌이체"),
	VIRTUAL_ACCOUNT("가상계좌");

	private final String description;
}
