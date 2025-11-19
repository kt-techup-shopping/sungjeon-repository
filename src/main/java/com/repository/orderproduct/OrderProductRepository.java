package com.repository.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.orderproduct.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
