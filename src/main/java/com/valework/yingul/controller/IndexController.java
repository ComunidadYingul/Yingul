package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.service.ItemService;

@RestController
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
    private ItemService itemService;
	
	@RequestMapping("/item/all")
    public List<Yng_Item> findItemList() {
        List<Yng_Item> itemList = itemService.findAll();
        return itemList;
    }
    
}
