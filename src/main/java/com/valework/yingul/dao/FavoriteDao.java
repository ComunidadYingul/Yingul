package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Favorite;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_User;

public interface FavoriteDao extends CrudRepository<Yng_Favorite, Long> {
	Yng_Favorite findByFavoriteId(Long favoriteId);
	Yng_Favorite findByItemAndUser(Yng_Item item, Yng_User user);
	List<Yng_Favorite> findByUserOrderByFavoriteIdDesc(Yng_User user);
	List<Yng_Favorite> findByItem(Yng_Item item);
}
