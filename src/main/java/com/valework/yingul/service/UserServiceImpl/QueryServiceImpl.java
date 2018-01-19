package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.QueryDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Query;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.QueryService;

@Service
public class QueryServiceImpl implements QueryService{
	
	@Autowired
	QueryDao queryDao;
	
	public List<Yng_Query> findByItem(Yng_Item yng_item) {
		Long itemId = yng_item.getItemId();
		List<Yng_Query> queryList = queryDao.findAll().stream() 			//convert list to stream
                .filter(query -> itemId==query.getYng_Item().getItemId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return queryList;
	}

	public List<Yng_Query> findByUser(Yng_User yng_user) {
		Long userId = yng_user.getUserId();
		List<Yng_Query> queryList = queryDao.findAll().stream() 			//convert list to stream
                .filter(query -> userId==query.getYng_Item().getUser().getUserId())	//filters the line, equals to username
                .filter(query -> null==query.getAnswer())
                .collect(Collectors.toList());
		
        return queryList;
	}
	
}
