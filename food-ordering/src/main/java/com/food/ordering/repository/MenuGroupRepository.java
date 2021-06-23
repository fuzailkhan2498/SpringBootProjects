package com.food.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.domain.MenuGroup;

@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Integer> {

}
