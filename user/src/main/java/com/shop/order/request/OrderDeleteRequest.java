package com.shop.order.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public record OrderDeleteRequest(
	@NotNull
	List<Long> productIds,
	@NotNull
	Long orderId
) {
}
