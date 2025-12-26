package com.shop.product.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shop.product.response.ProductDetailResponse;
import com.shop.product.response.ProductSearchResponse;
import com.shop.product.service.ProductService;
import com.shop.request.Paging;
import com.shop.response.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "상품", description = "상품을 관리하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	private final ProductService productService;

	@Operation(summary = "상품 검색/목록 조회", description = "상품을 검색하거나 목록을 조회합니다. 필터링, 정렬, 페이징 지원.")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Page<ProductSearchResponse>> searchList(
		@RequestParam(required = false) String keyword,
		@RequestParam(required = false) Long categoryId,
		@RequestParam(required = false) boolean activeOnly,
		@RequestParam(required = false) String sort,
		@Parameter Paging paging
	) {
		var list = productService.getSearchList(keyword, categoryId, activeOnly, sort, paging.toPageable());
		return ApiResult.ok(list);
	}

	@Operation(summary = "상품 상세 조회", description = "상품 ID를 통해 상세 정보를 조회합니다.")
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<ProductDetailResponse> getDetail(
		@PathVariable Long id
	) {
		var detail = productService.getProductDetail(id);
		return ApiResult.ok(detail);
	}
}
