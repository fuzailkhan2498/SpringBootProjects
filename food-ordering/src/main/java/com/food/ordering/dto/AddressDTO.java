package com.food.ordering.dto;

import com.food.ordering.domain.Address;

public class AddressDTO {
	private long customerId;
	private long addressId;
	private int pincode;
	private String landmark;
	private String city;
	private String state;
	private String country;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public AddressDTO() {
		super();
	}

	public AddressDTO(Address address) {
		super();
		this.addressId = address.getId();
		this.customerId = address.getCustomer().getId();
		this.pincode = address.getPincode();
		this.landmark = address.getLandmark();
		this.city = address.getCity();
		this.state = address.getState();
		this.country = address.getCountry();
	}

	@Override
	public String toString() {
		return "AddressDTO [customerId=" + customerId + ", addressId=" + addressId + ", pincode=" + pincode
				+ ", landmark=" + landmark + ", city=" + city + ", state=" + state + ", country=" + country + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (addressId ^ (addressId >>> 32));
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + ((landmark == null) ? 0 : landmark.hashCode());
		result = prime * result + pincode;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		AddressDTO other = (AddressDTO) obj;
		if (addressId != other.addressId)
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (customerId != other.customerId)
			return false;
		if (landmark == null) {
			if (other.landmark != null)
				return false;
		} else if (!landmark.equals(other.landmark))
			return false;
		if (pincode != other.pincode)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

}
