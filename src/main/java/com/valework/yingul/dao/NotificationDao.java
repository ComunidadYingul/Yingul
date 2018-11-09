package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Notification;
import com.valework.yingul.model.Yng_User;

public interface NotificationDao extends CrudRepository<Yng_Notification, Long> {
	List<Yng_Notification> findByUserAndStatusOrderByNotificationIdDesc(Yng_User user,String status);
	List<Yng_Notification> findByUserOrderByNotificationIdDesc(Yng_User user);
	Yng_Notification findByNotificationId(long notificationId);
	List<Yng_Notification> findByItem(Yng_Item item);
}
