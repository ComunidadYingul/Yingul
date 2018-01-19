package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_User;

public interface UserDao extends CrudRepository<Yng_User, Long> {
	Yng_User findByUsername(String username);
    Yng_User findByEmail(String email);
    List<Yng_User> findAll();
	
}
