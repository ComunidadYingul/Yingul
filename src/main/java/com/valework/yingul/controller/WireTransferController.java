package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.BankDao;
import com.valework.yingul.dao.CommissionDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.dao.WireTransferDao;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Commission;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_WireTransfer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/wireTransfer")
public class WireTransferController {

	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	private UserDao userDao;
	@Autowired 
	private AccountDao accountDao;
	@Autowired
	private TransactionDao transactionDao;
	@Autowired 
	private BankDao bankDao;
	@Autowired
	private WireTransferDao wireTransferDao;
	@Autowired 
	private CommissionDao commissionDao;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
    public String createWireTransfer(@Valid @RequestBody Yng_WireTransfer wireTransfer,@RequestHeader("Authorization") String authorization) throws MessagingException {	
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Account account=accountDao.findByAccountId(wireTransfer.getTransaction().getAccount().getAccountId());
		Yng_User yng_User= userDao.findByUsername(account.getUser().getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			Yng_Commission commission=commissionDao.findByConditionAndWhy("All","WireTransfer");
			Double commissionCost=(double)Math.round(((commission.getPercentage()*wireTransfer.getAmount()/100)+commission.getFixedPrice()) * 100d) / 100d;
			wireTransfer.setAmount((double)Math.round(wireTransfer.getAmount() * 100d) / 100d);
			if(((double)Math.round((wireTransfer.getAmount()+commissionCost)* 100d) / 100d)<=account.getAvailableMoney()) {
				Yng_Transaction transactionTemp = wireTransfer.getTransaction();
				transactionTemp.setAccount(account);
				Date date = new Date();
		    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
		    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
		    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
		    	DateFormat hourdateFormat4 = new SimpleDateFormat("HH");
		    	DateFormat hourdateFormat5 = new SimpleDateFormat("mm");
		    	DateFormat hourdateFormat6 = new SimpleDateFormat("ss");
		    	transactionTemp.setDay(Integer.parseInt(hourdateFormat.format(date)));
		    	transactionTemp.setMonth(Integer.parseInt(hourdateFormat1.format(date)));
		    	transactionTemp.setYear(Integer.parseInt(hourdateFormat2.format(date)));
		    	transactionTemp.setHour(Integer.parseInt(hourdateFormat4.format(date)));
				transactionTemp.setMinute(Integer.parseInt(hourdateFormat5.format(date)));
				transactionTemp.setSecond(Integer.parseInt(hourdateFormat6.format(date)));
				transactionTemp.setType("Debito");
				transactionTemp.setDescription("Debito a través de transferencia bancaria");
				transactionTemp.setAYingulTransaction(false);
				transactionTemp.setAWireTransfer(true);
				transactionTemp.setInvoiceStatus("unrequited");
				transactionTemp.setTypeCode("DTB");
				double saldo=account.getAvailableMoney();
				account.setAvailableMoney((double)Math.round((saldo-transactionTemp.getAmount()) * 100d) / 100d);
				wireTransfer.setBank(bankDao.findByBankId(wireTransfer.getBank().getBankId()));
				wireTransfer.setTransaction(transactionDao.save(transactionTemp));
				wireTransfer.setStatus("toDo");
				wireTransferDao.save(wireTransfer);
				account=accountDao.save(account);
				if(commissionCost>0) {
					Yng_Transaction transactionTemp1 = new Yng_Transaction();
					transactionTemp1.setAccount(account);
					transactionTemp1.setAmount(commissionCost);
					transactionTemp1.setCity("Moreno");
					transactionTemp1.setCountry("Argentina");
					transactionTemp1.setCountryCode("AR");
					transactionTemp1.setCurrency("ARS");
					transactionTemp1.setDay(Integer.parseInt(hourdateFormat.format(date)));
					transactionTemp1.setDescription("Costo de transacciones bancarias a travez de YingulPay");
					transactionTemp1.setIp("181.115.199.143");
					transactionTemp1.setLat("-16.5");
					transactionTemp1.setLon("-68.15");
					transactionTemp1.setMonth(Integer.parseInt(hourdateFormat1.format(date)));
					transactionTemp1.setOrg("Entel S.A. - EntelNet");
					transactionTemp1.setRegionName("Buenos Aires");
					transactionTemp1.setType("Débito");
					transactionTemp1.setYear(Integer.parseInt(hourdateFormat2.format(date)));
					transactionTemp1.setZip("1744");
					transactionTemp1.setHour(Integer.parseInt(hourdateFormat4.format(date)));
					transactionTemp1.setMinute(Integer.parseInt(hourdateFormat5.format(date)));
					transactionTemp1.setSecond(Integer.parseInt(hourdateFormat6.format(date)));
					transactionTemp1.setAWireTransfer(false);
					transactionTemp1.setAYingulTransaction(true);
					transactionTemp1.setInvoiceStatus("unrequited");
					transactionTemp1.setTypeCode("CTB");
					saldo=account.getAvailableMoney();
					account.setAvailableMoney((double)Math.round((saldo-transactionTemp1.getAmount()) * 100d) / 100d);
					account=accountDao.save(account);
					transactionDao.save(transactionTemp1);
				}
				try {
					smtpMailSender.send(yng_User.getEmail(), "Débito, de su cuenta en YingulPay", "El débito de "+transactionTemp.getAmount()+" $ a través de Tranferencia Bancaria a la cuenta "+wireTransfer.getCbu()+" de la persona "+wireTransfer.getTitularName()+" fue realizado exitosamente su saldo Disponible en Yingul Pay es de: "+account.getAvailableMoney()+" para ver el detalle de sus transacciones ingrese a: https://www.yingul.com/frontYingulPay"
					+ "<p>Cordialemente:</p>\r\n"  
					+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
					+ "<p>Su equípo de transferencias Yingul Company SRL</p>" +
					"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "save";
			}else {
				return "Monto superior a tu saldo en YingulPay";
			}
			
		}else {
			return "Usuario o contraseña Incorrecta";
		}   
    }
	
	@RequestMapping("/list/all")
    public List<Yng_WireTransfer> getAllWireTransfer() { 
        return wireTransferDao.findByOrderByWireTransferIdDesc();
    }
    
    @RequestMapping("/list/toDo")
    public List<Yng_WireTransfer> getToDoWireTransfer() { 
        return wireTransferDao.findByStatusOrderByWireTransferIdDesc("toDo");
    }
    
    @RequestMapping("/list/complete")
    public List<Yng_WireTransfer> getCompleteWireTransfer() { 
        return wireTransferDao.findByStatusOrderByWireTransferIdDesc("complete");
    }
    @RequestMapping("/update/{wireTransferId}")
    public String updateWireTransfer(@PathVariable("wireTransferId") Long wireTransferId) {
    	Yng_WireTransfer wireTransfer = wireTransferDao.findByWireTransferId(wireTransferId);
    	wireTransfer.setStatus("complete");
    	wireTransferDao.save(wireTransfer);
        return "save";
    }
    
}
