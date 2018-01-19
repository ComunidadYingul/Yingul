package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_ServiceProvince;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.UserService;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService{
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private ServiceDao serviceDao;
	
	@Autowired
	private ProvinceDao provinceDao;
	
	public Yng_Service findByServiceId(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Yng_Service createService(Yng_Service service, Set<Yng_ServiceProvince> cobertureZone) {
		Yng_Service localService = serviceDao.findByServiceId(service.getServiceId());

        if (localService != null) {
            LOG.info("User with service {} already exist. Nothing will be done. ");
        } else {
            for (Yng_ServiceProvince ur : cobertureZone) {
                provinceDao.save(ur.getProvince());
            }
            service.getCobertureZone().addAll(cobertureZone);
            
            localService = serviceDao.save(service);
        }

        return localService;
	}

	public List<Yng_Service> findByItem(Yng_Item yng_item) {
		Long itemId = yng_item.getItemId();
		List<Yng_Service> serviceList = serviceDao.findAll().stream() 			//convert list to stream
                .filter(city -> itemId==city.getYng_Item().getItemId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return serviceList;
	}

}
