package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.CategoryDao;
//import com.valework.yingul.dao.CityDao;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
    private CategoryDao categoryDao;
	
	public List<Yng_Category> findAll() {
		return categoryDao.findByOrderByNameAsc();
	}

	public List<Yng_Category> findByItemTypeAndLevel(String itemType,int level) {
		return categoryDao.findByItemTypeAndLevelOrderByNameAsc(itemType,level);
	}

	public List<Yng_Category> findByFatherId(Long father) {
		// TODO Auto-generated method stub
		return categoryDao.findByFatherIdOrderByNameAsc(father);
	}

	public List<Yng_Category> findByName(String name) {
		List<Yng_Category> categoryList = categoryDao.findByOrderByNameAsc().stream() 			//convert list to stream
                .filter(category -> category.getName().toLowerCase().replace(" ","").contains(name.toLowerCase().replace(" ","")))	//filters the line, equals to username
                .collect(Collectors.toList());
		return categoryList;
	}
	
}
