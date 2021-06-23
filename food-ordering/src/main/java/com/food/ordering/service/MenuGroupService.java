package com.food.ordering.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Menu;
import com.food.ordering.domain.MenuGroup;
import com.food.ordering.dto.MenuGroupDTO;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.MenuGroupRepository;

import ch.qos.logback.classic.Logger;

@Service
public class MenuGroupService {

	private MenuGroupRepository menuGroupRepo;
	private MenuService menuService;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MenuGroupService(MenuGroupRepository menuGroupRepo, MenuService menuService) {
		this.menuGroupRepo = menuGroupRepo;
		this.menuService = menuService;
	}

	/**
	 * This method will add menu group
	 * 
	 * @throws Exception
	 */
	public MenuGroupDTO saveAndUpdate(MenuGroupDTO menugroupDTO) throws Exception {
		logger.info("To save and Update Menu Group");
		Menu menu = menuService.validateAndGetMenu(menugroupDTO.getMenuId());
		validateMenuGroupDTO(menugroupDTO);
		MenuGroup menuGroup;

		if (menugroupDTO.getId() != 0) {
			menuGroup = validateAndUpdate(menugroupDTO);
		} else {
			menuGroup = new MenuGroup(menugroupDTO);
			menuGroup.setMenu(menu);
		}
		return new MenuGroupDTO(menuGroupRepo.save(menuGroup));
	}
	

	/**
	 * This method will validate the and update the menu group.
	 */

	private MenuGroup validateAndUpdate(MenuGroupDTO menugroupDTO) throws Exception {
		logger.info("Returning the menu group after validationg menu group  existence");
		MenuGroup menuGroup = validateAndGetMenuGroup(menugroupDTO.getId());
		if (menuGroup.getMenu().getId() != menugroupDTO.getMenuId()) {
			throw new UnprocessableEntityException("Menu is not belong to this menu group");
		}
		menuGroup.setMenuGroupName(menugroupDTO.getMenuGroupName());
		menuGroup.setMenuGroupType(menugroupDTO.getMenuGroupType());
		return menuGroup;
	}
	

	/**
	 * This method will validate the MenuGroupDTO.
	 */

	private void validateMenuGroupDTO(MenuGroupDTO menuGroupDTO) throws Exception {
		logger.info("To validate MenuGroupDTO object to save.");

		if (menuGroupDTO.getId() < 0) {
			logger.error("Please enter valid menu group id.");
			throw new UnprocessableEntityException("Please enter valid menu group  id.");
		}
		if (StringUtils.isBlank(menuGroupDTO.getMenuGroupName())) {
			logger.error("Please enter valid menu group name");
			throw new UnprocessableEntityException("Please enter valid menu group name.");

		}
		if (StringUtils.isBlank(menuGroupDTO.getMenuGroupType())) {
			logger.error("Please enter valid menu group type.");
			throw new UnprocessableEntityException("Please enter valid menu group type.");

		}
	}

	
	/**
	 * This method will validate and return the menu group object.
	 */

	public MenuGroup validateAndGetMenuGroup(int id) {
		logger.info("TO validate the menu group instance");
		Optional<MenuGroup> menuGroup = menuGroupRepo.findById(id);
		if (menuGroup.isEmpty()) {
			logger.error("Menu Group not found");
			throw new NotFoundException("Menu Group not found");
		}
		logger.info("Returning menu group after validating customer existence");
		return menuGroup.get();
	}
	

	/**
	 * This method will delete the menu group.
	 */

	public MenuGroupDTO deleteMenuGroup(int id) {
		MenuGroup menuGroup = validateAndGetMenuGroup(id);
		menuGroupRepo.deleteById(id);
		return new MenuGroupDTO(menuGroup);
	}

	
	/**
	 * This method will return the menu group according to their id.
	 */

	public MenuGroupDTO getMenuGroup(int id) {
		MenuGroup menuGroup = validateAndGetMenuGroup(id);
		return new MenuGroupDTO(menuGroup);
	}

}
