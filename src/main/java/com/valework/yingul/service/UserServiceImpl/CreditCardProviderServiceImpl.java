package com.valework.yingul.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valework.yingul.dao.CardProviderDao;
import com.valework.yingul.dao.CreditCardProviderDao;
import com.valework.yingul.model.Yng_CardProvider;
import com.valework.yingul.model.Yng_CreditCardProvider;
import com.valework.yingul.model.Yng_ListCreditCard;
import com.valework.yingul.service.CreditCardProviderService;

@Service
public class CreditCardProviderServiceImpl implements CreditCardProviderService{
	@Autowired 
	CreditCardProviderDao creditCardProviderDao;
	@Autowired
	CardProviderDao cardProviderDao;
	
	@Override
	public List<Yng_CardProvider> findByListCreditCard(Yng_ListCreditCard yng_ListCreditCard) {
		Long  listCreditCardId = yng_ListCreditCard.getListCreditCardId();
		List<Yng_CreditCardProvider> creditCardProviderList = creditCardProviderDao.findAll().stream() 			//convert list to stream
                .filter(creditCardProvider -> listCreditCardId==creditCardProvider.getListCreditCard().getListCreditCardId())	//filters the line, equals to username
                .collect(Collectors.toList());
		ArrayList<Yng_CardProvider> cardProviderList= new ArrayList<Yng_CardProvider>(creditCardProviderList.size());
		for(int i=0; i<creditCardProviderList.size();i++) {
			Yng_CardProvider temp = cardProviderDao.findByCardProviderId(creditCardProviderList.get(i).getCardProvider().getCardProviderId());
			cardProviderList.add(temp);
			//cardProviderList.add(cardProviderDao.findByCardProviderId(creditCardProviderList.get(i).getCardProvider().getCardProviderId()));
		}
		//logger.info("Eddy"+cardProviderList.toString());
        return cardProviderList;
	}
	
}
