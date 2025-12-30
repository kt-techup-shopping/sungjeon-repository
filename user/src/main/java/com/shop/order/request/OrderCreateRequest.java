package com.shop.order.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record OrderCreateRequest(
	@NotNull
	Map<Long, @Min(1) Long> productQuantity, // key: productId, value: quantity
	@NotBlank
	String receiverName,
	@NotBlank
	String receiverAddress,
	@NotBlank
	String receiverMobile
) {
}
