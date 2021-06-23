
package com.food.ordering.dto;

import com.food.ordering.domain.MenuGroup;

public class MenuGroupDTO {
	private int id;
	private String menuGroupName;
	private String menuGroupType;
	private int menuId;

	public MenuGroupDTO() {

	}

	public MenuGroupDTO(MenuGroup menugroup) {
		this.id = menugroup.getId();
		this.menuGroupName = menugroup.getMenuGroupName();
		this.menuGroupType = menugroup.getMenuGroupType();
		this.menuId = menugroup.getMenu().getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuGroupName() {
		return menuGroupName;
	}

	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}

	public String getMenuGroupType() {
		return menuGroupType;
	}

	public void setMenuGroupType(String menuGroupType) {
		this.menuGroupType = menuGroupType;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	@Override
	public String toString() {
		return "MenuGroupDTO [id=" + id + ", menuGroupName=" + menuGroupName + ", menuGroupType=" + menuGroupType
				+ ", menuId=" + menuId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((menuGroupName == null) ? 0 : menuGroupName.hashCode());
		result = prime * result + ((menuGroupType == null) ? 0 : menuGroupType.hashCode());
		result = prime * result + menuId;
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
		MenuGroupDTO other = (MenuGroupDTO) obj;
		if (id != other.id)
			return false;
		if (menuGroupName == null) {
			if (other.menuGroupName != null)
				return false;
		} else if (!menuGroupName.equals(other.menuGroupName))
			return false;
		if (menuGroupType == null) {
			if (other.menuGroupType != null)
				return false;
		} else if (!menuGroupType.equals(other.menuGroupType))
			return false;
		if (menuId != other.menuId)
			return false;
		return true;
	}

}
