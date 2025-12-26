package com.shop.discount.request;

import javax.validation.constraints.NotNull;

import com.shop.domain.discount.DiscountType;

public record AdminDiscountCreateRequest(
	@NotNull
	Long productId,
	@NotNull
	Long value,
	@NotNull
	DiscountType type
) {
}
