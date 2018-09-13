package com.valework.yingul.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.FavoriteDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Favorite;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_User;

@RestController
@RequestMapping("/favorite")
public class FavoritesController {
	
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	ItemDao itemDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FavoriteDao favoriteDao;

	@RequestMapping("/create/{itemId}/{username}")
    public String createFavorite(@PathVariable("itemId") Long itemId,@PathVariable("username") String username) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		Yng_User yng_User = userDao.findByUsername(username);
		if(favoriteDao.findByItemAndUser(yng_Item, yng_User)!=null) {
			return "Este item ya esta incluido en tu lista de favoritos";
		}
		Yng_Favorite yng_Favorite = new Yng_Favorite(); 
		yng_Favorite.setItem(yng_Item);
		yng_Favorite.setUser(yng_User);
		if(yng_Item.getUser().getUsername()==yng_User.getUsername()) {
    		return "no puedes agregar a favoritos producos, servicios, inmuebles o vehiculos propios";
    	}
    	else {
			favoriteDao.save(yng_Favorite);
		    /*try {
				smtpMailSender.send(yng_User.getEmail(), "Yingul favoritos", "Agregaste a favoritos "+yng_Item.getName()+" "+yng_Item.getDescription()+" para ver tus productos, servicios, inmuebles o vehiculos favoritos ingresa a: https://www.yingul.com/favorites");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return "save";
    	}	
    }
	@RequestMapping("/delete/{itemId}/{username}")
    public String deleteFavorite(@PathVariable("itemId") Long itemId,@PathVariable("username") String username) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		Yng_User yng_User = userDao.findByUsername(username);
		if(favoriteDao.findByItemAndUser(yng_Item, yng_User)!=null) {
			Yng_Favorite temp=favoriteDao.findByItemAndUser(yng_Item, yng_User);
			favoriteDao.delete(temp);
			return "save";
		}
    	else {
			return "algo salio mal vuelve a intentarlo";
    	}	
    }
	@RequestMapping("/getFavorite/{username}")
    public List<Yng_Favorite> getFavorite(@PathVariable("username") String username) {
		Yng_User user=userDao.findByUsername(username);
		List<Yng_Favorite> favoriteList = favoriteDao.findByUserOrderByFavoriteIdDesc(user);
        return favoriteList;
    }
    @RequestMapping(value = "/deleteFavorites", method = RequestMethod.POST)
	@ResponseBody
    public String deleteFavorites(@Valid @RequestBody Long[] deleteList) throws MessagingException {
    	for (Long st : deleteList) {
    		Yng_Favorite yng_Favorite = favoriteDao.findByFavoriteId(st);
			favoriteDao.delete(yng_Favorite);
		}
		return "save";
    }
    @RequestMapping("/getItemFavorite/{username}")
    public Set<Yng_Item> getItemFavorite(@PathVariable("username") String username) {
		Yng_User user=userDao.findByUsername(username);
		List<Yng_Favorite> favoriteList = favoriteDao.findByUserOrderByFavoriteIdDesc(user);
		Set<Yng_Item> listItemTemp = new HashSet<>();
    	for (Yng_Favorite st : favoriteList) {
    		listItemTemp.add(st.getItem());
		}
        return listItemTemp;
    }
    @RequestMapping("/itemIsFavorite/{itemId}/{username}")
    public String itemIsFavorite(@PathVariable("itemId") Long itemId,@PathVariable("username") String username) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		Yng_User yng_User = userDao.findByUsername(username);
		if(favoriteDao.findByItemAndUser(yng_Item, yng_User)!=null) {
			return "isFavorite";
		}
    	else {
			return "noFavorite";
    	}	
    }
}
