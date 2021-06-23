package com.food.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.OrderDTO;
import com.food.ordering.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;

	/**
	 * Add and Update order
	 * 
	 * @throws Exception
	 */
	@PostMapping("/order")
	private void saveOrder(@RequestBody OrderDTO orderDTO) throws Exception {
		orderService.saveOrder(orderDTO);
	}

	/**
	 * Get order respective to the id.
	 * 
	 * @throws Exception
	 */
	@GetMapping("/order/{id}")
	private OrderDTO getOrder(@PathVariable long id) throws Exception {
		return orderService.getOrder(id);
	}

	/**
	 * Delete the order respective to the id.
	 */
	@DeleteMapping("/order/{id}")
	private OrderDTO deleteOrder(@PathVariable long id) {
		return orderService.deleteOrder(id);
	}


	/** This handler is used for placing the order.
	 * @param orderId
	 * @throws Exception
	 */
	@PostMapping("order/{orderId}")
	private void placeOrder(@PathVariable long orderId) throws Exception {
		orderService.placeOrder(orderId);
	}

}
