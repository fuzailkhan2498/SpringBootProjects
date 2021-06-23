package com.food.ordering.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Order;
import com.food.ordering.domain.OrderStatus;
import com.food.ordering.dto.OrderStatusDTO;
import com.food.ordering.enums.OrderStatusType;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.OrderRepository;

@Service
public class OrderStatusService {

	private OrderService orderService;
	private OrderRepository orderRepo;

	@Autowired
	public OrderStatusService(OrderService orderService, OrderRepository orderRepo) {
		super();
		this.orderService = orderService;
		this.orderRepo = orderRepo;
	}


	/**
	 * This method will change the order status though the restaurant owner.
	 */
	public void changeOrderStatus(OrderStatusDTO orderStatusDTO) throws Exception {
	
		Order order = orderService.validateAndGetOrder(orderStatusDTO.getOrderId());
		if(order.getOrderedItems().isEmpty())
		{
			throw new UnprocessableEntityException("Add menu items to the order");
		}
		if(order.getRestaurant().getId()!= orderStatusDTO.getRestaurantId())
		{
			throw new UnprocessableEntityException("Only Valid Valid Restaurant Owner Change the Status");
		}
		OrderStatus orderStatus = order.getOrderStatus();
		order.setPaid(orderStatusDTO.isPaid());
		orderStatus.setPaid(orderStatusDTO.isPaid());
		orderStatus.setStatus(OrderStatusType.getEnum(orderStatusDTO.getOrderStatus()));
		orderRepo.save(order);

	}

}
