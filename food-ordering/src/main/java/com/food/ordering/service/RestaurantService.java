package com.food.ordering.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.MenuGroup;
import com.food.ordering.domain.MenuItems;
import com.food.ordering.domain.Restaurant;
import com.food.ordering.domain.User;
import com.food.ordering.dto.OrderDTO;
import com.food.ordering.dto.RestaurantDTO;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.RestaurantRepository;
import com.food.ordering.repository.RoleRepository;
import com.food.ordering.repository.UserRepository;

import ch.qos.logback.classic.Logger;

@Service
public class RestaurantService {

	private UserRepository userRepo;
	private RestaurantRepository restaurantRepo;
	private RoleRepository roleRepo;
	private EmailService emailService;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public RestaurantService(UserRepository userRepo, RestaurantRepository restaurantRepo, RoleRepository roleRepo,
			EmailService emailService) {
		super();
		this.userRepo = userRepo;
		this.restaurantRepo = restaurantRepo;
		this.roleRepo = roleRepo;
		this.emailService = emailService;
	}

	/**
	 * This method return the list of Restaurant.
	 */
	public List<RestaurantDTO> getRestaurants() {
		List<RestaurantDTO> restaurants = restaurantRepo.findAll().stream().map(RestaurantDTO::new)
				.collect(Collectors.toList());
		if (restaurants.isEmpty()) {
			throw new NotFoundException("Not restaurant is Found");
		}
		return restaurants;
	}

	/**
	 * This method will add and update the Restaurant.
	 * 
	 * @throws Exception
	 */
	public RestaurantDTO saveAndUpdate(RestaurantDTO restuarantDTO) throws Exception {
		logger.info("To save and update restaurant.");
		Optional<User> user = userRepo.findById(restuarantDTO.getUserId());
		if (user.isEmpty() || !user.get().isActive()) {
			throw new NotFoundException("User Not Found Exception");
		}

		validateRestaurantDTO(restuarantDTO);
		Restaurant restaurant;

		if (restuarantDTO.getId() != 0) {
			restaurant = validateAndUpdate(restuarantDTO);

		} else {
			if (user.get().getRestaurant() != null || user.get().getCustomer() != null) {

				throw new UnprocessableEntityException("User already exist");
			}
			if (!restuarantDTO.isPermissionGranted()) {
				throw new UnprocessableEntityException("Your Request is in processing");
			}
			restaurant = new Restaurant(restuarantDTO);
			user.get().setRole(roleRepo.findByNameIgnoreCase("restaurant_owner")
					.orElseThrow(() -> new NotFoundException("Role Not Found")));
			restaurant.setUser(user.get());
			emailService.sendRestaurantAcknowledgementMail(user.get(), restaurant);
		}
		return new RestaurantDTO(restaurantRepo.save(restaurant));
	}

	/**
	 * This method will validate the RestaurantDTO.
	 */
	private void validateRestaurantDTO(RestaurantDTO restaurantDTO) throws Exception {
		logger.info("To validate RestaurantDTO object to save.");

		if (restaurantDTO.getId() < 0) {
			logger.error("Please enter valid customer id.");
			throw new UnprocessableEntityException("Please enter valid customer id.");
		}

		if (StringUtils.isBlank(restaurantDTO.getName())
				|| !restaurantDTO.getName().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			logger.error("Please enter restaurant name");
			throw new UnprocessableEntityException("Please enter valid restaurant name.");
		}
		if (StringUtils.isBlank(restaurantDTO.getDescription())
				|| !restaurantDTO.getDescription().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			logger.error("Please enter restaurant name");
			throw new UnprocessableEntityException("Please enter valid restaurant name.");
		}
		if (StringUtils.isBlank(restaurantDTO.getType())
				|| !restaurantDTO.getType().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			logger.error("Please enter valid type");
			throw new UnprocessableEntityException("Please enter valid restaurant type");
		}
		if (StringUtils.isBlank(restaurantDTO.getAddress()) || restaurantDTO.getAddress().length() < 20) {
			logger.error("Please enter valid address");
			throw new UnprocessableEntityException("Please enter valid restaurant address");
		}
		if (StringUtils.isBlank(restaurantDTO.getPincode()) || !StringUtils.isNumeric(restaurantDTO.getPincode())) {
			logger.error("Please enter valid pincode");
			throw new UnprocessableEntityException("Please enter valid pincode");
		}

	}

	/**
	 * This method will validate restaurant object and return the same.
	 */
	public Restaurant validateAndGetRestaurant(long restaurantId) {
		Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
		if (restaurant.isEmpty()) {
			logger.error("Restaurant is not found");
			throw new NotFoundException("Restaurant not found");

		}
		logger.info("Returning restaurant after validating the restaurant");
		return restaurant.get();

	}

	/**
	 * This method will validate and update the restaurant.
	 */
	public Restaurant validateAndUpdate(RestaurantDTO restaurantDTO) throws UnprocessableEntityException {
		logger.info("To validate restaurant object to update.");
		Restaurant restaurant = validateAndGetRestaurant(restaurantDTO.getId());

		if (!(restaurant.getUser().getId() == restaurantDTO.getUserId())) {
			throw new UnprocessableEntityException("User not belong to this restaurant");
		}

		restaurant.setDescription(restaurantDTO.getDescription());
		restaurant.setName(restaurantDTO.getName());
		restaurant.setType(restaurantDTO.getType());
		restaurant.setAcceptingOrder(restaurantDTO.isAcceptingOrder());
		restaurant.setAddress(restaurantDTO.getAddress());
		restaurant.setPincode(restaurantDTO.getPincode());
		logger.info("Returning customer after validating restaurantDTO object to update.");
		return restaurant;

	}

	/**
	 * This method will return the restaurant according to their ID.
	 */
	public RestaurantDTO getRestaurant(long id) {
		Restaurant restaurant = validateAndGetRestaurant(id);
		return new RestaurantDTO(restaurant);
	}

	/**
	 * This method will return the list of order that the restaurant has get through
	 * customers.
	 */
	public List<OrderDTO> orders(long restaurantId) {

		Restaurant restaurant = validateAndGetRestaurant(restaurantId);
		return restaurant.getOrder().stream().sorted((s1, s2) -> -s1.getOrderDate().compareTo(s2.getOrderDate()))
				.map(OrderDTO::new).collect(Collectors.toList());
	}

	/**
	 * This method return the list of menu items and price according to restaurant.
	 */
	public Map<String, Map<String, Float>> restaurantMenuItems(long restaurantId) {

		Restaurant restaurant = validateAndGetRestaurant(restaurantId);
		if (restaurant.getMenu() == null) {
			throw new NotFoundException("Menu not found");
		}
		return restaurant.getMenu().getMenuGroup().stream()
				.collect(Collectors.toMap(MenuGroup::getMenuGroupName, (a) -> a.getMenuItems().stream()
						.collect(Collectors.toMap(MenuItems::getName, MenuItems::getPrice, (o, n) -> n))));

	}

}
