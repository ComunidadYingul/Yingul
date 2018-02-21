package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_User;

public interface AccountDao extends CrudRepository<Yng_Account, Long> {
	Yng_Account findByUser(Yng_User user);
}
