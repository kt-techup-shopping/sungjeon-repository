package com.shop.domain.cartitem.response;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public interface CartItemResponse {
	record Search(
		Long id,
		String productName,
		Long productCount,
		Long productPrice,
		Long totalPrice,
		LocalDateTime createdAt
	) {
		@QueryProjection
		public Search {
		}
	}
}