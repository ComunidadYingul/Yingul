package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
    private CategoryService categoryService;
	
	@RequestMapping("/all")
    public List<Yng_Category> findCategoryList() {
        List<Yng_Category> categoryList = categoryService.findAll();
        return categoryList;
    }
    @RequestMapping("/{item_type}/{level}")
    public List<Yng_Category> findCategoryListByLevel(@PathVariable("item_type") String item_type,@PathVariable("level") int level) {
    	 List<Yng_Category> categoryList = categoryService.findByItemTypeAndLevel(item_type,level);
         return categoryList;
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
    	List<Yng_Category> categoryList = categoryService.findByName(name);
    	if(categoryList.isEmpty()) {
    		System.out.println("/"+(int) (Math.random() * 5000));
    		return "/"+(int) (Math.random() * 5000);
    		
    	}else {
    		System.out.println("/"+categoryList.get(0).getCategoryId());
    		return "/"+categoryList.get(0).getCategoryId();
        }

    }
}
