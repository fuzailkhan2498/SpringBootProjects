package com.food.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.OrderedItemsDTO;
import com.food.ordering.service.OrderedItemsService;

@RestController
public class OrderedItemsController {
	@Autowired
	private OrderedItemsService orderedItemsService;

	/**
	 * Add and Update ordered items
	 * @throws Exception
	 */
	@PostMapping("/ordered-items")
	public OrderedItemsDTO saveAndUpdate(@RequestBody OrderedItemsDTO orderedItemsDTO) throws Exception {
		return orderedItemsService.saveAndUpdate(orderedItemsDTO);
	}
	
	/**
	 * Delete ordered items
	 * @throws Exception
	 */
	@DeleteMapping("/ordered-items/{id}")
	public OrderedItemsDTO delete(@PathVariable long id)
	{
		return orderedItemsService.delete(id);
	}

}
