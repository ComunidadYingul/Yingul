package com.valework.yingul;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(String to, String subject, String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body,true);
		//helper.setFrom("yingul@yingul.com");
		//helper.setFrom("daniel@internetvale.com");
		javaMailSender.send(message);

		
		/*System.out.println(to+"   subject:   "+subject+"  body: "+ body);
		 MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true);
				helper.setSubject(subject);
				helper.setTo(to);
				helper.setText(body,true);
				helper.setFrom("eddy@internetvale.com");
				
				System.out.println("email send");
				javaMailSender.send(message);			
			} catch (MessagingException e) {
				System.out.println("email send error");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}

}
