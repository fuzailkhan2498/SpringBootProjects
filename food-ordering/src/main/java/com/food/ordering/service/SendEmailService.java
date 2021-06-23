package com.food.ordering.service;

import java.io.File;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.food.ordering.config.PropConfig;
import com.food.ordering.exception.UnprocessableEntityException;

import ch.qos.logback.classic.Logger;

/**
 * @author Fuzail
 *  This class is not takes part in this project. This class is basically used to understand in how many ways a simple email can be send. 
 */
@Service
public class SendEmailService {

	@Autowired
	private SpringTemplateEngine templateEngine;
	private PropConfig propConfig;
	private JavaMailSender javaMailSender;
	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	public SendEmailService(PropConfig propConfig, JavaMailSender javaMailSender) {
		super();
		this.propConfig = propConfig;
		this.javaMailSender = javaMailSender;
	}

	public void sendEmail(String to, String body, String topic) throws Exception {
		logger.info("Sending Email ...");
		validateSmtp();
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("khanfuzail2498@gmail.com");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(topic);
		simpleMailMessage.setText(body);
		javaMailSender.send(simpleMailMessage);
		logger.info("Email Sent ...");

	}
	
	public void sendMailWithAttachment(String to,String subject,String body)
	{
		logger.info("Sending Email ...");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				try {
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
					mimeMessage.setFrom(new InternetAddress("khanfuzail2498@gmail.com"));
					mimeMessage.setSubject(subject);
					mimeMessage.setText(body);
					
					
					FileSystemResource file = new FileSystemResource(new File("C:/Users/Fuzail/Desktop/logo.jpg"));
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
					helper.addAttachment("logo.jpg", file);
					
				} catch (MessagingException e) {
					System.out.println(e.getMessage());
				}
				
			}
		};
		javaMailSender.send(preparator);
		logger.info("Email Sent ...");
	}
	public void sendMailWithAttachment(String to, String subject) throws MessagingException
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		try
		{
			helper.setTo(new InternetAddress(to));
			helper.setFrom(new InternetAddress("khanfuzail2498@gmail.com"));
			helper.setSubject(subject);
			helper.setText("<html><body>"
					+ "<h2>Dear User !!</h2> </br>"
					+ "<h3> Welcome to Food ADDA. </h3>"
					+ "<h3>Your account is created succesfully</h3><hr>"
					+  "<img src='cid:mylogo' style='float:left;width:60px;height:60px;'/>"
					+ "</body></html>", true);
			
			FileSystemResource file = new FileSystemResource(new File("C:/Users/Fuzail/Desktop/logo.jpg"));
			helper.addAttachment("logo.jpg", file);
			helper.addInline("mylogo", new File("C:/Users/Fuzail/Desktop/mylogo.png"));
			javaMailSender.send(message);
		}
		catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
		
	}
	public void sendEmailWithTemplate(String to,String subject) throws AddressException, MessagingException
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(new InternetAddress(to));
		helper.setFrom(new InternetAddress("khanfuzail2498@gmail.com"));
		helper.setSubject(subject);
		
		Context context = new Context();
		context.setVariable("form", "khanfuzail2498@g,mail.com");
		context.setVariable("to", to);
		context.setVariable("password", "dafault");
		String htmlContent = templateEngine.process("mytemplate", context);
		helper.setText(htmlContent, true);
		javaMailSender.send(message);
		
	}
	

	private void validateSmtp() throws Exception {
		if(StringUtils.isBlank(propConfig.getSmtpUserName()) || StringUtils.isBlank(propConfig.getSmtpPassword()) || 
				StringUtils.isBlank(propConfig.getSmtpHost()) || StringUtils.isBlank(propConfig.getSmtpPort()))
		{
			logger.error("SMTP credential is missing.");
			throw new UnprocessableEntityException("Error while sending mail.");
		}

	}

}
