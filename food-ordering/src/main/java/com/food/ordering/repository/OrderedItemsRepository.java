package com.food.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.ordering.domain.OrderedItems;

public interface OrderedItemsRepository extends JpaRepository<OrderedItems, Long> {

}
