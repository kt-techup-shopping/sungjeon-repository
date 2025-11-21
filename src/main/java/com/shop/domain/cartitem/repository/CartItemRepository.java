package com.shop.domain.cartitem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.domain.cart.model.Cart;
import com.shop.domain.cartitem.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, CartItemRepositoryCustom {

	// 1. 기본 정보만 (가장 빠름)
	Optional<Cart> findByUserId(Long userId);

	// 2. 상품 개수만 필요할 때
	@EntityGraph(attributePaths = "cartItems")
	Optional<Cart> findWithCartItemsByUserId(Long userId);

	// 3. 상품 정보도 필요할 때
	@EntityGraph(attributePaths = {"cartItems", "cartItems.product"})
	Optional<Cart> findWithCartItemsAndProductsByUserId(Long userId);

	// 4. 모든 정보가 필요할 때
	@EntityGraph(attributePaths = {"user", "cartItems", "cartItems.product"})
	Optional<Cart> findWithAllByUserId(Long userId);
}
}