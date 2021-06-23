package com.food.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.RoleDTO;
import com.food.ordering.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * Controller to save the role
	 * @throws Exception 
	 */

	@PostMapping("/role")
	public RoleDTO saveRole(@RequestBody RoleDTO role) throws Exception
	{
		return this.roleService.saveRole(role);
	}

}
