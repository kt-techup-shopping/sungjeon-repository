package com.shop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 20)
	private Role role;
	private String content;
	private LocalDateTime createAt;

	public ChatMessage(Long userId, Role role, String content) {
		this.userId = userId;
		this.role = role;
		this.content = content;
		this.createAt = LocalDateTime.now();
	}
}
