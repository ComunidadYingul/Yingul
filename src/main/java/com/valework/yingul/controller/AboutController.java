package com.valework.yingul.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.EmailDao;
import com.valework.yingul.model.Yng_Email;

@RestController
@RequestMapping("/about")
public class AboutController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	private EmailDao emailDao;
	
	@RequestMapping(value = "/createMail", method = RequestMethod.POST)
    @ResponseBody
    public String createClaim(@Valid @RequestBody Yng_Email email) throws Exception {	
		emailDao.save(email);
    	smtpMailSender.send(email.getSendTo(), email.getTitle(),email.getBody());
    	smtpMailSender.send(email.getSentFrom(), "Solicitud Creada","Hola\r\n" + 
    			"<br>" + 
    			"Gracias por contactarte con Yingul, la solicitud ha sido recibida por nuestros agentes quienes están trabajando para brindar una respuesta oportuna.");
    	return "save";
    }

}
