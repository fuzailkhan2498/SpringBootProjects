package com.food.ordering.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.food.ordering.dto.MenuGroupDTO;

@Entity
public class MenuGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "group_name")
	private String menuGroupName;
	
	@Column(name = "group_type")
	private String menuGroupType;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@OneToMany(mappedBy = "menuGroup",cascade = CascadeType.ALL)
	private List<MenuItems> menuItems;

	public MenuGroup() {
		
	}

	public MenuGroup(MenuGroupDTO menuGroup) {

		this.menuGroupName = menuGroup.getMenuGroupName();
		this.menuGroupType = menuGroup.getMenuGroupType();
//		this.menu = new Menu();
//		this.menu.setId(menuGroup.getMenuId());
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

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<MenuItems> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItems> menuItems) {
		this.menuItems = menuItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		result = prime * result + ((menuGroupName == null) ? 0 : menuGroupName.hashCode());
		result = prime * result + ((menuGroupType == null) ? 0 : menuGroupType.hashCode());
		result = prime * result + ((menuItems == null) ? 0 : menuItems.hashCode());
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
		MenuGroup other = (MenuGroup) obj;
		if (id != other.id)
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
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
		if (menuItems == null) {
			if (other.menuItems != null)
				return false;
		} else if (!menuItems.equals(other.menuItems))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuGroup [id=" + id + ", menuGroupName=" + menuGroupName + ", menuGroupType=" + menuGroupType
				+ ", menu=" + menu + ", menuItems=" + menuItems + "]";
	}
	
	

}
