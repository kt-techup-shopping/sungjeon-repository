package com.shop.domain.cartitem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shop.domain.cartitem.response.CartItemResponse;

public interface CartItemRepositoryCustom {
	Page<CartItemResponse.Search> search(String keyword, Pageable pageable);
}
