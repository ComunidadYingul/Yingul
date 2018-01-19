package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.BusinessDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.BusinessService;

@Service
public class BusinessServiceImpl implements BusinessService{
	

	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BusinessDao businessDao;

	@Override
	public Yng_Business createBusiness(Yng_Business business, Yng_User user) {
		Yng_Business localBusiness=business;
		localBusiness.setYng_User(userDao.findByEmail(user.getEmail()));
		businessDao.save(localBusiness);
		
		
		return localBusiness;
	}


	public List<Yng_Business> findByUser(Yng_User yng_user) {
	
		Long userId=yng_user.getUserId();
		logger.debug(Long.toString(userId));
		List<Yng_Business> businessList=businessDao.findAll().stream()
				.filter(city->userId==city.getYng_User().getUserId())
				.collect(Collectors.toList());

		return businessList;
	}

}
