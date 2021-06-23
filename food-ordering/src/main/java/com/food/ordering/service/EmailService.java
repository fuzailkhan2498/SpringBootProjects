package com.food.ordering.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.food.ordering.config.PropConfig;
import com.food.ordering.domain.Order;
import com.food.ordering.domain.OrderedItems;
import com.food.ordering.domain.Restaurant;
import com.food.ordering.domain.User;
import com.food.ordering.dto.UserDTO;
import com.food.ordering.exception.UnprocessableEntityException;

import ch.qos.logback.classic.Logger;

@Service
public class EmailService {

	private SpringTemplateEngine springTemplateEngine;
	private PropConfig propConfig;
	private JavaMailSender mailSender;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public EmailService(SpringTemplateEngine springTemplateEngine, PropConfig propConfig, JavaMailSender mailSender) {
		super();
		this.springTemplateEngine = springTemplateEngine;
		this.propConfig = propConfig;
		this.mailSender = mailSender;
	}

	/**
	 * This method will send the mail to the specified address that are passed asargument
	 * (Also include in-line image and HTML template as a part of message).
	 */

	public void sendMail(final String to, final String from, final String subject, final String msg) throws Exception {
		logger.info("To send Mail.");
		validateSmtp();
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setFrom(from);
			helper.setSubject(subject);
			helper.setText(msg, true);
			helper.addInline("mylogo", new File("C:/Users/Fuzail/Desktop/mylogo.png"));
			mailSender.send(message);

		} catch (Exception e) {
			logger.error("Email not sent to email address" + to, e);
		}

		logger.info("Returning after sending Mail.");
	}

	/**
	 * This method will send the mail to user when it trying reset the password.
	 */
	public void sendPasswordResetNotification(UserDTO user, String newPassword) throws Exception {
		logger.info("To send Password Notification Mail.");
		String sendTo = user.getEmail();
		String sendFrom = "khanfuzail2498@gmail.com";
		String subject = "Your Account password for Food Adda is reset succesfully";
		Context context = new Context();
		context.setVariable("from", sendFrom);
		context.setVariable("user", user);
		context.setVariable("password", newPassword);
		String html = springTemplateEngine.process("reset-password-by-admin", context);
		sendMail(sendTo, sendFrom, subject, html);
		logger.info("Returning after sending Password Reset Notification Mail.");
	}

	/**
	 * This method will send acknowledgement email to customer when he created his account.
	 */
	public void sendCustomerAcknowledgementMail(User user, String customerName) throws Exception {
		logger.info("To send notification that customer is created successfully");
		String sendTo = user.getEmail();
		String sendFrom = "khanfuzail2498@gmail.com";
		String subject = "Your account in FoodAdda is created succesfully";
		Context context = new Context();
		context.setVariable("name", customerName);
		context.setVariable("user", user);
		String html = springTemplateEngine.process("customer-acknowledge-mail", context);
		sendMail(sendTo, sendFrom, subject, html);
		logger.info("Returning after sending customer acknowledgement mail. ");

	}
	
	/**
	 * This method will send acknowledgement email to customer when he created his account.
	 */
	public void sendRestaurantAcknowledgementMail(User user, Restaurant restaurant) throws Exception {
		logger.info("To send notification that restaurant is created successfully");
		String sendTo = user.getEmail();
		String sendFrom = "khanfuzail2498@gmail.com";
		String subject = "Your account in FoodAdda is created succesfully";
		Context context = new Context();
		context.setVariable("name", restaurant.getName());
		context.setVariable("user", user);
		String html = springTemplateEngine.process("restaurant-acknowledgement-mail", context);
		sendMail(sendTo, sendFrom, subject, html);
		logger.info("Returning after sending restaurant acknowledgement mail. ");

	}

	/**
	 * This method will validate SMTP requirement.
	 */

	private void validateSmtp() throws Exception {
		if (StringUtils.isBlank(propConfig.getSmtpUserName()) || StringUtils.isBlank(propConfig.getSmtpPassword())
				|| StringUtils.isBlank(propConfig.getSmtpHost()) || StringUtils.isBlank(propConfig.getSmtpPort())) {
			logger.error("SMTP credential is missing.");
			throw new UnprocessableEntityException("Error while sending mail.");
		}

	}

	/**
	 * This method will send mail to the user when the order is placed.
	 */
	public void orderConfirmationMail(Order order) throws Exception {
		logger.info("To send notification that order is placed");
		String sendTo = order.getCustomer().getUser().getEmail();
		String sendFrom = "khanfuzail2498@gmail.com";
		String subject = "Your order is placed succesfully";
		Context context = new Context();
		context.setVariable("restaurant", order.getRestaurant().getName());
		context.setVariable("order", order);
		context.setVariable("items", order.getOrderedItems().stream().map(OrderedItems::getMenuItem).collect(Collectors.toList()));
		//context.setVariable("items", order.getOrderedItems().stream().collect(Collectors.toMap((a)->a.getMenuItem(), Function.identity(), (o,n) ->n)));
		String html = springTemplateEngine.process("order", context);
		sendMail(sendTo, sendFrom, subject, html);
		logger.info("Returning after order confirmation mail.");
		
	}
	

}
