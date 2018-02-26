package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.AmbientDao;
import com.valework.yingul.dao.AmenitiesDao;
import com.valework.yingul.dao.BankDao;
import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.ConfortDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.EquipmentDao;
import com.valework.yingul.dao.ExteriorDao;
import com.valework.yingul.dao.ItemCategoryDao;
import com.valework.yingul.dao.ItemImageDao;
import com.valework.yingul.dao.MotorizedConfortDao;
import com.valework.yingul.dao.MotorizedDao;
import com.valework.yingul.dao.MotorizedEquipmentDao;
import com.valework.yingul.dao.MotorizedExteriorDao;
import com.valework.yingul.dao.MotorizedSecurityDao;
import com.valework.yingul.dao.MotorizedSoundDao;
import com.valework.yingul.dao.ProductDao;
import com.valework.yingul.dao.PropertyAmbientDao;
import com.valework.yingul.dao.PropertyAmenitiesDao;
import com.valework.yingul.dao.PropertyDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.SecurityDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.ServiceProvinceDao;
import com.valework.yingul.dao.SoundDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.dao.WireTransferDao;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_ItemImage;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedConfort;
import com.valework.yingul.model.Yng_MotorizedEquipment;
import com.valework.yingul.model.Yng_MotorizedExterior;
import com.valework.yingul.model.Yng_MotorizedSecurity;
import com.valework.yingul.model.Yng_MotorizedSound;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_PropertyAmbient;
import com.valework.yingul.model.Yng_PropertyAmenities;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_ServiceProvince;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_WireTransfer;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.S3Services;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.UserServiceImpl.S3ServicesImpl;
import com.valework.yingul.service.StorageService;

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
	
	
}
