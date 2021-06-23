package com.food.ordering.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.food.ordering.dto.MenuDTO;

@Entity
@Table(name = "menu")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String menuName;

	@Column(name = "type")
	private String menuType;

	@OneToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	private List<MenuGroup> menuGroup;

	public Menu() {
		super();
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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<MenuGroup> getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(List<MenuGroup> menuGroup) {
		this.menuGroup = menuGroup;
	}

	public Menu(MenuDTO menu) {

		this.menuName = menu.getMenuName();
		this.menuType = menu.getMenuType();
//		this.restaurant = new Restaurant();
//		this.restaurant.setId(menu.getRestaurantId());
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuName=" + menuName + ", menuType=" + menuType + ", restaurant=" + restaurant
				+ ", menuGroup=" + menuGroup + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((menuGroup == null) ? 0 : menuGroup.hashCode());
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((menuType == null) ? 0 : menuType.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
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
		Menu other = (Menu) obj;
		if (id != other.id)
			return false;
		if (menuGroup == null) {
			if (other.menuGroup != null)
				return false;
		} else if (!menuGroup.equals(other.menuGroup))
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
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

}
