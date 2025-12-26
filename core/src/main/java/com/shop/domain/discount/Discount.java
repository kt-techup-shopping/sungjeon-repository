package com.shop.domain.discount;

import com.shop.domain.product.Product;
import com.shop.support.BaseEntity;

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
public class Discount extends BaseEntity {
	private Long value;
	@Enumerated(EnumType.STRING)
	private DiscountType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	public Discount(Long value, DiscountType type, Product product) {
		this.value = value;
		this.type = type;
		this.product = product;
	}
}
