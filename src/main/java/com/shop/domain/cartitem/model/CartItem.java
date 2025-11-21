package com.shop.domain.cartitem.model;

import com.shop.domain.cart.model.Cart;
import com.shop.domain.product.model.Product;
import com.shop.global.common.BaseEntity;
import com.shop.global.common.ErrorCode;
import com.shop.global.common.Preconditions;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CartItem extends BaseEntity {
	private Long productCount;
	private Long intCoupon; // TODO.. 쿠폰할인
	private Long percentCoupon;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public CartItem(Long productCount, Cart cart, Product product) {
		this.productCount = productCount;
		setCart(cart);
		this.product = product;
		Preconditions.validate(productCount >= 1, ErrorCode.MIN_PIECE);
	}

	public void setCart(Cart cart) {
		this.cart = cart;
		cart.addCartItem(this);
	}

	public void updateProductCount(Long productCount) {
		Preconditions.validate(productCount >= 1, ErrorCode.MIN_PIECE);
		this.productCount = productCount;
	}

	public void addProductCount(Long productCount) {
		Preconditions.validate(productCount >= 1, ErrorCode.MIN_PIECE);
		this.productCount += productCount;
	}

	public Long getTotalPrice() {
		return this.productCount * this.product.getPrice();
	}

	public Long getTotalDiscountPrice() {
		return this.productCount * this.product.getPrice() * percentCoupon - intCoupon;
		// return this.productCount * this.product.getDiscountPrice();
	} // TODO.. 할인정책 결정 필요, 현재는 퍼센트 할인 후 정수 할인

	public boolean isAvailable() {
		return product.isActive();
	}

	public boolean isSoldOut() {
		return product.isSoldOut();
	}
}
