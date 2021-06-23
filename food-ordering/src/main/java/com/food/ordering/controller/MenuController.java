package com.food.ordering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.MenuDTO;
import com.food.ordering.dto.MenuGroupDTO;
import com.food.ordering.service.MenuService;

@RestController
public class MenuController {
	@Autowired
	private MenuService menuService;

	/**
	 * Add and update menu in the database
	 * 
	 * @throws Exception
	 */
	@PostMapping("/menu")
	public MenuDTO saveAndUpdate(@RequestBody MenuDTO menuDTO) throws Exception {
		return menuService.saveAndUpdate(menuDTO);
	}

	
	/**
	 * Delete the menu
	 */
	@DeleteMapping("/menu/{id}")
	public MenuDTO deleteMenu(@PathVariable int id) {
		return menuService.deleteMenu(id);
	}
	

	/**
	 * Get Menu respective to their id.
	 */

	@GetMapping("menu/{id}")
	public MenuDTO getMenu(@PathVariable int id) {
		return menuService.getMenu(id);
	}
	

	/**
	 * Get Menu Group according to the menu
	 */
	@GetMapping("menu/{menuId}/menu-groups")
	public List<MenuGroupDTO> getMenuGroups(@PathVariable int menuId) {
		return menuService.getMenuGroups(menuId);
	}

}
