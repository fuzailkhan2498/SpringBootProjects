package com.food.ordering.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.food.ordering.dto.RestaurantDTO;
import com.sun.istack.NotNull;

@Entity
@Table(name = "restaurant")
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private String type;

	@NotNull
	private String address;

	@NotNull
	private String pincode;

	private boolean permissionGranted;

	@Column(name = "is_accepting")
	private boolean isAcceptingOrder;

	@OneToOne(mappedBy = "restaurant")
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@OneToMany(mappedBy = "restaurant")
	private List<Order> order;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Restaurant() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAcceptingOrder() {
		return isAcceptingOrder;
	}

	public void setAcceptingOrder(boolean isAcceptingOrder) {
		this.isAcceptingOrder = isAcceptingOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isPermissionGranted() {
		return permissionGranted;
	}

	public void setPermissionGranted(boolean permissionGranted) {
		this.permissionGranted = permissionGranted;
	}

	public Restaurant(RestaurantDTO restaurant) {

		this.name = restaurant.getName();
		this.description = restaurant.getDescription();
		this.type = restaurant.getType();
		this.isAcceptingOrder = restaurant.isAcceptingOrder();
		this.address = restaurant.getAddress();
		this.pincode = restaurant.getPincode();
		this.permissionGranted = restaurant.isPermissionGranted();
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type
				+ ", address=" + address + ", pincode=" + pincode + ", permissionGranted=" + permissionGranted
				+ ", isAcceptingOrder=" + isAcceptingOrder + ", menu=" + menu + ", order=" + order + ", user=" + user
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isAcceptingOrder ? 1231 : 1237);
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + (permissionGranted ? 1231 : 1237);
		result = prime * result + ((pincode == null) ? 0 : pincode.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Restaurant other = (Restaurant) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (isAcceptingOrder != other.isAcceptingOrder)
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (permissionGranted != other.permissionGranted)
			return false;
		if (pincode == null) {
			if (other.pincode != null)
				return false;
		} else if (!pincode.equals(other.pincode))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
