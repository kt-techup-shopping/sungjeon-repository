package com.shop.order.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.order.request.AdminOrderStatusChangeRequest;
import com.shop.order.response.AdminOrderDetailResponse;
import com.shop.order.response.AdminOrderDetailUserResponse;
import com.shop.order.service.AdminOrderService;
import com.shop.request.Paging;
import com.shop.response.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자 주문", description = "관리자 주문 API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/orders")
public class AdminOrderController {
	private final AdminOrderService adminOrderService;

	@Operation(summary = "관리자 주문 목록 조회", description = "관리자가 주문 ID, 사용자 ID, 주문 상태를 조건으로 주문 목록을 조회합니다.")
	@GetMapping
	public ApiResult<Page<AdminOrderDetailResponse>> getOrders(
		@RequestParam(required = false) Long orderId,
		@RequestParam(required = false) Long userId,
		@RequestParam(required = false) String status,
		@Parameter Paging paging
	) {
		return ApiResult.ok(adminOrderService.getOrders(orderId, userId, status, paging));
	}

	@Operation(summary = "관리자 주문 상세 조회", description = "관리자가 특정 주문 ID의 상세 정보를 조회합니다.")
	@GetMapping("/{id}/detail")
	public ApiResult<AdminOrderDetailUserResponse> getOrderDetail(
		@PathVariable Long id
	) {
		return ApiResult.ok(adminOrderService.getAdminOrderDetailById(id));
	}

	@Operation(summary = "주문 상태 변경", description = "관리자가 특정 주문의 상태를 변경합니다.")
	@PutMapping("/{orderId}/update")
	public ApiResult<Void> updateOrderStatus(
		@RequestBody @Valid AdminOrderStatusChangeRequest request,
		@PathVariable Long orderId
	) {
		adminOrderService.updateOrderStatus(request, orderId);
		return ApiResult.ok();
	}

	@Operation(summary = "주문 취소", description = "관리자가 특정 주문을 취소(CANCELLED) 상태로 변경합니다.")
	@PutMapping("/{orderId}/delete")
	public ApiResult<Void> deleteOrder(
		@PathVariable Long orderId
	) {
		adminOrderService.deleteOrder(orderId);
		return ApiResult.ok();
	}
}
