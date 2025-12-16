package com.shop.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.exception.ErrorCode;
import com.shop.common.support.Preconditions;
import com.shop.config.AiConfig;
import com.shop.dto.AiChatResponse;
import com.shop.dto.Role;
import com.shop.model.ChatMessage;
import com.shop.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AiChatService {

	private final ChatClient chatClient;
	private final ChatMessageRepository chatMessageRepository;

	// AI와 유저의 응답을 저장해두는 메서드
	public List<ChatMessage> getChatHistory(Long userId) {
		Preconditions.validate(userId != null && userId > 0, ErrorCode.NOT_FOUND_USER);

		List<ChatMessage> chatHistory = chatMessageRepository.findByUserIdOrderByCreateAtAsc(userId);
		Preconditions.validate(!chatHistory.isEmpty(), ErrorCode.EMPTY_CHAT_HISTORY);
		
		return chatHistory;
	}

	// AI에게 유저의 질문을 응답받고, AI와 유저가 주고 받은 응답을 저장하는 메서드
	public AiChatResponse sendMessage(Long userId, String userInput) {
		Preconditions.validate(userId != null && userId > 0, ErrorCode.NOT_FOUND_USER);
		Preconditions.validate(userInput != null && !userInput.trim().isEmpty(),
			ErrorCode.INVALID_CHAT);

		// 1. 유저의 질문에 대한 AI의 응답
		String aiResponse = chatClient.prompt().system(AiConfig.DEFAULT_PROMPT).user(userInput).call().content();

		// 2. User의 입력을 저장
		ChatMessage userMessage = new ChatMessage(userId, Role.USER, userInput);
		chatMessageRepository.save(userMessage);

		// 3. Assistant의 응답을 저장
		ChatMessage assistantMessage = new ChatMessage(userId, Role.ASSISTANT, aiResponse);
		chatMessageRepository.save(assistantMessage);

		return new AiChatResponse(Role.ASSISTANT.name(), aiResponse);
	}

	// 유저와 AI가 주고받았던 응답을 전부 삭제하는 메서드
	public void clearChatHistory(Long userId) {
		Preconditions.validate(userId != null && userId > 0, ErrorCode.NOT_FOUND_USER);
		chatMessageRepository.deleteAllByUserId(userId);
	}

}
