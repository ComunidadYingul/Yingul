package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Confirm;

public interface ConfirmDao extends CrudRepository<Yng_Confirm, Long>{
	Yng_Confirm findByConfirmId(Long confirmId);
	List<Yng_Confirm> findByOrderByConfirmIdDesc();
}
