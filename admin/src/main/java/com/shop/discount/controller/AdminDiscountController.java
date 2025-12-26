package com.shop.discount.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shop.discount.request.AdminDiscountCreateRequest;
import com.shop.discount.service.AdminDiscountService;
import com.shop.response.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자 할인", description = "관리자 할인 API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/discounts")
public class AdminDiscountController {
	private final AdminDiscountService adminDiscountService;

	@Operation(summary = "관리자 상품 할인 등록")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> createDiscount(
		@RequestBody @Valid AdminDiscountCreateRequest request
	) {
		adminDiscountService.createDiscount(
			request.productId(),
			request.value(),
			request.type()
		);
		return ApiResult.ok();
	}
}
