package com.shop.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.common.exception.CustomException;
import com.shop.common.exception.ErrorCode;
import com.shop.domain.payment.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	default Payment findByIdOrThrow(Long id, ErrorCode errorCode) {
		return findById(id).orElseThrow(() -> new CustomException(errorCode));
	}
}
