package com.shop.controller.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.common.enums.HistoryType;
import com.shop.common.response.ApiResult;
import com.shop.common.support.TechUpLogger;
import com.shop.dto.order.OrderRequest;
import com.shop.security.CurrentUser;
import com.shop.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	//주문생성
	// userId, action(주문생성), type(사용자, 관리자)
	@TechUpLogger(type = HistoryType.ORDER_CREATE, content = "사용자 주문 생성")
	@PostMapping
	public ApiResult<Void> create(
		@AuthenticationPrincipal CurrentUser currentUser,
		@RequestBody @Valid OrderRequest.Create request) {
		orderService.create(
			currentUser.getId(),
			request.productId(),
			request.receiverName(),
			request.receiverAddress(),
			request.receiverMobile(),
			request.quantity()
		);
		return ApiResult.ok();
	}
}
