package com.valework.yingul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_User;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired 
	UserDao userDao;
	
	@RequestMapping("/{username}")
    public Yng_User findByUsername(@PathVariable("username") String username) {
        return userDao.findByUsername(username);
    }
}
