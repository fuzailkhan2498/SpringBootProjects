package com.food.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
