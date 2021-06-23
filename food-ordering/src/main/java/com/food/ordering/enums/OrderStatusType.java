package com.food.ordering.enums;

import com.food.ordering.exception.UnprocessableEntityException;

public enum OrderStatusType {
	ORDERED("Ordered"), PREPARING("Preparing"), PREPARED("Prepared"), DELIVERED("Delivered"), TAKEN("Taken"),
	CANCELLED("Cancelled");

	private String orderStatus;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	private OrderStatusType(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public static OrderStatusType getEnum(String value) throws Exception {
		for (OrderStatusType o : values()) {
			if (o.getOrderStatus().equalsIgnoreCase(value)) {
				return o;
			}
		}
		throw new UnprocessableEntityException("Invalid status type.");
	}

}
