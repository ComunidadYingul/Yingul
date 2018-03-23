package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.PersonService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired 
	UserDao userDao;
	@Autowired 
	PersonService personService;
	@RequestMapping("/{username}")
    public Yng_User findByUsername(@PathVariable("username") String username) {
        return userDao.findByUsername(username);
    }
	@RequestMapping("/person/{username}")
    public Yng_Person getPerson(@PathVariable("username") String username) {
		Yng_User yng_User = userDao.findByUsername(username); 
		List<Yng_Person> personList= personService.findByUser(yng_User);
		Yng_Person person = personList.get(0);
		return person;	
    }
}
