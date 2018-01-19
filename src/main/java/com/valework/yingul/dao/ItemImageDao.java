package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_ItemImage;

public interface ItemImageDao extends CrudRepository<Yng_ItemImage, Long> {
	List<Yng_ItemImage> findAll();

}
