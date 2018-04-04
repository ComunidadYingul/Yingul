package com.valework.yingul.batch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.CommissionDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.TransactionDetailDao;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Commission;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_TransactionDetail;
import com.valework.yingul.service.MotorizedService;
import com.valework.yingul.service.PersonService;
import com.valework.yingul.service.PropertyService;

@Component
public class GreetingBatchBean {
	
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

	
	//@Scheduled(cron = "0,30 * * * * *")//para cada 30 segundos
	@Scheduled(cron = "0 0 6 * * *")//cada dia a las 6 de la mañana
	public void cronJob() throws ParseException {
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
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");;
    		Date endClaim= formatter.parse(str_date);
			if(date.after(endClaim)) {
				s.setStatus("closed");
				Yng_Account accountTemp= accountDao.findByUser(s.getBuy().getYng_item().getUser());
				//crear la transaccion con todo el costo del producto para luego descontar comisiones o costo de envio
				Yng_Transaction transactionTemp = new Yng_Transaction();
				transactionTemp.setAmount(s.getBuy().getYng_item().getPrice());
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
				
				List<Yng_Person> personList= personService.findByUser(s.getBuy().getYng_item().getUser());
				Yng_Person person = personList.get(0);
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
				transactionDetail.setCostCommission(((s.getBuy().getYng_item().getPrice()*commission.getPercentage())/100)+commission.getFixedPrice());
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
	
	
}
