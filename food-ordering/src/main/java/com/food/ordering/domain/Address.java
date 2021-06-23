package com.food.ordering.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.food.ordering.dto.AddressDTO;
import com.sun.istack.NotNull;

@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private int pincode;
	
	@NotNull
	private String landmark;
	
	@NotNull
	private String city;
	
	@NotNull
	private String state;
	
	@NotNull
	private String country;
	
	@NotNull
	@Column(name = "is_active")
	private boolean isActive;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLandmark() {
		return landmark;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Address() {

	}

	public Address(AddressDTO addressDTO) {
		super();
		this.pincode = addressDTO.getPincode();
		this.landmark = addressDTO.getLandmark();
		this.city = addressDTO.getCity();
		this.state = addressDTO.getState();
		this.country = addressDTO.getCountry();
//		this.customer = new Customer();
//		this.customer.setId(addressDTO.getCustomerId());
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", pincode=" + pincode + ", landmark=" + landmark + ", city=" + city + ", state="
				+ state + ", country=" + country + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
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
		Address other = (Address) obj;
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
