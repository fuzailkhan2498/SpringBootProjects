package com.food.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
	

}
