package com.food.ordering.service;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Role;
import com.food.ordering.domain.Role.RoleName;
import com.food.ordering.dto.RoleDTO;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.RoleRepository;

import ch.qos.logback.classic.Logger;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	

	/**
	 * This method will add the roles.
	 */
	public RoleDTO saveRole(RoleDTO roleDTO) throws Exception {
		logger.info("To save the role.");
		
		if (!RoleName.getEnum(roleDTO.getName())) {
			throw new UnprocessableEntityException("Invalid Role");
		}
		
		if(roleRepository.findByNameIgnoreCase(roleDTO.getName()).isPresent())
		{
			throw new UnprocessableEntityException(" Role Already Existed");
		}
		Role role = new Role(roleDTO);
		return new RoleDTO(roleRepository.save(role));
	}
	
	
	// This code is for temporary purpose
	@PostConstruct
	public void addRole()
	{
		Role r1 = new Role();
		r1.setName("customer");
		roleRepository.save(r1);
		
		Role r2 = new Role();
		r2.setName("restaurant_owner");
		roleRepository.save(r2);
		
	}

}
