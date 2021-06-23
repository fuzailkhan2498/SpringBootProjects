package com.food.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.AddressDTO;
import com.food.ordering.service.AddressService;

@RestController
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	/**
	 * This controller will save new address and update the existing address according to customer id
	 * @throws Exception 
	 */
	@PostMapping("/address")
	public AddressDTO saveAndUpdate(@RequestBody AddressDTO address) throws Exception
	{
		return addressService.saveAndUpdate(address);
	}

	
	/**
	 * This controller will delete  address and  according to customer id.
	 * @throws Exception 
	 */
	@DeleteMapping("/address/{id}")
	public AddressDTO deleteAddress(@PathVariable Long id) throws Exception
	{
		return addressService.deleteAddressDetails(id);
	}
	
	
	
	/**
	 * This controller will change the status of customer.
	 * @throws Exception 
	 */
	@PutMapping("/address/{id}")
	public void changeStatus(@PathVariable long id)
	{
		addressService.changeStatus(id);
	}
	

	/**
	 * This controller will change the status of customer.
	 * @throws Exception 
	 */
	@GetMapping("/address/{id}")
	public AddressDTO getAddress(@PathVariable long id) throws Exception
	{
		return addressService.getAddress(id);
	}

	
	

}
