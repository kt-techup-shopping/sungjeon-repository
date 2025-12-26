package com.shop.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shop.response.ApiResult;
import com.shop.user.request.UserCreateRequest;
import com.shop.user.request.UserUpdateRequest;
import com.shop.user.response.UserCreateResponse;
import com.shop.user.response.UserStatusResponse;
import com.shop.user.response.UserUpdateResponse;
import com.shop.user.service.AdminAdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자(관리자용)", description = "관리자 계정 관리 API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/admins")
public class AdminAdminController {
	private final AdminAdminService adminService;

	@Operation(summary = "관리자 생성", description = "관리자가 새로운 관리자 계정을 생성합니다.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<UserCreateResponse> create(
		@RequestBody @Valid UserCreateRequest request
	) {
		var admin = adminService.createAdmin(request);
		return ApiResult.ok(UserCreateResponse.from(admin));
	}

	@Operation(summary = "관리자 정보 수정", description = "특정 관리자 계정의 이름, 이메일, 휴대폰 정보를 수정합니다.")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<UserUpdateResponse> update(
		@PathVariable Long id,
		@RequestBody @Valid UserUpdateRequest request
	) {
		var admin = adminService.update(id, request.name(), request.email(), request.mobile());

		return ApiResult.ok(UserUpdateResponse.from(admin));
	}

	@PutMapping("{id}/delete")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "관리자 권한 삭제", description = "관리자 계정의 권한을 일반 사용자로 변경합니다.")
	public ApiResult<UserStatusResponse> roleToUser(
		@PathVariable Long id
	) {
		var admin = adminService.updateUserRoleToUser(id);
		return ApiResult.ok(UserStatusResponse.from(admin));
	}
}
