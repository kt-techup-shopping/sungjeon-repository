package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	List<ChatMessage> findByUserIdOrderByCreateAtAsc(Long userId);

	void deleteAllByUserId(Long userId);
}
