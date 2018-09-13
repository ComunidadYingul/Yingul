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
    	claim.setStatus("pending");
    	claim.setBack(false);
    	claim.setChange(false);
    	claim.setMinuse(false);
    	claim.setCodeBack(0);
    	claim.setCodeChange(0);
    	claim.setCodeMinuse(0);
    	claim=claimDao.save(claim);
    	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "RECLAMO URGENTE","Tu comprador hizo un reclamo respecto a :  "+confirmTemp.getBuy().getYng_item().getName()
    			+ "<br/> La acreditacion de Yingul a tu cuenta se encuentra temporalmente congelada "
    			+ "<br/> Tu comprador argumento: "+ claim.getClaimText()
    			+ "<br/> Nos pondremos en contacto contigo lo mas pronto posible o puedes escribirnos a yingulcompany@internetvale.com codigo de seguimiento:"+confirmTemp.getConfirmId());
		smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "RECLAMO REALIZADO EXITOSAMENTE", "Se hizo el reclamo respecto a :  "+confirmTemp.getBuy().getYng_item().getName() 
    			+ "<br/> Tu argumento es : "+ claim.getClaimText()
    			+ "<br/> Nos pondremos en contacto contigo lo mas pronto posible o puedes escribirnos a yingulcompany@internetvale.com codigo de seguimiento:"+confirmTemp.getConfirmId());
    			/*+ "<br/> Encuentrate con tu vendedor e ingresa a: https://www.yingul.com/agreement/"+claim.getClaimId()+" donde ambos podran firmar un acuerdo que los beneficie."
    			+ "<br/> No devuelvas el poducto al vendedor hasta que ambos hasta que te llegue un email indicando que lo puedes hacer si no no podremos devolverte tu dinero");*/
		smtpMailSender.send("yingulcompany@internetvale.com", "RECLAMO EN www.yingul.com", "Se hizo el reclamo respecto a :  "+confirmTemp.getBuy().getYng_item().getName() 
    			+ "<br/> El argumento del comprador es : "+ claim.getClaimText()
    			+ "<br/> Código de seguimiento:"+confirmTemp.getConfirmId());
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
	
	@RequestMapping(value = "/updateClaim", method = RequestMethod.POST)
    @ResponseBody
    public String updateClaim(@Valid @RequestBody Yng_Claim claim,@RequestHeader("Authorization") String authorization) throws Exception {	
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Claim claimTemp = claimDao.findByClaimId(claim.getClaimId());
		Yng_User seller= claimTemp.getConfirm().getSeller();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(seller.getUsername().equals(parts[0]) && encoder.matches(parts[1], seller.getPassword())){
			int codeConfirm=(1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
			String agreement="";
			if(claim.isBack()) {
				claimTemp.setBack(true);
				claimTemp.setCodeBack(codeConfirm);
				claimTemp.setChange(false);
				claimTemp.setCodeChange(0);
				claimTemp.setMinuse(false);
				claimTemp.setCodeMinuse(0);
				agreement="DEVOLUCIÓN DE PRODUCTO DEFECTUOSO";
			}
			if(claim.isChange()) {
				claimTemp.setBack(false);
				claimTemp.setCodeBack(0);
				claimTemp.setChange(true);
				claimTemp.setCodeChange(codeConfirm);
				claimTemp.setMinuse(false);
				claimTemp.setCodeMinuse(0);
				agreement="CAMBIO DE PRODUCTO DEFECTUOSO";
			}
			if(claim.isMinuse()) {
				claimTemp.setBack(false);
				claimTemp.setCodeBack(0);
				claimTemp.setChange(false);
				claimTemp.setCodeChange(0);
				claimTemp.setMinuse(true);
				claimTemp.setCodeMinuse(codeConfirm);
				agreement="MALA MANIPULACIÓN DEL PRODUCTO";
			}
		
			claimTemp=claimDao.save(claimTemp);
			smtpMailSender.send(claimTemp.getConfirm().getBuy().getUser().getEmail(), "CÓDIGO DE ACUERDO", "Tú y tu vendedor llegaron al cuerdo de "+agreement+"  respecto a :  "+claimTemp.getConfirm().getBuy().getYng_item().getName() +"  Descripción : "+claimTemp.getConfirm().getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +claimTemp.getConfirm().getBuy().getYng_item().getPrice() 
					+ "<br/> Tu reclamo fue : "+ claim.getClaimText()
					+ "<br/> Dale este código ("+codeConfirm+")a tu vendedor si estas de acuerdo y espera mas instrucciones antes de devolver el producto a tu vendedor o no podremos devolverte tu dinero");
			return "save";
		}else {
			return "prohibited";
		}
    }
 
}
