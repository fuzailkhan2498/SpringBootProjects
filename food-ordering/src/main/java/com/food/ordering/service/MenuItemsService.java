package com.food.ordering.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.MenuGroup;
import com.food.ordering.domain.MenuItems;
import com.food.ordering.dto.MenuItemsDTO;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.MenuItemsRepository;

import ch.qos.logback.classic.Logger;

@Service
public class MenuItemsService {
	private MenuItemsRepository menuItemsRepo;
	private MenuGroupService menuGroupService;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MenuItemsService(MenuItemsRepository menuItemsRepo, MenuGroupService menuGroupService) {
		super();
		this.menuItemsRepo = menuItemsRepo;
		this.menuGroupService = menuGroupService;
	}

	/**
	 * This method will add menu items details
	 */

	public MenuItemsDTO saveAndUpdate(MenuItemsDTO menuItemsDTO) throws Exception {
		logger.info("To save and Update Menu Items");
		MenuGroup menuGroup = menuGroupService.validateAndGetMenuGroup(menuItemsDTO.getMenuGroupId());
		validateMenuItemsDTO(menuItemsDTO);
		MenuItems menuItems;
		
		if (menuItemsDTO.getId() != 0) {
			menuItems = validateAndUpdate(menuItemsDTO);

		} else {
			menuItems = new MenuItems(menuItemsDTO);
			menuItems.setMenuGroup(menuGroup);
		}
		return new MenuItemsDTO(menuItemsRepo.save(menuItems));

	}

	
	/**
	 * This method will validate the MenuItemsDTO
	 */
	private void validateMenuItemsDTO(MenuItemsDTO menuitemsDTO) throws Exception {
		logger.info("To validate menuItemsDTO object to save.");
		
		if (menuitemsDTO.getRating() < 0 || menuitemsDTO.getRating() > 5) {
			logger.error("Please enter the valid rating range");
			throw new UnprocessableEntityException("Please enter the valid rating range.");
		}

		if (menuitemsDTO.getId() < 0) {
			logger.error("Please enter valid menu item id.");
			throw new UnprocessableEntityException("Please enter valid menu item id.");
		}
		if (StringUtils.isBlank(menuitemsDTO.getName())) {
			logger.error("Please enter valid menu item  name");
			throw new UnprocessableEntityException("Please enter valid menu item name.");

		}
		if (StringUtils.isBlank(menuitemsDTO.getType())) {
			logger.error("Please enter valid menu item type.");
			throw new UnprocessableEntityException("Please enter valid menu item  type.");

		}
		if (StringUtils.isBlank(menuitemsDTO.getDescription())) {
			logger.error("Please enter valid menu type.");
			throw new UnprocessableEntityException("Please enter valid menu type.");

		}
		if(menuitemsDTO.getPrice() <= 0)
		{
			logger.error("Please enter valid price type.");
			throw new UnprocessableEntityException("Please enter valid price.");	
		}
	}

	
	/**
	 * This method will validate the menu items object and return the object of it.
	 */
	public MenuItems validateMenuItems(int id) {
		logger.info("To validate menu item  instance");
		Optional<MenuItems> menuItems = menuItemsRepo.findById(id);
		if (menuItems.isEmpty()) {
			logger.error("Menu item not found");
			throw new NotFoundException("Menu itemnot found");
		}
		logger.info("Returning menu after validating menu existence");
		return menuItems.get();
	}

	
	/**
	 * This method will validate the menu items and update the menu items details.
	 */
	public MenuItems validateAndUpdate(MenuItemsDTO menuItemsDTO) throws Exception {
		logger.info("To validate menu items object to update.");
		MenuItems menuItems = validateMenuItems(menuItemsDTO.getId());
		if(menuItems.getMenuGroup().getId() != menuItemsDTO.getMenuGroupId())
		{
			throw new UnprocessableEntityException("Menu item is not belong to this menu group");
		}
		menuItems.setName(menuItemsDTO.getName());
		menuItems.setType(menuItemsDTO.getType());
		menuItems.setDescription(menuItemsDTO.getDescription());
		menuItems.setRating(menuItemsDTO.getRating());
		menuItems.setPrice(menuItemsDTO.getPrice());
		return menuItems;

	}
	
	/**
	 * This method will delete the menu items.
	 */
	public void deleteMenuItem(int id) {
		validateMenuItems(id);
		menuItemsRepo.deleteById(id);

	}

}
