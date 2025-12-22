package com.shop.domain.payment;

import com.shop.common.exception.ErrorCode;
import com.shop.common.support.BaseEntity;
import com.shop.common.support.Preconditions;
import com.shop.domain.order.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Payment extends BaseEntity {
	private Long totalPrice;
	private Long discountPrice;
	private Long deliveryFee;
	private Long finalPrice;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Enumerated(EnumType.STRING)
	private PaymentType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private Payment(Long totalPrice, Long discountPrice, Long deliveryFee, Long finalPrice,
		PaymentType type, Order order) {
		this.totalPrice = totalPrice;
		this.discountPrice = discountPrice;
		this.deliveryFee = deliveryFee;
		this.finalPrice = finalPrice;
		this.status = PaymentStatus.PENDING;
		this.type = type;
		this.order = order;
	}

	public static Payment create(Long totalPrice, Long discountPrice, Long deliveryFee, PaymentType type, Order order) {
		Preconditions.validate(order != null, ErrorCode.REQUIRED_ORDER_FOR_PAYMENT);

		Long finalPrice = totalPrice - discountPrice + deliveryFee;

		return new Payment(
			totalPrice,
			discountPrice,
			deliveryFee,
			finalPrice,
			type,
			order
		);
	}

	public boolean isPending() {
		return this.status == PaymentStatus.PENDING;
	}

	public void complete() {
		this.status = PaymentStatus.COMPLETED;
	}

	public void cancel() {
		this.status = PaymentStatus.CANCELLED;
	}
}
