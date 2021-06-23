package com.food.ordering.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.ordering.domain.OrderStatus;

public class OrderStatusDTO {
	private long id;
	private long orderId;
	private long restaurantId;
	private String orderStatus;

	@JsonProperty
	private boolean isPaid;

	public OrderStatusDTO() {

	}

	public OrderStatusDTO(OrderStatus orderstatus) {
		this.id = orderstatus.getId();
		this.orderId = orderstatus.getOrder().getId();
		this.orderStatus = orderstatus.getStatus().getOrderStatus();
		this.isPaid = orderstatus.isPaid();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Override
	public String toString() {
		return "OrderStatusDTO [id=" + id + ", orderId=" + orderId + ", restaurantId=" + restaurantId + ", orderStatus="
				+ orderStatus + ", isPaid=" + isPaid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + (int) (restaurantId ^ (restaurantId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderStatusDTO other = (OrderStatusDTO) obj;
		if (id != other.id)
			return false;
		if (isPaid != other.isPaid)
			return false;
		if (orderId != other.orderId)
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (restaurantId != other.restaurantId)
			return false;
		return true;
	}

}
