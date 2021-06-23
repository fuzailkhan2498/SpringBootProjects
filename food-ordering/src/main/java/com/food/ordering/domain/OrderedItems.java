package com.food.ordering.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ordered_items")
public class OrderedItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "menu_item_id")
	private MenuItems menuItem;

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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public MenuItems getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItems menuItem) {
		this.menuItem = menuItem;
	}

	public OrderedItems() {

	}

//	public OrderedItems(OrderedItemsDTO orderItems) {
//		this.quantity = orderItems.getQuantity();
//		this.order = new Order();
//		this.order.setId(orderItems.getOrderId());
//		this.menuItem = new MenuItems();
//		this.menuItem.setId(orderItems.getMenuItemId());
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((menuItem == null) ? 0 : menuItem.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		OrderedItems other = (OrderedItems) obj;
		if (id != other.id)
			return false;
		if (menuItem == null) {
			if (other.menuItem != null)
				return false;
		} else if (!menuItem.equals(other.menuItem))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderedItems [id=" + id + ", quantity=" + quantity + ", order=" + order + ", menuItem=" + menuItem
				+ "]";
	}
	

}
