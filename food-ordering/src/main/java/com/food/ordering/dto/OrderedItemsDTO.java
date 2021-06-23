package com.food.ordering.dto;

import com.food.ordering.domain.OrderedItems;

public class OrderedItemsDTO {
	private long id;
	private int quantity;
	private long orderId;
	private int menuItemId;

	public OrderedItemsDTO() {

	}

	public OrderedItemsDTO(OrderedItems orderItems) {
		super();
		this.id = orderItems.getId();
		this.quantity = orderItems.getQuantity();
		this.orderId = orderItems.getOrder().getId();
		this.menuItemId = orderItems.getMenuItem().getId();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	@Override
	public String toString() {
		return "OrderedItemsDTO [id=" + id + ", quantity=" + quantity + ", orderId=" + orderId + ", menuItemId="
				+ menuItemId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + menuItemId;
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + quantity;
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
		OrderedItemsDTO other = (OrderedItemsDTO) obj;
		if (id != other.id)
			return false;
		if (menuItemId != other.menuItemId)
			return false;
		if (orderId != other.orderId)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
