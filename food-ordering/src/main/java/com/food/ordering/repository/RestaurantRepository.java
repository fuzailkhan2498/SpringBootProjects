package com.food.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.domain.Restaurant;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
//	Optional<Restaurant> findByIdAndOrderByOrder_OrderDateDesc(Long id);
	

}
