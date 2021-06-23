package com.food.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.OrderStatusDTO;
import com.food.ordering.service.OrderStatusService;

@RestController
public class OrderStatusController {
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	/**
	 * Change the status of order 
	 * @throws Exception 
	 */
	@PostMapping("/order/change-status")
	void changeOrderStatus(@RequestBody OrderStatusDTO orderStatus) throws Exception
	{
		 orderStatusService.changeOrderStatus(orderStatus);
	}

}
