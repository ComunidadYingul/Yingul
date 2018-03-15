package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.ConfirmService;

@RestController
@RequestMapping("/confirm")
public class ConfirmController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired 
	ConfirmDao confirmDao;
	@Autowired
	ConfirmService confirmService;
	@Autowired
	UserDao userDao;
	
    @RequestMapping("/getConfirmForId/{confirmId}")
    public Yng_Confirm findConfirmForId(@PathVariable("confirmId") Long confirmId) {
    	if(confirmService.exitsByConfirmId(confirmId)) {
    		Yng_Confirm confirm = confirmDao.findByConfirmId(confirmId);
    		if(confirm.isSellerConfirm()) {
    			return null;
    		}else {
    			confirm.setCodeConfirm(0);
    			return confirm;
    		}
    	}else {
    		return null;
    	}
    }
    
    @RequestMapping("/getConfirmToClaimForUser/{username}")
    public Set<Yng_Confirm> findConfirmToClaimForUser(@PathVariable("username") String username) {
    	Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
    	Yng_User yng_User = userDao.findByUsername(username);
    	List<Yng_Confirm> listConfirm = confirmDao.findByBuyerAndStatusOrderByConfirmIdDesc(yng_User,"confirm");
    	Set<Yng_Confirm> setConfirm = new HashSet<>();
    	for (Yng_Confirm s : listConfirm) {
			if(s.getDayEndClaim()>=Integer.parseInt(hourdateFormat.format(date))&&s.getMonthEndClaim()>=Integer.parseInt(hourdateFormat1.format(date))&&s.getYearEndClaim()>=Integer.parseInt(hourdateFormat2.format(date))) {
				setConfirm.add(s);
			}
    	}
    	return setConfirm;
    }
    
    
    @RequestMapping(value = "/updateConfirm", method = RequestMethod.POST)
	@ResponseBody
    public String queryItemPost(@Valid @RequestBody Yng_Confirm confirm) throws MessagingException {
    	Yng_Confirm confirmTemp=confirmDao.findByConfirmId(confirm.getConfirmId());
    	if(confirmTemp.getCodeConfirm()==confirm.getCodeConfirm()) {
    		confirmTemp.setBuyerConfirm(true);
    		confirmTemp.setSellerConfirm(true);
    		Date date = new Date();
        	DateFormat hourdateFormat = new SimpleDateFormat("dd");
        	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
        	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
        	DateTime now = new DateTime( date );
        	DateTime endClaim = now.plusDays( 7 );
        	
        	confirmTemp.setDayBuyerConfirm(Integer.parseInt(hourdateFormat.format(date)));
        	confirmTemp.setDaySellerConfirm(Integer.parseInt(hourdateFormat.format(date)));
        	confirmTemp.setMonthBuyerConfirm(Integer.parseInt(hourdateFormat1.format(date)));
        	confirmTemp.setMonthSellerConfirm(Integer.parseInt(hourdateFormat1.format(date)));
        	confirmTemp.setYearBuyerConfirm(Integer.parseInt(hourdateFormat2.format(date)));
        	confirmTemp.setYearSellerConfirm(Integer.parseInt(hourdateFormat2.format(date)));
        	confirmTemp.setDayInitClaim(Integer.parseInt(hourdateFormat.format(date)));
        	confirmTemp.setMonthInitClaim(Integer.parseInt(hourdateFormat1.format(date)));
        	confirmTemp.setYearInitiClaim(Integer.parseInt(hourdateFormat2.format(date)));
        	confirmTemp.setDayEndClaim(Integer.parseInt(hourdateFormat.format(endClaim.toDate())));
        	confirmTemp.setMonthEndClaim(Integer.parseInt(hourdateFormat1.format(endClaim.toDate())));
        	confirmTemp.setYearEndClaim(Integer.parseInt(hourdateFormat2.format(endClaim.toDate())));
        	confirmTemp.setStatus("confirm");
        	confirmDao.save(confirmTemp);
        	System.out.println("Eddy:"+confirmTemp.getBuy().getYng_item().getUser().getEmail());
        	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "CONFIRMACIÓN DE ENTREGA EXITOSA","Se realizo la confirmacion de la entrega del producto :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice()
        			+ "<br/> --Si tu comprador no tiene ninguna observacion del producto en 7 días podras recoger tu dinero ingresando a : http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/frontYingulPay");
			smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "CONFIRMACIÓN DE RECEPCIÓN EXITOSA", "Se realizo la confirmacion de la entrega del producto : "+confirmTemp.getBuy().getQuantity()+" "+confirmTemp.getBuy().getYng_item().getName()+" a:"+confirmTemp.getBuy().getCost()
					+ "<br/> --Tiene 7 días de garantia con Yingul para realizar alguna observación ingrese a: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/userFront/purchases despues de ese lapso no se aceptaran reclamos");
    		return "save";
    	}else {
    		return "el codigo es incorrecto!!!";
    	}
    }
}
