package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.CardProviderDao;
import com.valework.yingul.model.Yng_CardProvider;
import com.valework.yingul.model.Yng_ListCreditCard;
import com.valework.yingul.service.CardProviderService;

@Service
public class CardProviderServiceImpl implements CardProviderService{

	@Autowired
	CardProviderDao cardProviderDao;
	
	public List<Yng_CardProvider> findByListCreditCard(Yng_ListCreditCard yng_ListCreditCard) {

        return null;
	}

}
