package com.food.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.MenuItemsDTO;
import com.food.ordering.service.MenuItemsService;

@RestController
public class MenuItemsController {

	@Autowired
	private MenuItemsService menuItemsService;

	/**
	 * Add and Update menu item into the database according to menuGroup
	 * 
	 * @throws Exception
	 */
	@PostMapping("/menu-item")
	public MenuItemsDTO saveAndUpdate(@RequestBody MenuItemsDTO menuItems) throws Exception {
		return menuItemsService.saveAndUpdate(menuItems);
	}


	/**
	 * Delete the menu items
	 */
	@DeleteMapping("/menu-item/{id}")
	public void delete(@PathVariable int id) {
		menuItemsService.deleteMenuItem(id);
	}
	

}
