package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.valework.yingul.model.Yng_Confirm;
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
	
    @RequestMapping("/getConfirmForId/{confirmId}")
    public Yng_Confirm findConfirmForId(@PathVariable("confirmId") Long confirmId) {
    	if(confirmService.exitsByConfirmId(confirmId)) {
    		Yng_Confirm confirm = confirmDao.findByConfirmId(confirmId);
    		if(confirm.isSellerConfirm()) {
    			return null;
    		}else {
    			return confirm;
    		}
    	}else {
    		return null;
    	}
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
        	confirmDao.save(confirmTemp);
        	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "CONFIRMACIÓN DE ENTREGA EXITOSA","Se realizo la confirmacion de la entrega del producto :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice()+ "      --Si tu comprador no tiene ninguna observacion del producto en 7 días podras recoger tu dinero ingresando a :");
			smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "CONFIRMACIÓN DE RECEPCIÓN EXITOSA", "Se realizo la confirmacion de la entrega del producto : "+confirmTemp.getBuy().getQuantity()+" "+confirmTemp.getBuy().getYng_item().getName()+" a:"+confirmTemp.getBuy().getCost()+" tiene 7 días de garantia con Yingul para realizar alguna observación ingrese a: despues de ese lapso no se aceptaran reclamos");
    		return "save";
    	}else {
    		return "el codigo es incorrecto!!!";
    	}
    }
}
