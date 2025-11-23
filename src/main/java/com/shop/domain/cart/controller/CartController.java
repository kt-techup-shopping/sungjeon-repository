/**
 * ## 장바구니 API (로그인 사용자)
 *
 * - 장바구니 와 유저는 1 대 1, 장바구니와 상품은 1 : M, 장바구니와 장바구니 아이템 1:M
 * - `GET /cart`
 *     - 내 장바구니 목록 조회(상품 정보 + 수량 + 현재 가격 + 할인 가격)
 * - `POST /cart/items`
 *     - 장바구니 담기(상품ID, 수량) – 재고/상태 검증
 * - `PUT /cart/items/{itemId}`
 *     - 수량 수정 (1이상의 범위에서만 수정 가능)
 * - `PUT /cart/items/delete`
 *     - 장바구니 상품(리스트로 받아서) 삭제
 * - `PUT /cart/clear`
 *     - 장바구니 전체 비우기
 * - 장바구니에 담은 상품이 비활성화/삭제가 된다면 장바구니에서 삭제
 * - 장바구니에 담은 상품이 품절이 된다면 장바구니에서 솔드아웃 표시
 * - 장바구니 결제 완료 이후 삭제
 * - 주문 취소시 복구, 배송 상태 변경시 삭제
 *
 * > 비로그인 장바구니는 요구사항 도출만, 구현 보류
 * >
 * >
 * > → 비로그인 상태에서 위 API 호출 시 401 + “로그인 필요” 응답.
 * >
 */
package com.shop.domain.cart.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import com.shop.global.security.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@GetMapping
	public ResponseEntity<CartResponse.Detail> getCart(
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CartResponse.Detail cart = cartService.getCart(userPrincipal.getId());
		return ResponseEntity.ok(cart);
	}

	@GetMapping("/items")
	public ResponseEntity<List<CartItemResponse.Detail>> getCartItems(
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		List<CartItemResponse.Detail> items = cartService.getCartItems(userPrincipal.getId());
		return ResponseEntity.ok(items);
	}

	@GetMapping("/items/search")
	public ResponseEntity<Page<CartItemResponse.Detail>> searchCartItems(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(required = false) String keyword,
		Pageable pageable) {
		Page<CartItemResponse.Detail> result = cartService.searchCartItems(
			userPrincipal.getId(), keyword, pageable);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/items")
	public ResponseEntity<Long> addCartItem(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@Valid @RequestBody CartItemRequest.Create request) {
		Long cartItemId = cartService.addCartItem(userPrincipal.getId(), request);
		return ResponseEntity.ok(cartItemId);
	}

	@PutMapping("/items/{itemId}")
	public ResponseEntity<Void> updateCartItem(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable Long itemId,
		@Valid @RequestBody CartItemRequest.Update request) {
		cartService.updateCartItem(userPrincipal.getId(), itemId, request);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/items/delete")
	public ResponseEntity<Void> deleteCartItems(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@Valid @RequestBody CartItemRequest.DeleteItems request) {
		cartService.deleteCartItems(userPrincipal.getId(), request);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/clear")
	public ResponseEntity<Void> clearCart(
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		cartService.clearCart(userPrincipal.getId());
		return ResponseEntity.ok().build();
	}
}