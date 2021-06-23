package com.food.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
