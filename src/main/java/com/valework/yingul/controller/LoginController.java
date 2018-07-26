package com.valework.yingul.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.ResetPasswordDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Query;
import com.valework.yingul.model.Yng_ResetPassword;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	private UserService userService;
	@Autowired
	private ResetPasswordDao resetPasswordDao;
	@Autowired
	private UserDao userDao;
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/sendRecoveryEmail", method = RequestMethod.POST)
	@ResponseBody
    public String updatePropertyPost(@Valid @RequestBody Yng_User user) throws MessagingException {
		Yng_ResetPassword resetPassword= new Yng_ResetPassword();
        if(userService.checkEmailExists(user.getEmail().trim())) {
        	Yng_User userTemp= userService.findByEmail(user.getEmail().trim());
        	if(resetPasswordDao.findByUser(userTemp) == null) {
        		resetPassword.setUser(userDao.findByUsername(userTemp.getUsername()));
        		resetPassword.setCodeResetPassword(1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
        		resetPassword=resetPasswordDao.save(resetPassword);
        		
        	}else {
        		resetPassword=resetPasswordDao.findByUser(userTemp);
        		resetPassword.setCodeResetPassword(1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
        		resetPassword=resetPasswordDao.save(resetPassword);
        	}
        	smtpMailSender.send(userTemp.getEmail(), "Restaure la contraseña del usuario de Yingul", "Estimado "+userTemp.getUsername()+":<br>" + 
        			"Para restaurar la contraseña, haga clic en este vínculo.<br>" + 
        			"http://www.yingul.com/resetPassword/"+resetPassword.getResetpasswordId()+"<br>" +
        			"Ingres este código "+resetPassword.getCodeResetPassword()+"<br>" +
        			"Tenga en cuenta lo siguiente: <br>" + 
        			"Por motivos de seguridad, el vínculo caducará 72 horas después de su envío.<br>" + 
        			"Si no puede acceder al vínculo, copie y pegue toda la URL en el navegador.<br>" + 
        			"El equipo de Yingul<br>" + 
        			"Copyright 2018 Yigul S.R.L.. All rights reserved.");
        	return "save";
        }else {
        	return "El email ingresado no esta registrado en Yingul.";
        }     
    }
	@RequestMapping(value = "/editPassword", method = RequestMethod.POST)
	@ResponseBody
    public String updatePasswordUser(@Valid @RequestBody Yng_ResetPassword resetPassword) throws MessagingException {
        if(resetPasswordDao.findByResetpasswordId(resetPassword.getResetpasswordId()) != null) {
        	Yng_ResetPassword resetPasswordTemp=  resetPasswordDao.findByResetpasswordId(resetPassword.getResetpasswordId());
        	Yng_User userTemp=resetPasswordTemp.getUser();
        	String encryptedPassword = passwordEncoder.encode(resetPassword.getUser().getPassword().trim());
        	userTemp.setPassword(encryptedPassword);
        	userDao.save(userTemp);
        	resetPasswordDao.delete(resetPasswordTemp);
        	smtpMailSender.send(userTemp.getEmail(), "La contraseña del Usuario de Yingul ha cambiado", "Estimado "+userTemp.getUsername()+":<br>" + 
        			"<br>" + 
        			"Su contraseña de Yingul ha cambiado recientemente.<br>" + 
        			"Si usted no solicitó el cambio de contraseña, póngase en contacto con el equipo de asistencia al cliente.<br>" + 
        			"bout/contactUs<br>" + 
        			"<br>" + 
        			"El equipo de Yingul<br>" + 
        			"<br>" + 
        			"Copyright 2018 Yingul S.R.L. All rights reserved.");
        	return "save";
        }else {
        	return "prohibited";
        }     
    }
	@RequestMapping(value = "/checkAuthorizationAndroid", method = RequestMethod.POST)
	@ResponseBody
    public String checkAuthorizationAndroid(@Valid @RequestBody Yng_ResetPassword resetPassword) throws MessagingException, JsonProcessingException {
		Yng_User user = userDao.findByEmail(resetPassword.getUser().getEmail());
		Yng_ResetPassword resetPasswordTemp = resetPasswordDao.findByUserAndCodeResetPassword(user, resetPassword.getCodeResetPassword()); 
        if(resetPasswordTemp != null) {
        	ObjectMapper mapper = new ObjectMapper();
    		String jsonInString = mapper.writeValueAsString(resetPasswordTemp);
        	return jsonInString;
        }else {
        	System.out.println("incorrecto");
        	return "prohibited";
        }     
    }
	@RequestMapping("/checkAuthorization/{resetPasswordId}")
    public String checkAuthorization(@PathVariable("resetPasswordId") Long resetPasswordId) {
    	if(resetPasswordDao.findByResetpasswordId(resetPasswordId) != null) {
    		return "true";
    	}else {
    		return "false";
    	}
    }
	
    
}
