package com.food.ordering.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Customer;
import com.food.ordering.domain.User;
import com.food.ordering.dto.AddressDTO;
import com.food.ordering.dto.CustomerDTO;
import com.food.ordering.dto.OrderDTO;
import com.food.ordering.enums.Gender;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.CustomerRepository;
import com.food.ordering.repository.RoleRepository;
import com.food.ordering.repository.UserRepository;

import ch.qos.logback.classic.Logger;

@Service
public class CustomerService {

	private EmailService emailService;
	private UserRepository userRepo;
	private CustomerRepository customerRepo;
	private RoleRepository roleRepo;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public CustomerService(EmailService emailService, UserRepository userRepo, CustomerRepository customerRepo,
			RoleRepository roleRepo) {
		this.emailService = emailService;
		this.userRepo = userRepo;
		this.customerRepo = customerRepo;
		this.roleRepo = roleRepo;
	}

	/**
	 * This method will return the list of element
	 */
	public List<CustomerDTO> getAllCustomers() {
		logger.info("To get the list of customers.");

		List<CustomerDTO> customers = customerRepo.findByIsActiveTrueOrderByFirstNameAsc().stream()
				.map(CustomerDTO::new).collect(Collectors.toList());

		if (customers.isEmpty()) {
			throw new NotFoundException("No customer is Present");
		}
		return customers;
	}

	/**
	 * This method will delete a particular customer regarding their id.
	 * 
	 * @throws Exception
	 */
	public void deleteCustomerDetails(Long id) throws Exception {
		Customer customer = validateAndGetCustomer(id);
		if (!customer.isActive()) {
			throw new UnprocessableEntityException("Customer is already deactivated");
		}
		customer.setActive(false);
		customerRepo.save(customer);
		logger.info("customer is deactivated");

	}

	/**
	 * This method will return the Customer Order Details
	 * 
	 * @throws Exception
	 */
	public List<OrderDTO> customerOrders(Long id) throws Exception {
		Customer customer = validateAndGetCustomer(id);
		if (customer.getOrder().isEmpty()) {
			throw new UnprocessableEntityException("Not Yet any order found");
		}
		return customer.getOrder().stream().map(OrderDTO::new).collect(Collectors.toList());
	}

	/**
	 * This method will return the Customer Details
	 * 
	 * @throws Exception
	 */
	public CustomerDTO getCustomer(Long id) throws Exception {
		Customer customer = validateAndGetCustomer(id);
		if (!customer.isActive()) {
			throw new UnprocessableEntityException("Customer is deactivated");
		}
		return new CustomerDTO(customer);
	}

	/**
	 * This method will update and save new user the details of the user
	 */

	public CustomerDTO saveAndUpdate(CustomerDTO customerDTO) throws Exception {
		logger.info("To save and update customer.");
		Optional<User> user = userRepo.findById(customerDTO.getUserId());
		if (user.isEmpty() || !user.get().isActive()) {
			throw new NotFoundException("User Not Found");
		}

		validateCustomerDTO(customerDTO);
		Customer customer;

		if (customerDTO.getId() != 0) {
			customer = validateAndUpdate(customerDTO);
		} else {
			if (user.get().getCustomer() != null || user.get().getRestaurant() != null) {

				throw new UnprocessableEntityException("User already exist");
			}
			// Adding the new Customer Details
			customer = new Customer(customerDTO);
			customer.setActive(true);
			customer.setCreatedOn(LocalDate.now());
			user.get().setRole(roleRepo.findByNameIgnoreCase("CUSTOMER")
					.orElseThrow(() -> new NotFoundException("Role Not Found")));
			customer.setUser(user.get());
			
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.submit(()-> {
				try {
				emailService.sendCustomerAcknowledgementMail(user.get(),customerDTO.getFirstName() + " " + customerDTO.getLastName());
				}
				catch (Exception e) {
				e.printStackTrace();
				}
				finally {
				executorService.shutdown();
				}
			});


		}

		return new CustomerDTO(customerRepo.save(customer));

	}

	/**
	 * This method will update the customer status
	 * 
	 */
	public void changeStatus(long customerId) {
		Customer customer = validateAndGetCustomer(customerId);

		if (customer.isActive()) {
			logger.info("customer is deactivated");
			customer.setActive(false);
		} else {
			logger.info("customer is activated");
			customer.setActive(true);
		}
		customerRepo.save(customer);

	}

	/**
	 * This method will return the customer addresses
	 * 
	 */
	public List<AddressDTO> customerAddresses(long id) {
		Customer customer = validateAndGetCustomer(id);
		if (!customer.isActive()) {
			throw new NotFoundException("Customer is deactivated");
		}
		if (customer.getAddress().isEmpty()) {
			throw new NotFoundException("No address Found");
		}
		return customer.getAddress().stream().filter(a -> a.isActive()).map(AddressDTO::new)
				.collect(Collectors.toList());
	}

	/**
	 * This method will validate the customerDTO object fields
	 * 
	 */
	private void validateCustomerDTO(CustomerDTO customerDTO) throws Exception {
		logger.info("To validate CustomerDTO object to save.");

		if (customerDTO.getId() < 0) {
			logger.error("Please enter valid customer id.");
			throw new UnprocessableEntityException("Please enter valid customer id.");
		}
		if (StringUtils.isBlank(customerDTO.getFirstName())
				|| !customerDTO.getFirstName().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			logger.error("Please enter valid first name.");
			throw new UnprocessableEntityException("Please enter valid first name.");
		}
		if (StringUtils.isBlank(customerDTO.getLastName())
				|| !customerDTO.getLastName().matches("[A-Za-z]+([ ][A-Za-z]+)*")) {
			logger.error("Please enter valid last name.");
			throw new UnprocessableEntityException("Please enter valid last name.");
		}

		if (!StringUtils.isNumeric(customerDTO.getMobileNo()) || customerDTO.getMobileNo().length() != 10) {
			logger.error("Please enter valid mobile no.");
			throw new UnprocessableEntityException("Please enter valid mobile number ");
		}

	}

	/**
	 * This method will validate the customer to check it is present or not.
	 * 
	 */

	public Customer validateAndGetCustomer(long customerId) {
		logger.info("To validate customer existence.");
		Optional<Customer> customer = customerRepo.findById(customerId);
		if (customer.isEmpty()) {
			logger.error("Customer not found");
			throw new NotFoundException("Customer not found");
		}
		logger.info("Returning customer after validating customer existence.");
		return customer.get();
	}

	/**
	 * This method will update the customer details
	 * 
	 */
	public Customer validateAndUpdate(CustomerDTO customerDTO) throws Exception {
		logger.info("To validate CustomerDTO object to update.");
		Customer customer = validateAndGetCustomer(customerDTO.getId());

		if (!(customer.getUser().getId() == customerDTO.getUserId())) {
			throw new UnprocessableEntityException("User not belong to this customer");
		}

		if (!customer.isActive()) {
			throw new UnprocessableEntityException("This customer  is deactivated. Please activate.");
		}

		if (customerDTO.getFirstName() != null && customerDTO.getLastName() != null) {
			customer.setFirstName(customerDTO.getFirstName());
			customer.setLastName(customerDTO.getLastName());
		}

		if (customerDTO.getMobileNo() != null) {
			customer.setMobileNo(customerDTO.getMobileNo());
		}

		customer.setUpdatedOn(LocalDate.now());
		customer.setActive(true);
		customer.setGender(Gender.getEnum(customerDTO.getGender()));
		logger.info("Returning customer after validating CustomerDTO object to update.");
		return customer;
	}

	/**This method will send mail to all the active customer
	 * @throws UnprocessableEntityException
	 */
	public void sendOfferCouponMail() throws UnprocessableEntityException {
		logger.info("Sending offer mail to all customers");
		List<Customer> customers = customerRepo.findAllByIsActiveTrue();
		if (customers.isEmpty()) {
			throw new UnprocessableEntityException("No active user is found");
		}

		List<String> customerEmailIds = customers.stream().map(Customer::getUser).map(User::getEmail)
				.collect(Collectors.toList());
		final ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		customerEmailIds.forEach(email -> executorService.submit(() -> {
			try {
				emailService.sendMail(email, "khanfuzail2498@gmail.com", "Offer Coupon code", "Dear " + email
						+ "!<br><br>"
						+ "You have receiveed a coupon code <b> YUMMY500 </b> worth Rs 500.<br>You can place your order to get the benefit of this offer.<br><br><br>Foood Adda Team");
				System.out.println(Thread.currentThread().getName());
				} 
			catch (Exception e) {
				e.printStackTrace();
				}
			finally {
				executorService.shutdown();
				}
		}));
		logger.info("Returning after sending mail to all customers");

	}
	

}
