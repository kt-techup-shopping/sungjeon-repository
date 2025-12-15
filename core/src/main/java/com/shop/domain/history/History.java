package com.shop.domain.history;

import com.shop.common.enums.HistoryType;
import com.shop.common.support.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
public class History extends BaseEntity {
	@Enumerated(value = EnumType.STRING)
	private HistoryType type;

	private String content;

	private Long userId;

	public History(HistoryType type, String content, Long userId) {
		this.type = type;
		this.content = content;
		this.userId = userId;
	}
}
