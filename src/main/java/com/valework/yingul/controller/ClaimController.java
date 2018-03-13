package com.valework.yingul.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.ClaimDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Claim;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.service.ConfirmService;

@RestController
@RequestMapping("/claim")
public class ClaimController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired 
	ConfirmDao confirmDao;
	@Autowired
	ConfirmService confirmService;
	@Autowired
	UserDao userDao;
	@Autowired
	ClaimDao claimDao;
	
	@RequestMapping(value = "/createClaim", method = RequestMethod.POST)
    @ResponseBody
    public String createClaim(@Valid @RequestBody Yng_Claim claim) throws Exception {	
    	Yng_Confirm confirmTemp = confirmDao.findByConfirmId(claim.getConfirm().getConfirmId());
    	confirmTemp.setStatus("claiming");
    	confirmTemp=confirmDao.save(confirmTemp);
    	claim.setConfirm(confirmTemp);
    	claimDao.save(claim);
    	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "RECLAMO URGENTE","Tu comprador hizo un reclamo respecto a :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice()
    			+ "<br/> La acreditacion de Yingul a tu cuenta se encuentra temporalmente congelada "
    			+ "<br/> Tu comprador argumento: "+ claim.getClaimText()
    			+ "<br/> Encuentrate con tu comprador e ingresa a: donde ambos podran firmar un acuerdo y podamos acreditar tu dinero a tu cuenta.");
		smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "RECLAMO REALIZADO EXITOSAMENTE", "Se hizo el reclamo respecto a :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice() 
    			+ "<br/> Tu argumento es : "+ claim.getClaimText()
    			+ "<br/> Encuentrate con tu vendedor e ingresa a: donde ambos podran firmar un acuerdo que beneficie a ambos."
    			+ "<br/> No devuelvas el poducto al vendedor hasta que ambos firmen el acuerdo si no no podremos devolrte tu dinero");
    	return "save";
    }
 
}
