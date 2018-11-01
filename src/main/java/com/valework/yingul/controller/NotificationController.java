package com.valework.yingul.controller;

import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.dao.NotificationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Notification;
import com.valework.yingul.model.Yng_User;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	@Autowired 
	UserDao userDao;
	@Autowired
	NotificationDao notificationDao;
		
	@RequestMapping("/getNotificationByUserAndStatus/{username}/{status}/{start}/{end}")
    public List<Yng_Notification> getNotificationByUserAndStatus(@PathVariable("username") String username,@PathVariable("status") String status,@PathVariable("start") int start,@PathVariable("end") int end,@RequestHeader("Authorization") String authorization) {
		Yng_User user = userDao.findByUsername(username); 
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(user.getUsername().equals(yng_User.getUsername()) && yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			List<Yng_Notification> notificationList = new ArrayList<Yng_Notification>();
			if(status.equals("all")) {
				notificationList = notificationDao.findByUserOrderByNotificationIdDesc(user);
			}else {
				notificationList = notificationDao.findByUserAndStatusOrderByNotificationIdDesc(user,status);
			}
    		if(end==0) {
    			end=notificationList.size();
    		}
			if(notificationList.size()>=start) {
    			if(end>=notificationList.size()) {
    				notificationList=notificationList.subList(start, notificationList.size());	
    			}else{
    				notificationList=notificationList.subList(start, end);	
    			}
    		}
            return notificationList;
		}else {
			return null;
		}
			
    }
    
    @RequestMapping(value = "/updateAllNotificationsForUser/{username}/{status}", method = RequestMethod.POST)
	@ResponseBody
    public String updateAllNotificationsForUser(@PathVariable("status") String status, @PathVariable("username") String username,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User user= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(user.getUsername().equals(parts[0])  && encoder.matches(parts[1], user.getPassword())){
			List<Yng_Notification> notificationList = new ArrayList<Yng_Notification>();
			notificationList = notificationDao.findByUserOrderByNotificationIdDesc(user);
			for (Yng_Notification notification : notificationList) {
				notification.setStatus(status);
				notification= notificationDao.save(notification);
			}
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
    
    @RequestMapping(value = "/updateNotificationsForUser/{username}/{notificationId}/{status}", method = RequestMethod.POST)
	@ResponseBody
    public String updateNotificationsForUser(@PathVariable("notificationId") long notificationId,@PathVariable("status") String status, @PathVariable("username") String username,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User user= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(user.getUsername().equals(parts[0])  && encoder.matches(parts[1], user.getPassword())){
			Yng_Notification notification = notificationDao.findByNotificationId(notificationId);
			notification.setStatus(status);
			notification= notificationDao.save(notification);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	
	

   
}
