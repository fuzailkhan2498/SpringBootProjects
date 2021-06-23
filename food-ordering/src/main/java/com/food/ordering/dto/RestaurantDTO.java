package com.food.ordering.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.ordering.domain.Restaurant;

public class RestaurantDTO {
	private long id;
	private long userId;
	private String name;
	private String description;
	private String type;
	private String address;
	private String pincode;

	@JsonProperty
	private boolean permissionGranted;

	@JsonProperty
	private boolean acceptingOrder;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAcceptingOrder() {
		return acceptingOrder;
	}

	public void setAcceptingOrder(boolean isAcceptingOrder) {
		this.acceptingOrder = isAcceptingOrder;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public RestaurantDTO() {

	}

	public boolean isPermissionGranted() {
		return permissionGranted;
	}

	public void setPermissionGranted(boolean permissionGranted) {
		this.permissionGranted = permissionGranted;
	}

	public RestaurantDTO(Restaurant restaurant) {
		super();
		this.id = restaurant.getId();
		this.userId = restaurant.getUser().getId();
		this.name = restaurant.getName();
		this.description = restaurant.getDescription();
		this.type = restaurant.getType();
		this.acceptingOrder = restaurant.isAcceptingOrder();
		this.address = restaurant.getAddress();
		this.pincode = restaurant.getPincode();
		this.permissionGranted = restaurant.isPermissionGranted();
	}

	@Override
	public String toString() {
		return "RestaurantDTO [id=" + id + ", userId=" + userId + ", name=" + name + ", description=" + description
				+ ", type=" + type + ", address=" + address + ", pincode=" + pincode + ", permissionGranted="
				+ permissionGranted + ", acceptingOrder=" + acceptingOrder + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (acceptingOrder ? 1231 : 1237);
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (permissionGranted ? 1231 : 1237);
		result = prime * result + ((pincode == null) ? 0 : pincode.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
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
		RestaurantDTO other = (RestaurantDTO) obj;
		if (acceptingOrder != other.acceptingOrder)
			return false;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		if (userId != other.userId)
			return false;
		return true;
	}

}
