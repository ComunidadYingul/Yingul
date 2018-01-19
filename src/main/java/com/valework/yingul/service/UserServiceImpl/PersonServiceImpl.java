package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService{

	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Override
	public Yng_Person createPerson(Yng_Person person, Yng_User user) {
		Yng_Person localPerson=person;
		localPerson.setYng_User(userDao.findByEmail(user.getEmail()));
		personDao.save(localPerson);
		return localPerson;
		
	}

	public List<Yng_Person> findByUser(Yng_User yng_user) {
		Long userId = yng_user.getUserId();
		logger.debug(Long.toString(userId));
		List<Yng_Person> personList = personDao.findAll().stream() 			//convert list to stream
                .filter(city -> userId==city.getYng_User().getUserId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return personList;
	}
	
}
