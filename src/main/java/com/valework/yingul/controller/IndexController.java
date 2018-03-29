package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.service.ItemService;

@RestController
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
    private ItemService itemService;
	@Autowired
	private StandardDao standardDao;
	
	@RequestMapping("/item/all")
    public List<Yng_Item> findItemList(@RequestHeader("X-API-KEY") String XAPIKEY) {
    	System.out.println(XAPIKEY);
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		List<Yng_Item> itemList = itemService.findByOrderByItemIdDesc();
    		return itemList;
    	}else {
    		return null;
    	}
    }
    
}
