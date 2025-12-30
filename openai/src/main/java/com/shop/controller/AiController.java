package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.model.ChatMessage;
import com.shop.request.AiChatRequest;
import com.shop.response.AiChatResponse;
import com.shop.response.ApiResult;
import com.shop.vector.AiChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class AiController {

	private final AiChatService aiChatService;

	@Value("${spring.ai.openai.chat.options.model}")
	private String model;

	@GetMapping("/model")
	public String getModel() {
		return "AI Chat Service model: " + model;
	}

	// DB에 저장해둔 대화 내역을 요청하는 API
	@GetMapping("/history")
	public List<ChatMessage> getChatHistory(@RequestParam Long userId) {
		return aiChatService.getChatHistory(userId);
	}

	// AI에게 질문을 보내는 API
	@PostMapping
	public AiChatResponse sendMessage(@RequestParam Long userId, @RequestBody AiChatRequest request) {
		return aiChatService.sendMessage(userId, request.userInput());
	}

	// DB에 저장해둔 대화 내역을 삭제하는 API
	@PutMapping("/history/delete")
	public ApiResult<Void> clearChatHistory(@RequestParam Long userId) {
		aiChatService.clearChatHistory(userId);
		return ApiResult.ok();
	}
}

// GetMapping 으로 메시지를 입력하여 잘 작동 하는지 확인
// @GetMapping("/test")
// public Map<String, String> completion(
// 	@RequestParam(value = "message", defaultValue = "예의주시") String message) {
// 	return Map.of("ChatAI", this.chatClient.prompt().user(message).call().content());
// }
