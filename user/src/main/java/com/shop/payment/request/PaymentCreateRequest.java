package com.shop.payment.request;

import javax.validation.constraints.NotNull;

import com.shop.domain.payment.PaymentType;

public record PaymentCreateRequest(
	@NotNull
	PaymentType type
) {
}
