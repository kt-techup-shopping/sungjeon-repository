package com.shop.category.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.category.request.AdminCategoryCreateRequest;
import com.shop.category.response.AdminCategoryDetailResponse;
import com.shop.category.response.AdminCategoryListResponse;
import com.shop.category.service.AdminCategoryService;
import com.shop.response.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자 카테고리", description = "관리자 카테고리 API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	private final AdminCategoryService adminCategoryService;

	@Operation(summary = "카테고리 생성", description = "관리자가 카테고리를 생성하는 API")
	@PostMapping
	public ApiResult<AdminCategoryDetailResponse> createCategory(
		@RequestBody @Valid AdminCategoryCreateRequest request
	) {
		var category = adminCategoryService.createCategory(request.name(), request.parentCategoryId());
		return ApiResult.ok(category);
	}

	@Operation(summary = "카테고리 조회", description = "관리자가 카테고리 목록을 조회")
	@GetMapping
	public ApiResult<AdminCategoryListResponse> getCategory() {
		return ApiResult.ok(adminCategoryService.getCategories());
	}
}
