package com.food.ordering.enums;

import com.food.ordering.exception.UnprocessableEntityException;

public enum Gender {
	MALE("Male"), FEMALE("Female"), TRANSGENDER("Transgender");

	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	private Gender(String gender) {
		this.gender = gender;
	}

	public static Gender getEnum(String value) throws Exception {
		for (Gender g : values()) {
			if (g.getGender().equalsIgnoreCase(value)) {
				return g;
			}
		}
		throw new UnprocessableEntityException("Invalid gender type.");
	}

}
