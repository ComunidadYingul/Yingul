package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_ResetPassword;
import com.valework.yingul.model.Yng_User;

public interface ResetPasswordDao extends CrudRepository<Yng_ResetPassword, Long>{
	Yng_ResetPassword findByUser(Yng_User user);
}
