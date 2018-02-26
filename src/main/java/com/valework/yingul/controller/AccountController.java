package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.TransactionDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Transaction;
import com.valework.yingul.model.Yng_User;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired 
	private UserDao userDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired 
	private TransactionDao transactionDao;
	
	@RequestMapping("/getAccountByUser/{username}")
    public Yng_Account getAccoutnByUser(@PathVariable("username") String username,@RequestHeader("Authorization") String authorization) {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User = userDao.findByUsername(username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			if(!yng_User.isAccountNonExpired()||!yng_User.isAccountNonLocked()) {
				return null;
			}else {
				Yng_Account yng_Account=accountDao.findByUser(yng_User);
				if(!yng_Account.isAccountNonLocked()||!yng_Account.isAccountNonExpired()) {
					return null;
				}else {
					return yng_Account;
				}
			}
		}else {
			return null;
		}
    }
	
	@RequestMapping("/getTransactionsByUser/{username}")
	@ResponseBody
	public List<Yng_Transaction> getTransactionByUser(@PathVariable("username") String username){
		Yng_User yng_User = userDao.findByUsername(username);
		if(!yng_User.isAccountNonExpired()||!yng_User.isAccountNonLocked()) {
			return null;
		}else {
			Yng_Account yng_Account=accountDao.findByUser(yng_User);
			if(!yng_Account.isAccountNonLocked()||!yng_Account.isAccountNonExpired()) {
				return null;
			}else {
				return transactionDao.findByAccountOrderByTransactionIdDesc(yng_Account);
			}
		}
	}

}
