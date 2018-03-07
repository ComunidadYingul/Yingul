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
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.dao.WireTransferDao;
import com.valework.yingul.model.Yng_Account;
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
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
    public String sellServicePost(@Valid @RequestBody Yng_WireTransfer wireTransfer,@RequestHeader("Authorization") String authorization) throws MessagingException {	
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Account account=accountDao.findByAccountId(wireTransfer.getTransaction().getAccount().getAccountId());
		Yng_User yng_User= userDao.findByUsername(account.getUser().getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			if(wireTransfer.getAmount()<=account.getAvailableMoney()) {
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
				double saldo=account.getAvailableMoney();
				account.setAvailableMoney(saldo-transactionTemp.getAmount());
				wireTransfer.setBank(bankDao.findByBankId(wireTransfer.getBank().getBankId()));
				wireTransfer.setTransaction(transactionDao.save(transactionTemp));
				wireTransfer.setStatus("toDo");
				wireTransferDao.save(wireTransfer);
				accountDao.save(account);
				try {
					smtpMailSender.send(yng_User.getEmail(), "Débito, de su cuenta en YingulPay", "El débito de "+transactionTemp.getAmount()+" $ a través de Tranferencia Bancaria a la cuenta "+wireTransfer.getCbu()+" de la persona "+wireTransfer.getTitularName()+" fue realizado exitosamente su saldo Disponible en Yingul Pay es de: "+account.getAvailableMoney()+" puede ver sus transacciones en: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/frontYingulPay");
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
