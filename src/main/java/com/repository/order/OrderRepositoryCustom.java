package com.repository.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dto.order.OrderResponse;

public interface OrderRepositoryCustom {
	Page<OrderResponse.Search> search(String keyword, Pageable pageable);
}
