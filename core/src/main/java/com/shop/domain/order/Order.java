package com.shop.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.shop.common.support.BaseEntity;
import com.shop.domain.delivery.Delivery;
import com.shop.domain.orderproduct.OrderProduct;
import com.shop.domain.payment.Payment;
import com.shop.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {
	@Embedded
	private Receiver receiver;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	private LocalDateTime deliveredAt;

	@Version
	private Long version;
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Delivery delivery;

	@PostPersist
	private void createDelivery() {
		if (this.delivery == null) {
			this.delivery = new Delivery(this);
		}
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "order")
	private List<OrderProduct> orderProducts = new ArrayList<>();

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<Payment> payments = new ArrayList<>();

	private Order(Receiver receiver, User user) {
		this.receiver = receiver;
		this.user = user;
		this.deliveredAt = LocalDateTime.now().plusDays(2);
		this.status = OrderStatus.PENDING;
	}

	public static Order create(Receiver receiver, User user) {
		return new Order(
			receiver,
			user
		);
	}

	public void mapToOrderProduct(OrderProduct orderProduct) {
		this.orderProducts.add(orderProduct);
	}

	//결제 대기 중인 결제가 있으면 요청 불가
	public boolean canRequestPayment() {
		return payments.stream().noneMatch(Payment::isPending);
	}

	public Long calculateTotalPrice() {
		return orderProducts.stream().mapToLong(
			op -> op.getProduct().getPrice() * op.getQuantity()
		).sum();
	}

	public void cancel() {
		this.isDeleted = true;
		this.status = OrderStatus.CANCELLED;
	}

	public void updateStatus(OrderStatus orderStatus) {
		this.status = orderStatus;
	}

	public void addPayment(Payment payment) {
		this.payments.add(payment);
	}

	public boolean isPending() {
		return this.status == OrderStatus.PENDING;
	}

	public boolean isCompleted() {
		return this.status == OrderStatus.COMPLETED;
	}

	public void completePayment() {
		this.status = OrderStatus.COMPLETED;
	}

	public void resetToPending() {
		this.status = OrderStatus.PENDING;
	}
}
