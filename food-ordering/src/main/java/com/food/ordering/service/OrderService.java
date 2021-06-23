package com.food.ordering.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Customer;
import com.food.ordering.domain.Order;
import com.food.ordering.domain.OrderStatus;
import com.food.ordering.domain.OrderedItems;
import com.food.ordering.domain.Restaurant;
import com.food.ordering.dto.OrderDTO;
import com.food.ordering.enums.OrderStatusType;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.OrderRepository;

import ch.qos.logback.classic.Logger;

@Service
public class OrderService {

	@Autowired
	private EmailService emailService;
	private OrderRepository orderRepo;
	private CustomerService customerService;
	private RestaurantService restaurantService;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public OrderService(OrderRepository orderRepo, CustomerService customerService,
			RestaurantService restaurantService) {
		this.orderRepo = orderRepo;
		this.customerService = customerService;
		this.restaurantService = restaurantService;
	}

	/**
	 * This method add order items
	 * 
	 * @throws Exception
	 */
	public void saveOrder(OrderDTO orderDTO) throws Exception {
		validateOrderDTO(orderDTO);
		Customer customer = customerService.validateAndGetCustomer(orderDTO.getCustomerId());
		Restaurant restaurant = restaurantService.validateAndGetRestaurant(orderDTO.getRestaurantId());
		
		if (customer.getAddress().isEmpty()) {
			throw new UnprocessableEntityException("Please add the Address");
		}
		
		Order order = new Order(orderDTO);
		order.setRestaurant(restaurant);
		order.setCustomer(customer);
		order.setOrderDate(LocalDateTime.now());
		order.setOrderPlaced(false);
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrder(order);
		orderStatus.setStatus(OrderStatusType.ORDERED);
		order.setOrderStatus(orderStatus);

		orderRepo.save(order);
		//return new OrderDTO(order, true);

	}

	/**
	 * This method will validate the OrderDTO.
	 */
	private void validateOrderDTO(OrderDTO orderDTO) throws Exception {
		logger.info("To validate OrderDTO object to save.");

		if (orderDTO.getId() < 0) {
			logger.error("Please enter valid order id.");
			throw new UnprocessableEntityException("Please enter valid order id.");
		}
		if (orderDTO.getPrice() < 0) {
			logger.error("Please enter valid price. ");
			throw new UnprocessableEntityException("Please enter valid price for order.");
		}

	}

	/**
	 * This method will validate the order object and return the same.
	 */
	public Order validateAndGetOrder(long orderId) {
		Optional<Order> order = orderRepo.findById(orderId);
		if (order.isEmpty()) {
			logger.error("order is not found");
			throw new NotFoundException("order not found");

		}
		logger.info("Returning order details after validating the order");
		return order.get();

	}

	/**
	 * This method will get the order details according to their ID.
	 */
	public OrderDTO getOrder(long id) throws Exception {
		Order order = validateAndGetOrder(id);
		if (order.getOrderedItems() == null) {
			throw new UnprocessableEntityException("Add order details to the order");
		}
		return new OrderDTO(order);
	}

	/**
	 * This method will cancel and delete the order.
	 */
	public OrderDTO deleteOrder(long id) {
		Order order = validateAndGetOrder(id);
		orderRepo.deleteById(id);
		return new OrderDTO(order);
	}

	/**
	 * This method will placed the order.
	 */
	public void placeOrder(long orderId) throws Exception
	{
		float totalPrice = 0;
		Order order = validateAndGetOrder(orderId);
		if(order.isOrderPlaced())
		{
			throw new UnprocessableEntityException("Order is already placed");
		}
		Iterator<OrderedItems> itr = order.getOrderedItems().iterator();
		while (itr.hasNext()) {
			OrderedItems orderedItems = (OrderedItems) itr.next();
			int quantity = orderedItems.getQuantity();
			float price = orderedItems.getMenuItem().getPrice();
			totalPrice = quantity * price + totalPrice;

		}
		order.setPrice(totalPrice);
		order.setOrderPlaced(true);
		orderRepo.save(order);
		
		Executors.newSingleThreadExecutor().submit( ()-> {
			try {
			emailService.orderConfirmationMail(order);
		} catch (Exception e) 
			{
			e.printStackTrace();
		}});
		
		
	}

}
