package com.shop.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.domain.payment.Payment;
import com.shop.exception.CustomException;
import com.shop.exception.ErrorCode;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	default Payment findByIdOrThrow(Long id, ErrorCode errorCode) {
		return findById(id).orElseThrow(() -> new CustomException(errorCode));
	}
}
