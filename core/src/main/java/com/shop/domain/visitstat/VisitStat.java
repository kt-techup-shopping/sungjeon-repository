package com.shop.domain.visitstat;

import java.time.LocalDateTime;

import com.shop.common.support.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class VisitStat extends BaseEntity {
	private String ip;
	private String userAgent;
	private Long userId;
	private LocalDateTime visitedAt = LocalDateTime.now();

	public VisitStat(String ip, String userAgent, Long userId) {
		this.ip = ip;
		this.userAgent = userAgent;
		this.userId = userId;
	}
}
