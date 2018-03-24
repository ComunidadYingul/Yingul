package com.valework.yingul.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.ResetPasswordDao;
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
	
	@RequestMapping(value = "/sendRecoveryEmail", method = RequestMethod.POST)
	@ResponseBody
    public String updatePropertyPost(@Valid @RequestBody Yng_User user) throws MessagingException {
		Yng_ResetPassword resetPassword= new Yng_ResetPassword();
        if(userService.checkEmailExists(user.getEmail())) {
        	Yng_User userTemp= userService.findByEmail(user.getEmail());
        	if(resetPasswordDao.findByUser(userTemp)!=null) {
        		resetPassword=resetPasswordDao.findByUser(userTemp);
        	}else {
        		resetPassword.setUser(userService.findByEmail(userTemp.getUsername()));
        		resetPassword=resetPasswordDao.save(resetPassword);
        	}
        	smtpMailSender.send(userTemp.getEmail(), "Restaure la contraseña del usuario de Yingul", "Estimado "+userTemp.getUsername()+":<br>" + 
        			"Para restaurar la contraseña, haga clic en este vínculo.<br>" + 
        			"http://yingul.com/resetPassword/"+resetPassword.getResetpasswordId()+"<br>" + 
        			"Tenga en cuenta lo siguiente: <br>" + 
        			"Por motivos de seguridad, el vínculo caducará 72 horas después de su envío.<br>" + 
        			"Si no puede acceder al vínculo, copie y pegue toda la URL en el navegador.<br>" + 
        			"El equipo de Yingul<br>" + 
        			"Copyright 2018 Yigul S.R.L.. All rights reserved.");
        	return "save";
        }else {
        	return "not registered";
        }     
    }
    
}
