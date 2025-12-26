package com.shop.faq.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shop.faq.request.FAQRequestCreate;
import com.shop.faq.service.FAQService;
import com.shop.response.ApiResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/faq")
@RequiredArgsConstructor
public class AdminFAQController {
	private final FAQService faqService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<Void> create(@RequestBody @Valid FAQRequestCreate request) throws Exception {
		faqService.create(request);
		return ApiResult.ok();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> delete(@PathVariable Long id) {
		faqService.delete(id);
		return ApiResult.ok();
	}
}
