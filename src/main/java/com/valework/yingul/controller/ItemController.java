package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.FindMotorizedDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.MotorizedDao;
import com.valework.yingul.dao.ProductDao;
import com.valework.yingul.dao.PropertyDao;
import com.valework.yingul.dao.QueryDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.model.Yng_FindMotorized;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_ItemImage;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_Query;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.ItemCategoryService;
import com.valework.yingul.service.ItemImageService;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.MotorizedService;
import com.valework.yingul.service.PersonService;
import com.valework.yingul.service.ProductService;
import com.valework.yingul.service.PropertyService;
import com.valework.yingul.service.QueryService;
import com.valework.yingul.service.ServiceService;

@RestController
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	ServiceService serviceService;
	@Autowired 
	PersonService personService;
	@Autowired
	ItemDao itemDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ItemService itemService;
	@Autowired
	ItemImageService itemImageService;
	@Autowired
	ItemCategoryService itemCategoryService;
	@Autowired 
	CategoryDao categoryDao;
	@Autowired
	QueryDao queryDao;
	@Autowired 
	QueryService queryService;
	@Autowired
	ProductService productService;
	@Autowired
	PropertyService propertyService;
	@Autowired
	MotorizedService motorizedService;
	@Autowired 
	ServiceDao serviceDao;
	@Autowired
	MotorizedDao motorizedDao;
	@Autowired 
	FindMotorizedDao findMotorizedDao;
	@Autowired
	PropertyDao propertyDao;
	@Autowired
	ProductDao productDao;
	@RequestMapping("/itemType/{itemId}")
    public String getItemTypeById(@PathVariable("itemId") Long itemId) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		List<Yng_Service> serviceList= serviceService.findByItem(yng_Item);
		List<Yng_Product> productList= productService.findByItem(yng_Item);
		List<Yng_Motorized> motorizedList= motorizedService.findByItem(yng_Item);
		List<Yng_Property> propertyList= propertyService.findByItem(yng_Item);
		//hacer pára productos, motorizados inmuebkes  
		if(serviceList.size()==1) {
			return "Servicio";
		}
		if(productList.size()==1) {
			return "Producto";
		}
		if(motorizedList.size()==1) {
			return "Vehiculo";
		}
		if(propertyList.size()==1) {
			return "Inmueble";
		}
		else {
			return "otro";
		}
		
    }
	@RequestMapping("/Producto/{itemId}")
    public Yng_Product getProductByIdItem(@PathVariable("itemId") Long itemId) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		List<Yng_Product> productList= productService.findByItem(yng_Item);
		Yng_Product product = productList.get(0);
		System.out.println("pro: "+product);
		return product;	
    }
	
	@RequestMapping("/Servicio/{itemId}")
    public Yng_Service getServiceByIdItem(@PathVariable("itemId") Long itemId) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		List<Yng_Service> serviceList= serviceService.findByItem(yng_Item);
		Yng_Service service = serviceList.get(0);
		return service;	
    }
	@RequestMapping("/Vehiculo/{itemId}")
    public Yng_Motorized getMotorizedByIdItem(@PathVariable("itemId") Long itemId) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		List<Yng_Motorized> motorizedList= motorizedService.findByItem(yng_Item);
		Yng_Motorized motorized = motorizedList.get(0);
		return motorized;	
    }
	@RequestMapping("/Inmueble/{itemId}")
    public Yng_Property getPropertyByIdItem(@PathVariable("itemId") Long itemId) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		List<Yng_Property> propertyList= propertyService.findByItem(yng_Item);
		Yng_Property property = propertyList.get(0);
		return property;	
    }
	//vamos a hacer un cambio de pedir seller por username a pedir vendedor por itemid
	@RequestMapping("/Seller/{itemId}")
    public Yng_Person getSellerByItem(@PathVariable("itemId") Long itemId) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);
		Yng_User yng_User = userDao.findByUsername(yng_Item.getUser().getUsername()); 
		List<Yng_Person> personList= personService.findByUser(yng_User);
		Yng_Person person = personList.get(0);
		return person;	
    }
	@RequestMapping("/ItemById/{itemId}")
    public Yng_Item findItemsBySeller(@PathVariable("itemId") Long itemId) {
    	Yng_Item yng_Item = itemDao.findByItemId(itemId);
        return yng_Item;
    }
	@RequestMapping("/Item/{username}")
    public List<Yng_Item> findItemsBySeller(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Item> itemList = itemService.findByUser(yng_User);
        return itemList;
    }
    @RequestMapping("/Image/{itemId}")
    public List<Yng_ItemImage> findImageByItem(@PathVariable("itemId") Long itemId) {
    	Yng_Item yng_Item = itemDao.findByItemId(itemId);
        List<Yng_ItemImage> itemImageList = itemImageService.findByItem(yng_Item);
        return itemImageList;
    }
    @RequestMapping("/Categories/{itemId}")
    public List<Yng_ItemCategory> findCategoriesByItem(@PathVariable("itemId") Long itemId) {
    	Yng_Item yng_Item = itemDao.findByItemId(itemId);
        List<Yng_ItemCategory> itemCategoryList = itemCategoryService.findByItem(yng_Item);
        return itemCategoryList;
    }
    @RequestMapping("/Query/{itemId}")
    public List<Yng_Query> findqueryByItem(@PathVariable("itemId") Long itemId) {
    	Yng_Item yng_Item = itemDao.findByItemId(itemId);
        List<Yng_Query> queryList = queryService.findByItem(yng_Item);
        return queryList;
    }
    
    //etse metodo tambien debe pedir autenticacion basica
    @RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
    public String queryItemPost(@Valid @RequestBody Yng_Query query) throws MessagingException {
    	//filtro de comentarios
    	String s = query.getQuery();
    	String[] words = s.split("\\s+");
    	query.setQuery("");
    	for (int i = 0; i < words.length; i++) {
    		if(words[i].indexOf('@')==-1||words[i].indexOf(".com")==-1) {
    			query.setQuery(query.getQuery()+words[i]+" ");
    		}
    	}
    	Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	query.setDate(hourdateFormat.format(date));
    	//fin del filtro de comentarios
    	query.setYng_Item(itemDao.findByItemId(query.getYng_Item().getItemId()));
    	query.setUser(userDao.findByUsername(query.getUser().getUsername()));
    	//filtro para que no se pueda comentar un item propio
    	if(query.getUser().getUsername()==query.getYng_Item().getUser().getUsername()) {
    		return "no puedes comentar producos, servicios, inmuebles o vehiculos propios";
    	}
    	else {
			queryDao.save(query);
		    try {
				smtpMailSender.send(query.getYng_Item().getUser().getEmail(), "Consulta urgente sobre su Item", query.getUser().getUsername()+" pregunto "+query.getQuery()+" sobre el Item "+query.getYng_Item().getName()+". Puedes responder las consultas en: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/query");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "save";
    	}
    }
    
    //talvez estos metodos deberan ir en otro controlador 
    @RequestMapping("/itemsCategory/{categoryId}")
    public List<Yng_ItemCategory> findItemsByCategory(@PathVariable("categoryId") Long categoryId) {
    	Yng_Category yng_Category = categoryDao.findByCategoryId(categoryId);
    	List<Yng_ItemCategory> itemCategoryList = itemCategoryService.findByCategory(yng_Category); 
        return itemCategoryList;
    }
    
    @RequestMapping("/itemsByCategory/{categoryId}")
    public Set<Yng_Item> findOnlyItemsByCategory(@PathVariable("categoryId") Long categoryId) {
    	Yng_Category yng_Category = categoryDao.findByCategoryId(categoryId);
    	List<Yng_ItemCategory> itemCategoryList = itemCategoryService.findByCategory(yng_Category); 
    	Set<Yng_Item> listItemTemp = new HashSet<>();
    	for (Yng_ItemCategory st : itemCategoryList) {
    		listItemTemp.add(st.getItem());
		}
        return listItemTemp;
    }
    
    @RequestMapping("/service/all")
    public Set<Yng_Item> findServiceList() { 
    	List<Yng_Service> serviceList = serviceDao.findAll();
        Set<Yng_Item> itemList = itemService.findServices(serviceList);
        return itemList;
    }
    @RequestMapping("/motorized/all")
    public Set<Yng_Item> findMotorizedList() { 
    	List<Yng_Motorized> motorizedList = motorizedDao.findAll();
        Set<Yng_Item> itemList = itemService.findMotorized(motorizedList);
        return itemList;
    }
    @RequestMapping("/findMotorized/{categoryId}")
    public Yng_FindMotorized findSearchMotorizedByCategory(@PathVariable("categoryId") Long categoryId) {
    	//arreglar aqui
    	if(categoryId==0) {
    		categoryId=(long) 2001;
    	}
    	Yng_Category categoryTemp= categoryDao.findByCategoryId(categoryId);
    	while(categoryTemp.getLevel()!=0) {
    		categoryTemp=categoryDao.findByCategoryId(categoryTemp.getFatherId());
    	}
    	Yng_FindMotorized yng_FindMotorized = findMotorizedDao.findByCategoryId(categoryTemp.getCategoryId());
        return yng_FindMotorized;
    }
    @RequestMapping("/searchMotorized/{categoryId}/{minPrice}/{maxPrice}/{minYear}/{maxYear}")
    public Set<Yng_Item> searchMotorizedList(@PathVariable("categoryId") Long categoryId,@PathVariable("minPrice") Long minPrice,@PathVariable("maxPrice") Long maxPrice,@PathVariable("minYear") Long minYear,@PathVariable("maxYear") Long maxYear) { 
    	System.out.println(" "+categoryId+"/"+minPrice+"/"+maxPrice+"/"+minYear+"/"+maxYear);
    	if(categoryId == 0) {
    		List<Yng_Motorized> motorizedList = motorizedDao.findAll();
            Set<Yng_Item> itemList = itemService.findMotorized(motorizedList);
            return itemList;
    	}else {
    		List<Yng_Motorized> motorizedList = motorizedDao.findAll();
    		Set<Yng_Item> itemList = itemService.searchMotorized(motorizedList, categoryId, minPrice, maxPrice, minYear, maxYear);
            return itemList;
    	}

    }
    @RequestMapping("/property/all")
    public Set<Yng_Item> findPropertyList() { 
    	List<Yng_Property> propertyList = propertyDao.findAll();
        Set<Yng_Item> itemList = itemService.findProperty(propertyList);
        return itemList;
    }
    @RequestMapping("/searchProperty/{categoryId}/{cityId}")
    public Set<Yng_Item> searchPropertyList(@PathVariable("categoryId") Long categoryId,@PathVariable("cityId") Long cityId) { 
    	System.out.println(" "+categoryId+"/"+cityId);
    	List<Yng_Property> propertyList = propertyDao.findAll();
    	if(categoryId == 0 && cityId == 0) {
            Set<Yng_Item> itemList = itemService.findProperty(propertyList);
            return itemList;
    	}else{
    		Set<Yng_Item> itemList = itemService.searchProperty(propertyList, categoryId, cityId);
            return itemList;
    	}
    }
	@RequestMapping("/producto/exist/{itemId}")
    public boolean getProductByIdItemExist(@PathVariable("itemId") Long itemId) { 
		boolean exist=productService.findByItemIdExist(itemId);
		
		return exist;	
    }
    @RequestMapping("/product/all")
    public List<Yng_Product> findProductList() { 
    	List<Yng_Product> productsList = productDao.findAll();
       // Set<Yng_Item> itemList = itemService.findProperty(propertyList);
        return productsList;
    }
}
