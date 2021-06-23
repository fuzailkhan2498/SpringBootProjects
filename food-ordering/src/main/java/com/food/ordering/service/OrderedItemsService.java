package com.food.ordering.service;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.MenuItems;
import com.food.ordering.domain.Order;
import com.food.ordering.domain.OrderedItems;
import com.food.ordering.dto.OrderedItemsDTO;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.OrderedItemsRepository;

import ch.qos.logback.classic.Logger;

@Service
public class OrderedItemsService {

	@Autowired
	private OrderedItemsRepository orderedItemsRepository;
	private OrderService orderService;
	private MenuItemsService menuItemsService;

	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public OrderedItemsService(OrderedItemsRepository orderedItemsRepository, OrderService orderService,
			MenuItemsService menuItemsService) {
		this.orderedItemsRepository = orderedItemsRepository;
		this.orderService = orderService;
		this.menuItemsService = menuItemsService;
	}

	/**
	 * This method will save and update the orderitem that the customer is placed.
	 */
	public OrderedItemsDTO saveAndUpdate(OrderedItemsDTO orderedItemsDTO) throws Exception {
		validateOrderItemDTO(orderedItemsDTO);
		MenuItems menuItems = menuItemsService.validateMenuItems(orderedItemsDTO.getMenuItemId());
		Order order = orderService.validateAndGetOrder(orderedItemsDTO.getOrderId());

		if (order.getRestaurant().getId() != menuItems.getMenuGroup().getMenu().getRestaurant().getId()) {
			throw new UnprocessableEntityException("Menu item not belong to this restaurant");
		}

		OrderedItems orderItems;
		if (orderedItemsDTO.getId() != 0) {
			logger.info("TO validate order items to be updated");
			orderItems = validateOrderItems(orderedItemsDTO.getId());

			if (!(orderItems.getMenuItem().getId() == orderedItemsDTO.getMenuItemId())
					|| !(orderItems.getOrder().getId() == orderedItemsDTO.getOrderId())) {

				throw new UnprocessableEntityException("Updation Not Belong to that order item");
			}
			orderItems.setQuantity(orderedItemsDTO.getQuantity());

		} else {
			orderItems = new OrderedItems();
			orderItems.setQuantity(orderedItemsDTO.getQuantity());
			orderItems.setMenuItem(menuItems);
			orderItems.setOrder(order);
		}
		return new OrderedItemsDTO(orderedItemsRepository.save(orderItems));

	}

	
	/**
	 * This method will validate the order item object and return it.
	 */
	public OrderedItems validateOrderItems(long id) {
		logger.info("To validate order item instance");
		Optional<OrderedItems> orderItems = orderedItemsRepository.findById(id);
		if (orderItems.isEmpty()) {

			logger.error("OrderItem not found");
			throw new NotFoundException("OrderItem not found");
		}
		logger.info("Returning OrderItem after validating orderItem existence");
		return orderItems.get();
	}

	
	/**
	 * This method will validate the OrderItemDTO.
	 */

	private void validateOrderItemDTO(OrderedItemsDTO orderedItemsDTO) throws Exception {
		logger.info("To validate OrderItem object to save.");

		if (orderedItemsDTO.getId() < 0) {
			logger.error("Please enter valid order item id.");
			throw new UnprocessableEntityException("Please enter valid order item id.");
		}
		if (orderedItemsDTO.getQuantity() <= 0) {
			logger.error("Please enter valid quantity.");
			throw new UnprocessableEntityException("Please enter quantity.");
		}

	}
	

	/**
	 * This method will delete the order items that customer is added into their
	 * order.
	 */
	public OrderedItemsDTO delete(long id) {
		OrderedItems orderedItems = validateOrderItems(id);
		orderedItemsRepository.deleteById(id);
		return new OrderedItemsDTO(orderedItems);
	}

}
