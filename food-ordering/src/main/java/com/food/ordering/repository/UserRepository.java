package com.food.ordering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	//Find user by user name
	User findByEmail(String email);
	//Find List of user according to role
	List<User> findByIsActiveTrueAndRole_NameIgnoreCase(String name);
	List<User> findByIsActiveTrue();

}
