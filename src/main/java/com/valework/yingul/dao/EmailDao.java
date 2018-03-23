package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Email;

public interface EmailDao extends CrudRepository<Yng_Email, Long> {

}
