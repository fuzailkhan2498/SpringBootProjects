package com.food.ordering.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.food.ordering.dto.OrderStatusDTO;
import com.food.ordering.enums.OrderStatusType;

@Entity
@Table(name = "order_status")
public class OrderStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private OrderStatusType status;

	private boolean isPaid;

	@OneToOne
	private Order order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrderStatusType getStatus() {
		return status;
	}

	public void setStatus(OrderStatusType status) {
		this.status = status;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderStatus() {

	}

	public OrderStatus(OrderStatusDTO orderStatusDTO) throws Exception {
		this.status = OrderStatusType.getEnum(orderStatusDTO.getOrderStatus());
		this.isPaid = orderStatusDTO.isPaid();
	}

	@Override
	public String toString() {
		return "OrderStatus [id=" + id + ", status=" + status + ", isPaid=" + isPaid + "]";
	}

}
