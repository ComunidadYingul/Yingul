package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.CardDao;
import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.CardService;

@Service
@Transactional
public class CardServiceImpl implements CardService{
	@Autowired
	private CardDao cardDao;
	@Override
	public List<Yng_Card> findByUser(Yng_User yng_User) {
		Long userId = yng_User.getUserId();
		List<Yng_Card> cardList = cardDao.findAll().stream() 			//convert list to stream
                .filter(card -> userId==card.getUser().getUserId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return cardList;
	}

}
