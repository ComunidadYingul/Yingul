package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.logistic.GetStateSend;
import com.valework.yingul.model.Yng_Confirm;

import com.valework.yingul.model.Yng_StateShipping;

import com.valework.yingul.model.Yng_Standard;

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

	@Autowired 
	StandardDao standardDao;

	
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
    public Set<Yng_Confirm> findConfirmToClaimForUser(@PathVariable("username") String username) throws ParseException {
    	Date date = new Date();
    	Yng_User yng_User = userDao.findByUsername(username);
    	List<Yng_Confirm> listConfirm = confirmDao.findByBuyerAndStatusOrderByConfirmIdDesc(yng_User,"confirm");
    	Set<Yng_Confirm> setConfirm = new HashSet<>();
    	for (Yng_Confirm s : listConfirm) {
    		String str_date="";
    		if(s.getDayEndClaim()>=10) {
    			str_date=""+s.getDayEndClaim();
    		}else{
    			str_date="0"+s.getDayEndClaim();
    		}
    		if(s.getMonthEndClaim()>=10) {
    			str_date+="-"+s.getMonthEndClaim();
    		}else {
    			str_date+="-0"+s.getMonthEndClaim();
    		}
    		str_date += "-"+s.getYearEndClaim();
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");;
    		Date endClaim= formatter.parse(str_date);
			if(date.before(endClaim)) {
				setConfirm.add(s);
			}
    	}
    	return setConfirm;
    }
    
    @RequestMapping("/findConfirmForSeller/{username}")
    public List<Yng_Confirm> findConfirmForSeller(@PathVariable("username") String username) throws ParseException {
    	Yng_User yng_User = userDao.findByUsername(username);
    	return confirmDao.findBySellerOrderByConfirmIdDesc(yng_User);	
    }
    
    @RequestMapping("/findConfirmForBuyer/{username}")
    public List<Yng_Confirm> findConfirmForBuyer(@PathVariable("username") String username) throws ParseException {
    	Yng_User yng_User = userDao.findByUsername(username);
    	return confirmDao.findByBuyerOrderByConfirmIdDesc(yng_User);
    }
    
    @RequestMapping(value = "/updateConfirm", method = RequestMethod.POST)
	@ResponseBody
    public String queryItemPost(@Valid @RequestBody Yng_Confirm confirm) throws MessagingException {
    	Yng_Confirm confirmTemp=confirmDao.findByConfirmId(confirm.getConfirmId());
    	if(confirmTemp.getBuy().getShippingCost() == 0) {
    		if(confirmTemp.getCodeConfirm()==confirm.getCodeConfirm()) {
        		confirmTemp.setBuyerConfirm(true);
        		confirmTemp.setSellerConfirm(true);
        		Date date = new Date();
            	DateFormat hourdateFormat = new SimpleDateFormat("dd");
            	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
            	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
            	DateTime now = new DateTime( date );
            	Yng_Standard daysForClaims = standardDao.findByKey("daysForClaims");
            	DateTime endClaim = now.plusDays( Integer.parseInt(daysForClaims.getValue()) );
            	
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
            			+ "<br/> --Si tu comprador no tiene ninguna observacion del producto en "+daysForClaims.getValue()+" días podras recoger tu dinero ingresando a : http://www.yingul.com/frontYingulPay");
    			smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "CONFIRMACIÓN DE RECEPCIÓN EXITOSA", "Se realizo la confirmacion de la entrega del producto : "+confirmTemp.getBuy().getQuantity()+" "+confirmTemp.getBuy().getYng_item().getName()+" a:"+confirmTemp.getBuy().getCost()
    					+ "<br/> --Tiene "+daysForClaims.getValue()+" días de garantia con Yingul para realizar alguna observación ingrese a: http://www.yingul.com/userFront/claims despues de ese lapso no se aceptaran reclamos");
        		return "save";
        	}else {
        		return "el codigo es incorrecto!!!";
        	}
    	}else {
    		return "prohibidet";
    	}
    }
    @RequestMapping("/findNumber")
    public String findNumber() {
    	
    	Calendar fechaInicial = null; 
    	Calendar fechaFinal = null;
    	 int diffDays= 0;
    	  //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
    	  while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

    	  //si el dia de la semana de la fecha minima es diferente de sabado o domingo
    	  if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY || fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
    	   //se aumentan los días de diferencia entre min y max
    	   diffDays++;
    	   }
    	  //se suma 1 dia para hacer la validación del siguiente dia.
    	  fechaInicial.add(Calendar.DATE, 1);
    	  }
    	//return diffDays;
    	
    	return "save";
    }
    @RequestMapping("/getNumberPendingDeliveriesForUser/{username}")
    public int getNumberPendingDeliveriesForUser(@PathVariable("username") String username) throws ParseException {
    	Yng_User yng_User = userDao.findByUsername(username);
    	List<Yng_Confirm> listConfirm = confirmDao.findBySellerAndStatusOrderByConfirmIdDesc(yng_User,"pending");
    	return listConfirm.size();
    }
    @RequestMapping(value = "/updateConfirmApi", method = RequestMethod.POST)
   	@ResponseBody
       public String queryItemPostApi(@Valid @RequestBody Yng_Confirm confirm) throws MessagingException {
       	Yng_Confirm confirmTemp=confirmDao.findByConfirmId(confirm.getConfirmId());
       	/*******************************************************************************/
       	//codeConfirmAndreani
       	String confirmStateDao=standardDao.findByKey("codeConfirmAndreani").getValue();
       	//System.out.println("confirmTemp:"+confirmTemp.toString()+" value:"+);
       	Yng_StateShipping stateShipping=new Yng_StateShipping();
    	GetStateSend getState = new GetStateSend();
    	String confirmState=confirmTemp.getBuy().getShipping().getYng_Shipment().getShipmentCod();
    	String stateApi ="";
    	Yng_Standard clienteStandard = standardDao.findByKey("Cliente");
    	try {
    		stateShipping=getState.sendState(""+confirmState,clienteStandard.getValue());
    		stateApi=stateShipping.getEstado();
    		System.out.println("state:"+stateApi+":"+confirmStateDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       	 
       	 /**************************************/
       	if(confirmStateDao.equals(stateApi)) {
       		confirmTemp.setBuyerConfirm(true);
       		confirmTemp.setSellerConfirm(true);
       		Date date = new Date();
           	DateFormat hourdateFormat = new SimpleDateFormat("dd");
           	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
           	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
           	
           	int dd;
            dd=Integer.parseInt(hourdateFormat.format(date));
            int mm;
            mm=Integer.parseInt(hourdateFormat1.format(date));
            int yy;
            yy=Integer.parseInt(hourdateFormat2.format(date));
            GregorianCalendar calendar = new GregorianCalendar(yy, mm-1, dd);
            //GregorianCalendar calendar = new GregorianCalendar(2018, 12-1, 9);
            DateTime seni = new DateTime( date );
            DateTime endSend = null;
            //int i = calendar.get(Calendar.DAY_OF_WEEK);
            String Valor_dia = null;
             int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
                 if (diaSemana == 1) {
                Valor_dia = "Domingo";
                endSend= seni.plusDays( 4 );
                } else if (diaSemana == 2) {
                Valor_dia = "Lunes";
                endSend= seni.plusDays( 4 );
                } else if (diaSemana == 3) {
                Valor_dia = "Martes";
                endSend= seni.plusDays( 6 );
                } else if (diaSemana == 4) {
                Valor_dia = "Miercoles";
                endSend= seni.plusDays( 6 );
                } else if (diaSemana == 5) {
                Valor_dia = "Jueves";
                endSend= seni.plusDays( 6 );
                } else if (diaSemana == 6) {
                Valor_dia = "Viernes";
                endSend= seni.plusDays( 6 );
                } else if (diaSemana == 7) {
                Valor_dia = "Sabado";
                endSend= seni.plusDays( 5 );
                }
                System.out.println("dayOfTheWeek:"+Valor_dia);
                
                DateTime now = new DateTime( endSend );
               	DateTime endClaim = now.plusDays( 7 );
           	confirmTemp.setDaySellerConfirm(Integer.parseInt(hourdateFormat.format(date)));
           	confirmTemp.setMonthSellerConfirm(Integer.parseInt(hourdateFormat1.format(date)));
           	confirmTemp.setYearSellerConfirm(Integer.parseInt(hourdateFormat2.format(date)));
           	
           	
           	confirmTemp.setDayBuyerConfirm(Integer.parseInt(hourdateFormat.format(endSend.toDate())));           	
           	confirmTemp.setMonthBuyerConfirm(Integer.parseInt(hourdateFormat1.format(endSend.toDate())));           	
           	confirmTemp.setYearBuyerConfirm(Integer.parseInt(hourdateFormat2.format(endSend.toDate())));
           	
           	confirmTemp.setDayInitClaim(Integer.parseInt(hourdateFormat.format(endSend.toDate())));
           	confirmTemp.setMonthInitClaim(Integer.parseInt(hourdateFormat1.format(endSend.toDate())));
           	confirmTemp.setYearInitiClaim(Integer.parseInt(hourdateFormat2.format(endSend.toDate())));
           	
           	confirmTemp.setDayEndClaim(Integer.parseInt(hourdateFormat.format(endClaim.toDate())));
           	confirmTemp.setMonthEndClaim(Integer.parseInt(hourdateFormat1.format(endClaim.toDate())));
           	confirmTemp.setYearEndClaim(Integer.parseInt(hourdateFormat2.format(endClaim.toDate())));
           	confirmTemp.setStatus("confirm");
           	confirmDao.save(confirmTemp);
           	System.out.println("Eddy:"+confirmTemp.getBuy().getYng_item().getUser().getEmail());
           	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "CONFIRMACIÓN DE ENTREGA EXITOSA","Se realizo la confirmacion de la entrega del producto :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice()
           			+ "<br/> --Si tu comprador no tiene ninguna observacion del producto en 7 días podras recoger tu dinero ingresando a : http://www.yingul.com/frontYingulPay");
   			smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "CONFIRMACIÓN DE RECEPCIÓN EXITOSA", "Se realizo la confirmacion de la entrega del producto : "+confirmTemp.getBuy().getQuantity()+" "+confirmTemp.getBuy().getYng_item().getName()+" a:"+confirmTemp.getBuy().getCost()
   					+ "<br/> --Tiene 7 días de garantia con Yingul para realizar alguna observación ingrese a: http://www.yingul.com/userFront/purchases despues de ese lapso no se aceptaran reclamos");
       		return "save";
       	}else {
       		return "el codigo es incorrecto!!!";
       	}
       }
}
