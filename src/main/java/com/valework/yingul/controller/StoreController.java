package com.valework.yingul.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.StoreDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Store;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.S3Services;

@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	UserDao userDao;
	@Autowired
	StoreDao storeDao;
	@Autowired 
	CategoryDao categoryDao;
	@Autowired
	S3Services s3Services;
	@Autowired
	private StandardDao standardDao;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
    public String createStorePost(@Valid @RequestBody Yng_Store store) throws MessagingException {	
		Yng_Store storeTemp = store;
		//para el video de youtube
		if(store.getVideo()!="" && store.getVideo()!=null) {
			if(store.getVideo().contains("youtu")&&store.getVideo().contains("https://")) {
				if(store.getVideo().contains("embed")){}
				else {
					//https://youtu.be/zabDFISMtJI
					if(store.getVideo().contains("https://youtu.be/")) {
						storeTemp.setVideo(store.getVideo().replace("https://youtu.be/", "https://www.youtube.com/embed/"));
					}else {
						storeTemp.setVideo("https://www.youtube.com/embed/"+store.getVideo().substring(store.getVideo().indexOf("=")+1));
					}
				}
			}else {
				store.setVideo(null);
			}
		}
		//fin de video de youtube
		storeTemp.setName(storeTemp.getName().replace(" ", ""));
		Yng_User userTemp =userDao.findByUsername(storeTemp.getUser().getUsername());
		storeTemp.setUser(userTemp);
		if(storeDao.findByUser(userTemp)!=null) {
			return "Ya tienes una tienda en Yingul Shop";
		}
		if(storeDao.findByName(storeTemp.getName())!=null) {
			int aux=0;
			while(storeDao.findByName(storeTemp.getName())!=null){
				aux++;
				storeTemp.setName(storeTemp.getName()+aux);
			}
		}
		if(storeTemp.getMainCategory()!=null) {
			storeTemp.setMainCategory(categoryDao.findByCategoryId(storeTemp.getMainCategory().getCategoryId()));
		}else {
			storeTemp.setMainCategory(null);
		}
		//imagen principal
		String image=storeTemp.getMainImage();
		storeTemp.setMainImage("");
		String extension="";
        String nombre="";
        byte[] bI;
		if(image.equals("sin")) {
			storeTemp.setMainImage("nullMain.jpg");
		}
		else {
			extension=image.substring(11,14);
			if(image.charAt(13)=='e') {
				extension="jpeg";
			}
			nombre="main"+storeTemp.getName();
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			s3Services.uploadFile("store/"+nombre,extension, bI);
			nombre=nombre+"."+extension;   
			storeTemp.setMainImage(nombre);
		}
		//banner
		image=storeTemp.getBannerImage();
		storeTemp.setBannerImage("");
		extension="";
        nombre="";
		if(image.equals("sin")) {
			storeTemp.setBannerImage("nullBanner.jpg");
		}
		else {
			extension=image.substring(11,14);
			if(image.charAt(13)=='e') {
				extension="jpeg";
			}
			nombre="banner"+storeTemp.getName();
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			s3Services.uploadFile("store/"+nombre,extension, bI);
			nombre=nombre+"."+extension;   
			storeTemp.setBannerImage(nombre);
		}
		storeDao.save(storeTemp);
		try {
			smtpMailSender.send(storeTemp.getUser().getEmail(), "Tienda registrada exitosamente", "Su tienda ya esta registrada compartela y encuentrala en: http://www.yingul.com/tiendaOficial/"+storeTemp.getName());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "save";
    }
	
	@RequestMapping("/findByName/{nameStore}")
    public Yng_Store getStoreByName(@PathVariable("nameStore") String nameStore) {
		Yng_Store store = storeDao.findByName(nameStore);
		return store;	
    }
	@RequestMapping("/all")
    public List<Yng_Store> getAllStores(@RequestHeader("X-API-KEY") String XAPIKEY) {
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		return storeDao.findAll();
    	}else {
    		return null;
    	}
    }
}
