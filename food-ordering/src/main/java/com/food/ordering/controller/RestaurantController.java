package com.food.ordering.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.OrderDTO;
import com.food.ordering.dto.RestaurantDTO;
import com.food.ordering.service.RestaurantService;

@RestController
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;

	/**
	 * Return the list of Restaurant
	 */
	@GetMapping("/restaurants")
	public List<RestaurantDTO> getRestaurants() {
		return restaurantService.getRestaurants();
	}
	
	
	/**
	 * Return the list of particular restaurant associated with id.
	 */
	@GetMapping("/restaurant/{id}")
	public RestaurantDTO getRestaurant(@PathVariable long id)
	{
		return restaurantService.getRestaurant(id);
	}
	

	/**
	 * Add and Update the restaurant
	 * @throws Exception 
	 */
	@PostMapping("/restaurant")
	public RestaurantDTO saveAndUpdate(@RequestBody RestaurantDTO restuarant) throws Exception {
		return restaurantService.saveAndUpdate(restuarant);
	}

	
	
	/**
	 * This method will return the restaurant name and menu details and menu items
	 */
	@GetMapping("/restaurant/{restaurantId}/menu")
	public Map<String, Map<String, Float>> restaurantMenu(@PathVariable long restaurantId)
	{
		return restaurantService.restaurantMenuItems(restaurantId);
	}
	
	
	/**
	 * This method will return the restaurant order according to order date
	 */
	@GetMapping("restaurant/{restaurantId}/order")
	public List<OrderDTO> orders(@PathVariable long restaurantId)
	{
		return restaurantService.orders(restaurantId);
	}

	

}
