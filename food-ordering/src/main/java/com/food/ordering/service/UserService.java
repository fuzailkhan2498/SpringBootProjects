package com.food.ordering.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ordering.domain.Role;
import com.food.ordering.domain.Role.RoleName;
import com.food.ordering.domain.User;
import com.food.ordering.dto.UserDTO;
import com.food.ordering.exception.NotFoundException;
import com.food.ordering.exception.UnprocessableEntityException;
import com.food.ordering.repository.UserRepository;
import com.food.ordering.util.CommonUtils;

import ch.qos.logback.classic.Logger;

@Service
public class UserService {

	private UserRepository userRepository;
	private EmailService emailService;

	@Autowired
	public UserService(UserRepository userRepository, EmailService emailService) {
		super();
		this.userRepository = userRepository;
		this.emailService = emailService;
	}

	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	/**
	 * This method will return the user Details according to their role
	 */
	public List<UserDTO> getUsersByRole(String userRole) {
		List<UserDTO> users = userRepository.findByIsActiveTrueAndRole_NameIgnoreCase(userRole).stream()
				.map(UserDTO::new).collect(Collectors.toList());
		if (users.isEmpty()) {
			throw new NotFoundException("No active user is present regarding this role");
		}
		return users;

	}

	/**
	 * This method will return the user Details
	 * 
	 * @throws Exception
	 */
	public UserDTO getUser(Long id) throws Exception {
		User user = validateAndGetUser(id);
		if (!user.isActive()) {
			throw new UnprocessableEntityException("user is deactivated");
		}
		return new UserDTO(user);
	}

	/**
	 * This method will update and save new user the details of the user
	 */

	public UserDTO saveAndUpdate(UserDTO userDTO) throws Exception {
		logger.info("To save and update user.");
		validateUserDTO(userDTO);
		User user;

		if (userDTO.getId() != 0) {
			if (!validateAndGetUser(userDTO.getId()).getEmail().equals(userDTO.getEmail())) {
				logger.error("Email is already existing.");
				throw new UnprocessableEntityException("Email is already existing.");
			}
			user = validateAndUpdate(userDTO);
			emailService.sendPasswordResetNotification(userDTO, userDTO.getPassword());

		} else {
			if (userRepository.findByEmail(userDTO.getEmail()) != null) {
				logger.error("Please enter valid email id. Email is already exist.");
				throw new UnprocessableEntityException("Please enter valid email id. Email is already exist.");
			}
			user = new User(userDTO);
//			sendEmailService.sendEmail(userDTO.getEmail(), "Dear User !!\n" + "Welcome to FOOD ADDA.\nYour account is created succesfully", "Account Created to FOOD ADDA");
		}
		return new UserDTO(userRepository.save(user));

	}

	/**
	 * This method will validate the role
	 */

	public void validateRole(List<Role> roles) throws Exception {
		for (Role role : roles) {
			if (!RoleName.getEnum(role.getName())) {
				throw new UnprocessableEntityException("Enter valid user role");
			}
		}

	}

	/**
	 * This method will update the customer status
	 * 
	 */
	public void changeStatus(long userId) {
		User user = validateAndGetUser(userId);

		if (user.isActive()) {
			logger.info("user is deactivated");
			user.setActive(false);

		} else {
			logger.info("user is activated");
			user.setActive(true);
		}
		userRepository.save(user);

	}

	/**
	 * This method will validate the customerDTO object fields
	 * 
	 */
	private void validateUserDTO(UserDTO userDTO) throws Exception {
		logger.info("To validate UserDTO object to save.");

		if (userDTO.getId() < 0) {
			logger.error("Please enter valid user id.");
			throw new UnprocessableEntityException("Please enter valid user id.");
		}

		if (StringUtils.isBlank(userDTO.getEmail()) || !CommonUtils.validateEmail(userDTO.getEmail())
				|| userDTO.getEmail().length() > 65) {
			logger.error("Please enter valid email id.");
			throw new UnprocessableEntityException("Please enter valid email address.");
		}

		if (StringUtils.isBlank(userDTO.getPassword()) 
				|| !CommonUtils.isPasswordValid(userDTO.getPassword())
				|| userDTO.getPassword().length() > 45) {
			logger.error("Please enter valid password no.");
			throw new UnprocessableEntityException("Please enter valid password.");
		}
		if (StringUtils.isBlank(userDTO.getConfirmPassword())
				|| !CommonUtils.isPasswordValid(userDTO.getConfirmPassword())
				|| userDTO.getConfirmPassword().length() > 45) {
			logger.error("Please enter valid password no.");
			throw new UnprocessableEntityException("Please enter valid password.");
		}
		if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
			logger.error("Password not Matched.");
			throw new UnprocessableEntityException("Password not Matched.");
		}

	}

	/**
	 * This method will validate the customer to check it is present or not.
	 * 
	 */

	public User validateAndGetUser(long userId) {
		logger.info("To validate user existence.");
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			logger.error("User not found");
			throw new NotFoundException("User not found");
		}
		logger.info("Returning User after validating User existence.");
		return user.get();
	}

	/**
	 * This method will update the customer details
	 * 
	 */
	public User validateAndUpdate(UserDTO userDTO) throws Exception {
		logger.info("To validate UserDTO object to update.");

		User user = validateAndGetUser(userDTO.getId());

		if (!user.isActive()) {
			throw new UnprocessableEntityException("This user  is deactivated. Please activate.");
		}

		if (userDTO.getPassword() != null) {
			user.setPassword(userDTO.getPassword());
		}
		user.setActive(true);
		user.setTokenExpired(false);
		// user.setRoles(userDTO.getRoles());
		logger.info("Returning user after validating CustomerDTO object to update.");
		return user;
	}

	/**
	 * This method will return list of active users.
	 */
	public List<UserDTO> getAllUsers() {

		List<UserDTO> users = userRepository.findByIsActiveTrue().stream().map(UserDTO::new)
				.collect(Collectors.toList());
		if (users.isEmpty()) {
			throw new NotFoundException("No active user is present");
		}
		return users;
	}

}
