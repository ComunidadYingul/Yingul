package com.valework.yingul.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.valework.yingul.model.Yng_User;
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
    	claim=claimDao.save(claim);
    	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "RECLAMO URGENTE","Tu comprador hizo un reclamo respecto a :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice()
    			+ "<br/> La acreditacion de Yingul a tu cuenta se encuentra temporalmente congelada "
    			+ "<br/> Tu comprador argumento: "+ claim.getClaimText()
    			+ "<br/> Encuentrate con tu comprador e ingresa a: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/agreement/"+claim.getClaimId()+" donde ambos podran firmar un acuerdo y podamos acreditar tu dinero a tu cuenta.");
		smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "RECLAMO REALIZADO EXITOSAMENTE", "Se hizo el reclamo respecto a :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice() 
    			+ "<br/> Tu argumento es : "+ claim.getClaimText()
    			+ "<br/> Encuentrate con tu vendedor e ingresa a: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/agreement/"+claim.getClaimId()+" donde ambos podran firmar un acuerdo que los beneficie."
    			+ "<br/> No devuelvas el poducto al vendedor hasta que ambos hasta que te llegue un email indicando que lo puedes hacer si no no podremos devolverte tu dinero");
    	return "save";
    }
	
	@RequestMapping("/getClaimById/{claimId}")
    public Yng_Claim findConfirmForId(@PathVariable("claimId") Long claimId,@RequestHeader("Authorization") String authorization) {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Claim claim=claimDao.findByClaimId(claimId);
		Yng_User seller= claim.getConfirm().getSeller();
		Yng_User buyer= claim.getConfirm().getBuyer();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if((buyer.getUsername().equals(parts[0]) && encoder.matches(parts[1], buyer.getPassword()))||(seller.getUsername().equals(parts[0]) && encoder.matches(parts[1], seller.getPassword()))){
			if(claim.getStatus().equals("pending")) {
				claim.getConfirm().getBuy().setYng_Payment(null);
				claim.setCodeBack(0);
				claim.setCodeChange(0);
				claim.setCodeMinuse(0);
				return claim;
			}else {
				return null;
			}
		}
		return null;
    }
 
}
