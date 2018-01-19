package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Barrio;

public interface BarrioDao extends CrudRepository<Yng_Barrio, Long>{
	List<Yng_Barrio> findAll();
	Yng_Barrio findByBarrioId(int barrioId);
}
