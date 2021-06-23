package com.food.ordering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.ordering.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public List<Customer> findByIsActiveTrueOrderByFirstNameAsc();

	public List<Customer> findAllByIsActiveTrue();

}
