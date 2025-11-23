package com.shop.domain.cart.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.domain.cart.response.CartResponse;
import com.shop.domain.cart.service.CartService;
import com.shop.domain.cartitem.request.CartItemRequest;
import com.shop.domain.cartitem.response.CartItemResponse;
import com.shop.global.common.ApiResult;
import com.shop.global.security.CurrentUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	// 내 장바구니 조회
	@GetMapping
	public ApiResult<CartResponse.Detail> getCart(
		@AuthenticationPrincipal CurrentUser currentUser) {
		CartResponse.Detail cart = cartService.getCart(currentUser.getId());
		return ApiResult.ok(cart);
	}

	// 장바구니 검색
	@GetMapping("/search")
	public ApiResult<Page<CartItemResponse.Detail>> searchCartItems(
		@AuthenticationPrincipal CurrentUser currentUser,
		@RequestParam(required = false) String keyword,
		Pageable pageable) {
		Page<CartItemResponse.Detail> result = cartService.searchCartItems(
			currentUser.getId(), keyword, pageable);
		return ApiResult.ok(result);
	}

	// 장바구니 추가
	@PostMapping("/items")
	public ApiResult<Long> addCartItem(
		@AuthenticationPrincipal CurrentUser currentUser,
		@Valid @RequestBody CartItemRequest.Create request) {
		Long cartItemId = cartService.addCartItem(currentUser.getId(), request.getProductId(), request);
		return ApiResult.ok(cartItemId);
	}

	// 수량 변경
	@PutMapping("/items/{itemId}")
	public ApiResult<Void> updateCartItem(
		@AuthenticationPrincipal CurrentUser currentUser,
		@PathVariable Long itemId,
		@Valid @RequestBody CartItemRequest.Update request) {
		cartService.updateCartItem(currentUser.getId(), itemId, request);
		return ApiResult.ok();
	}

	// 특정 아이템 삭제
	@PutMapping("/items/{itemId}/delete")
	public ApiResult<Void> deleteCartItem(
		@AuthenticationPrincipal CurrentUser currentUser,
		@PathVariable Long itemId) {
		cartService.deleteCartItem(currentUser.getId(), itemId);
		return ApiResult.ok();
	}

	// 일괄 삭제
	@PutMapping("/items")
	public ApiResult<Void> deleteCartItems(
		@AuthenticationPrincipal CurrentUser currentUser,
		@Valid @RequestBody CartItemRequest.Delete request) {
		cartService.deleteCartItems(currentUser.getId(), request);
		return ApiResult.ok();
	}

	// P전체 비우기
	@PutMapping
	public ApiResult<Void> clearCart(
		@AuthenticationPrincipal CurrentUser currentUser) {
		cartService.clearCart(currentUser.getId());
		return ApiResult.ok();
	}
}