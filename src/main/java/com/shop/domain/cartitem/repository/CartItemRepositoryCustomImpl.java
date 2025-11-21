package com.shop.domain.cartitem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.domain.cartitem.model.QCartItem;
import com.shop.domain.cartitem.response.CartItemResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryCustomImpl implements CartItemRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	private final QCartItem cartItem = QCartItem.cartItem;

	@Override
	public Page<CartItemResponse.Search> search(String keyword, Pageable pageable) {
		return null;
	}
}
