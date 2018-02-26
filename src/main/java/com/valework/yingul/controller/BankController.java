package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.dao.BankDao;
import com.valework.yingul.model.Yng_Bank;

@RestController
@RequestMapping("/bank")
public class BankController {
	@Autowired 
	private BankDao bankDao;
	
	@RequestMapping("/all")
    public List<Yng_Bank> getAllBank() {
    	return bankDao.findByOrderByNameAsc();
    }

}
