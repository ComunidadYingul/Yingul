package com.valework.yingul.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.FindMotorizedDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.MotorizedDao;
import com.valework.yingul.dao.ProductDao;
import com.valework.yingul.dao.PropertyDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.QueryDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UbicationDao;
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
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Ubication;
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
	@Autowired
	private StandardDao standardDao;
    @Autowired 
    CityDao cityDao;
    
    @Autowired 
    ProvinceDao provinceDao;
    
    @Autowired
    DepartmentDao departmentDao;
    
    @Autowired
    UbicationDao ubicationDao;
	@RequestMapping("/itemType/{itemId}")
    public String getItemTypeById(@PathVariable("itemId") Long itemId) {
		Yng_Item yng_Item = itemDao.findByItemId(itemId);System.out.println("itemId 1 :"+itemId);
		List<Yng_Service> serviceList= serviceService.findByItem(yng_Item);System.out.println("itemId 1 :"+itemId);
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
    
    @RequestMapping("/ProductsByCategory/{categoryId}")
    public Set<Yng_Product> findOnlyProductsByCategory(@PathVariable("categoryId") Long categoryId) {
    	Yng_Category yng_Category = categoryDao.findByCategoryId(categoryId);
    	List<Yng_ItemCategory> itemCategoryList = itemCategoryService.findByCategory(yng_Category); 
    	Set<Yng_Item> listItemTemp = new HashSet<>();
    	Set<Yng_Product> listProductTemp = new HashSet<>();
    	for (Yng_ItemCategory st : itemCategoryList) {
    		listItemTemp.add(st.getItem());
		}
    	for (Yng_Item st : listItemTemp) {
    		List<Yng_Product> productList= productService.findByItem(st);
    		Yng_Product product = productList.get(0);
    		listProductTemp.add(product);
		}
        return listProductTemp;
    }
    
    @RequestMapping("/MotorizedByCategory/{categoryId}")
    public Set<Yng_Motorized> findOnlyMotorizedByCategory(@PathVariable("categoryId") Long categoryId) {
    	Yng_Category yng_Category = categoryDao.findByCategoryId(categoryId);
    	List<Yng_ItemCategory> itemCategoryList = itemCategoryService.findByCategory(yng_Category); 
    	Set<Yng_Item> listItemTemp = new HashSet<>();
    	Set<Yng_Motorized> listMotorizedTemp = new HashSet<>();
    	for (Yng_ItemCategory st : itemCategoryList) {
    		listItemTemp.add(st.getItem());
		}
    	for (Yng_Item st : listItemTemp) {
    		List<Yng_Motorized> motorizedList= motorizedService.findByItem(st);
    		Yng_Motorized motorized = motorizedList.get(0);
    		listMotorizedTemp.add(motorized);
		}
        return listMotorizedTemp;
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
    @RequestMapping("/onlyMotorized/all")
    public List<Yng_Motorized> findOnlyMotorizedList() { 
    	List<Yng_Motorized> motorizedList = motorizedDao.findAll();
        return motorizedList;
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
    @RequestMapping("/type/{itemId}")
    public String getTypeItem(@PathVariable("itemId") Long itemId) {
    	String type="false";
    	boolean existProd=productService.findByItemIdExist(itemId);
    	boolean existServ=serviceService.findByItemIdExist(itemId);
    	boolean existProp=propertyService.findByItemIdExist(itemId);
    	boolean existMoto=motorizedService.findByItemIdExist(itemId);
    	if(existProd==true) {type= "Producto";}
    	if(existServ==true) {type= "Service";}
    	if(existProp==true) {type= "Inmueble";}
    	if(existMoto==true) {type= "Vehiculo";}
    	System.out.println("type");
    	if(this.getProductByIdItemExist(itemId)) {
    		return "Product";
    	}    	
    	else {
    	return "false";
    	}
    }
    @RequestMapping("/product/{itemId}")
    public Yng_Product findProduct(@PathVariable("itemId") Long itemId) {
    	if(this.getProductByIdItemExist(itemId)) {
    	Yng_Product yng_Product=this.getProductByIdItem(itemId);
    	yng_Product.getYng_Item().getUser().setPassword("");
        return yng_Product;
    	}
    	else return null;
    }
    public boolean getPropertyByIdItemExist(Long itemId) { 
		boolean exist=productService.findByItemIdExist(itemId);
		
		return exist;	
    }
    @RequestMapping(value = "/product/update", method = RequestMethod.POST)
	@ResponseBody
    public String sellProducPost(@Valid @RequestBody Yng_Product product) throws MessagingException {
    	Yng_Product prod=new Yng_Product();
    	prod=product;
    	Yng_Item yng_Item=prod.getYng_Item();
    	itemDao.save(yng_Item);    	
    	productDao.save(prod);
    	return "save";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
    public String updateItemPost(@Valid @RequestBody Yng_Item item) throws MessagingException {    	
    	Yng_Item yng_Item=item;
    	itemDao.save(yng_Item);    	
    	return "save";
    }
    @RequestMapping(value = "/motorized/update", method = RequestMethod.POST)
	@ResponseBody
    public String updateMotorizedPost(@Valid @RequestBody Yng_Motorized motorized) throws MessagingException {
    	Yng_Motorized moto=new Yng_Motorized();
    	moto=motorized;
    	Yng_Item yng_Item=moto.getYng_Item();
    	itemDao.save(yng_Item);    	
    	motorizedDao.save(moto);
    	return "save";
    }
    @RequestMapping(value = "/property/update", method = RequestMethod.POST)
	@ResponseBody
    public String updatePropertyPost(@Valid @RequestBody Yng_Property property) throws MessagingException {
    	Yng_Property prop=new Yng_Property();
    	prop=property;
    	Yng_Item yng_Item=prop.getYng_Item();
    	itemDao.save(yng_Item);    	
    	propertyDao.save(prop);
    	return "save";
    }
    @RequestMapping("/over/{sw}")
    public List<Yng_Item> findItemsOver(@PathVariable("sw") boolean sw, @RequestHeader("X-API-KEY") String XAPIKEY) {
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		List<Yng_Item> itemList = itemDao.findByIsOverOrderByItemIdDesc(sw);
            return itemList;
    	}else {
    		return null;
    	}
        
    }
    @RequestMapping(value = "/service/update", method = RequestMethod.POST)
   	@ResponseBody
       public String updateServicePost(@Valid @RequestBody Yng_Service service) throws MessagingException {
    	Yng_Service serv=new Yng_Service();
    	serv=service;
       	Yng_Item yng_Item=serv.getYng_Item();
       	itemDao.save(yng_Item);    	
       	serviceDao.save(serv);
       	return "save";
       }
   /* @RequestMapping("/motorized/{itemId}")
    public Yng_Product findMotorized(@PathVariable("itemId") Long itemId) {
    	List<Yng_Motorized> motTem=motorizedDao.findByYng_Item(itemId);
    	System.out.println(motTem.size()+" string: "+motTem.toString());
    	if(this.getProductByIdItemExist(itemId)) {
    	Yng_Product yng_Product=this.getProductByIdItem(itemId);
    	yng_Product.getYng_Item().getUser().setPassword("");
        return yng_Product;
    	}
    	else return null;
    }*/
    @RequestMapping(value = "/ubication/update", method = RequestMethod.POST)
   	@ResponseBody
       public String updateUbicationPost(@Valid @RequestBody Yng_Item item) throws MessagingException {
       	Yng_Item itemTem=new Yng_Item();
       	itemTem=itemDao.findByItemId(item.getItemId());
       	Yng_Ubication ubiEnt=new Yng_Ubication();
       	ubiEnt=item.getYng_Ubication();
       	Yng_Ubication ubiTemp=ubiEnt;    	
       	ubiTemp.setAditional(ubiEnt.getAditional());
       	String codAndreani="";
   		LogisticsController log=new LogisticsController();
   		try {
   			codAndreani=log.andreaniSucursales(ubiTemp.getPostalCode(), "", "");
   		} catch (Exception e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
   		}		
       	ubiTemp.setCodAndreani(codAndreani);
       	ubiTemp.setDepartment(ubiEnt.getDepartment());
       	ubiTemp.setNumber(ubiEnt.getNumber());
       	ubiTemp.setPostalCode(ubiEnt.getPostalCode());
   		ubiTemp.setStreet(ubiEnt.getStreet());
   		ubiTemp.setWithinStreets(ubiEnt.getWithinStreets());
       	ubiTemp.setYng_City(cityDao.findByCityId(ubiEnt.getYng_City().getCityId()));ubiTemp.setYng_Province(provinceDao.findByProvinceId(ubiEnt.getYng_Province().getProvinceId()));
   		itemTem.setYng_Ubication(ubicationDao.save(ubiTemp));
       	itemDao.save(itemTem);
       	return "save";
       }
}
