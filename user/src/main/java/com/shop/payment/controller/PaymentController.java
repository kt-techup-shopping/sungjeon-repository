package com.shop.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shop.payment.service.PaymentService;
import com.shop.response.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "결제", description = "결제를 관리하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
	private final PaymentService paymentService;

	@Operation(summary = "결제 완료", description = "결제 완료 처리를 진행합니다.")
	@PutMapping("/internal/{paymentId}/complete")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> completePayment(
		@PathVariable Long paymentId
	) {
		paymentService.completePayment(paymentId);
		return ApiResult.ok();
	}

	@Operation(summary = "결제 취소", description = "결제 취소 처리를 진행합니다.")
	@PutMapping("{paymentId}/cancel")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> cancelPayment(
		@PathVariable Long paymentId
	) {
		paymentService.cancelPayment(paymentId);
		return ApiResult.ok();
	}
}
