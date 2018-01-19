package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Product;

public interface ProductDao extends CrudRepository<Yng_Product, Long>{
	Yng_Product findByProductId(Long id);
	List<Yng_Product> findAll();
}
