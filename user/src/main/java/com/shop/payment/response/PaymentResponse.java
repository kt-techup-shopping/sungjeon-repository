package com.shop.payment.response;

import com.shop.domain.payment.Payment;

public record PaymentResponse(
	Long id,
	Long totalPrice,
	Long discountPrice,
	Long deliveryFee,
	Long finalPrice,
	String status,
	String type
) {
	public static PaymentResponse of(Payment payment) {
		return new PaymentResponse(
			payment.getId(),
			payment.getTotalPrice(),
			payment.getDiscountPrice(),
			payment.getDeliveryFee(),
			payment.getFinalPrice(),
			payment
				.getStatus().name(),
			payment
				.getType().name()
		);
	}
}
