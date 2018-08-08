package com.valework.yingul.batch;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.http.client.ClientProtocolException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.PayUFunds;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.CommissionDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.PaymentDao;
import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.TransactionDetailDao;
import com.valework.yingul.logistic.GetStateSend;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Commission;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Payment;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_StateShipping;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_TransactionDetail;
import com.valework.yingul.service.MotorizedService;
import com.valework.yingul.service.PersonService;
import com.valework.yingul.service.PropertyService;

@Component
public class GreetingBatchBean {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	ConfirmDao confirmDao;
	@Autowired
	TransactionDao transactionDao;
	@Autowired
	AccountDao accountDao;

	@Autowired 
	CommissionDao commissionDao;
	@Autowired
	PersonService personService;
	@Autowired
	PropertyService propertyService;
	@Autowired 
	MotorizedService motorizedService;

	@Autowired
	StandardDao standardDao;
	@Autowired
	TransactionDetailDao transactionDetailDao;
	@Autowired
	PaymentDao paymentDao;
	@Autowired
	ItemDao itemDao;
	@Autowired
	PayUFunds payUFunds;
	@Autowired
	PersonDao personDao;
	
	//@Scheduled(cron = "0,30 * * * * *")//para cada 30 segundos
	//@Scheduled(cron = "0 0 6 * * *")//cada dia a las 6 de la mañana
	//@Scheduled(cron = "0 0/16 12 * * ?")//cada 8 minutos desde las 10:45
	@Scheduled(cron = "0 30/16 17 * * ?")//cada 8 minutos desde las 10:45
	public void cronJob() throws ParseException {
		System.out.println("primer cron");
		Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
    	DateFormat hourdateFormat4 = new SimpleDateFormat("HH");
    	DateFormat hourdateFormat5 = new SimpleDateFormat("mm");
    	DateFormat hourdateFormat6 = new SimpleDateFormat("ss");
		List<Yng_Confirm> listConfirm = confirmDao.findByStatus("confirm");
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
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    		Date endClaim= formatter.parse(str_date);
    		DateTime dateTime = new DateTime(endClaim);
    		dateTime = dateTime.plus(1);
    		endClaim = dateTime.toDate();
			if(date.after(endClaim)) {
				s.setStatus("closed");
				Yng_Account accountTemp= accountDao.findByUser(s.getBuy().getYng_item().getUser());
				//crear la transaccion con todo el costo del producto para luego descontar comisiones o costo de envio
				Yng_Transaction transactionTemp = new Yng_Transaction();
				transactionTemp.setAmount(s.getBuy().getCost());
				transactionTemp.setCity("Moreno");
				transactionTemp.setCountry("Argentina");
				transactionTemp.setCountryCode("AR");
				transactionTemp.setCurrency("ARS");
				transactionTemp.setDay(Integer.parseInt(hourdateFormat.format(date)));
				transactionTemp.setDescription("Acreditación por venta del producto ");
				transactionTemp.setIp("181.115.199.143");
				transactionTemp.setLat("-16.5");
				transactionTemp.setLon("-68.15");
				transactionTemp.setMonth(Integer.parseInt(hourdateFormat1.format(date)));
				transactionTemp.setOrg("Entel S.A. - EntelNet");
				transactionTemp.setRegionName("Buenos Aires");
				transactionTemp.setType("Acreditacion");
				transactionTemp.setYear(Integer.parseInt(hourdateFormat2.format(date)));
				transactionTemp.setZip("1744");
				transactionTemp.setHour(Integer.parseInt(hourdateFormat4.format(date)));
				transactionTemp.setMinute(Integer.parseInt(hourdateFormat5.format(date)));
				transactionTemp.setSecond(Integer.parseInt(hourdateFormat6.format(date)));
				transactionTemp.setAWireTransfer(false);
				transactionTemp.setAYingulTransaction(true);
				double saldo=accountTemp.getAvailableMoney();
				accountTemp.setAvailableMoney(saldo+transactionTemp.getAmount());
				accountTemp=accountDao.save(accountTemp);
				transactionTemp.setAccount(accountTemp);
				transactionDao.save(transactionTemp);
				
				//transaccion del cobro de comisiones
				Yng_Transaction commissionTemp = new Yng_Transaction();
				commissionTemp.setCity("Moreno");
				commissionTemp.setCountry("Argentina");
				commissionTemp.setCountryCode("AR");
				commissionTemp.setCurrency("ARS");
				commissionTemp.setDay(Integer.parseInt(hourdateFormat.format(date)));
				commissionTemp.setDescription("Cobro de transaccion por venta a travez de Yingul Pay");
				commissionTemp.setIp("181.115.199.143");
				commissionTemp.setLat("-16.5");
				commissionTemp.setLon("-68.15");
				commissionTemp.setMonth(Integer.parseInt(hourdateFormat1.format(date)));
				commissionTemp.setOrg("Entel S.A. - EntelNet");
				commissionTemp.setRegionName("Buenos Aires");
				commissionTemp.setType("Débito");
				commissionTemp.setYear(Integer.parseInt(hourdateFormat2.format(date)));
				commissionTemp.setZip("1744");
				commissionTemp.setHour(Integer.parseInt(hourdateFormat4.format(date)));
				commissionTemp.setMinute(Integer.parseInt(hourdateFormat5.format(date)));
				commissionTemp.setSecond(Integer.parseInt(hourdateFormat6.format(date)));
				commissionTemp.setAWireTransfer(false);
				commissionTemp.setAYingulTransaction(true);
				double costCommission=0;
				Yng_TransactionDetail transactionDetail=new Yng_TransactionDetail();
				System.out.println(s.getBuy().getYng_item().getUser().getUsername());
				List<Yng_Person> personList= personDao.findAll();
				Yng_Person person = new Yng_Person();
				for (Yng_Person yng_Person : personList) {
					if(yng_Person.getYng_User().getUsername().equals(s.getBuy().getYng_item().getUser().getUsername())) {
						person = yng_Person;
						System.out.println(person.getName()+" todo bien");
					}
				}
				
				Yng_Commission commission= new Yng_Commission();
				Yng_Commission commissionPAYU= new Yng_Commission();
				
				//condicional para el cobro de comisiones				
				if(s.getBuy().getShippingCost()==0) {
					//cobro de comisiones
					
					switch(s.getBuy().getYng_item().getType()) {
					case "Product":
						if(person.isBusiness()) {
							commission =commissionDao.findByToWhoAndWhy("Business", "Product");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
						}else {
							commission =commissionDao.findByToWhoAndWhy("Person", "Product");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
						}
						break;
					case "Property":
						if(s.getBuy().getYng_item().getCondition().equals("Rental")) {
							commission =commissionDao.findByConditionAndWhy("Rental", "Property");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
						}else {
							commission =commissionDao.findByToWhoAndWhy("All", "Property");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
						}
						break;
					case "Motorized":
						if(s.getBuy().getYng_item().getCondition().equals("New")) {
							commission =commissionDao.findByConditionAndWhy("New", "Motorized");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
						}else {
							commission =commissionDao.findByConditionAndWhy("All", "All");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
						}
						break;
					default:
						commission =commissionDao.findByConditionAndWhy("All", "All");
						commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
						break;
					}
					//
				}else {
					if(s.getBuy().getYng_item().getProductPagoEnvio().equals("gratis")) {
						//cobro de comisiones
						Yng_Transaction payShipping = new Yng_Transaction();
						payShipping.setCity("Moreno");
						payShipping.setCountry("Argentina");
						payShipping.setCountryCode("AR");
						payShipping.setCurrency("ARS");
						payShipping.setDay(Integer.parseInt(hourdateFormat.format(date)));
						payShipping.setDescription("Cobro de envio por venta a travez de Yingul Express");
						payShipping.setIp("181.115.199.143");
						payShipping.setLat("-16.5");
						payShipping.setLon("-68.15");
						payShipping.setMonth(Integer.parseInt(hourdateFormat1.format(date)));
						payShipping.setOrg("Entel S.A. - EntelNet");
						payShipping.setRegionName("Buenos Aires");
						payShipping.setType("Débito");
						payShipping.setYear(Integer.parseInt(hourdateFormat2.format(date)));
						payShipping.setZip("1744");
						payShipping.setHour(Integer.parseInt(hourdateFormat4.format(date)));
						payShipping.setMinute(Integer.parseInt(hourdateFormat5.format(date)));
						payShipping.setSecond(Integer.parseInt(hourdateFormat6.format(date)));
						payShipping.setAWireTransfer(false);
						payShipping.setAYingulTransaction(true);
						payShipping.setAmount(s.getBuy().getShippingCost());
						saldo=accountTemp.getAvailableMoney();
						accountTemp.setAvailableMoney(saldo-payShipping.getAmount());
						accountTemp=accountDao.save(accountTemp);
						payShipping.setAccount(accountTemp);
						transactionDao.save(payShipping);
						
						switch(s.getBuy().getYng_item().getType()) {
						case "Product":
							if(person.isBusiness()) {
								commission =commissionDao.findByToWhoAndWhy("Business", "Product");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}else {
								commission =commissionDao.findByToWhoAndWhy("Person", "Product");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}
							break;
						case "Property":
								if(s.getBuy().getYng_item().getCondition().equals("Rental")) {
									commission =commissionDao.findByConditionAndWhy("Rental", "Property");
									commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
								}else {
									commission =commissionDao.findByToWhoAndWhy("All", "Property");
									commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
								}
							break;
						case "Motorized":
							if(s.getBuy().getYng_item().getCondition().equals("New")) {
								commission =commissionDao.findByConditionAndWhy("New", "Motorized");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}else {
								commission =commissionDao.findByConditionAndWhy("All", "All");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}
							break;
						default:
							commission =commissionDao.findByConditionAndWhy("All", "All");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							break;
						}
						//
					}else {
						//cobro de comisiones
						switch(s.getBuy().getYng_item().getType()) {
						case "Product":						
							if(person.isBusiness()) {
								commission =commissionDao.findByToWhoAndWhy("Business", "Product");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}else {
								commission =commissionDao.findByToWhoAndWhy("Person", "Product");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}
							break;
						case "Property":
								if(s.getBuy().getYng_item().getCondition().equals("Rental")) {
									commission =commissionDao.findByConditionAndWhy("Rental", "Property");
									commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
								}else {
									commission =commissionDao.findByToWhoAndWhy("All", "Property");
									commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
								}
							break;
						case "Motorized":
							if(s.getBuy().getYng_item().getCondition().equals("New")) {
								commission =commissionDao.findByConditionAndWhy("New", "Motorized");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}else {
								commission =commissionDao.findByConditionAndWhy("All", "All");
								commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							}
							break;
						default:
							commission =commissionDao.findByConditionAndWhy("All", "All");
							commissionPAYU = commissionDao.findByConditionAndWhy("ARS", "PAYU");
							break;
						}
						//
					}
				}
				transactionDetail.setCostCommission(((s.getBuy().getCost()*commission.getPercentage())/100)+commission.getFixedPrice());
				transactionDetail.setCostPAYU(((s.getBuy().getCost()*commissionPAYU.getPercentage())/100)+commissionPAYU.getFixedPrice());
				costCommission=(transactionDetail.getCostCommission()+transactionDetail.getCostPAYU());
				transactionDetail.setCostTotal(costCommission);
				
				commissionTemp.setAmount(costCommission);
				saldo=accountTemp.getAvailableMoney();
				accountTemp.setAvailableMoney(saldo-commissionTemp.getAmount());
				accountTemp=accountDao.save(accountTemp);
				commissionTemp.setAccount(accountTemp);
				transactionDetail.setTransaction(transactionDao.save(commissionTemp));
				transactionDetailDao.save(transactionDetail);

				confirmDao.save(s);
			}
    	}
	}
	
	//@Scheduled(cron = "0,59 * * * * *")//para cada 30 segundos
	//@Scheduled(cron = "0 0 4 * * *")//cada dia a las 5 de la mañana
	@Scheduled(cron = "0 34/16 17 * * ?")//cada 8 minutos desde las 10:45
	public void cronJob1() throws ClientProtocolException, IOException, Exception {
		System.out.println("segundo cron");
		List<Yng_Payment> confirmCashPayment= paymentDao.findByTypeAndStatusAndBuyStatus("CASH","PENDING","PENDING");
		for (Yng_Payment s : confirmCashPayment) {
			Date fecha = new Date();
			if(s.getCashPayment().getExpiration().before(fecha)) {
				s.setBuyStatus("EXPIRED");
				s=paymentDao.save(s);
				ObjectMapper mapper = new ObjectMapper();
				Yng_Buy buyTemp = new Yng_Buy();
				try {
					buyTemp = mapper.readValue(s.getCashPayment().getBuyJson(), Yng_Buy.class);
					// System.out.println(token.toString());	 
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Yng_Item itemTemp= itemDao.findByItemId(buyTemp.getYng_item().getItemId());
				itemTemp.setQuantity(itemTemp.getQuantity()+1);
				itemTemp.setEnabled(true);
				itemTemp=itemDao.save(itemTemp);
			}else {
				if(payUFunds.queryByOrderId(s).equals("save")){
					s.setBuyStatus("AUTHORIZED");
					s=paymentDao.save(s);
				}else {
					//poner algo para guardar los que no se pudo por alguna razon
				}
			}
			
		}
	}
	
	//@Scheduled(cron = "0,59 * * * * *")//para cada 30 segundos
	//@Scheduled(cron = "0 0 5 * * *")//cada dia a las 6 de la mañana
	@Scheduled(cron = "0 38/16 17 * * ?")//cada 8 minutos desde las 10:45
	public void deliveryConfirmation() throws MessagingException{
		System.out.println("tercer cron");
		List<Yng_Confirm> listConfirm = confirmDao.findByStatus("pending");
		for (Yng_Confirm s : listConfirm) {
			Yng_Confirm confirmTemp=s;
			if(confirmTemp.getBuy().getShipping()!=null) {
		    	if(confirmTemp.getBuy().getShipping().getTypeShipping().equals("branch")) {
		    		String confirmStateDao=standardDao.findByKey("codeConfirmAndreani").getValue();
		    		Yng_StateShipping stateShipping=new Yng_StateShipping();
			    	GetStateSend getState = new GetStateSend();
			    	String confirmState=confirmTemp.getBuy().getShipping().getYng_Shipment().getShipmentCod();
			    	String stateApi ="";
			    	try {
			    		stateShipping=getState.sendState(""+confirmState);
			    		stateApi=stateShipping.getEstado();
			    		System.out.println("state:"+stateApi+":"+confirmStateDao);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		String status=stateApi;
		    		Yng_Standard codeDeliveryConfirmAndreani = standardDao.findByKey("codeDeliveryConfirmAndreani");//entre ala sucurlas el envio ingresado 
		    		if(status.equals(codeDeliveryConfirmAndreani.getValue())) {
		    			confirmTemp.setBuyerConfirm(false);
		        		confirmTemp.setSellerConfirm(true);
		        		Date date = new Date();
		            	DateFormat hourdateFormat = new SimpleDateFormat("dd");
		            	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
		            	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
		            	DateTime now = new DateTime( date );
		            	confirmTemp.setDaySellerConfirm(Integer.parseInt(hourdateFormat.format(date)));
		            	confirmTemp.setMonthSellerConfirm(Integer.parseInt(hourdateFormat1.format(date)));
		            	confirmTemp.setYearSellerConfirm(Integer.parseInt(hourdateFormat2.format(date)));
		            	confirmTemp.setStatus("delivered");
		            	confirmDao.save(confirmTemp);
		            	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "CONFIRMACIÓN DE ENTREGA EXITOSA","Se realizo la confirmacion de la entrega del producto en una sucursal andreani:  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice()
		            			+ "<br/>--Despues de que tu comprador recoja el producto tendra 10 dias vigentes para realizar reclamos acerca del producto.");
		            	DateTime endClaim = now.plusDays(4);
		            	smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "CONFIRMACIÓN DE ENTREGA EXITOSA", "Tu vendedor realizo la entrega del producto : "+confirmTemp.getBuy().getQuantity()+" "+confirmTemp.getBuy().getYng_item().getName()+" a:"+confirmTemp.getBuy().getCost()+" en la sucursal andreani"
		    					+ "<br/>--Puedes recoger el producto de la sucursal Andreani desde "+Integer.parseInt(hourdateFormat.format(endClaim.toDate()))+"/"+Integer.parseInt(hourdateFormat1.format(endClaim.toDate()))+"/"+Integer.parseInt(hourdateFormat2.format(endClaim.toDate())));
		    		}		
		    	}
			}
		}
	}
	
	//@Scheduled(cron = "0 0 7 * * *")//cada dia a las 6 de la mañana
	@Scheduled(cron = "0 42/16 17 * * ?")//cada 8 minutos desde las 10:51
	public void whithdrawalConfirmation() throws MessagingException{
		System.out.println("cuarto cron");
		List<Yng_Confirm> listConfirm = confirmDao.findByStatus("delivered");
		for (Yng_Confirm s : listConfirm) {
			Yng_Confirm confirmTemp=s;
			//codeConfirmAndreani
	       	String confirmStateDao=standardDao.findByKey("codeConfirmAndreani").getValue();
	       	//System.out.println("confirmTemp:"+confirmTemp.toString()+" value:"+);
	       	Yng_StateShipping stateShipping=new Yng_StateShipping();
	    	GetStateSend getState = new GetStateSend();
	    	String confirmState=confirmTemp.getBuy().getShipping().getYng_Shipment().getShipmentCod();
	    	String stateApi ="";
	    	try {
	    		stateShipping=getState.sendState(""+confirmState);
	    		stateApi=stateShipping.getEstado();
	    		System.out.println("state:"+stateApi+":"+confirmStateDao);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			String status=stateApi;//envio entregado al comprador
			Yng_Standard codeWhithdrawalConfirmAndreani = standardDao.findByKey("codeWhithdrawalConfirmAndreani");
	    	if(status.equals(codeWhithdrawalConfirmAndreani.getValue())) {
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
	            	confirmTemp.setMonthBuyerConfirm(Integer.parseInt(hourdateFormat1.format(date)));
	            	confirmTemp.setYearBuyerConfirm(Integer.parseInt(hourdateFormat2.format(date)));
	            	confirmTemp.setDayInitClaim(Integer.parseInt(hourdateFormat.format(date)));
	            	confirmTemp.setMonthInitClaim(Integer.parseInt(hourdateFormat1.format(date)));
	            	confirmTemp.setYearInitiClaim(Integer.parseInt(hourdateFormat2.format(date)));
	            	confirmTemp.setDayEndClaim(Integer.parseInt(hourdateFormat.format(endClaim.toDate())));
	            	confirmTemp.setMonthEndClaim(Integer.parseInt(hourdateFormat1.format(endClaim.toDate())));
	            	confirmTemp.setYearEndClaim(Integer.parseInt(hourdateFormat2.format(endClaim.toDate())));
	            	confirmTemp.setStatus("confirm");
	            	confirmDao.save(confirmTemp);
	            	smtpMailSender.send(confirmTemp.getBuy().getYng_item().getUser().getEmail(), "CONFIRMACIÓN DE RECEPCIÓN EXITOSA","Tu comprador realizo la confirmacion de la recepción del producto :  "+confirmTemp.getBuy().getYng_item().getName() +"  Descripción : "+confirmTemp.getBuy().getYng_item().getDescription()+ "  " +"  Precio: " +confirmTemp.getBuy().getYng_item().getPrice()+" de la suscursal Andreani"
	            			+ "<br/>--Si tu comprador no tiene ninguna observacion del producto en "+daysForClaims.getValue()+" días podras recoger tu dinero ingresando a : http://www.yingul.com/frontYingulPay");
	    			smtpMailSender.send(confirmTemp.getBuy().getUser().getEmail(), "CONFIRMACIÓN DE RECEPCIÓN EXITOSA", "Se realizo la confirmacion de la recepción del producto : "+confirmTemp.getBuy().getQuantity()+" "+confirmTemp.getBuy().getYng_item().getName()+" a:"+confirmTemp.getBuy().getCost()+" de la sucursal Andreani"
	    					+ "<br/> --Tiene "+daysForClaims.getValue()+" días de garantia con Yingul para realizar alguna observación ingrese a: http://www.yingul.com/userFront/claims despues de ese lapso no se aceptaran reclamos.");
	    	}
		}
	}
}
