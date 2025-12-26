package com.shop.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shop.order.request.OrderCreateRequest;
import com.shop.order.request.OrderDeleteRequest;
import com.shop.order.request.OrderUpdateRequest;
import com.shop.order.response.OrderDetailResponse;
import com.shop.order.response.OrderDetailUserResponse;
import com.shop.order.service.OrderService;
import com.shop.payment.request.PaymentCreateRequest;
import com.shop.payment.response.PaymentResponse;
import com.shop.payment.service.PaymentService;
import com.shop.response.ApiResult;
import com.shop.security.DefaultCurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "주문", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;
	private final PaymentService paymentService;

	@Operation(summary = "주문 생성", description = "사용자가 상품을 선택하여 주문을 생성합니다.")
	@GetMapping
	public ApiResult<Void> createOrder(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@RequestBody @Valid OrderCreateRequest request
	) {
		orderService.createOrder(
			currentUser.getId(),
			request.productQuantity().keySet().stream().toList(),
			request
		);
		return ApiResult.ok();
	}

	@Operation(summary = "내 주문 조회", description = "사용자가 자신의 모든 주문 내역을 조회합니다.")
	@GetMapping
	public ApiResult<List<OrderDetailResponse>> getMyOrders(
		@AuthenticationPrincipal DefaultCurrentUser currentUser
	) {
		List<OrderDetailResponse> orders = orderService.getMyOrders(currentUser.getId());
		return ApiResult.ok(orders);
	}

	@Operation(summary = "주문 수정", description = "이미 생성한 주문의 수령인 정보 및 상품 정보를 수정합니다.")
	@PutMapping("/update")
	public ApiResult<Void> updateOrder(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@RequestBody @Valid OrderUpdateRequest request
	) {
		orderService.updateOrder(
			currentUser.getId(),
			request.productQuantity().keySet().stream().toList(),
			request
		);
		return ApiResult.ok();
	}

	@Operation(summary = "주문 삭제", description = "이미 생성한 주문의 수령인 정보 및 상품 정보를 삭제합니다.")
	@PutMapping("/delete")
	public ApiResult<Void> deleteOrder(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@RequestBody @Valid OrderDeleteRequest request
	) {
		orderService.deleteOrder(
			currentUser.getId(),
			request.productIds(),
			request
		);
		return ApiResult.ok();
	}

	@Operation(summary = "내 주문 상세 조회", description = "사용자가 자신의 특정 주문 내역을 상세 조회합니다.")
	@GetMapping("/{id}/detail")
	public ApiResult<OrderDetailUserResponse> getOrderDetail(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@PathVariable Long id
	) {
		var orders = orderService.getMyOrderDetail(currentUser.getId(), id);
		return ApiResult.ok(orders);
	}

	@Operation(summary = "결제 생성", description = "오더 ID를 통해 결제를 생성합니다.")
	@PostMapping("/{orderId}/payments")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<Void> createPayment(
		@RequestBody @Valid PaymentCreateRequest request,
		@PathVariable Long orderId
	) {
		paymentService.createPayment(orderId, request.type());
		return ApiResult.ok();
	}

	@Operation(summary = "결제 조회", description = "오더 ID를 통해 결제를 조회합니다.")
	@GetMapping("/{orderId}/payments")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<List<PaymentResponse>> getPaymentInfo(
		@PathVariable Long orderId
	) {
		var payments = paymentService.getPayment(orderId);
		return ApiResult.ok(payments);
	}
}
