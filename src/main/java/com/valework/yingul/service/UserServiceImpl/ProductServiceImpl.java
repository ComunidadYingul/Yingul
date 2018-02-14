package com.valework.yingul.service.UserServiceImpl;


import java.util.List;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.ProductDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;

	public List<Yng_Product> findByItem(Yng_Item yng_item) {
		Long itemId = yng_item.getItemId();
		List<Yng_Product> productList = productDao.findAll().stream() 			//convert list to stream
                .filter(product -> itemId==product.getYng_Item().getItemId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return productList;
	}

	
	public Yng_Product findByItemId(Long I) {
		Long itemId=I;
		List<Yng_Product> productLis=productDao.findAll().stream()
				.filter(product ->itemId==product.getYng_Item().getItemId())
				.collect(Collectors.toList());
		return null;
	}


	@Override
	public boolean findByItemIdExist(Long idItem) {
		Long itemId=idItem;
		boolean exist=false;
		List<Yng_Product> productLisTem=productDao.findAll();// TODO Auto-generated method stub
		//Set<Yng_Product> productList=new HashSet<>();
		for(Yng_Product p:productLisTem) {
			//System.out.println("p.getYng_Item().getItemId() : "+p.getYng_Item().getItemId()+"idItem"+idItem);
			Long id=p.getYng_Item().getItemId();
			if(id.equals(itemId)) {
				//System.out.println("p.getYng_Item().getItemId() : "+p.getYng_Item().getItemId()+"idItem"+idItem);
				exist=true;
			};
		}
	
		return exist;
	}

}
