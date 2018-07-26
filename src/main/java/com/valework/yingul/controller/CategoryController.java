package com.valework.yingul.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.service.CategoryService;



@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
    private CategoryService categoryService;
	@Autowired 
	private CategoryDao categoryDao;
	@Autowired
	private StandardDao standardDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired ItemController itemController;
	
	@RequestMapping("/all")
    public List<Yng_Category> findCategoryList() {
        List<Yng_Category> categoryList = categoryService.findAll();
        return categoryList;
    }
    @RequestMapping("/{item_type}/{level}")
    public List<Yng_Category> findCategoryListByLevel(@PathVariable("item_type") String item_type,@PathVariable("level") int level,@RequestHeader("X-API-KEY") String XAPIKEY) {
    	List<Yng_Category> categoryList = categoryService.findByItemTypeAndLevel(item_type,level);
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		return categoryList;
    	}else {
    		return null;
    	} 
    	
    }
    @RequestMapping("/father/{father}")
    public List<Yng_Category> findCategoryListByFather(@PathVariable("father") Long father) {
    	 List<Yng_Category> categoryList = categoryService.findByFatherId(father);
         return categoryList;
    }

    @RequestMapping("/categories/{name}")
    public List<Yng_Category> findCategoryByName(@PathVariable("name") String name) {
        List<Yng_Category> categoryList = categoryService.findByName(name);
        if(categoryList.size()>10) {
        	return categoryList.subList(0, 10);
        }
        else {
        	return categoryList;
        }
    }
    @RequestMapping("/bestMatch/{name}")
    public String findBestMatch(@PathVariable("name") String name) {
		List<Yng_Item> itemList = itemDao.findByOrderByItemIdAsc();
		for (Yng_Item yng_Item : itemList) {
			if(yng_Item.getName().toLowerCase().replace(" ","").contains(name.toLowerCase().replace(" ",""))){
				List<Yng_ItemCategory> itemCategoryList = itemController.findCategoriesByItem(yng_Item.getItemId());
				return "/"+itemCategoryList.get(0).getCategory().getCategoryId();
			}
		}
		System.out.println("esta random");
		return "/"+(int) (Math.random() * 5000);
    }
    @RequestMapping("/getCategory/{categoryId}")
    public Yng_Category findCategoryById(@PathVariable("categoryId") Long categoryId) {
        Yng_Category categoryTemp = categoryDao.findByCategoryId(categoryId);
        return categoryTemp;
    }
    @RequestMapping("/getTypeCategory/{categoryId}")
    public String findTypeCategoryById(@PathVariable("categoryId") Long categoryId) {
        Yng_Category categoryTemp = categoryDao.findByCategoryId(categoryId);
        while(categoryTemp.getLevel()!=0) {
        	categoryTemp=categoryDao.findByCategoryId(categoryTemp.getFatherId());
        }
        return categoryTemp.getItemType();
    }
    @RequestMapping("/fatherForItemTypeAndNamecategory/{itemType}/{name}")
    public Set<Yng_Category> fatherForItemTypeAndNamecategory(@PathVariable("itemType") String itemType,@PathVariable("name") String name) {
        if(name.equals("temporario")) {
        	name="Alquiler temporario";
        }
    	List<Yng_Category> categoryList = categoryDao.findByItemTypeAndNameOrderByNameAsc(itemType,name);
        Set<Yng_Category> fathers= new HashSet<>();
        for (Yng_Category yng_Category : categoryList) {
        	fathers.add(categoryDao.findByCategoryId(yng_Category.getFatherId()));
		}
        return fathers;
    }
    @RequestMapping("/categoryForFatherAndNamecategory/{fatherId}/{name}")
    public Yng_Category categoryForFatherAndNamecategory(@PathVariable("fatherId") Long fatherId,@PathVariable("name") String name) {
        if(name.equals("temporario")) {
        	name="Alquiler temporario";
        }
    	Yng_Category category = categoryDao.findByFatherIdAndNameOrderByNameAsc(fatherId,name);

        return category;
    }
    
}
