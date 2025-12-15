package com.shop.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.domain.history.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
