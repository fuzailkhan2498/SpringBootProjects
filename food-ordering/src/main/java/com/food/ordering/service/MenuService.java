package com.food.ordering.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Menu;
import com.food.ordering.domain.Restaurant;
import com.food.ordering.dto.MenuDTO;
import com.food.ordering.dto.MenuGroupDTO;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.MenuRepository;

import ch.qos.logback.classic.Logger;

@Service
public class MenuService {

	private MenuRepository menuRepo;
	private RestaurantService restaurantService;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MenuService(MenuRepository menuRepo, RestaurantService restaurantService) {
		super();
		this.menuRepo = menuRepo;
		this.restaurantService = restaurantService;
	}

	/**
	 * This method will save and update the details of MENU.
	 */
	public MenuDTO saveAndUpdate(MenuDTO menuDTO) throws Exception {
		logger.info("To save and Update Menu");
		Restaurant restaurant = restaurantService.validateAndGetRestaurant(menuDTO.getRestaurantId());
		validateMenuDTO(menuDTO);
		Menu menu;
		if (menuDTO.getId() != 0) {
			menu = validateAndUpdate(menuDTO);

		} else {

			if (restaurant.getMenu() != null) {
				logger.info("Restaurant already have a menu");
				throw new UnprocessableEntityException("Restaurant already have menu");
			}
			menu = new Menu(menuDTO);
			menu.setRestaurant(restaurant);
		}
		return new MenuDTO(menuRepo.save(menu));

	}

	/**
	 * This method will validate the MenuDTO object.
	 */
	private void validateMenuDTO(MenuDTO menuDTO) throws Exception {
		logger.info("To validate menuDTO object to save.");

		if (menuDTO.getId() < 0) {
			logger.error("Please enter valid menu id.");
			throw new UnprocessableEntityException("Please enter valid menu id.");
		}
		if (StringUtils.isBlank(menuDTO.getMenuName())) {
			logger.error("Please enter valid menu name");
			throw new UnprocessableEntityException("Please enter valid menu name.");

		}
		if (StringUtils.isBlank(menuDTO.getMenuType())) {
			logger.error("Please enter valid menu type.");
			throw new UnprocessableEntityException("Please enter valid menu type.");

		}
	}

	/**
	 * This method will validate the menu object and return it.
	 */

	public Menu validateAndGetMenu(int id) {
		logger.info("To validate menu instance");
		Optional<Menu> menu = menuRepo.findById(id);
		if (menu.isEmpty()) {
			logger.error("Menu not found");
			throw new NotFoundException("Menu not found");
		}
		logger.info("Returning menu after validating menu existence");
		return menu.get();
	}

	/**
	 * This method will validate and update the menu details.
	 */
	public Menu validateAndUpdate(MenuDTO menuDTO) throws Exception {
		logger.info("To validate menu object to update.");

		Menu menu = validateAndGetMenu(menuDTO.getId());
		if (menu.getRestaurant().getId() != menuDTO.getRestaurantId()) {
			throw new UnprocessableEntityException("Menu not belong to this restaurant");
		}
		menu.setMenuName(menuDTO.getMenuName());
		menu.setMenuType(menuDTO.getMenuType());
		return menu;

	}

	/**
	 * This method will delete the Menu.
	 */

	public MenuDTO deleteMenu(int id) {
		Menu menu = validateAndGetMenu(id);
		menuRepo.deleteById(id);
		return new MenuDTO(menu);
	}

	/**
	 * This method return the Menu object according to the Menu id.
	 */

	public MenuDTO getMenu(int id) {
		Menu menu = validateAndGetMenu(id);
		return new MenuDTO(menu);
	}

	/**
	 * This method return the MenuGroup of a particular restaurant according to
	 * their Restaurant id.
	 */
	public List<MenuGroupDTO> getMenuGroups(int menuId) {
		Menu menu = validateAndGetMenu(menuId);
		List<MenuGroupDTO> menuGroups = menu.getMenuGroup().stream().map(MenuGroupDTO::new)
				.collect(Collectors.toList());
		if (menuGroups.isEmpty()) {
			throw new NotFoundException("Menu Group is not found for this restaurant");
		}
		return menuGroups;
	}

}
