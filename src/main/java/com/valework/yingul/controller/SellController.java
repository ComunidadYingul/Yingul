package com.valework.yingul.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AmbientDao;
import com.valework.yingul.dao.AmenitiesDao;
import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.dao.BranchAndreaniDao;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.ConfortDao;
import com.valework.yingul.dao.CountryDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.EquipmentDao;
import com.valework.yingul.dao.ExteriorDao;
import com.valework.yingul.dao.ItemCategoryDao;
import com.valework.yingul.dao.ItemImageDao;
import com.valework.yingul.dao.MotorizedConfortDao;
import com.valework.yingul.dao.MotorizedDao;
import com.valework.yingul.dao.MotorizedEquipmentDao;
import com.valework.yingul.dao.MotorizedExteriorDao;
import com.valework.yingul.dao.MotorizedSecurityDao;
import com.valework.yingul.dao.MotorizedSoundDao;
import com.valework.yingul.dao.ProductDao;
import com.valework.yingul.dao.PropertyAmbientDao;
import com.valework.yingul.dao.PropertyAmenitiesDao;
import com.valework.yingul.dao.PropertyDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.SecurityDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.ServiceProvinceDao;
import com.valework.yingul.dao.SoundDao;
import com.valework.yingul.dao.StandarCostAndreaniDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.dao.YingulRequestDao;
import com.valework.yingul.logistic.PropertyObjectHttp;
import com.valework.yingul.logistic.RequestPropertyHeders;
import com.valework.yingul.logistic.http;
import com.valework.yingul.model.FacebookPhoto;
import com.valework.yingul.model.Yng_BranchAndreani;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.model.Yng_Country;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_ItemImage;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedConfort;
import com.valework.yingul.model.Yng_MotorizedEquipment;
import com.valework.yingul.model.Yng_MotorizedExterior;
import com.valework.yingul.model.Yng_MotorizedSecurity;
import com.valework.yingul.model.Yng_MotorizedSound;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_PropertyAmbient;
import com.valework.yingul.model.Yng_PropertyAmenities;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_ServiceProvince;
import com.valework.yingul.model.Yng_StandarCostAndreani;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_YingulRequest;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.S3Services;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.UserServiceImpl.S3ServicesImpl;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.valework.yingul.service.StorageService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


@RestController
@RequestMapping("/sell")
public class SellController {
	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired 
	ItemImageDao itemImageDao;
	
	@Autowired 
	ItemService itemService;
	
	@Autowired 
	ServiceService serviceService;
	
	@Autowired
	BarrioDao barrioDao;
	
	@Autowired 
	CityDao cityDao;
	
	@Autowired 
	ProvinceDao provinceDao;
	
	@Autowired
	DepartmentDao departmentDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired 
	ServiceDao serviceDao;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	PropertyDao propertyDao;
	
	@Autowired
	MotorizedDao motorizedDao;
	
	@Autowired 
	UbicationDao ubicationDao;
	
	@Autowired
	StorageService storageService;

	@Autowired
	ItemCategoryDao itemCategoryDao; 
	
	@Autowired
	ServiceProvinceDao serviceProvinceDao;
	
	@Autowired
	MotorizedSecurityDao motorizedSecurityDao;
	
	@Autowired
	MotorizedConfortDao motorizedConfortDao;
	
	@Autowired
	MotorizedEquipmentDao motorizedEquipmentDao;
	
	@Autowired
	MotorizedExteriorDao notorizedExteriorDao;
	
	@Autowired
	MotorizedSoundDao motorizedSoundDao;
	
	@Autowired
	PropertyAmbientDao propertyAmbietDao;
	
	@Autowired
	PropertyAmenitiesDao propertyAmenitiesDao;
	
	@Autowired
	PropertyAmenitiesDao propertiesAmenitiesDao;
	
	@Autowired 
	CountryDao countryDao;

	@Autowired
	SecurityDao securityDao;
	
	@Autowired
	ConfortDao confortDao;
	
	@Autowired
	EquipmentDao equipmentDao;
	
	@Autowired
	ExteriorDao exteriorDao;
	
	@Autowired
	SoundDao soundDao;
	
	@Autowired
	AmbientDao ambientDao;
	
	@Autowired
	AmenitiesDao amenitiesDao;
	
	
	@Autowired
	S3Services s3Services;
	
	@Autowired
	StandarCostAndreaniDao standarCostAndreaniDao;
	@Autowired
	YingulRequestDao yingulRequestDao; 
	@Autowired
	BranchAndreaniDao branchAndreaniDao;
	@Autowired
	StandardDao standardDao;
	@RequestMapping(value = "/service", method = RequestMethod.POST)
	@ResponseBody
	public String sellServicePost(@Valid @RequestBody Yng_Service service) throws MessagingException, IOException {	
//capturar objeto inicio
		ObjectMapper mapper = new ObjectMapper();
		java.util.Date fecha = new Date();
		System.out.println (fecha);
		try {
			String jsonInString = mapper.writeValueAsString(service);
			System.out.println("jsonInString"+jsonInString);
			Yng_YingulRequest serviceJson=new Yng_YingulRequest();
			serviceJson.setJson(jsonInString);
			serviceJson.setDate(fecha);
			yingulRequestDao.save(serviceJson);
		} catch (JsonProcessingException e1) {
			
			e1.printStackTrace();
		}
//capturar objeto fin	
		
		String ruta="Servicios";
		Yng_Service serviceTemp = service;
		Yng_Item itemTemp=serviceTemp.getYng_Item();
		//setear fecha de publicacion del item
		Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
    	itemTemp.setDayPublication(Integer.parseInt(hourdateFormat.format(date)));
    	itemTemp.setMonthPublication(Integer.parseInt(hourdateFormat1.format(date)));
    	itemTemp.setYearPublication(Integer.parseInt(hourdateFormat2.format(date)));
		//
		//obtenemos la lista de las imagenes 
		Set<Yng_ItemImage> itemImage=serviceTemp.getYng_Item().getItemImage();
		//borramos las imagenes 
		serviceTemp.getYng_Item().setItemImage(null);
		//obtenemos la lista de categorias
		Set<Yng_ItemCategory> itemCategory = new HashSet<>();
		itemCategory=serviceTemp.getYng_Item().getItemCategory();
		//borramos la lista de cagorias para que no se inserte dos veces
		serviceTemp.getYng_Item().setItemCategory(null);
		//para setear la ubicacion del item 
		Yng_Ubication ubicationTemp = new Yng_Ubication();
		ubicationTemp.setStreet(serviceTemp.getYng_Item().getYng_Ubication().getStreet());
		ubicationTemp.setNumber(serviceTemp.getYng_Item().getYng_Ubication().getNumber());
		ubicationTemp.setPostalCode(serviceTemp.getYng_Item().getYng_Ubication().getPostalCode());
		ubicationTemp.setAditional(serviceTemp.getYng_Item().getYng_Ubication().getAditional());
		Yng_Country countrySw=countryDao.findByCountryId(serviceTemp.getYng_Item().getYng_Ubication().getYng_Country().getCountryId());
		if(!countrySw.isToSell()) {
			return "Tu país todavia no esta habilitado para vender en Yingul estamos trabjando en ello";
		}
		ubicationTemp.setYng_Country(countryDao.findByCountryId(serviceTemp.getYng_Item().getYng_Ubication().getYng_Country().getCountryId()));
		ubicationTemp.setYng_Province(provinceDao.findByProvinceId(serviceTemp.getYng_Item().getYng_Ubication().getYng_Province().getProvinceId()));
		ubicationTemp.setYng_City(cityDao.findByCityId(serviceTemp.getYng_Item().getYng_Ubication().getYng_City().getCityId()));	
		//ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(serviceTemp.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
        Yng_Ubication ubicationTempo=ubicationDao.save(ubicationTemp);
        itemTemp.setYng_Ubication(ubicationTempo);
		//para setear el usuario
		Yng_User userTemp= userDao.findByUsername(itemTemp.getUser().getUsername());
		userTemp.setPhone(itemTemp.getUser().getPhone());
		userTemp.setPhone2(itemTemp.getUser().getPhone2());
		userTemp.setWebSite(itemTemp.getUser().getWebSite());
		userTemp.setYng_Ubication(ubicationTempo);
		userTemp=userDao.save(userTemp);
		itemTemp.setUser(userTemp);
		//hasta aqui para el usuario
		//volvemos la imagen nulo para que no guarde en la base de datos
		String image=itemTemp.getPrincipalImage();
		itemTemp.setPrincipalImage("");
		itemService.save(itemTemp);
        Yng_Item temp = itemService.findByItemId(itemTemp.getItemId());
        for (Yng_ItemCategory s : itemCategory) {
        	ruta= ruta +"/"+s.getCategory().getName();
        	s.setItem(temp);
        	itemCategoryDao.save(s);	    
		}
        //imagen principal
        String extension;
        String nombre;
        byte[] bI;
        logger.info(String.valueOf(image.charAt(0)));
		if(image.charAt(0)=='s') {
			temp.setPrincipalImage("sin.jpg");
		}
		else {
			extension="jpeg";
			nombre="principal"+temp.getItemId();
			logger.info(extension);
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			bI=convertImage(bI);
			s3Services.uploadFile(nombre,extension, bI);
			nombre=nombre+"."+extension;   
			temp.setPrincipalImage(nombre);
		}
		temp.setType("Service");
		temp.setQuantity(1);
		temp.setOver(false);
		temp.setEnabled(true);
		itemService.save(temp);
		int k=0;
		for (Yng_ItemImage st : itemImage) {
        	k++;
        	image=st.getImage();
    		st.setImage("");
    		extension="jpeg";
    		nombre="img"+k+temp.getItemId();
    		logger.info(extension);
    		bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
    		bI=convertImage(bI);
    		s3Services.uploadFile(nombre,extension, bI);
    		nombre=nombre+"."+extension;   
    		st.setImage(nombre);
    		st.setItem(temp);	
        	itemImageDao.save(st);	    
		}
		  
        serviceTemp.setYng_Item(temp);
        //obtenemos la lista de provincia de la zona de cobertura
  		Set<Yng_ServiceProvince> serviceProvince = new HashSet<>();
  		serviceProvince=serviceTemp.getCobertureZone();
  		//borramos la lista de cobertura para que no se inserte dos veces
  		serviceTemp.setCobertureZone(null);
        Yng_Service serz = serviceDao.save(serviceTemp);
        for (Yng_ServiceProvince si : serviceProvince) {
        	si.setProvince(provinceDao.findByProvinceId(si.getProvince().getProvinceId()));
        	si.setService(serz);
        	serviceProvinceDao.save(si);	    
		}
        try {
			smtpMailSender.send(userTemp.getEmail(), "Servicio registrado exitosamente", "Su servicio ya esta registrado en las categorias de: "+ruta+
					"<br> puede encontrarlo y compartirlo en: www.yingul.com/itemDetail/"+serz.getYng_Item().getItemId());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("save");
        facebookPostPhoto(temp);
        return "save";
    }
	
	
	
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseBody
    public String sellProducPost(@Valid @RequestBody Yng_Product product) throws MessagingException, IOException  {
		//capturar objeto inicio
				ObjectMapper mapper = new ObjectMapper();
				java.util.Date fecha = new Date();
				System.out.println (fecha);
				try {
					String jsonInString = mapper.writeValueAsString(product);
					System.out.println("jsonInString"+jsonInString);
					Yng_YingulRequest serviceJson=new Yng_YingulRequest();
					serviceJson.setJson(jsonInString);
					serviceJson.setDate(fecha);
					yingulRequestDao.save(serviceJson);
				} catch (JsonProcessingException e1) {
					
					e1.printStackTrace();
				}
		//capturar objeto fin	
				
		String ruta="Productos";

		Yng_Product productTemp=product;
		Yng_Item itemTemp=productTemp.getYng_Item();
		Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
    	itemTemp.setDayPublication(Integer.parseInt(hourdateFormat.format(date)));
    	itemTemp.setMonthPublication(Integer.parseInt(hourdateFormat1.format(date)));
    	itemTemp.setYearPublication(Integer.parseInt(hourdateFormat2.format(date)));
		   	//Yng_Service serviceTemp = service;
			//Yng_Item itemTemp=serviceTemp.getYng_Item();
		//obtenemos la lista de las imagenes 
		Set<Yng_ItemImage> itemImage=productTemp.getYng_Item().getItemImage();
		//borramos las imagenes 
		productTemp.getYng_Item().setItemImage(null);
		//obtenemos la lista de categorias
		Set<Yng_ItemCategory> itemCategory = new HashSet<>();
		itemCategory=productTemp.getYng_Item().getItemCategory();
		//borramos la lista de cagorias para que no se inserte dos veces
		productTemp.getYng_Item().setItemCategory(null);
		//para setear la ubicacion del item 
		Yng_Ubication ubicationTemp = new Yng_Ubication();
		ubicationTemp.setStreet(productTemp.getYng_Item().getYng_Ubication().getStreet());
		ubicationTemp.setNumber(productTemp.getYng_Item().getYng_Ubication().getNumber());
		ubicationTemp.setPostalCode(productTemp.getYng_Item().getYng_Ubication().getPostalCode());
		ubicationTemp.setAditional(productTemp.getYng_Item().getYng_Ubication().getAditional());
		Yng_Country countrySw=countryDao.findByCountryId(productTemp.getYng_Item().getYng_Ubication().getYng_Country().getCountryId());
		if(!countrySw.isToSell()) {
			return "Tu país todavia no esta habilitado para vender en Yingul estamos trabjando en ello";
		}
		ubicationTemp.setYng_Country(countrySw);
		ubicationTemp.setYng_Province(provinceDao.findByProvinceId(productTemp.getYng_Item().getYng_Ubication().getYng_Province().getProvinceId()));
		ubicationTemp.setYng_City(cityDao.findByCityId(productTemp.getYng_Item().getYng_Ubication().getYng_City().getCityId()));	
		//ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(productTemp.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
		String codAndreani="";
		LogisticsController log=new LogisticsController();
		Yng_BranchAndreani branchAndreani=new Yng_BranchAndreani();
		try {
			branchAndreani=log.andreaniSucursales(ubicationTemp.getPostalCode(), "", "");
			codAndreani=branchAndreani.getCodAndreani();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		branchAndreaniDao.save(branchAndreani);
		ubicationTemp.setCodAndreani(""+codAndreani);
		Yng_Ubication ubicationTempo= new Yng_Ubication();
		ubicationTempo=ubicationDao.save(ubicationTemp);
        
        itemTemp.setYng_Ubication(ubicationTempo);
		//para setear el usuario
		Yng_User userTemp= userDao.findByUsername(itemTemp.getUser().getUsername());
		//userTemp.setPhone(itemTemp.getUser().getPhone());
		//userTemp.setPhone2(itemTemp.getUser().getPhone2());
		//userTemp.setWebSite(itemTemp.getUser().getWebSite());
		userTemp.setYng_Ubication(ubicationTempo);// System.out.println(""+ubicationTempo.toString());
		userTemp=userDao.save(userTemp);
		itemTemp.setUser(userTemp);
		//hasta aqui para el usuario
		//volvemos la imagen nulo para que no guarde en la base de datos
		String image=itemTemp.getPrincipalImage();
		itemTemp.setPrincipalImage("");
		itemService.save(itemTemp);
        Yng_Item temp = itemService.findByItemId(itemTemp.getItemId());
        for (Yng_ItemCategory s : itemCategory) {
        	ruta= ruta +"/"+s.getCategory().getName();
        	s.setItem(temp);
        	itemCategoryDao.save(s);	    
		}
        //imagen principal
        
        String extension;
        String nombre;
        byte[] bI;
        logger.info(String.valueOf(image.charAt(0)));
		if(image.charAt(0)=='s') {
			temp.setPrincipalImage("sin.jpg");
			logger.info("si funciono");
		}
		else {
			logger.info("no funciono");
			extension="jpeg";
			nombre="principal"+temp.getItemId();
			logger.info(extension);
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			bI=convertImage(bI);
			s3Services.uploadFile(nombre,extension, bI);
			nombre=nombre+"."+extension;   
			temp.setPrincipalImage(nombre);
		}
        
		temp.setType("Product");
		temp.setOver(false);
		temp.setEnabled(true);
		if(productTemp.getProductCondition().equals("Nuevo")) {
			temp.setCondition("New");
		}else {
			temp.setCondition("Used");
		}
		temp.setProductPagoEnvio(productTemp.getProductPagoEnvio());
		if(temp.getProductPagoEnvio().equals("gratis") || temp.getPriceDiscount()>0) {
			temp.setOver(true);
		}
		itemService.save(temp);
		int k=0;
		for (Yng_ItemImage st : itemImage) {
        	k++;
        	image=st.getImage();
    		st.setImage("");
    		extension="jpeg";
    		nombre="img"+k+temp.getItemId();
    		logger.info(extension);
    		bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
    		bI=convertImage(bI);
    		s3Services.uploadFile(nombre,extension, bI);
    		nombre=nombre+"."+extension;   
    		st.setImage(nombre);
    		st.setItem(temp);	
        	itemImageDao.save(st);	    
		}
		  
		productTemp.setYng_Item(temp);
        //obtenemos la lista de provincia de la zona de cobertura
  		//Set<Yng_ServiceProvince> serviceProvince = new HashSet<>();
  		//serviceProvince=productTemp.getCobertureZone();
  		//borramos la lista de cagorias para que no se inserte dos veces
  		//serviceTemp.setCobertureZone(null);
       // Yng_Product serz =
        productTemp=productDao.save(productTemp);
       /* for (Yng_ServiceProvince si : serviceProvince) {
        	si.setProvince(provinceDao.findByProvinceId(si.getProvince().getProvinceId()));
        	si.setService(serz);
        	serviceProvinceDao.save(si);	    
		}*/
        try {
			smtpMailSender.send(userTemp.getEmail(), "Producto registrado exitosamente", "Su producto ya esta registrado en las categorias de: "+ruta+
					"<br> puede encontrarlo y compartirlo en: www.yingul.com/itemDetail/"+productTemp.getYng_Item().getItemId());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Yng_Item item=productTemp.getYng_Item();
		facebookPostPhoto(item);
		
        return "save";
    }
	
		
	@RequestMapping(value = "/property", method = RequestMethod.POST)
	@ResponseBody
    public String sellPropertyPost(@Valid @RequestBody Yng_Property property) throws MessagingException , IOException {	
		//capturar objeto inicio
		ObjectMapper mapper = new ObjectMapper();
		java.util.Date fecha = new Date();
		System.out.println (fecha);
		try {
			String jsonInString = mapper.writeValueAsString(property);
			System.out.println("jsonInString"+jsonInString);
			Yng_YingulRequest serviceJson=new Yng_YingulRequest();
			serviceJson.setJson(jsonInString);
			serviceJson.setDate(fecha);
			yingulRequestDao.save(serviceJson);
		} catch (JsonProcessingException e1) {
			
			e1.printStackTrace();
		}
//capturar objeto fin
		String ruta="Property";

		Yng_Property propertyTemp=property;
		Yng_Item itemTemp=propertyTemp.getYng_Item();
		Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
    	itemTemp.setDayPublication(Integer.parseInt(hourdateFormat.format(date)));
    	itemTemp.setMonthPublication(Integer.parseInt(hourdateFormat1.format(date)));
    	itemTemp.setYearPublication(Integer.parseInt(hourdateFormat2.format(date)));
		   	//Yng_Service serviceTemp = service;
			//Yng_Item itemTemp=serviceTemp.getYng_Item();
		//obtenemos la lista de las imagenes 
		Set<Yng_ItemImage> itemImage=propertyTemp.getYng_Item().getItemImage();
		//borramos las imagenes 
		propertyTemp.getYng_Item().setItemImage(null);
		//obtenemos la lista de categorias
		Set<Yng_ItemCategory> itemCategory = new HashSet<>();
		itemCategory=propertyTemp.getYng_Item().getItemCategory();
		for (Yng_ItemCategory t : itemCategory) {
			Yng_Category cate = new Yng_Category();
			cate=categoryDao.findByCategoryId(t.getItemCategoryId());
			//if((t.getCategory().getName()).contains("Alquiler")) {
			if(cate.getName().contains("Alquiler")) {
				propertyTemp.setCondition("Rental");
			}
		}
		//borramos la lista de cagorias para que no se inserte dos veces
		propertyTemp.getYng_Item().setItemCategory(null);
		//para setear la ubicacion del item 
		Yng_Ubication ubicationTemp = new Yng_Ubication();
		ubicationTemp.setStreet(propertyTemp.getYng_Item().getYng_Ubication().getStreet());
		ubicationTemp.setNumber(propertyTemp.getYng_Item().getYng_Ubication().getNumber());
		ubicationTemp.setPostalCode(propertyTemp.getYng_Item().getYng_Ubication().getPostalCode());
		ubicationTemp.setAditional(propertyTemp.getYng_Item().getYng_Ubication().getAditional());
		Yng_Country countrySw=countryDao.findByCountryId(propertyTemp.getYng_Item().getYng_Ubication().getYng_Country().getCountryId());
		if(!countrySw.isToSell()) {
			return "Tu país todavia no esta habilitado para vender en Yingul estamos trabjando en ello";
		}
		ubicationTemp.setYng_Country(countryDao.findByCountryId(propertyTemp.getYng_Item().getYng_Ubication().getYng_Country().getCountryId()));
		ubicationTemp.setYng_Province(provinceDao.findByProvinceId(propertyTemp.getYng_Item().getYng_Ubication().getYng_Province().getProvinceId()));
		ubicationTemp.setYng_City(cityDao.findByCityId(propertyTemp.getYng_Item().getYng_Ubication().getYng_City().getCityId()));	
		//ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(propertyTemp.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
        Yng_Ubication ubicationTempo=ubicationDao.save(ubicationTemp);
        itemTemp.setYng_Ubication(ubicationTempo);
		//para setear el usuario
		Yng_User userTemp= userDao.findByUsername(itemTemp.getUser().getUsername());
		userTemp.setPhone(itemTemp.getUser().getPhone());
		userTemp.setPhone2(itemTemp.getUser().getPhone2());
		userTemp.setWebSite(itemTemp.getUser().getWebSite());
		userTemp=userDao.save(userTemp);
		itemTemp.setUser(userTemp);
		//hasta aqui para el usuario
		//volvemos la imagen nulo para que no guarde en la base de datos
		String image=itemTemp.getPrincipalImage();
		itemTemp.setPrincipalImage("");
		itemService.save(itemTemp);
        Yng_Item temp = itemService.findByItemId(itemTemp.getItemId());
        for (Yng_ItemCategory s : itemCategory) {
        	ruta= ruta +"/"+s.getCategory().getName();
        	s.setItem(temp);
        	itemCategoryDao.save(s);	    
		}
        //imagen principal
		
        String extension;
        String nombre;
        byte[] bI;
        logger.info(String.valueOf(image.charAt(0)));
		if(image.charAt(0)=='s') {
			temp.setPrincipalImage("sin.jpg");
			logger.info("si funciono");
		}
		else {
			logger.info("no funciono");
			extension="jpeg";
			nombre="principal"+temp.getItemId();
			logger.info(extension);
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			bI=convertImage(bI);
			s3Services.uploadFile(nombre,extension, bI);
			nombre=nombre+"."+extension;   
			temp.setPrincipalImage(nombre);
		} 
		
		temp.setType("Property");
		temp.setQuantity(1);
		temp.setOver(false);
		temp.setEnabled(true);
		temp.setCondition(propertyTemp.getCondition());
		itemService.save(temp);
		int k=0;
		for (Yng_ItemImage st : itemImage) {
        	k++;
        	image=st.getImage();
    		st.setImage("");
    		extension="jpeg";
    		nombre="img"+k+temp.getItemId();
    		logger.info(extension);
    		bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
    		bI=convertImage(bI);
    		s3Services.uploadFile(nombre,extension, bI);
    		nombre=nombre+"."+extension;   
    		st.setImage(nombre);
    		st.setItem(temp);	
        	itemImageDao.save(st);	    
		}
		  
		propertyTemp.setYng_Item(temp);
        //obtenemos la lista de provincia de la zona de cobertura
  		//Set<Yng_ServiceProvince> serviceProvince = new HashSet<>();
  		//serviceProvince=productTemp.getCobertureZone();
  		//borramos la lista de cagorias para que no se inserte dos veces
  		//serviceTemp.setCobertureZone(null);
  		
  		
  		Set<Yng_PropertyAmenities> propertyAmenities=new HashSet<>();
  		Set<Yng_PropertyAmbient> propertyAmbient=new HashSet<>();
  		
  		propertyAmenities=propertyTemp.getPropertyAmenities();
  		propertyAmbient=propertyTemp.getPropertyAmbient();
  		
  		propertyTemp.setPropertyAmbient(null);
  		propertyTemp.setPropertyAmenities(null);
  		
        Yng_Property prop = propertyDao.save(propertyTemp);
        
        
        
        for(Yng_PropertyAmbient si:propertyAmbient)
        {
        	si.setAmbient(ambientDao.findByAmbientId(si.getAmbient().getAmbientId()));
        	si.setProperty(prop);
        	propertyAmbietDao.save(si); 	
       
        }
        
        for(Yng_PropertyAmenities si:propertyAmenities)       	
        {
        	si.setAmenities(amenitiesDao.findByAmenitiesId(si.getAmenities().getAmenitiesId()));
        	si.setProperty(prop);
        	propertyAmenitiesDao.save(si);       	
        }

        
        
        
        
        
        try {
			smtpMailSender.send(userTemp.getEmail(), "INMUEBLE registrado exitosamente", "Su inmueble ya esta registrado en las categorias de: "+ruta+
					"<br> puede encontrarlo y compartirlo en: www.yingul.com/itemDetail/"+prop.getYng_Item().getItemId());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        facebookPostPhoto(temp);
        return "save";
    }
	
	
	@RequestMapping(value = "/motorized", method = RequestMethod.POST)
	@ResponseBody
    public String sellMororizedPost(@Valid @RequestBody Yng_Motorized motorized) throws MessagingException , IOException {	
		//capturar objeto inicio
		ObjectMapper mapper = new ObjectMapper();
		java.util.Date fecha = new Date();
		System.out.println (fecha);
		try {
			String jsonInString = mapper.writeValueAsString(motorized);
			System.out.println("jsonInString"+jsonInString);
			Yng_YingulRequest serviceJson=new Yng_YingulRequest();
			serviceJson.setJson(jsonInString);
			serviceJson.setDate(fecha);
			yingulRequestDao.save(serviceJson);
		} catch (JsonProcessingException e1) {
			
			e1.printStackTrace();
		}
//capturar objeto fin
		String ruta="Motorized";
		Yng_Motorized motorizedTemp=motorized;
		Yng_Item itemTemp=motorizedTemp.getYng_Item();
		Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd");
    	DateFormat hourdateFormat1 = new SimpleDateFormat("MM");
    	DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy");
    	itemTemp.setDayPublication(Integer.parseInt(hourdateFormat.format(date)));
    	itemTemp.setMonthPublication(Integer.parseInt(hourdateFormat1.format(date)));
    	itemTemp.setYearPublication(Integer.parseInt(hourdateFormat2.format(date)));
		   	//Yng_Service serviceTemp = service;
			//Yng_Item itemTemp=serviceTemp.getYng_Item();
		//obtenemos la lista de las imagenes 
		Set<Yng_ItemImage> itemImage=motorizedTemp.getYng_Item().getItemImage();
		//borramos las imagenes 
		motorizedTemp.getYng_Item().setItemImage(null);
		//obtenemos la lista de categorias
		Set<Yng_ItemCategory> itemCategory = new HashSet<>();
		itemCategory=motorizedTemp.getYng_Item().getItemCategory();
		//borramos la lista de cagorias para que no se inserte dos veces
		motorizedTemp.getYng_Item().setItemCategory(null);
		//para setear la ubicacion del item 
		Yng_Ubication ubicationTemp = new Yng_Ubication();
		ubicationTemp.setStreet(motorizedTemp.getYng_Item().getYng_Ubication().getStreet());
		ubicationTemp.setNumber(motorizedTemp.getYng_Item().getYng_Ubication().getNumber());
		ubicationTemp.setPostalCode(motorizedTemp.getYng_Item().getYng_Ubication().getPostalCode());
		ubicationTemp.setAditional(motorizedTemp.getYng_Item().getYng_Ubication().getAditional());
		Yng_Country countrySw=countryDao.findByCountryId(motorizedTemp.getYng_Item().getYng_Ubication().getYng_Country().getCountryId());
		if(!countrySw.isToSell()) {
			return "Tu país todavia no esta habilitado para vender en Yingul estamos trabjando en ello";
		}
		ubicationTemp.setYng_Country(countryDao.findByCountryId(motorizedTemp.getYng_Item().getYng_Ubication().getYng_Country().getCountryId()));
		ubicationTemp.setYng_Province(provinceDao.findByProvinceId(motorizedTemp.getYng_Item().getYng_Ubication().getYng_Province().getProvinceId()));
		ubicationTemp.setYng_City(cityDao.findByCityId(motorizedTemp.getYng_Item().getYng_Ubication().getYng_City().getCityId()));	
		ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(motorizedTemp.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
        Yng_Ubication ubicationTempo=ubicationDao.save(ubicationTemp);
        itemTemp.setYng_Ubication(ubicationTempo);
		//para setear el usuario
		Yng_User userTemp= userDao.findByUsername(itemTemp.getUser().getUsername());
		userTemp.setPhone(itemTemp.getUser().getPhone());
		userTemp.setPhone2(itemTemp.getUser().getPhone2());
		userTemp.setWebSite(itemTemp.getUser().getWebSite());
		userTemp=userDao.save(userTemp);		
		itemTemp.setUser(userTemp);
		//hasta aqui para el usuario
		//volvemos la imagen nulo para que no guarde en la base de datos
		String image=itemTemp.getPrincipalImage();
		itemTemp.setPrincipalImage("");
		itemService.save(itemTemp);
        Yng_Item temp = itemService.findByItemId(itemTemp.getItemId());
        for (Yng_ItemCategory s : itemCategory) {
        	ruta= ruta +"/"+s.getCategory().getName();
        	s.setItem(temp);
        	itemCategoryDao.save(s);	    
		}
        //imagen principal
		
        String extension;
        String nombre;
        byte[] bI;
        logger.info(String.valueOf(image.charAt(0)));
		if(image.charAt(0)=='s') {
			temp.setPrincipalImage("sin.jpg");
			logger.info("si funciono");
		}
		else {
			logger.info("no funciono");
			extension="jpeg";
			nombre="principal"+temp.getItemId();
			logger.info(extension);
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			bI=convertImage(bI);
			s3Services.uploadFile(nombre,extension, bI);
			nombre=nombre+"."+extension;   
			temp.setPrincipalImage(nombre);
		}
		
		temp.setType("Motorized");
		temp.setOver(false);
		temp.setEnabled(true);
		if(temp.getKilometer()==0) {
			temp.setCondition("New");
		}else {
			temp.setCondition("Used");
		}
		itemService.save(temp);
		int k=0;
		for (Yng_ItemImage st : itemImage) {
        	k++;
        	image=st.getImage();
    		st.setImage("");
    		extension="jpeg";
    		nombre="img"+k+temp.getItemId();
    		logger.info(extension);
    		bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
    		bI=convertImage(bI);
    		s3Services.uploadFile(nombre,extension, bI);
    		nombre=nombre+"."+extension;   
    		st.setImage(nombre);
    		st.setItem(temp);	
        	itemImageDao.save(st);	    
		}		
		motorizedTemp.setYng_Item(temp);
		
		
		
		Set<Yng_MotorizedSecurity> motorizedSecurity = new HashSet<>();		
		Set<Yng_MotorizedConfort> motorizedConfort = new HashSet<>();
		Set<Yng_MotorizedEquipment> motorizedEquipment = new HashSet<>();
		Set<Yng_MotorizedExterior> motorizedExterior = new HashSet<>();
		Set<Yng_MotorizedSound> motorizedSound = new HashSet<>();
		
		
		motorizedSecurity=motorizedTemp.getMotorizedSecurity();
		motorizedConfort=motorizedTemp.getMotorizedConfort();
		motorizedEquipment=motorizedTemp.getMotorizedEquipment();
		motorizedExterior = motorizedTemp.getMotorizedExterior();
		motorizedSound=motorizedTemp.getMotorizedSound();
		
		motorizedTemp.setMotorizedSecurity(null);
		motorizedTemp.setMotorizedConfort(null);
		motorizedTemp.setMotorizedEquipment(null);
		motorizedTemp.setMotorizedExterior(null);
		motorizedTemp.setMotorizedSound(null);
		
		
        Yng_Motorized mots = motorizedDao.save(motorizedTemp);        
 

        for (Yng_MotorizedSecurity si : motorizedSecurity) {

        	si.setSecurity(securityDao.findBySecurityId(si.getSecurity().getSecurityId()));
        	si.setMotorized(mots);
        	motorizedSecurityDao.save(si);	    
		} 
        
        for(Yng_MotorizedConfort si:motorizedConfort)
        {
        	si.setConfort(confortDao.findByConfortId(si.getConfort().getConfortId()));
        	si.setMotorized(mots);
        	motorizedConfortDao.save(si);
        
        }
        
        for(Yng_MotorizedEquipment si:motorizedEquipment)
        {
        	si.setEquipment(equipmentDao.findByEquipmentId(si.getEquipment().getEquipmentId()));
        	si.setMotorized(mots);
        	motorizedEquipmentDao.save(si);
        
        }
        
        
        for(Yng_MotorizedExterior si:motorizedExterior)
        {
        	si.setExterior(exteriorDao.findByExteriorId(si.getExterior().getExteriorId()));
        	si.setMotorized(mots);
        	notorizedExteriorDao.save(si);
        }
        
        for(Yng_MotorizedSound si:motorizedSound) {
        	si.setSound(soundDao.findBySoundId(si.getSound().getSoundId()));
        	si.setMotorized(mots);
        	motorizedSoundDao.save(si);
        }  
        
        try {
			smtpMailSender.send(userTemp.getEmail(), "Vehículo registrado exitosamente", "Su vehículo ya esta registrado en las categorias de: "+ruta+
					"<br> puede encontrarlo y compartirlo en: www.yingul.com/itemDetail/"+mots.getYng_Item().getItemId());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        facebookPostPhoto(temp);
        return "save";
        
    }
	
	
	
	
	
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	@ResponseBody
    public String signupPost(@Valid @RequestBody String image) throws MessagingException {
		String extension=image.substring(11,14);
		if(image.charAt(13)=='e') {
			extension="jpeg";
		}
		String nombre="principal";
		logger.info(extension);
		byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
		s3Services.uploadFile(nombre,extension, bI);
		return "save";
	}
	
	@RequestMapping("/ubication/{username}")
    public Yng_Ubication findQueriesByUser(@PathVariable("username") String username) {
		Yng_Ubication yng_Ubication=null;		
    	Yng_User yng_User = userDao.findByUsername(username);
    	if(yng_User.getYng_Ubication()!=null){
    		//yng_Ubication=yng_User.getYng_Ubication();
    		yng_Ubication=ubicationDao.findByUbicationId(yng_User.getYng_Ubication().getUbicationId());
    		return yng_Ubication;
    	}    	 
    	else{
    		return null;
    	}
	}
	
	@RequestMapping("/standardCost/{weightAforado}")
    public Yng_Ubication findStandardCostById(@PathVariable("weightaforado") String weightAforado) {
		
    	return null;
	}
	@RequestMapping("/standardCostAndreani")
    public List<Yng_StandarCostAndreani> findStandardCostAndreani(){
    	List<Yng_StandarCostAndreani> findStandardCostAndreani=standarCostAndreaniDao.findAll();
    	return findStandardCostAndreani;
    }
	@RequestMapping(value = "/pdf", method = RequestMethod.POST)
	@ResponseBody
    public String signupPostPDF(@Valid @RequestBody String image) throws MessagingException {
		uploadPDF(image,"fedex2"); 
		return "save";
	}
	public String uploadPDF(String StringBAse64,String name) throws MessagingException {
		
		String extension="pdf";
		logger.info(extension);
		byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64(StringBAse64.getBytes());
		s3Services.uploadFile("fedexPdf/"+name,extension, bI);
		return "save";
	}
	
	public static void decodeString(String stringBase64, String targetFile) throws Exception {

        byte[] decodedBytes = Base64.decodeBase64(stringBase64.getBytes());

        writeByteArraysToFile(targetFile, decodedBytes);
    }

    /**
     * This method loads a file from file system and returns the byte array of the content.
     * 
     * @param fileName
     * @return
     * @throws Exception
     */
    public static byte[] loadFileAsBytesArray(String fileName) throws Exception {

        File file = new File(fileName);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;

    }

    /**
     * This method writes byte array content into a file.
     * 
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

        File file = new File(fileName);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();

    }
    public byte[] convertImage(byte[] inputImage) throws IOException {
    	int weight =inputImage.length/1024;
    	if(weight>=0 && weight<20) {
			return inputImage;
		}
    	
    	InputStream is = new ByteArrayInputStream(inputImage);
		// create a BufferedImage as the result of decoding the supplied InputStream
		BufferedImage image = ImageIO.read(is);
		image = Scalr.resize(image,  Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,500, 500, Scalr.OP_ANTIALIAS);
		
		System.out.println(image.getHeight()+": "+image.getWidth());
		
		ByteArrayOutputStream compressed = new ByteArrayOutputStream();
		ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);

		// NOTE: The rest of the code is just a cleaned up version of your code

		// Obtain writer for JPEG format
		ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpeg").next();

		// Configure JPEG compression: 70% quality
		ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
		jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		System.out.println("peso inicial"+inputImage.length/1024);

		if(weight>=20 && weight<30) {
			jpgWriteParam.setCompressionQuality(0.5f);
		}
		if(weight>=30 && weight<40) {
			jpgWriteParam.setCompressionQuality(0.6f);
		}
		if(weight>=40 && weight<60) {
			jpgWriteParam.setCompressionQuality(0.7f);
		}
		if(weight>=60 && weight<120) {
			jpgWriteParam.setCompressionQuality(0.7f);
		}
		if(weight>=120 && weight<180) {
			jpgWriteParam.setCompressionQuality(0.6f);
		}
		if(weight>=180 && weight<540) {
			jpgWriteParam.setCompressionQuality(0.4f);
		}
		if(weight>=540) {
			jpgWriteParam.setCompressionQuality(0.3f);
		}
		// Set your in-memory stream as the output
		jpgWriter.setOutput(outputStream);
		// Write image as JPEG w/configured settings to the in-memory stream
		// (the IIOImage is just an aggregator object, allowing you to associate
		// thumbnails and metadata to the image, it "does" nothing)
		jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);
		// Dispose the writer to free resources
		jpgWriter.dispose();
		// Get data for further processing...
		byte[] jpegData = compressed.toByteArray();
		System.out.println("peso final"+jpegData.length/1024);
		return jpegData;
    }
    
    public String facebookPostPhoto(Yng_Item item) {
    	PropertySocial p=new PropertySocial();
    	//****Local
    	p=propertyLocal();
    	//****production
    	//p=propertyProduction();

    	FacebookPhoto photo= new FacebookPhoto();
    	
    	String urlp=p.getUrlImage()+item.getPrincipalImage();    	
    	
    	
		photo.setUrl(""+urlp);
		String currency="$";
		if(!item.getMoney().equals("ARS")) {
			currency="USD";
		}
    	String message=""
    			+ "\n"+item.getName().toUpperCase()
    			//+ "\n"+item.getDescription()
    			+ "\n"+currency+"  "+item.getPrice()
    			+"\n"+p.urlPagina+item.getItemId();
		photo.setMessage(message);
    	
    	Yng_Standard access_token = standardDao.findByKey("Facebook_access_token");
    	photo.setAccess_token(access_token.getValue());
    	http  h=new http();
    	PropertyObjectHttp propertyObjectHttp = new PropertyObjectHttp();
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonInString="";
    	try {
    		jsonInString= mapper.writeValueAsString(photo);
    		System.out.println("jsonInString");
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "error";
		}
    	//String page_id="807575726109590";
    	
    	Yng_Standard page_id = standardDao.findByKey("Facebook_page_id");
    	String url = "https://graph.facebook.com/"+page_id.getValue()+"/photos";
    	
    	String body = jsonInString;
    	String requestMethod = propertyObjectHttp.POST;
    	List<RequestPropertyHeders> requestProperty = new ArrayList<>();
    	RequestPropertyHeders rep= new RequestPropertyHeders();
    	rep.setName("Content-Type");
    	rep.setValue("application/json");
		requestProperty.add(rep);
		propertyObjectHttp.setBody(body);	    	
		propertyObjectHttp.setRequestMethod(requestMethod);
		propertyObjectHttp.setRequestProperty(requestProperty);
		propertyObjectHttp.setUrl(url);
		try {
			h.request(propertyObjectHttp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		twitterPostPhoto(item,p);
    	return "save";
    }
    
    public String twitterPostPhoto(Yng_Item item,PropertySocial p) { 	
    	
    	String currency="$";
		if(!item.getMoney().equals("ARS")) {
			currency="USD";
		}
    	ConfigurationBuilder twitterConfigBuilder = new ConfigurationBuilder();
    	twitterConfigBuilder.setDebugEnabled(true);
    	Yng_Standard Twitter_consumer_key = standardDao.findByKey("Twitter_consumer_key");
    	twitterConfigBuilder.setOAuthConsumerKey(""+Twitter_consumer_key.getValue());
    	Yng_Standard Twitter_consumer_secret = standardDao.findByKey("Twitter_consumer_secret");
    	twitterConfigBuilder.setOAuthConsumerSecret(""+Twitter_consumer_secret.getValue());
    	Yng_Standard Twitter_access_token = standardDao.findByKey("Twitter_access_token");
    	twitterConfigBuilder.setOAuthAccessToken(""+Twitter_access_token.getValue());
    	Yng_Standard Twitter_access_token_secret = standardDao.findByKey("Twitter_access_token_secret");
    	twitterConfigBuilder.setOAuthAccessTokenSecret(""+Twitter_access_token_secret.getValue());

    	Twitter twitter = new TwitterFactory(twitterConfigBuilder.build()).getInstance();
    	String statusMessage = ""
    			+ "\n"+item.getName().toUpperCase()
    			//+ "\n"+item.getDescription()
    			+ "\n"+currency+"  "+item.getPrice()
    			+"\n"+p.getUrlPagina()+item.getItemId();    	
    	StatusUpdate status = new StatusUpdate(statusMessage);
		String Photo=p.getUrlImage()+item.getPrincipalImage();
		InputStream is;
		try {
			is = new URL(Photo).openStream();
			status.setMedia("nu", is);//;(file); // set the image to be uploaded here.
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    	
    	try {
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fallo";
		}
    	return "Save";
    }
    public PropertySocial propertyLocal(){
		PropertySocial p=new PropertySocial();
		p.setUrlImage("https://s3-us-west-2.amazonaws.com/jsa-s3-bucketimage/dev/image/");
		p.setUrlPagina("http://localhost:8080/itemDetail/");
		return p;
	}
	public PropertySocial propertyProduction(){
		PropertySocial p=new PropertySocial();
		p.setUrlImage("https://s3-us-west-2.amazonaws.com/jsa-s3-bucketimage/image/");
		p.setUrlPagina("http://www.yingul.com/itemDetail/");
		return p;
	}
    public class PropertySocial{
    	public String urlPagina="";
    	public String urlImage="";
    	
		public String getUrlPagina() {
			return urlPagina;
		}
		public void setUrlPagina(String urlPagina) {
			this.urlPagina = urlPagina;
		}
		public String getUrlImage() {
			return urlImage;
		}
		public void setUrlImage(String urlImage) {
			this.urlImage = urlImage;
		}
    	
    }
	@RequestMapping("/meta/{itemId}")
    public String  getProductByIdItem(@PathVariable("itemId") Long itemId) {
		Yng_Item item=new Yng_Item();
		item=itemService.findByItemId(itemId);
		System.out.println("item:"+item);
		if(item!=null) {
		
		String urlImage="\"https://s3-us-west-2.amazonaws.com/jsa-s3-bucketimage/image/principal"+ itemId	+ ".jpeg\" />\r\n";
		String url="\"http://yingulbackend-env.accmfbwpye.us-west-2.elasticbeanstalk.com/ft/"+ itemId+ "\" />\r\n" ;
		
		
		String title="\""
				+ item.getName()
				+ "\" />\r\n" ;
		String description="\""
				+ item.getPrice()
				+ "\" />\r\n" ;
		
		return "<!doctype html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"<head>\r\n" + 
				"  <meta charset=\"utf-8\">\r\n" + 
				"  <meta property=\"fb:app_id\" content=\"432316930562411\" />\r\n" + 
				"  <!--meta property=\"fb:admins\" content=\"100000189380463\" /-->\r\n" + 
				"    <meta name=\"twitter:card\" content=\"summary_large_image\" />\r\n" + 
				"    <meta name=\"twitter:site\" content=\"@YingulPruebas\" />\r\n" + 
				"    <meta name=\"twitter:image\" content="+
				urlImage + 
				"    <meta name=\"twitter:title\" content="				+ 
				 title+
				"    <meta name=\"twitter:description\" content="+
				description+ 
				"    <meta name=\"twitter:creator\" content=\"@rockcontent_es\" />\r\n" + 
				"    \r\n" + 
				"    <meta property=\"og:url\"                content="	+ 
				 url+
				"    <meta property=\"og:type\"               content=\"article\" />\r\n" + 
				"    <meta property=\"og:title\"              content="
				+ title + 
				"    <meta property=\"og:description\"        content="
				+ description + 
				"    <meta property=\"og:image\"              content="
				+urlImage + 
				"  <title>"
				+ title
				+ "</title>\r\n" + 
				"  <base href=\"/\">\r\n" + 
				"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"     <!-- HTML meta refresh URL redirection -->\r\n" + 
				"   <meta http-equiv=\"refresh\" \r\n" + 
				"   content=\"0; url=http://www.yingul.com/itemDetail/"
				+ itemId
				+ "\">\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"     <p>The page has moved to: \r\n" + 
				"   <a href=\"http://www.yingul.com/itemDetail/"
				+ itemId
				+ "\">this page</a></p>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>";	
    
	}
	return "";
	}
}
