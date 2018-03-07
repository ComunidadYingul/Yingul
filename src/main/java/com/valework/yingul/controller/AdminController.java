package com.valework.yingul.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserDao userDao;
	@Autowired 
	UserService userService;

	@RequestMapping("/isAdmin")
    public boolean getIsAdmin(@RequestHeader("Authorization") String authorization) {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		return userService.isAdmin(yng_User);
    }
    
}
