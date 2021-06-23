package com.food.ordering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.food.ordering.dto.AddressDTO;
import com.food.ordering.dto.CustomerDTO;
import com.food.ordering.dto.OrderDTO;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Return the list of Active customer present in DB.
	 */
	@GetMapping("/customers")
	public List<CustomerDTO> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	/**
	 * This controller will provide the detail of a particular customer
	 * @throws Exception
	 */
	@GetMapping("/customer/{id}")
	public CustomerDTO getCustomer(@PathVariable Long id) throws Exception {
		return customerService.getCustomer(id);
	}

	/**
	 * This controller will provide the customer orders details that he placed.
	 * 
	 * @throws Exception
	 */
	@GetMapping("/customer/{customerId}/order")
	public List<OrderDTO> customerOrders(@PathVariable Long customerId) throws Exception {
		return customerService.customerOrders(customerId);
	}

	/**
	 * This controller will add new customer and update the detail of existing
	 * customer having valid credentials.
	 */
	@PostMapping("/customer")
	public CustomerDTO saveAndUpdate(@RequestBody CustomerDTO customer) throws Exception {
		return customerService.saveAndUpdate(customer);
	}

	/**
	 * This controller will delete the particular customer
	 * 
	 * @throws Exception
	 */
	@DeleteMapping("/customer/{id}")
	public void delete(@PathVariable Long id) throws Exception {
		customerService.deleteCustomerDetails(id);
	}

	/**
	 * This controller will give the particular customer addresses
	 */
	@GetMapping("/customer/{customerId}/address")
	public List<AddressDTO> getCustomeraddresses(@PathVariable long customerId) {
		return customerService.customerAddresses(customerId);
	}

	/**
	 * This controller will change the status of customer
	 */
	@PutMapping("customer/{customerId}")
	void changeStatus(@PathVariable("customerId") long customerId) {
		customerService.changeStatus(customerId);
	}
	
	@PostMapping("/send-offer-mail")
	public void sendOfferCouponMail() throws UnprocessableEntityException {
		customerService.sendOfferCouponMail();
		
	}


}
