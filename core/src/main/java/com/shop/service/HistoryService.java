package com.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.enums.HistoryType;
import com.shop.domain.history.History;
import com.shop.repository.history.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {
	private final HistoryRepository historyRepository;

	public void create(HistoryType type, String content, Long userId) {
		historyRepository.save(
			new History(
				type, content, userId
			)
		);
	}
}
