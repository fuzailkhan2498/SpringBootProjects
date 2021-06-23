package com.food.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.ordering.domain.MenuItems;

public interface MenuItemsRepository extends JpaRepository<MenuItems, Integer> {

}
