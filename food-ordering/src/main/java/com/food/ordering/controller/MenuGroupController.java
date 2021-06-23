package com.food.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.MenuGroupDTO;
import com.food.ordering.service.MenuGroupService;

@RestController
public class MenuGroupController {
	@Autowired
	
	private MenuGroupService menuGroupService;
	/**
	 * Add and update menu group details
	 * @throws Exception 
	 */
	@PostMapping("/menu-group")
	public MenuGroupDTO saveAndUpdate(@RequestBody MenuGroupDTO menugroup) throws Exception
	{
		return menuGroupService.saveAndUpdate(menugroup);
	}
	
	/**
	 * Delete the menu group 
	 */
	@DeleteMapping("/menu-group/{id}")
	public MenuGroupDTO deleteMenuGroup(@PathVariable int id )
	{
		return menuGroupService.deleteMenuGroup(id);
	}
	
	/**
	 * Get menu group respective to their id.
	 */
	@GetMapping("/menu-group/{id}")
	public MenuGroupDTO getMenuGroup(@PathVariable int id)
	{
		return menuGroupService.getMenuGroup(id);
	}
	 

}
