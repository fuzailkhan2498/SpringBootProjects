package com.food.ordering.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Address;
import com.food.ordering.domain.Customer;
import com.food.ordering.dto.AddressDTO;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.AddressRepository;

import ch.qos.logback.classic.Logger;

@Service
public class AddressService {
	private AddressRepository addressRepo;
	private CustomerService customerService;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public AddressService(AddressRepository addressRepo, CustomerService customerService) {
		super();
		this.addressRepo = addressRepo;
		this.customerService = customerService;
	}

	/**
	 * This method will delete the address of particular customer
	 * 
	 * @throws Exception
	 */
	public AddressDTO deleteAddressDetails(Long id) throws Exception {
		Address address = validateAndGetAddress(id);
		if (!address.isActive()) {
			throw new UnprocessableEntityException("address is alredy removed");
		}
		// address.setActive(false);
		addressRepo.deleteById(id);
		logger.info("address is deleted");
		return new AddressDTO(address);

	}

	/**
	 * This method will save new address and update the existing address according
	 * to customer id.
	 * 
	 * @throws Exception
	 */
	public AddressDTO saveAndUpdate(AddressDTO addressDTO) throws Exception {
		validateAddressDTO(addressDTO);
		Customer customer = customerService.validateAndGetCustomer(addressDTO.getCustomerId());
		Address address;

		if (addressDTO.getAddressId() != 0) {
			address = validateUpdation(addressDTO);
		} else {

			address = new Address(addressDTO);
			address.setCustomer(customer);
			address.setActive(true);
		}
		return new AddressDTO(addressRepo.save(address));
	}

	/**
	 * This method will validate the address if it fine that it will return the
	 * address object
	 */
	public Address validateAndGetAddress(long addressId) {
		logger.info("To validate address existence.");
		Optional<Address> address = addressRepo.findById(addressId);
		if (address.isEmpty()) {

			logger.error("Address not found");
			throw new NotFoundException("address not found");
		}

		logger.info("Returning address after validating address existence.");
		return address.get();
	}

	/**
	 * This method will validate the AddressDTO object
	 */

	private void validateAddressDTO(AddressDTO addressDTO) throws Exception {
		if (addressDTO.getAddressId() < 0) {
			throw new UnprocessableEntityException("Please enter the address id");
		}
		if (StringUtils.isBlank(addressDTO.getCity()) || !addressDTO.getCity().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			throw new UnprocessableEntityException("Please enter the valid city");
		}
		if (StringUtils.isBlank(addressDTO.getCountry())
				|| !addressDTO.getCountry().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			throw new UnprocessableEntityException("Please enter the valid country");
		}

		if (StringUtils.isBlank(addressDTO.getState()) || !addressDTO.getState().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			throw new UnprocessableEntityException("Please enter the valid state");
		}
		if (!(addressDTO.getPincode() > 100000) || !(addressDTO.getPincode() < 999999)) {
			throw new UnprocessableEntityException("Please enter the valid pincode");
		}

	}

	/**
	 * This method will validate and update the details of existing address.
	 */

	private Address validateUpdation(AddressDTO addressDTO) throws Exception {
		Address address = validateAndGetAddress(addressDTO.getAddressId());
		if (!address.isActive()) {
			throw new UnprocessableEntityException("customer address is deleted");
		}
		if (address.getCustomer().getId() != addressDTO.getCustomerId()) {
			throw new UnprocessableEntityException("Address not belong to that customer");
		}
		address.setCity(addressDTO.getCity());
		address.setCountry(addressDTO.getCountry());
		address.setLandmark(addressDTO.getLandmark());
		address.setPincode(addressDTO.getPincode());
		address.setState(addressDTO.getState());
		return address;

	}

	/**
	 * This method will change the status of address from active to in-active
	 */
	public void changeStatus(long id) {
		Address address = validateAndGetAddress(id);
		if (address.isActive()) {
			logger.info("address is remove");
			address.setActive(false);

		} else {
			logger.info("address is set to active");
			address.setActive(true);

		}
		addressRepo.save(address);
	}

	/**
	 * This method will return the address regerding to their id.
	 */
	public AddressDTO getAddress(long id) throws Exception {
		Address address = validateAndGetAddress(id);
		if (!address.isActive()) {
			throw new UnprocessableEntityException("Adresss not found");
		}
		return new AddressDTO(address);

	}

}
