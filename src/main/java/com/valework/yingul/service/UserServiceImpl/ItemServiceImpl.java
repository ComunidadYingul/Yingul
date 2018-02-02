package com.valework.yingul.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.ItemCategoryService;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.UserService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	ItemCategoryService itemCategoryService;
	
	@Override
	public Yng_Item findByItemId(Long id) {
		return itemDao.findByItemId(id);
	}

	@Override
	public Yng_Item createItem(Yng_Item item, Set<Yng_ItemCategory> itemCategory) {
		Yng_Item localItem = itemDao.findByItemId(item.getItemId());

        if (localItem != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", item.getName());
        } else {
            for (Yng_ItemCategory ur : itemCategory) {
                categoryDao.save(ur.getCategory());
            }
            item.getItemCategory().addAll(itemCategory);
            
            localItem = itemDao.save(item);
        }

        return localItem;
	}

	public List<Yng_Item> findAll() {
		// TODO Auto-generated method stub
		return itemDao.findAll();
	}

	public void save(Yng_Item yng_item) {
		itemDao.save(yng_item);
		
	}

	public List<Yng_Item> findByUser(Yng_User yng_user) {
		Long userId = yng_user.getUserId();
		List<Yng_Item> itemList = itemDao.findAll().stream() 			//convert list to stream
                .filter(city -> userId==city.getUser().getUserId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return itemList;
	}

	public Set<Yng_Item> findServices(List<Yng_Service> serviceList) {
		Set<Yng_Item> listItem = new HashSet<>();
		for (Yng_Service s : serviceList) {
			Yng_Item item=itemDao.findByItemId(s.getYng_Item().getItemId());
    		listItem.add(item);
    	}
		return listItem;
	}

	public Set<Yng_Item> findMotorized(List<Yng_Motorized> motorizedList) {
		Set<Yng_Item> listItem = new HashSet<>();
		for (Yng_Motorized s : motorizedList) {
			Yng_Item item=itemDao.findByItemId(s.getYng_Item().getItemId());
    		listItem.add(item);
    	}
		return listItem;
	}

	@Override
	public Set<Yng_Item> searchMotorized(List<Yng_Motorized> motorizedList, Long categoryId, Long minPrice,
			Long maxPrice, Long minYear, Long maxYear) {
		
		Set<Yng_Item> listItem = new HashSet<>();
		for (Yng_Motorized s : motorizedList) {
			Yng_Item item=itemDao.findByItemId(s.getYng_Item().getItemId());
			
			if(maxYear==0) {
				if(maxPrice==0) {
					if(item.getPrice()>=minPrice && Long.parseLong(s.getMotorizedYear())>=minYear && this.verifyItemByCategory(s.getYng_Item().getItemId(),categoryId)) {
						listItem.add(item);
					}
				}else{
					if(item.getPrice()>=minPrice && item.getPrice()<=maxPrice && Long.parseLong(s.getMotorizedYear())>=minYear && this.verifyItemByCategory(s.getYng_Item().getItemId(),categoryId)) {
						listItem.add(item);
					}
				}	
			}else {
				if(maxPrice==0) {
					if(item.getPrice()>=minPrice && Long.parseLong(s.getMotorizedYear())>=minYear && Long.parseLong(s.getMotorizedYear())<=maxYear && this.verifyItemByCategory(s.getYng_Item().getItemId(),categoryId)) {
						listItem.add(item);
					}
				}else{
					if(item.getPrice()>=minPrice && item.getPrice()<=maxPrice && Long.parseLong(s.getMotorizedYear())>=minYear && Long.parseLong(s.getMotorizedYear())<=maxYear && this.verifyItemByCategory(s.getYng_Item().getItemId(),categoryId)) {
						listItem.add(item);
					}
				}
				
			}
			
    	}
		return listItem;
	}

	public boolean verifyItemByCategory(Long itemId, Long categoryId) {
		Yng_Category yng_Category = categoryDao.findByCategoryId(categoryId);
    	List<Yng_ItemCategory> itemCategoryList = itemCategoryService.findByCategory(yng_Category); 
    	for (Yng_ItemCategory s : itemCategoryList) {
    		if(s.getItem().getItemId()==itemId) {
    			return true;
    		}
    	}
    	return false;
    	
	}

	public Set<Yng_Item> findProperty(List<Yng_Property> propertyList) {
		Set<Yng_Item> listItem = new HashSet<>();
		for (Yng_Property s : propertyList) {
			Yng_Item item=itemDao.findByItemId(s.getYng_Item().getItemId());
    		listItem.add(item);
    	}
		return listItem;
	}

	public Set<Yng_Item> searchProperty(List<Yng_Property> propertyList, Long categoryId, Long cityId) {
		Set<Yng_Item> listItem = new HashSet<>();
		for (Yng_Property s : propertyList) {
			Yng_Item item=itemDao.findByItemId(s.getYng_Item().getItemId());
			if(cityId==0) {
				if(this.verifyItemByCategory(s.getYng_Item().getItemId(),categoryId)) {
					listItem.add(item);
				}
			}else{
				if(categoryId==0) {
					if( cityId == s.getYng_Item().getYng_Ubication().getYng_City().getCityId()) {
						listItem.add(item);
					}
				}else {
					if( cityId == s.getYng_Item().getYng_Ubication().getYng_City().getCityId() && this.verifyItemByCategory(s.getYng_Item().getItemId(),categoryId)) {
						listItem.add(item);
					}
				}
			}	
			
    	}
		return listItem;
	}


	public List<Yng_Item> findByOrderByItemIdDesc() {
		
		return itemDao.findByOrderByItemIdDesc();
	}	
}
