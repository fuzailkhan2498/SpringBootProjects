package com.food.ordering.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.food.ordering.dto.MenuItemsDTO;
import com.sun.istack.NotNull;

@Entity
public class MenuItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@NotNull
	private String type;
	
	@NotNull
	private float rating;
	private float price;

	@ManyToOne
	@JoinColumn(name = "menu_group_id")
	private MenuGroup menuGroup;
	
	@OneToMany(mappedBy = "menuItem",cascade = CascadeType.ALL)
	private List<OrderedItems> orderedItems;

	public MenuItems() {

	}

	public MenuItems(MenuItemsDTO menuItems) {
		this.name = menuItems.getName();
		this.description = menuItems.getDescription();
		this.type = menuItems.getType();
		this.rating = menuItems.getRating();
		this.price = menuItems.getPrice();
//		this.menuGroup = new MenuGroup();
//		this.menuGroup.setId(menuItems.getMenuGroupId());
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public List<OrderedItems> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<OrderedItems> orderedItems) {
		this.orderedItems = orderedItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((menuGroup == null) ? 0 : menuGroup.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderedItems == null) ? 0 : orderedItems.hashCode());
		result = prime * result + Float.floatToIntBits(rating);
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
		MenuItems other = (MenuItems) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (menuGroup == null) {
			if (other.menuGroup != null)
				return false;
		} else if (!menuGroup.equals(other.menuGroup))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderedItems == null) {
			if (other.orderedItems != null)
				return false;
		} else if (!orderedItems.equals(other.orderedItems))
			return false;
		if (Float.floatToIntBits(rating) != Float.floatToIntBits(other.rating))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuItems [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type
				+ ", rating=" + rating + ", menuGroup=" + menuGroup + ", orderedItems=" + orderedItems + "]";
	}

}
