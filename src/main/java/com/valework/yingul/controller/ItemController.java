package com.valework.yingul.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AmbientDao;
import com.valework.yingul.dao.AmenitiesDao;
import com.valework.yingul.dao.BranchAndreaniDao;
import com.valework.yingul.dao.BuyDao;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.ClaimDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.ConfortDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.EquipmentDao;
import com.valework.yingul.dao.ExteriorDao;
import com.valework.yingul.dao.FavoriteDao;
import com.valework.yingul.dao.FindMotorizedDao;
import com.valework.yingul.dao.ItemCategoryDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.ItemImageDao;
import com.valework.yingul.dao.MotorizedConfortDao;
import com.valework.yingul.dao.MotorizedDao;
import com.valework.yingul.dao.MotorizedEquipmentDao;
import com.valework.yingul.dao.MotorizedExteriorDao;
import com.valework.yingul.dao.MotorizedSecurityDao;
import com.valework.yingul.dao.MotorizedSoundDao;
import com.valework.yingul.dao.NotificationDao;
import com.valework.yingul.dao.OmittedWordDao;
import com.valework.yingul.dao.ProductDao;
import com.valework.yingul.dao.PropertyAmbientDao;
import com.valework.yingul.dao.PropertyAmenitiesDao;
import com.valework.yingul.dao.PropertyDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.QueryDao;
import com.valework.yingul.dao.QuoteDao;
import com.valework.yingul.dao.SecurityDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.ServiceProvinceDao;
import com.valework.yingul.dao.ShipmentDao;
import com.valework.yingul.dao.ShippingDao;
import com.valework.yingul.dao.SoundDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.dao.XubioSalesInvoiceDao;
import com.valework.yingul.dao.XubioTransaccionProductoItemsDao;
import com.valework.yingul.model.Yng_BranchAndreani;
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.model.Yng_Claim;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Favorite;
import com.valework.yingul.model.Yng_FindMotorized;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_ItemImage;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedConfort;
import com.valework.yingul.model.Yng_MotorizedEquipment;
import com.valework.yingul.model.Yng_MotorizedExterior;
import com.valework.yingul.model.Yng_MotorizedSecurity;
import com.valework.yingul.model.Yng_MotorizedSound;
import com.valework.yingul.model.Yng_Notification;
import com.valework.yingul.model.Yng_OmittedWord;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_PropertyAmbient;
import com.valework.yingul.model.Yng_PropertyAmenities;
import com.valework.yingul.model.Yng_Query;
import com.valework.yingul.model.Yng_Quote;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_ServiceProvince;
import com.valework.yingul.model.Yng_Shipment;
import com.valework.yingul.model.Yng_Shipping;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_XubioSalesInvoice;
import com.valework.yingul.model.Yng_XubioTransaccionProductoItems;
import com.valework.yingul.service.ItemCategoryService;
import com.valework.yingul.service.ItemImageService;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.MotorizedService;
import com.valework.yingul.service.PersonService;
import com.valework.yingul.service.ProductService;
import com.valework.yingul.service.PropertyService;
import com.valework.yingul.service.QueryService;
import com.valework.yingul.service.S3Services;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.UserService;

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
    @Autowired
	ServiceProvinceDao serviceProvinceDao;
    //@Autowired
    //ServiceProvinceService provinceService;
    @Autowired
	PropertyAmbientDao propertyAmbietDao;
    @Autowired
	AmbientDao ambientDao;
	@Autowired
	AmenitiesDao amenitiesDao;
	@Autowired
	PropertyAmenitiesDao propertyAmenitiesDao;
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
	MotorizedSecurityDao motorizedSecurityDao;
	@Autowired
	MotorizedConfortDao motorizedConfortDao;
	@Autowired
	MotorizedEquipmentDao motorizedEquipmentDao;
	@Autowired
	MotorizedExteriorDao motorizedExteriorDao;
	@Autowired
	MotorizedSoundDao motorizedSoundDao;
	@Autowired
	BranchAndreaniDao branchAndreaniDao;
	@Autowired
	CategoryController categoryController;
	@Autowired
	S3Services s3Services;
	@Autowired 
	ItemImageDao itemImageDao;
	@Autowired
	OmittedWordDao omittedWordDao;
	@Autowired 
	UserService userService;
	@Autowired 
	ShipmentDao shipmentDao;
	@Autowired
	ShippingDao shippingDao;
	@Autowired
	BuyDao buyDao;
	@Autowired
	ConfirmDao confirmDao;
	@Autowired 
	ClaimDao claimDao;
	@Autowired 
	XubioSalesInvoiceDao xubioSalesInvoiceDao;
	@Autowired 
	XubioTransaccionProductoItemsDao xubioTransaccionProductoItemsDao;
	@Autowired
	FavoriteDao favoriteDao;
	@Autowired
	ItemCategoryDao itemCategoryDao;
	@Autowired
	NotificationDao notificationDao;
	@Autowired 
	QuoteDao quoteDao;
	
	@Value("${jsa.s3.bucket}")
	private String bucketName;
	
	@RequestMapping("/getQuantityAllItems")
    public String getQuantityAllItems() {
		List<Yng_Item> itemList = itemDao.findAll();
		return String.valueOf(itemList.size());
    }
	
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
    	System.out.println("username:"+username);
    	Yng_User yng_User = userDao.findByUsername(username);
    	
    	Long userId = yng_User.getUserId();
    	System.out.println("userId:"+userId);
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
    	if(categoryId == 0 && minPrice == 0 && maxPrice == 0 && minYear == 0 && maxYear == 0) {
    		List<Yng_Motorized> motorizedList = motorizedDao.findAll();
            Set<Yng_Item> itemList = itemService.findMotorized(motorizedList);
            return itemList;
    	}else {
    		List<Yng_Motorized> motorizedList = motorizedDao.findAll();
    		Set<Yng_Item> itemList = itemService.searchMotorized(motorizedList, categoryId, minPrice, maxPrice, minYear, maxYear);
            return itemList;
    	}

    }
    @RequestMapping("/searchPropertyCategoryConditonUbication/{categoryId}/{condition}/{typeUbication}/{idTypeUbication}")
    public Set<Yng_Item> searchPropertyCategoryConditonUbication(@PathVariable("categoryId") Long categoryId,@PathVariable("condition") String condition,@PathVariable("typeUbication") String typeUbication,@PathVariable("idTypeUbication") int idTypeUbication) {
    	Set<Yng_Item> filtered = new HashSet<>();
    	Set<Yng_Item> filteredB = new HashSet<>();
    	System.out.println(" "+categoryId+"/"+condition+"/"+typeUbication+"/"+idTypeUbication);
    	Set<Yng_Category> categories = new HashSet<>();
    	categories = categoryController.fatherForItemTypeAndNamecategory("Property",condition);
    	for (Yng_Category yng_Category : categories) {
    		Set<Yng_Item> itemCategory = new HashSet<>();
    		itemCategory = findOnlyItemsByCategory(yng_Category.getCategoryId());
    		for (Yng_Item yng_Item : itemCategory) {
				filtered.add(yng_Item);
			}
		}
    	
    	if(categoryId==0) {
  
    	}else {
    		filtered=new HashSet<>();
    		Yng_Category category = categoryController.categoryForFatherAndNamecategory(categoryId, condition);
    		Set<Yng_Item> itemCategory = new HashSet<>();
    		itemCategory = findOnlyItemsByCategory(category.getCategoryId());
    		for (Yng_Item yng_Item : itemCategory) {
				filtered.add(yng_Item);
			}
    	}
    	
    	if(idTypeUbication==0) {
    		
    	}else {
    		filteredB = filtered;
			filtered = new HashSet<>();
    		switch (typeUbication) {
			case "country":
				for (Yng_Item yng_Item : filteredB) {
					if(yng_Item.getYng_Ubication().getYng_Country().getCountryId()==idTypeUbication) {
						filtered.add(yng_Item);
					}
				}
				break;
			case "province":
				for (Yng_Item yng_Item : filteredB) {
					if(yng_Item.getYng_Ubication().getYng_Province().getProvinceId()==idTypeUbication) {
						filtered.add(yng_Item);
					}
				}
				
				break;
			case "city":
				for (Yng_Item yng_Item : filteredB) {
					if(yng_Item.getYng_Ubication().getYng_City().getCityId()==idTypeUbication) {
						filtered.add(yng_Item);
					}
				}
				break;
			default:
				break;
			}
    	}
    	
    	return filtered;
    	

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
    	//************inicioBorramosLasCategoriasPreviamente___MejorarNoFuncionaAl100%*****
    	Yng_Motorized motorizedOrigin =new Yng_Motorized();
    	Yng_Motorized objetNull=motorizedDao.findByMotorizedId((long) 0);
    	
    	motorizedOrigin=getMotorizedByIdItem(moto.getYng_Item().getItemId());
    	Set<Yng_MotorizedSecurity> motorizedSecurityOrigin = new HashSet<>();		
		Set<Yng_MotorizedConfort> motorizedConfortOrigin = new HashSet<>();
		Set<Yng_MotorizedEquipment> motorizedEquipmentOrigin = new HashSet<>();
		Set<Yng_MotorizedExterior> motorizedExteriorOrigin = new HashSet<>();
		Set<Yng_MotorizedSound> motorizedSoundOrigin = new HashSet<>();
    	
		
		
		motorizedSecurityOrigin=motorizedOrigin.getMotorizedSecurity();
  		for (Yng_MotorizedSecurity pas : motorizedSecurityOrigin) {
			motorizedSecurityDao.delete(pas);
			pas.setMotorized(objetNull);
		}
  		
  		motorizedConfortOrigin=motorizedOrigin.getMotorizedConfort();
  		for (Yng_MotorizedConfort motorizedConfort : motorizedConfortOrigin) {
			motorizedConfortDao.delete(motorizedConfort);
			motorizedConfort.setMotorized(objetNull);
		}
  		motorizedEquipmentOrigin = motorizedOrigin.getMotorizedEquipment();
  		for (Yng_MotorizedEquipment motorizedEquipment : motorizedEquipmentOrigin) {
  			motorizedEquipment.setMotorized(objetNull);
  			motorizedEquipmentDao.delete(motorizedEquipment);			
		}
  		motorizedSoundOrigin= motorizedOrigin.getMotorizedSound();
  		for (Yng_MotorizedSound motorizedSound : motorizedSoundOrigin) {
  			motorizedSound.setMotorized(objetNull);
  			motorizedSoundDao.delete(motorizedSound);
		}
  		motorizedExteriorOrigin =motorizedOrigin.getMotorizedExterior();
  		for (Yng_MotorizedExterior motorizedExterior : motorizedExteriorOrigin) {
  			motorizedExterior.setMotorized(objetNull);
  			motorizedExteriorDao.delete(motorizedExterior);
		}
    	//************finBorramosLasCategoriasPreviamente*******//
    	//*************inicioInsertar**************
		Set<Yng_MotorizedSecurity> motorizedSecurity = new HashSet<>();		
		Set<Yng_MotorizedConfort> motorizedConfort = new HashSet<>();
		Set<Yng_MotorizedEquipment> motorizedEquipment = new HashSet<>();
		Set<Yng_MotorizedExterior> motorizedExterior = new HashSet<>();
		Set<Yng_MotorizedSound> motorizedSound = new HashSet<>();
		
		
		motorizedSecurity=moto.getMotorizedSecurity();
		motorizedConfort=moto.getMotorizedConfort();
		motorizedEquipment=moto.getMotorizedEquipment();
		motorizedExterior = moto.getMotorizedExterior();
		motorizedSound=moto.getMotorizedSound();
		
		moto.setMotorizedSecurity(null);
		moto.setMotorizedConfort(null);
		moto.setMotorizedEquipment(null);
		moto.setMotorizedExterior(null);
		moto.setMotorizedSound(null);
		
		
        Yng_Motorized mots = motorizedDao.save(moto);        
 

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
        	motorizedExteriorDao.save(si);
        }
        
        for(Yng_MotorizedSound si:motorizedSound) {
        	si.setSound(soundDao.findBySoundId(si.getSound().getSoundId()));
        	si.setMotorized(mots);
        	motorizedSoundDao.save(si);
        } 
    	//*************finInsertar*****************
    	//motorizedDao.save(moto);
    	return "save";
    }
    @RequestMapping(value = "/property/update", method = RequestMethod.POST)
	@ResponseBody
    public String updatePropertyPost(@Valid @RequestBody Yng_Property property) throws MessagingException {
    	Yng_Property prop=new Yng_Property();
    	prop=property;
    	Yng_Item yng_Item=prop.getYng_Item();
    	itemDao.save(yng_Item);
    	
       	//************inicioBorramosLasCategoriasPreviamente___MejorarNoFuncionaAl100%*****
    	
    	
    	
    	Yng_Property propOrigin=new Yng_Property();      	
    	propOrigin=getPropertyByIdItem(prop.getYng_Item().getItemId());       		
       		Set<Yng_PropertyAmbient> propertyAmbientOrigin = new HashSet<>();
       		propertyAmbientOrigin=propOrigin.getPropertyAmbient();
           	//System.out.println("serviceProvinceOrigin:"+serviceProvinceOrigin.toString());
           	Yng_Property serviceTemp=propertyDao.findByPropertyId((long) 0);
      		for (Yng_PropertyAmbient sp : propertyAmbientOrigin) {
      			System.out.println("delete sp:"+sp.getAmbient().getName());      			
      			Long id = sp.getPropertyAmbientId();
      			System.out.println("id sp:"+id);
				propertyAmbietDao.delete(id);
      			sp.setProperty(serviceTemp);
      			
    		}
       	
      		Set<Yng_PropertyAmenities> propertyAmenitiesOrigin=new HashSet<>();
      		propertyAmenitiesOrigin=propOrigin.getPropertyAmenities();
      		for (Yng_PropertyAmenities pas : propertyAmenitiesOrigin) {
				propertyAmenitiesDao.delete(pas);
				pas.setProperty(serviceTemp);
			}
       	
       	//**************finBorramosLasCategoriasPreviamente*******
    	
    	//*************inicioInsertar**************
    	Set<Yng_PropertyAmenities> propertyAmenities=new HashSet<>();
  		Set<Yng_PropertyAmbient> propertyAmbient=new HashSet<>();
  		
  		propertyAmenities=prop.getPropertyAmenities();
  		propertyAmbient=prop.getPropertyAmbient();
  		
  		prop.setPropertyAmbient(null);
  		prop.setPropertyAmenities(null);
  		
        Yng_Property propT = propertyDao.save(prop);
        
        
        
        for(Yng_PropertyAmbient si:propertyAmbient)
        {
        	si.setAmbient(ambientDao.findByAmbientId(si.getAmbient().getAmbientId()));
        	si.setProperty(propT);
        	propertyAmbietDao.save(si);        
        }        
        for(Yng_PropertyAmenities si:propertyAmenities)       	
        {
        	si.setAmenities(amenitiesDao.findByAmenitiesId(si.getAmenities().getAmenitiesId()));
        	si.setProperty(propT);
        	propertyAmenitiesDao.save(si);       	
        }

    	//*************finInsertar****************    	
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
    @RequestMapping("/over20first/{sw}")
    public List<Yng_Item> getOver20first(@PathVariable("sw") boolean sw, @RequestHeader("X-API-KEY") String XAPIKEY) {
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		List<Yng_Item> itemList = itemDao.findByIsOverOrderByItemIdDesc(sw);
    		if(itemList.size()>20) {
    			itemList=itemList.subList(0, 20);
    		}
            return itemList;
    	}else {
    		return null;
    	}
        
    }
    @RequestMapping("/listItemParams/{type}/{over}/{order}/{start}/{end}")
    public List<Yng_Item> listItemParams(@PathVariable("type") String type,@PathVariable("over") String over,@PathVariable("order") String order,@PathVariable("start") int start,@PathVariable("end") int end, @RequestHeader("X-API-KEY") String XAPIKEY) {
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		List<Yng_Item> itemList = new ArrayList<Yng_Item>();
    		if(type.equals("All")) {
    			if(order.equals("Asc")) {
    				switch (over) {
	    	            case "All":  
	    	            	itemList = itemDao.findByOrderByItemIdAsc();
	    	                break;
	    	            case "true":  
	    	            	itemList = itemDao.findByIsOverOrderByItemIdAsc(true);
	    	                break;
	    	            case "false":  
	    	            	itemList = itemDao.findByIsOverOrderByItemIdAsc(false);
	    	                break;
    	            }
				}else {
					switch (over) {
    	            case "All":  
    	            	itemList = itemDao.findByOrderByItemIdDesc();
    	                break;
    	            case "true":  
    	            	itemList = itemDao.findByIsOverOrderByItemIdDesc(true);
    	                break;
    	            case "false":  
    	            	itemList = itemDao.findByIsOverOrderByItemIdDesc(false);
    	                break;
					}
				}
    		}else {
    			if(order.equals("Asc")) {
    				switch (over) {
    	            case "All":  
    	            	itemList = itemDao.findByTypeOrderByItemIdAsc(type);
    	                break;
    	            case "true":  
    	            	itemList = itemDao.findByIsOverAndTypeOrderByItemIdAsc(true,type);
    	                break;
    	            case "false":  
    	            	itemList = itemDao.findByIsOverAndTypeOrderByItemIdAsc(false,type);
    	                break;
					}
				}else {
					switch (over) {
    	            case "All":  
    	            	itemList = itemDao.findByTypeOrderByItemIdDesc(type);
    	                break;
    	            case "true":  
    	            	itemList = itemDao.findByIsOverAndTypeOrderByItemIdDesc(true,type);
    	                break;
    	            case "false":  
    	            	itemList = itemDao.findByIsOverAndTypeOrderByItemIdDesc(false,type);
    	                break;
					}
				}
    		}
    		if(itemList.size()>=start) {
    			System.out.println("si "+end+"es mayor que"+itemList.size());
    			if(end>=itemList.size()) {
    				itemList=itemList.subList(start, itemList.size());	
    			}else{
    				itemList=itemList.subList(start, end);	
    			}
    		}
            return itemList;
    	}else {
    		return null;
    	}
        
    }
    @RequestMapping("/ListItemsToEdit/{username}")
    public List<Yng_Item> ListItemsToEdit(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Item> itemList = itemService.findByUser(yng_User);
        Set<Yng_Item> listItemTemp = new HashSet<>();
    	for (Yng_Item st : itemList) {
    		if(!((st.getType().equals("Motorized")||st.getType().equals("Property"))&&(st.getQuantity()<=0||!st.isEnabled()))) {
    			listItemTemp.add(st);
    		}
		}
        return itemList;
    }
    @RequestMapping(value = "/service/update", method = RequestMethod.POST)
   	@ResponseBody
       public String updateServicePost(@Valid @RequestBody Yng_Service service) throws MessagingException {
    	Yng_Service serv=new Yng_Service();
    	serv=service;
       	Yng_Item yng_Item=serv.getYng_Item();
       	//*************inicio_borramos_las_categorias_previamente_Mejorar_no_funciona _la 100%
       	Yng_Service servOrigin=new Yng_Service();
     
       	if(serv.getYng_Item().getType().equals("Service")) {
       		servOrigin=getServiceByIdItem(serv.getYng_Item().getItemId());       		
       		Set<Yng_ServiceProvince> serviceProvinceOrigin = new HashSet<>();
           	serviceProvinceOrigin=servOrigin.getCobertureZone();
           	System.out.println("serviceProvinceOrigin:"+serviceProvinceOrigin.toString());
           	Yng_Service serviceTemp=serviceDao.findByServiceId((long) 0);
      		for (Yng_ServiceProvince sp : serviceProvinceOrigin) {
      			System.out.println("delete sp:"+sp.getProvince().getName());      			
      			Long id = sp.getServiceProvinceId();
				serviceProvinceDao.delete(id);
      			sp.setService(serviceTemp);
      			System.out.println("id sp:"+id);
    		}      		
       	}
       	
       	//fin borramos las categorias previamente
       
        //obtenemos la lista de provincia de la zona de cobertura
  		Set<Yng_ServiceProvince> serviceProvince = new HashSet<>();
  		serviceProvince=serv.getCobertureZone();
  		//borramos la lista de cagorias para que no se inserte dos veces
  		serv.setCobertureZone(null);
        Yng_Service serz = serviceDao.save(serv);
        //insertamos las nuevas Zonas de cobertura
        for (Yng_ServiceProvince si : serviceProvince) {
        	si.setProvince(provinceDao.findByProvinceId(si.getProvince().getProvinceId()));
        	si.setService(serz);
        	serviceProvinceDao.save(si);	    
		}
        
       	itemDao.save(yng_Item);
       	return "save";
       }

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
   		Yng_BranchAndreani branchAndreani=new Yng_BranchAndreani();
   		try {
   			branchAndreani=log.andreaniSucursales(ubiTemp.getPostalCode(), "", "");
   			//codAndreani=log.andreaniSucursales(ubiTemp.getPostalCode(), "", "").getCodAndreani();
   			codAndreani=branchAndreani.getCodAndreani();
   		} catch (Exception e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
   		}	
   		branchAndreaniDao.save(branchAndreani);
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
    @DeleteMapping("/students/{id}")
	public void deleteStudent(@PathVariable long id) {
    	serviceProvinceDao.delete(id);
	}
    
    @RequestMapping(value = "/updateName", method = RequestMethod.POST)
	@ResponseBody
	public String updateName(@Valid @RequestBody Yng_Item item,@RequestHeader("Authorization") String authorization) throws MessagingException, IOException {	
    	String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Item itemTemp = itemDao.findByItemId(item.getItemId());
		Yng_User yng_User= userDao.findByUsername(itemTemp.getUser().getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			itemTemp.setName(item.getName());
	    	itemTemp=itemDao.save(itemTemp);
	    	smtpMailSender.send(yng_User.getEmail(), "El Título de un Artículo de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
        			"<br>" + 
        			"Su Título de un Artículo de Yingul ha cambiado recientemente.<br>" + 
        			"Puede ver sus modificaciones <a href=\"https://www.yingul.com/itemDetail/"+itemTemp.getItemId()+"\" target=\"_blank\">aquí</a> <br>"+
        			"<br>"
        			+ "<p>Atentamente:</p>\r\n"  
					+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
					+ "<p>Su equípo de registro Yingul Company SRL</p>" +
					"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        			"<br>" + 
        			"Copyright 2018 Yingul S.R.L. All rights reserved.");
	    	return "save";				
		}else {
			return "prohibited";
		}
    }
    @RequestMapping(value = "/updateDescription", method = RequestMethod.POST)
	@ResponseBody
	public String updateDescription(@Valid @RequestBody Yng_Item item,@RequestHeader("Authorization") String authorization) throws MessagingException, IOException {	
    	String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Item itemTemp = itemDao.findByItemId(item.getItemId());
		Yng_User yng_User= userDao.findByUsername(itemTemp.getUser().getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			itemTemp.setDescription(item.getDescription());
	    	itemTemp=itemDao.save(itemTemp);
	    	smtpMailSender.send(yng_User.getEmail(), "La Descripción de un Artículo de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
        			"<br>" + 
        			"Su Descripción de un Artículo de Yingul ha cambiado recientemente.<br>" + 
        			"Puede ver sus modificaciones <a href=\"https://www.yingul.com/itemDetail/"+itemTemp.getItemId()+"\" target=\"_blank\">aquí</a><br>"+
        			"<br>" 
        			+ "<p>Atentamente:</p>\r\n"  
					+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
					+ "<p>Su equípo de registro Yingul Company SRL</p>" +
					"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        			"<br>" + 
        			"Copyright 2018 Yingul S.R.L. All rights reserved.");
	    	return "save";				
		}else {
			return "prohibited";
		}
    }
    @RequestMapping(value = "/updateQuantity", method = RequestMethod.POST)
	@ResponseBody
	public String updateQuantity(@Valid @RequestBody Yng_Item item,@RequestHeader("Authorization") String authorization) throws MessagingException, IOException {	
    	String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Item itemTemp = itemDao.findByItemId(item.getItemId());
		Yng_User yng_User= userDao.findByUsername(itemTemp.getUser().getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			itemTemp.setQuantity(item.getQuantity());
			if(itemTemp.getQuantity()==0) {
				itemTemp.setEnabled(false);
			}
			if(itemTemp.getQuantity()>0) {
				itemTemp.setEnabled(true);
			}
	    	itemTemp=itemDao.save(itemTemp);
	    	smtpMailSender.send(yng_User.getEmail(), "La Cantidad en Stock de un Artículo de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
        			"<br>" + 
        			"Su Cantidad en Stock de un Artículo de Yingul ha cambiado recientemente.<br>" + 
        			"Puede ver sus modificaciones <a href=\"https://www.yingul.com/itemDetail/"+itemTemp.getItemId()+"\" target=\"_blank\">aquí</a> <br>"+
        			"<br>" 
        			+ "<p>Atentamente:</p>\r\n"  
					+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
					+ "<p>Su equípo de registro Yingul Company SRL</p>" +
					"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        			"<br>" + 
        			"Copyright 2018 Yingul S.R.L. All rights reserved.");
	    	return "save";				
		}else {
			return "prohibited";
		}
    }
    @RequestMapping(value = "/updatePrice", method = RequestMethod.POST)
	@ResponseBody
	public String updatePrice(@Valid @RequestBody Yng_Item item,@RequestHeader("Authorization") String authorization) throws MessagingException, IOException {	
    	String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Item itemTemp = itemDao.findByItemId(item.getItemId());
		Yng_User yng_User= userDao.findByUsername(itemTemp.getUser().getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			itemTemp.setPrice(item.getPrice());
			itemTemp.setPriceDiscount(item.getPriceDiscount());
			itemTemp.setPriceNormal(item.getPriceNormal());
			itemTemp.setMoney(item.getMoney());
	    	itemTemp=itemDao.save(itemTemp);
	    	smtpMailSender.send(yng_User.getEmail(), "El Precio de un Artículo de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
        			"<br>" + 
        			"Su Precio de un Artículo de Yingul ha cambiado recientemente.<br>" + 
        			"Puede ver sus modificaciones  <a href=\"https://www.yingul.com/itemDetail/"+itemTemp.getItemId()+"\" target=\"_blank\">aquí</a><br>"+
        			"<br>"  
        			+ "<p>Atentamente:</p>\r\n"  
					+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
					+ "<p>Su equípo de registro Yingul Company SRL</p>" +
					"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        			"<br>" + 
        			"Copyright 2018 Yingul S.R.L. All rights reserved.");
	    	return "save";				
		}else {
			return "prohibited";
		}
    }
    @RequestMapping(value = "/updateImages", method = RequestMethod.POST)
	@ResponseBody
	public String updateImages(@Valid @RequestBody Yng_Item item,@RequestHeader("Authorization") String authorization) throws MessagingException, IOException {	
    	String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_Item itemTemp = itemDao.findByItemId(item.getItemId());
		Yng_User yng_User= userDao.findByUsername(itemTemp.getUser().getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			/*************para reemplazar la imagen principal**************/
			String image=item.getPrincipalImage();
			String extension="jpeg";
			String nombre=itemTemp.getPrincipalImage();
			if(!nombre.equals("sin.jpg")) {
				deleteImage(nombre);	
			}
			byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			bI=convertImage(bI);
			System.out.println(nombre);
			String[] parts1 = nombre.split("\\.");
			System.out.println(Arrays.toString(parts1));
			nombre=parts1[0];
			int a=0;
			if(nombre.contains("-")) {
				parts1 = nombre.split("-");
				a = Integer.parseInt(parts1[1]);
				a++;
				nombre=parts1[0];
			}
			nombre=nombre+"-"+a;
			s3Services.uploadFile(nombre,extension, bI);
			nombre=nombre+"."+extension;   
			itemTemp.setPrincipalImage(nombre);
			/***************************************************************/
			/********************para reemplzar la otras imagenes*********************************/
			List<Yng_ItemImage> imagelist=findImageByItem(itemTemp.getItemId());
			for (Yng_ItemImage yng_ItemImage : imagelist) {
				deleteImage(yng_ItemImage.getImage());
				itemImageDao.delete(yng_ItemImage);
			}
			
			Set<Yng_ItemImage> imageListUp = item.getItemImage();
			int k=0;
			for (Yng_ItemImage st : imageListUp) {
	        	k++;
	        	image=st.getImage();
	    		st.setImage("");
	    		extension="jpeg";
	    		nombre="img"+k+itemTemp.getItemId()+"-"+a;
	    		bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
	    		bI=convertImage(bI);
	    		s3Services.uploadFile(nombre,extension, bI);
	    		nombre=nombre+"."+extension;   
	    		st.setImage(nombre);
	    		st.setItem(itemTemp);	
	        	itemImageDao.save(st);	    
			}
			/*************************************************************************************/
			itemTemp=itemDao.save(itemTemp);
	    	smtpMailSender.send(yng_User.getEmail(), "La(s) imagen(es) de un Artículo de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
        			"<br>" + 
        			"Su(s) imagen(es) de un Artículo de Yingul ha cambiado recientemente.<br>" + 
        			"Puede ver sus modificaciones <a href=\"https://www.yingul.com/itemDetail/"+itemTemp.getItemId()+"\" target=\"_blank\">aquí</a> <br>"+
        			"<br>" 
        			+ "<p>Atentamente:</p>\r\n"  
					+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
					+ "<p>Su equípo de registro Yingul Company SRL</p>" +
					"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        			"<br>" + 
        			"Copyright 2018 Yingul S.R.L. All rights reserved.");
	    	return "save";				
		}else {
			return "prohibited";
		}
    }
    public String  deleteImage(String nombre) {
		String KeyName="";
		s3Services.deleteFile(bucketName+"/image", nombre);
		 return "delete";
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
    @RequestMapping("/listItemByName/{name}/{start}/{end}")
    public List<Yng_Item> listItemByName(@PathVariable("name") String name,@PathVariable("start") int start,@PathVariable("end") int end, @RequestHeader("X-API-KEY") String XAPIKEY) {
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
	    	String[] parts = name.split(" ");
	    	List<String> wordList = new LinkedList<String>(Arrays.asList(parts));  
	    	List<Yng_OmittedWord> omittedWords = omittedWordDao.findAll();
	    	List<Yng_Item> itemListForReturn = new ArrayList<Yng_Item>();
	    	List<Yng_Item> itemList = itemDao.findByOrderByItemIdDesc();
	    	for (Yng_Item yng_Item : itemList) {
    			if(yng_Item.getName().toLowerCase().replace(" ","").contains(name.toLowerCase().replace(" ",""))){
    				itemListForReturn.add(yng_Item);
    			}
    		}
	    	System.out.println(wordList.toString());
	    	for (int i=0;i<wordList.size();i++) {
	    		for (Yng_OmittedWord yng_OmittedWord : omittedWords) {
	    			if(wordList.get(i).equals(yng_OmittedWord.getWord())) {
	    				System.out.println(wordList.get(i)+" "+yng_OmittedWord.getWord());
	    				wordList.set(i, " ");
	    				//wordList =removeItemFromArray(wordList,string);
	    			}
		    	}
	    	}
	    	System.out.println(wordList.toString());
	    	for (String string : wordList) {
	    		if(!string.equals(" ")) {
	    			for (Yng_Item yng_Item : itemList) {
		    			if(string.charAt(string.length()-1)=='s'||string.charAt(string.length()-1)=='S') {
		    				string=string.substring(0, string.length()-1);
		    			}
		    			if(yng_Item.getName().toLowerCase().contains(string.toLowerCase())){
		    				if(itemListForReturn.contains(yng_Item)) {
		    						
		    				}else {
		    					itemListForReturn.add(yng_Item);
		    				}
		    			}	
		    		}	
	    		}	
    		}   		
    		if(itemListForReturn.size()>=start) {
    			if(end>=itemListForReturn.size()) {
    				itemListForReturn=itemListForReturn.subList(start, itemListForReturn.size());	
    			}else{
    				itemListForReturn=itemListForReturn.subList(start, end);	
    			}
    		}
    		return itemListForReturn;	
    	}else {
    		return null;
    	}
        
    }
    
    @RequestMapping("/getQuantityItemByName/{name}")
    public String getQuantityItemByName(@PathVariable("name") String name, @RequestHeader("X-API-KEY") String XAPIKEY) {
    	Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		String[] parts = name.split(" ");
	    	List<String> wordList = new LinkedList<String>(Arrays.asList(parts));  
	    	List<Yng_OmittedWord> omittedWords = omittedWordDao.findAll();
	    	List<Yng_Item> itemListForReturn = new ArrayList<Yng_Item>();
	    	List<Yng_Item> itemList = itemDao.findByOrderByItemIdDesc();
	    	for (Yng_Item yng_Item : itemList) {
    			if(yng_Item.getName().toLowerCase().replace(" ","").contains(name.toLowerCase().replace(" ",""))){
    				itemListForReturn.add(yng_Item);
    			}
    		}
	    	System.out.println(wordList.toString());
	    	for (int i=0;i<wordList.size();i++) {
	    		for (Yng_OmittedWord yng_OmittedWord : omittedWords) {
	    			if(wordList.get(i).equals(yng_OmittedWord.getWord())) {
	    				System.out.println(wordList.get(i)+" "+yng_OmittedWord.getWord());
	    				wordList.set(i, " ");
	    				//wordList =removeItemFromArray(wordList,string);
	    			}
		    	}
	    	}
	    	System.out.println(wordList.toString());
	    	for (String string : wordList) {
	    		if(!string.equals(" ")) {
	    			for (Yng_Item yng_Item : itemList) {
		    			if(string.charAt(string.length()-1)=='s'||string.charAt(string.length()-1)=='S') {
		    				string=string.substring(0, string.length()-1);
		    			}
		    			if(yng_Item.getName().toLowerCase().contains(string.toLowerCase())){
		    				if(itemListForReturn.contains(yng_Item)) {
		    						
		    				}else {
		    					itemListForReturn.add(yng_Item);
		    				}
		    			}	
		    		}	
	    		}	
    		} 
    		return itemListForReturn.size()+"";
    	}else {
    		return null;
    	}
        
    }
    
    @RequestMapping("/deleteItemById/{itemId}")
    public String deleteItemById(@PathVariable("itemId") Long itemId, @RequestHeader("Authorization") String authorization) {
    	String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User admin= userDao.findByUsername(parts[0]);
    	if(userService.isAdmin(admin)) {
    		Yng_Item item = new Yng_Item();
    		item= itemDao.findByItemId(itemId);
    		if(item!=null) {
	    		deleteBuyByItem(item);
	    		deleteFavoriteByItem(item);
	    		deleteItemCategoryByItem(item);
	    		deleteItemImageByItem(item);
	    		deleteMotorizedByItem(item);
	    		deleteNotificationByItem(item);
	    		deleteProductByItem(item);
	    		deletePropertyByItem(item);
	    		deleteQueryByItem(item);
	    		deleteQuoteByItem(item);
	    		deleteServiceByItem(item);
	    		deleteShipmentByItem(item);
	    		itemDao.delete(item);
	    		return "save";
	    	}else {
	    		return "doesNoExist";
	    	}
    	}else {
        	return "prohibited";
    	}
    }
    
    public void deleteServiceByItem(Yng_Item item) {
    	List<Yng_Service> serviceList = serviceDao.findAll();
    	for (Yng_Service service : serviceList) {
			if(service.getYng_Item()!=null && service.getYng_Item().getItemId()==item.getItemId()) {
				deleteServiceProvinceByService(service);
				serviceDao.delete(service);
			}
		}
    }
    
    public void deleteServiceProvinceByService(Yng_Service service) {
    	List<Yng_ServiceProvince> provinceList = serviceProvinceDao.findByService(service);
    	for (Yng_ServiceProvince province : provinceList) {
    		serviceProvinceDao.delete(province);
		}
    }
    
    public void deleteQuoteByItem(Yng_Item item) {
    	List<Yng_Quote> quoteList = quoteDao.findAll();
    	for (Yng_Quote quote : quoteList) {
			if(quote.getYng_Item()!=null && quote.getYng_Item().getItemId()==item.getItemId()) {
				deleteShippingByQuote(quote);
				quoteDao.delete(quote);
			}
		}
    }
    
    public void deleteShippingByQuote(Yng_Quote quote) {
    	List<Yng_Shipping> shippingList = shippingDao.findAll();
    	for (Yng_Shipping shipping : shippingList) {
			if(shipping.getYng_Quote()!=null && shipping.getYng_Quote().getQuoteId()==quote.getQuoteId()) {
				deleteBuyByShipping(shipping);
				shippingDao.delete(shipping);
			}
		}
    }
    
    public void deleteQueryByItem(Yng_Item item) {
    	List<Yng_Query> queryList = queryDao.findAll();
    	for (Yng_Query query : queryList) {
			if(query.getYng_Item()!=null && query.getYng_Item().getItemId()==item.getItemId()) {
				queryDao.delete(query);
			}
		}
    }
    
    public void deletePropertyByItem(Yng_Item item) {
    	List<Yng_Property> propertyList = propertyDao.findAll();
    	for (Yng_Property property : propertyList) {
			if(property.getYng_Item()!=null && property.getYng_Item().getItemId()==item.getItemId()) {
				deletePropertyAmbientByProperty(property);
				deletePropertyAmenitiesByProperty(property);
				propertyDao.delete(property);
			}
		}
    }
    
    public void deletePropertyAmbientByProperty(Yng_Property property) {
    	List<Yng_PropertyAmbient> ambientList = propertyAmbietDao.findByProperty(property);
    	for (Yng_PropertyAmbient ambient : ambientList) {
    		propertyAmbietDao.delete(ambient);
		}
    }
    
    public void deletePropertyAmenitiesByProperty(Yng_Property property) {
    	List<Yng_PropertyAmenities> amenitieList = propertyAmenitiesDao.findByProperty(property);
    	for (Yng_PropertyAmenities amenitie : amenitieList) {
    		propertyAmenitiesDao.delete(amenitie);
		}
    }
    
    public void deleteProductByItem(Yng_Item item) {
    	List<Yng_Product> productList = productDao.findAll();
    	for (Yng_Product product : productList) {
			if(product.getYng_Item()!=null && product.getYng_Item().getItemId()==item.getItemId()) {
				productDao.delete(product);
			}
		}
    }
    
    public void deleteNotificationByItem(Yng_Item item) {
    	List<Yng_Notification> notificationList = notificationDao.findByItem(item);
    	for (Yng_Notification notification : notificationList) {
			notificationDao.delete(notification);
		}
    }
    
    public void deleteMotorizedByItem(Yng_Item item) {
    	List<Yng_Motorized> motorizedList = motorizedDao.findAll();
    	for (Yng_Motorized motorized : motorizedList) {
			if(motorized.getYng_Item()!=null && motorized.getYng_Item().getItemId()==item.getItemId()) {
				deleteMotorizedConfortByMotorized(motorized);
				deleteMotorizedEquipmentByMotorized(motorized);
				deleteMotorizedExteriorByMotorized(motorized);
				deleteMotorizedSecurityByMotorized(motorized);
				deleteMotorizedSoundByMotorized(motorized);
				motorizedDao.delete(motorized);
			}
		}
    }
    
    public void deleteMotorizedSoundByMotorized(Yng_Motorized motorized) {
    	List<Yng_MotorizedSound> soundList = motorizedSoundDao.findByMotorized(motorized);
    	for (Yng_MotorizedSound sound : soundList) {
    		motorizedSoundDao.delete(sound);
		}
    }
    
    public void deleteMotorizedSecurityByMotorized(Yng_Motorized motorized) {
    	List<Yng_MotorizedSecurity> securityList = motorizedSecurityDao.findByMotorized(motorized);
    	for (Yng_MotorizedSecurity security : securityList) {
    		motorizedSecurityDao.delete(security);
		}
    }
    
    public void deleteMotorizedConfortByMotorized(Yng_Motorized motorized) {
    	List<Yng_MotorizedConfort> confortList = motorizedConfortDao.findByMotorized(motorized);
    	for (Yng_MotorizedConfort confort : confortList) {
			motorizedConfortDao.delete(confort);
		}
    }
    
    public void deleteMotorizedEquipmentByMotorized(Yng_Motorized motorized) {
    	List<Yng_MotorizedEquipment> equipmentList = motorizedEquipmentDao.findByMotorized(motorized);
    	for (Yng_MotorizedEquipment equipment : equipmentList) {
			motorizedEquipmentDao.delete(equipment);
		}
    }
    
    public void deleteMotorizedExteriorByMotorized(Yng_Motorized motorized) {
    	List<Yng_MotorizedExterior> exteriorList = motorizedExteriorDao.findByMotorized(motorized);
    	for (Yng_MotorizedExterior exterior : exteriorList) {
			motorizedExteriorDao.delete(exterior);
		}
    }
    
    public void deleteItemImageByItem(Yng_Item item) {
    	List<Yng_ItemImage> itemImageList = itemImageDao.findByItem(item);
    	for (Yng_ItemImage itemImage : itemImageList) {	
    		deleteImage(itemImage.getImage());
    		itemImageDao.delete(itemImage);
		}
    }
    
    public void deleteItemCategoryByItem(Yng_Item item) {
    	List<Yng_ItemCategory> itemCategoryList = itemCategoryDao.findByItem(item);
    	for (Yng_ItemCategory itemCategory : itemCategoryList) {	
    		itemCategoryDao.delete(itemCategory);
		}
    }
    
    public void deleteFavoriteByItem(Yng_Item item) {
    	List<Yng_Favorite> favoriteList = favoriteDao.findByItem(item);
    	for (Yng_Favorite favorite : favoriteList) {
    		favoriteDao.delete(favorite);
		}
    }
    
    public void deleteBuyByItem(Yng_Item item) {
    	List<Yng_Buy> buyList = buyDao.findAll();
    	for (Yng_Buy buy : buyList) {
    		if(buy.getYng_item()!=null && buy.getYng_item().getItemId()==item.getItemId()) {
    			deleteConfirmByBuy(buy);
        		buyDao.delete(buy);	
    		}
		}
    }
    
    public void deleteShipmentByItem(Yng_Item item) {
    	List<Yng_Shipment> shipmentList = shipmentDao.findAll();
		for (Yng_Shipment shipment : shipmentList) {
			if(shipment.getYng_Item()!=null && shipment.getYng_Item().getItemId()==item.getItemId()) {
				deleteShippingByShipment(shipment);
				shipmentDao.delete(shipment);
			}
		}
    }
    
    public void deleteShippingByShipment(Yng_Shipment shipment) {
    	List<Yng_Shipping> shippingList = shippingDao.findAll();
		for (Yng_Shipping shipping : shippingList) {
			if(shipping.getYng_Shipment()!=null && shipping.getYng_Shipment().getShipmentId()==shipment.getShipmentId()) {
				deleteBuyByShipping(shipping);
				shippingDao.delete(shipping);
			}
		}
    }
    
    public void deleteBuyByShipping(Yng_Shipping shipping){
    	List<Yng_Buy> buyList = buyDao.findByShipping(shipping);
    	for (Yng_Buy buy : buyList) {
    		deleteConfirmByBuy(buy);
			buyDao.delete(buy);
		}
    }
    
    public void deleteConfirmByBuy(Yng_Buy buy) {
    	Yng_Confirm confirm = confirmDao.findByBuy(buy);
    	if(confirm!=null) {
    		deleteClaimByConfirm(confirm);
        	deleteXubioSalesInvoiceByConfirm(confirm);
        	confirmDao.delete(confirm);	
    	}
    }
    
    public void deleteClaimByConfirm(Yng_Confirm confirm) {
    	Yng_Claim claim = claimDao.findByConfirm(confirm);
    	if(claim!=null)
    	claimDao.delete(claim);
    }
    
    public void deleteXubioSalesInvoiceByConfirm(Yng_Confirm confirm) {
    	List<Yng_XubioSalesInvoice> invoiceList = xubioSalesInvoiceDao.findByConfirm(confirm);
    	for (Yng_XubioSalesInvoice invoice : invoiceList) {
    		delteXubioTransaccionProductoItemsByXubioSalesInvoice(invoice);
        	xubioSalesInvoiceDao.delete(invoice);	
		}
    } 
    
    public void delteXubioTransaccionProductoItemsByXubioSalesInvoice(Yng_XubioSalesInvoice xubiSalesInvoice) {
    	List<Yng_XubioTransaccionProductoItems> itemsList = xubioTransaccionProductoItemsDao.findByXubioSalesInvoice(xubiSalesInvoice);
    	for (Yng_XubioTransaccionProductoItems item : itemsList) {
    		xubioTransaccionProductoItemsDao.delete(item);
		}
    }
    
}
