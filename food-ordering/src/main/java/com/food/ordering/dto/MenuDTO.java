package com.food.ordering.dto;

import com.food.ordering.domain.Menu;

public class MenuDTO {
	private int id;
	private String menuName;
	private String menuType;
	private long restaurantId;

	public MenuDTO(Menu menu) {
		super();
		this.id = menu.getId();
		this.menuName = menu.getMenuName();
		this.menuType = menu.getMenuType();
		this.restaurantId = menu.getRestaurant().getId();
	}

	public MenuDTO() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Override
	public String toString() {
		return "MenuDTO [id=" + id + ", menuName=" + menuName + ", menuType=" + menuType + ", restaurantId="
				+ restaurantId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((menuType == null) ? 0 : menuType.hashCode());
		result = prime * result + (int) (restaurantId ^ (restaurantId >>> 32));
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
		MenuDTO other = (MenuDTO) obj;
		if (id != other.id)
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (menuType == null) {
			if (other.menuType != null)
				return false;
		} else if (!menuType.equals(other.menuType))
			return false;
		if (restaurantId != other.restaurantId)
			return false;
		return true;
	}

}
