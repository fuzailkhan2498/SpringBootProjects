package com.food.ordering.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.ordering.domain.MenuItems;
import com.food.ordering.domain.Order;
import com.food.ordering.domain.OrderedItems;

public class OrderDTO {
	private long id;
	private LocalDateTime orderDate;
	@JsonProperty
	private boolean paid;

	private double price;
	private String orderStatus;
	private long customerId;
	private long restaurantId;

	@JsonIgnore
	float totalPrice;

	private Map<String, Float> orderedItems = new HashMap<>();

	public OrderDTO() {
	}

	public OrderDTO(Order order, boolean flag) {
		this.id = order.getId();
		this.price = order.getPrice();
		this.customerId = order.getCustomer().getId();
		this.restaurantId = order.getRestaurant().getId();
		this.orderDate = order.getOrderDate();
		this.orderStatus = order.getOrderStatus().getStatus().getOrderStatus();
		this.paid = order.isPaid();
	}

	public OrderDTO(Order order) {
		this.id = order.getId();

		Iterator<OrderedItems> itr = order.getOrderedItems().iterator();

		while (itr.hasNext()) {
			OrderedItems orderedItems = (OrderedItems) itr.next();
			int quantity = orderedItems.getQuantity();
			float price = orderedItems.getMenuItem().getPrice();
			totalPrice = quantity * price + totalPrice;

		}
		this.orderStatus = order.getOrderStatus().getStatus().getOrderStatus();
		this.price = totalPrice;
		this.customerId = order.getCustomer().getId();
		this.restaurantId = order.getRestaurant().getId();
		this.orderDate = order.getOrderDate();
		this.orderedItems = order.getOrderedItems().stream().map(o -> o.getMenuItem())
				.collect(Collectors.toMap(MenuItems::getName, MenuItems::getPrice, (o, n) -> n));
		this.paid = order.isPaid();
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Map<String, Float> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(Map<String, Float> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", orderDate=" + orderDate + ", paid=" + paid + ", price=" + price
				+ ", orderStatus=" + orderStatus + ", customerId=" + customerId + ", restaurantId=" + restaurantId
				+ ", totalPrice=" + totalPrice + ", orderedItems=" + orderedItems + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + ((orderedItems == null) ? 0 : orderedItems.hashCode());
		result = prime * result + (paid ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (restaurantId ^ (restaurantId >>> 32));
		result = prime * result + Float.floatToIntBits(totalPrice);
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
		OrderDTO other = (OrderDTO) obj;
		if (customerId != other.customerId)
			return false;
		if (id != other.id)
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (orderedItems == null) {
			if (other.orderedItems != null)
				return false;
		} else if (!orderedItems.equals(other.orderedItems))
			return false;
		if (paid != other.paid)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (restaurantId != other.restaurantId)
			return false;
		if (Float.floatToIntBits(totalPrice) != Float.floatToIntBits(other.totalPrice))
			return false;
		return true;
	}

}
