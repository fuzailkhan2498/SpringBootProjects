package com.food.ordering.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.food.ordering.domain.MenuGroup;
import com.food.ordering.domain.MenuItems;
import com.food.ordering.domain.Restaurant;

public class RestaurantMenuDTO {
	private String name;
	private String description;
	private String type;
	private String menuName;
	private List<String> menuItem = new ArrayList<String>();

	public RestaurantMenuDTO(Restaurant restaurant) {
		this.name = restaurant.getName();
		this.description = restaurant.getDescription();
		this.type = restaurant.getType();
		this.menuName = restaurant.getMenu().getMenuName();
		List<MenuItems> menuItems = restaurant.getMenu().getMenuGroup().stream().map(MenuGroup::getMenuItems)
				.flatMap(list -> list.stream()).collect(Collectors.toList());
		for (MenuItems items : menuItems) {
			menuItem.add(items.getName());
		}

	}

	public RestaurantMenuDTO() {
		super();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<String> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<String> menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public String toString() {
		return "RestaurantMenuDTO [name=" + name + ", description=" + description + ", type=" + type + ", menuName="
				+ menuName + ", menuItem=" + menuItem + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((menuItem == null) ? 0 : menuItem.hashCode());
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		RestaurantMenuDTO other = (RestaurantMenuDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (menuItem == null) {
			if (other.menuItem != null)
				return false;
		} else if (!menuItem.equals(other.menuItem))
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
