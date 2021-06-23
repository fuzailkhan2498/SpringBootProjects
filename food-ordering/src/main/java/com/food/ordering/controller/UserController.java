package com.food.ordering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.UserDTO;
import com.food.ordering.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * This controller will get the details of particular customer
	 */

	@GetMapping("/user/{id}")
	public UserDTO getUser(@PathVariable long id) throws Exception {
		return userService.getUser(id);
	}

	/**
	 * This controller will update and save the new user
	 */
	@PostMapping("/user")
	public UserDTO saveAndUpdate(@RequestBody UserDTO userDTO) throws Exception {
		return userService.saveAndUpdate(userDTO);
	}

	/**
	 * This controller will change the status of user
	 */
	@PutMapping("/user/{id}")
	public void changeStatus(@PathVariable long id) {
		userService.changeStatus(id);
	}

	/**
	 * This controller will provide the list of user
	 */
	@GetMapping("/users")
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}
	
	/**
	 * This controller will provide the list of user according to their roles
	 */
	@GetMapping("/users/{userRole}")
	public List<UserDTO> getUsersByRole(@PathVariable String userRole)
	{
		return userService.getUsersByRole(userRole);
	}
	

}
