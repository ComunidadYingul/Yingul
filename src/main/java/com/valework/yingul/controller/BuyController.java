package com.valework.yingul.controller;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.PayUFunds;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.BranchAndreaniDao;
import com.valework.yingul.dao.BranchDao;
import com.valework.yingul.dao.BuyDao;
import com.valework.yingul.dao.CardDao;
import com.valework.yingul.dao.CashPaymentDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.CountryDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.EnvioDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.ListCreditCardDao;
import com.valework.yingul.dao.NotificationDao;
import com.valework.yingul.dao.PaymentDao;
import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.ProductDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.QuoteDao;
import com.valework.yingul.dao.RequestBodyDao;
import com.valework.yingul.dao.RequestDao;
import com.valework.yingul.dao.ResponseBodyDao;
import com.valework.yingul.dao.ResponseDao;
import com.valework.yingul.dao.ResponseHeaderDao;
import com.valework.yingul.dao.ShipmentDao;
import com.valework.yingul.dao.ShippingDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.dao.YingulRequestDao;
import com.valework.yingul.logistic.FedexResponce;
import com.valework.yingul.logistic.FedexXML;
import com.valework.yingul.logistic.GetStateSend;
import com.valework.yingul.logistic.GetTraceability;
import com.valework.yingul.logistic.Logistic;
import com.valework.yingul.logistic.PropertyObjectHttp;
import com.valework.yingul.logistic.Yng_AndreaniTrazabilidad;
import com.valework.yingul.logistic.http;
import com.valework.yingul.model.Yng_Branch;
import com.valework.yingul.model.Yng_BranchAndreani;
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_CashPayment;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Country;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ListCreditCard;
import com.valework.yingul.model.Yng_Notification;
import com.valework.yingul.model.Yng_Payment;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Quote;
import com.valework.yingul.model.Yng_Shipment;
import com.valework.yingul.model.Yng_Shipping;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_StateShipping;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_YingulRequest;
import com.valework.yingul.service.CardService;
import com.valework.yingul.service.ProductService;
import com.valework.yingul.service.StandardService;
import com.valework.yingul.util.VisaAPIClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;

@RestController
@RequestMapping("/buy")
public class BuyController {
	private  String FedEXAuthenticationKey ;
	private  String FedExMeterNumber;
	private  String FedExAccountNumber;
	private  String FedexPassword;
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
    private ListCreditCardDao listCreditCardDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ItemDao itemDao;
	@Autowired
	CardDao cardDao;
	@Autowired
	PaymentDao paymentDao;
	@Autowired
	BuyDao buyDao;
	@Autowired 
	CardService cardService;
	//visacybersource
	CloseableHttpResponse response;
	String pushFundsRequest;
	@Autowired
	VisaAPIClient visaAPIClient;
	@Autowired 
	RequestDao requestDao;
	@Autowired
	ResponseDao responseDao;
	@Autowired 
	ResponseHeaderDao responseHeaderDao;
	@Autowired 
	ResponseBodyDao  responseBodyDao;
	@Autowired 
	RequestBodyDao requestBodyDao;
	//visacybersource
	@Autowired
	ShippingDao shippingDao;
	 @Autowired
	 EnvioDao  envioDao;
	@Autowired 
	ConfirmDao confirmDao;
	@Autowired
	PayUFunds payUFunds;
	
	@Autowired
	QuoteDao quoteDao;
	@Autowired
	BranchDao branchDao;
	@Autowired
	ShipmentDao shipmentDao;
	@Autowired
	com.valework.yingul.service.PersonService  personService;
	@Autowired
	ProductService 	productService;
	@Autowired 
	UbicationDao ubicationDao;
	@Autowired 
	ProvinceDao provinceDao;	
	@Autowired
	DepartmentDao departmentDao;
	@Autowired 
	CityDao cityDao;
	@Autowired 
	CountryDao countryDao;
	Yng_Standard standard;
	@Autowired
	StandardService standardService;
	@Autowired 
	Logistic logistic;
	
	@Autowired
	BranchAndreaniDao branchAndreaniDao;
	@Autowired
	StandardDao standardDao;
	@Autowired
	CashPaymentDao cashPaymentDao; 
	@Autowired
	LogisticsController logisticsController;
	@Autowired
	YingulRequestDao yingulRequestDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	PersonDao personDao;
	@Autowired
	NotificationDao notificationDao;
	
	@RequestMapping("/listCreditCard/all")
    public List<Yng_ListCreditCard> findProvinceList() {
        List<Yng_ListCreditCard> creditCardList = listCreditCardDao.findAll();
        return creditCardList;
    }

    @RequestMapping("/getCardForUser/{username}")
    public List<Yng_Card> findCardForUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Card> cardList = cardService.findByUser(yng_User);
        return cardList;
    }
    
    @RequestMapping("/getSwForUser/{username}")
    public boolean getSwForUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        System.out.println(yng_User.getPhone());
        if(yng_User.getPhone()==null||(yng_User.getDocumentNumber()==null)) {
        	
        	return false;
        }
        else {
        	return true;
        }
    }
    
    @RequestMapping(value = "/createBuy", method = RequestMethod.POST)
    @ResponseBody
    public String createBuy(@Valid @RequestBody Yng_Buy buy,@RequestHeader("Authorization") String authorization) throws Exception {	
    	//capturar objeto inicio 	
    	String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User authorized= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(authorized.getUsername().equals(parts[0]) && authorized.getUsername().equals(buy.getUser().getUsername()) && encoder.matches(parts[1], authorized.getPassword())){
	    			ObjectMapper mapperRequest = new ObjectMapper();
	    			java.util.Date fecha = new Date();
	    			System.out.println (fecha);
	    			try {
	    				String jsonInString = mapperRequest.writeValueAsString(buy);
	    				System.out.println("jsonInString"+jsonInString);
	    				Yng_YingulRequest serviceJson=new Yng_YingulRequest();
	    				serviceJson.setJson(jsonInString);
	    				serviceJson.setDate(fecha);
	    				yingulRequestDao.save(serviceJson);
	    			} catch (JsonProcessingException e1) {
	    				
	    				e1.printStackTrace();
	    			}
	    	//capturar objeto fin	
	    	//backup para pago en efectivo
	    	JSONObject cashBuy=null;
	    	String jsonInString = "";
	    	ObjectMapper mapper = new ObjectMapper();
			try {
				jsonInString = mapper.writeValueAsString(buy);
				System.out.println(jsonInString);
				jsonInString = jsonInString.replace(",\"credentialsNonExpired\":true", "");
				jsonInString = jsonInString.replace(",\"accountNonExpired\":true", "");
				jsonInString = jsonInString.replace(",\"accountNonLocked\":true", "");
				System.out.println(jsonInString);
		    	cashBuy = new JSONObject(jsonInString);
			} catch (JsonProcessingException e1) {
				
				e1.printStackTrace();
			}
	    	//para setear el item
	    	Yng_Item itemTemp=itemDao.findByItemId(buy.getYng_item().getItemId());
	    	System.out.println(buy.getQuantity());
	    	System.out.println(itemTemp.getQuantity());
	    	//System.out.println(itemTemp.getQuantity());
	    	if(itemTemp.getQuantity()<=0||!itemTemp.isEnabled()||itemTemp.getQuantity()<buy.getQuantity()) {
	    		return "Sin stock";
	    	}
	    	buy.setYng_item(itemTemp);
	    	//fin setear el item
	    	//para setear el usuario y el vendedor 
	    	Yng_User userTemp= userDao.findByUsername(buy.getUser().getUsername());
	    	Yng_Country countrySw=countryDao.findByCountryId(userTemp.getYng_Ubication().getYng_Country().getCountryId());
			if(!countrySw.isToBuy()) {
				return "Tu país todavia no esta habilitado para comprar en Yingul estamos trabajando en ello";
			}
	    	buy.setUser(userTemp);
	    	Yng_User sellerTemp = userDao.findByUsername(itemTemp.getUser().getUsername());
	    	buy.setSeller(sellerTemp);
	    	//hasta aqui para el usuario
	    	//pagos en efectivo 
	    	if(buy.getYng_Payment().getType().equals("CASH")) {
	    		if(buy.getYng_Payment().getPaymentId()!=null) {
	    			Yng_Payment paymentSw= paymentDao.findByPaymentId(buy.getYng_Payment().getPaymentId());
	    			Yng_Standard cashConfirm = standardDao.findByKey("PAYU_cash_confirm");
	    			if(paymentSw.getStatus().equals(cashConfirm.getValue())) {
	    				buy.setYng_Payment(paymentDao.save(paymentSw));
	    			}else {
	    				return "internError";
	    			}
	    		}else {
	    			//Autorización de la tarjeta
			    	Yng_Payment autorized =  payUFunds.authorizeCash(buy,userTemp);
			    	//
			    	if(autorized==null) {
			    		return "problemCash";
			    	}else {	
			    		autorized.setUser(null);
			    		try {
			    			jsonInString = mapper.writeValueAsString(autorized);
			    		} catch (JsonProcessingException e1) {
			    			
			    			e1.printStackTrace();
			    		}
			    		
			    		cashBuy.put("yng_Payment", new JSONObject(jsonInString));
	
			    		Yng_CashPayment cashTemp = cashPaymentDao.findByCashPaymentId(autorized.getCashPayment().getCashPaymentId());
			    		cashTemp.setBuyJson(cashBuy.toString());
	            		cashPaymentDao.save(cashTemp);
	
			    		Yng_Item itemTempE=itemDao.findByItemId(buy.getYng_item().getItemId());
			    		if(!itemTempE.getType().equals("Service")) {
			    			itemTempE.setQuantity(itemTempE.getQuantity()-buy.getQuantity());
			    		}
			    		if(itemTempE.getQuantity()<=0) {
			    			itemTempE.setEnabled(false);
			    		}
			    		itemTempE=itemDao.save(itemTempE);
			    		
			    		return "cash:"+autorized.getPaymentId();
			    	}
	    		}
	    	}
	    	if(buy.getYng_Payment().getType().equals("CARD")) {
	    		//Autorización de la tarjeta
		    	Yng_Payment autorized =  payUFunds.authorizeCard(buy,userTemp);
		    	//
		    	if(autorized==null) {
		    		return "problemCard";
		    	}else {
		    		autorized.setCashPayment(null);
		    		buy.setYng_Payment(paymentDao.save(autorized));
		    	}
	    	}
	    	//fin del metodo de pago
	    	Date time = new Date();
	    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	buy.setTime(hourdateFormat.format(time));
			////////////////////////////////////////////////////////////
			System.out.println("shippingdaniel :"+ buy.getShipping().toString());
			String typeEnvio=buy.getShipping().getTypeShipping();
			Yng_BranchAndreani branchAndreaniC=new Yng_BranchAndreani();
	        Yng_BranchAndreani branchAndreaniV=new Yng_BranchAndreani();
			if(buy.getShipping().getTypeShipping().equals("home")) {		
				Yng_Shipping shipping=null;
				buy.setShipping(shipping);
			}
			else {
			
			///*nuevo codigo
				
			Yng_Shipping tempShipping =new Yng_Shipping();
			Yng_Shipping ship =new Yng_Shipping();
			Yng_BranchAndreani branchAndreani=new Yng_BranchAndreani();
			ship=buy.getShipping();
			String nameMail=ship.getYng_Quote().getYng_Branch().getNameMail();
			String typeMail;
			boolean andreani=false,dhl=false,fedex=false;
			switch (nameMail.toLowerCase()) {
			    case "andreani":  andreani = true;typeMail="andreani";
			             break;
			    case "dhl":  dhl = true;typeMail="dhl";
			             break;
			    case "fedex":  fedex = true;typeMail="fedex";
			             break;
			    default: typeMail = "Invalid Mail";
			             break;
			             }
			Yng_Shipment yng_Shipment=new Yng_Shipment();
			System.out.println(nameMail.toLowerCase());
			if(typeMail.equals("andreani")) {
			tempShipping.setAndreani(andreani);
			tempShipping.setDhl(dhl);
			tempShipping.setFedex(fedex);
			tempShipping.setShippingStatus("imprecionTicket");
			Yng_Branch branchTemp=branchDao.save(buy.getShipping().getYng_Quote().getYng_Branch());
			Yng_Quote quote=new Yng_Quote();
			quote=buy.getShipping().getYng_Quote();
			quote.setYng_Item(buy.getYng_item());
			quote.setYng_User(buy.getUser());
			quote.setYng_Branch(branchTemp);
			quote=quoteDao.save(buy.getShipping().getYng_Quote());
			tempShipping.setNameContact(buy.getShipping().getNameContact());
			tempShipping.setPhoneContact(buy.getShipping().getPhoneContact());
			tempShipping.setLastName(buy.getShipping().getLastName());
			tempShipping.setYng_Quote(quote);
			//branchAndreaniDao.findByCodAndreani();
			
				
				//Logistic logistic=new Logistic();
				String link="";
				String pdf="";
				String numberAndreani="";
				try {
					 Yng_Product getProductByIdItem=new Yng_Product();
					 List<Yng_Product> productList= productDao.findAll();
				  		for (Yng_Product yng_Product : productList) {
							if(yng_Product.getYng_Item().getItemId()==quote.getYng_Item().getItemId()) {
								getProductByIdItem = yng_Product;
							}
					}
					SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
			        SAXParser sAXParser=saxParseFactory.newSAXParser();
			        Yng_Person per=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
			        List<Yng_Person> personListItem=personDao.findAll();
			        for (Yng_Person yng_Person : personListItem) {
						if(yng_Person.getYng_User().getUsername().equals(buy.getUser().getUsername())) {
							per = yng_Person;
						}
					}
			        Yng_Person perItem=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
			        for (Yng_Person yng_Person : personListItem) {
						if(yng_Person.getYng_User().getUsername().equals(buy.getSeller().getUsername())) {
							perItem = yng_Person;
						}
					}
			        //per.getYng_User().getYng_Ubication().setPostalCode(buy.getShipping().getYng_Shipment().getYng_User().getYng_Ubication().getPostalCode());
			        //per.setName(buy.getShipping().getNameContact());
			       // per.setLastname(buy.getShipping().getLastName());
			        String numV="";
			        String numC="";
	    			try {
	    				branchAndreaniV=logisticsController.andreaniSucursalesObject(buy.getShipping().getYng_Quote().getYng_Item().getYng_Ubication().getPostalCode(), "", "");
	    				numV=branchAndreaniV.getCodAndreani();
	    				System.out.println("numV:-----"+numV+" pos:"+perItem.getYng_User().getYng_Ubication().getPostalCode());
	    				branchAndreaniC=logisticsController.andreaniSucursalesObject(buy.getShipping().getYng_Shipment().getYng_User().getYng_Ubication().getPostalCode(), "", "");
	    				numC=branchAndreaniC.getCodAndreani();
	    				System.out.println("numC:-----"+numC+" pos:"+buy.getShipping().getYng_Shipment().getYng_User().getYng_Ubication().getPostalCode());
	    			} catch (Exception e1) {
	    				e1.printStackTrace();
	    			}
					String xml=logistic.andreaniRemitenteWSDL(this.logistic.andreaniStringRe(per,tempShipping,perItem,getProductByIdItem,branchAndreaniC,branchAndreaniV,buy));
					com.valework.yingul.logistic.EnvioHandler handlerS=new com.valework.yingul.logistic.EnvioHandler();
			        
			        sAXParser.parse(new InputSource(new StringReader(xml)), handlerS);
			        ArrayList<com.valework.yingul.logistic.EnvioResponce> envios=handlerS.getEnvioResponse();
			        System.out.println("aniem");
			        for (com.valework.yingul.logistic.EnvioResponce versione : envios) {
			        	numberAndreani=versione.getNumeroAndreani();
			            System.out.println("versione.getNumero1:"+numberAndreani);
			        	}
			        System.out.println("logistic.andreaniPdfLink:"+numberAndreani);
					System.out.println("res:"+xml);
			        yng_Shipment.setRespuesta(xml);
			        int i = 0;
					System.out.println("numberAndreani  daniel :"+numberAndreani);
					System.out.println(":"+numberAndreani+":");
			        
			        
					link=logistic.andreaniPdfLink(numberAndreani +"");
			        while (link.equals(logistic.errorPDF())) {          //Condición trivial: siempre cierta
			            i++;
			            link=logistic.andreaniPdfLink(numberAndreani +"");
			            System.out.println ("Valor de i: " + i);
			            if (i==11) { break;}
			        } 
					System.out.println("linkda: "+link);
					if (link != null) {
			            //strResponse = link;
			            com.valework.yingul.logistic.ImprimirConstanciaHandler handlerI=new com.valework.yingul.logistic.ImprimirConstanciaHandler();
			            sAXParser.parse(new InputSource(new StringReader(link)), handlerI);
			            ArrayList<com.valework.yingul.logistic.ImprimirConstanciaResponse> impr=handlerI.getImprimirResponce();
			            for (com.valework.yingul.logistic.ImprimirConstanciaResponse versione : impr) {
			            	pdf=versione.getPdfLinkFile();
			                System.out.println("versione.getNumero2:"+versione.getPdfLinkFile());            
			            }
			        }
			        System.out.println("link pdf : "+pdf);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				yng_Shipment.setShipmentCod(numberAndreani);
				yng_Shipment.setTicket(pdf);
				yng_Shipment.setTypeMail(typeMail);
				yng_Shipment.setYng_Item(buy.getYng_item());
				yng_Shipment.setYng_User(buy.getUser());
				Yng_Shipment shipmentTemp=new Yng_Shipment();
				shipmentTemp=yng_Shipment;
				System.out.println("shipmentTemp"+shipmentTemp.toString());
				yng_Shipment=shipmentDao.save(shipmentTemp);
			}
				//-----fin del nuevo codigo
				tempShipping.setYng_Shipment(yng_Shipment);
			
			tempShipping.setTypeShipping(typeEnvio);
			tempShipping.setNameContact(buy.getShipping().getNameContact());
			tempShipping.setPhoneContact(buy.getShipping().getPhoneContact());
			System.out.println("tempShipping:"+tempShipping.toString());
			tempShipping=shippingDao.save(tempShipping);
			//shi
			buy.setShipping(tempShipping);
			
			buy.setShipping(shippingDao.save(buy.getShipping()));
			}
			
			
			////////////////////////////////////////
			//verificar que el stock funcione
			Yng_Item itemTemp1=itemDao.findByItemId(buy.getYng_item().getItemId());
			if(!itemTemp1.getType().equals("Service")) {
				itemTemp1.setQuantity(itemTemp1.getQuantity()-buy.getQuantity());
			}
			if(itemTemp1.getQuantity()<=0) {
				itemTemp1.setEnabled(false);
			}
			itemTemp1=itemDao.save(itemTemp1);
			buy.setYng_item(itemTemp1);
			//
	    	buy=buyDao.save(buy);    	
	    	Yng_Confirm confirm=new Yng_Confirm();
	    	confirm.setBuy(buy);
	    	confirm.setBuyerConfirm(false);
	    	confirm.setSellerConfirm(false);
	    	confirm.setCodeConfirm(1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
	    	confirm.setStatus("pending");
	    	confirm.setBuyer(buy.getUser());
	    	confirm.setSeller(buy.getSeller());
	    	confirm=confirmDao.save(confirm);
	    	//******************crear las notificaciones para comprador y vendedor*********************************
	    	Yng_Notification sellerNotification = new Yng_Notification();
	    	sellerNotification.setDate(buy.getTime());
	    	sellerNotification.setDescription("¡Vendiste!");
	    	sellerNotification.setItem(buy.getYng_item());
	    	sellerNotification.setStatus("pending");
	    	sellerNotification.setDesktopStatus("pending");
	    	sellerNotification.setTitle("Acabas de vender el producto "+buy.getYng_item().getName());
	    	sellerNotification.setUrl("https://www.yingul.com/userFront/sales");
	    	sellerNotification.setUser(buy.getSeller());
	    	notificationDao.save(sellerNotification);
	    	Yng_Notification buyerNotification = new Yng_Notification();
	    	buyerNotification.setDate(buy.getTime());
	    	buyerNotification.setDescription("¡Compraste!");
	    	buyerNotification.setItem(buy.getYng_item());
	    	buyerNotification.setStatus("pending");
	    	buyerNotification.setDesktopStatus("pending");
	    	buyerNotification.setTitle("Acabas de comprar el producto "+buy.getYng_item().getName());
	    	buyerNotification.setUrl("https://www.yingul.com/userFront/purchases");
	    	buyerNotification.setUser(buy.getUser());
	    	notificationDao.save(buyerNotification);
	    	//*****************************************************************************************************
	    	//modificar los correos para pagos no con tarjeta
			
			if(typeEnvio.equals("home")) {
				smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","<b>DETALLE DE LA VENTA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Este importe esta sujeto al cobro de comisiones Yingul por venta."
						+ "<br/> Los datos del comprador son: "+"Email :"+userTemp.getEmail()+"  Teléfono : "+userTemp.getPhone()+"  Dirección: "+userTemp.getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ userTemp.getYng_Ubication().getYng_City().getName()+" Calle: "+userTemp.getYng_Ubication().getStreet()+"  Numero: "+userTemp.getYng_Ubication().getNumber()
						+ "<br/> Encuentrate con tu comprador para firmar la entrega del producto."
						+ "<br/> - Al Momento de entregar el producto al comprador ingresa <a href=\"https://www.yingul.com/confirmwos/"+confirm.getConfirmId()+"\" target=\"_blank\">aquí</a> donde tu y tu comprador firmaran la entrega del producto en buenas condiciones "
						+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina "
						+ "<br/> - No entregues el producto sin que tu y el vendedor firmen la entrega no aceptaremos reclamos si la confirmacion no esta firmada por ambas partes"
						+ "<br/> - Por tu seguridad no entregues el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
						+ "<br/> - Despues de entregar el producto tu comprador tiene 10 dias para observar sus condiciones posterior a eso te daremos mas instrucciones para recoger tu dinero"+
						"<p>Cordialemente:</p>" + 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				if(buy.getYng_Payment().getType().equals("CASH")) {
					smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Pago realizado en EFECTIVO a través de: "+buy.getYng_Payment().getCashPayment().getPaymentMethod()+" Cumpla las siguientes instrucciones:."
							+ "<br/> Los datos del vendedor son: "+"Email :"+buy.getYng_item().getUser().getEmail()+"  Teléfono : "+buy.getYng_item().getUser().getPhone()+"  Dirección:"+buy.getYng_item().getUser().getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ buy.getYng_item().getUser().getYng_Ubication().getYng_City().getName()+" Calle:"+buy.getYng_item().getUser().getYng_Ubication().getStreet()+"  Numero:"+buy.getYng_item().getYng_Ubication().getNumber()
							+ "<br/> - Al Momento de recibir el producto dale este codigo a tu vendedor: "+confirm.getCodeConfirm()+" si el producto esta en buenas condiciones."
							+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina."
							+ "<br/> - No recibas el producto ni des el código si no estas conforme con el producto no aceptaremos reclamos posteriores."
							+ "<br/> - Por tu seguridad no recibas el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día."
							+ "<br/> - Despues de recibir el producto tienes 10 dias para observar sus condiciones posterior a ese lapzo no se aceptan reclamos ni devolucion de tu dinero."+
							"<p>Cordialemente:</p>"+
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				}
				if(buy.getYng_Payment().getType().equals("CARD")) {
					smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Pago realizado con TARJETA: "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+" Cumpla las siguientes instrucciones:."
							+ "<br/> Los datos del vendedor son: "+"Email :"+buy.getYng_item().getUser().getEmail()+"  Teléfono : "+buy.getYng_item().getUser().getPhone()+"  Dirección:"+buy.getYng_item().getUser().getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ buy.getYng_item().getUser().getYng_Ubication().getYng_City().getName()+" Calle:"+buy.getYng_item().getUser().getYng_Ubication().getStreet()+"  Numero:"+buy.getYng_item().getYng_Ubication().getNumber()
							+ "<br/> - Al Momento de recibir el producto dile este codigo a tu vendedor: "+confirm.getCodeConfirm()+" si el producto esta en buenas condiciones "
							+ "<br/> - Espera el mensaje de confirmación exitosa de nuestra página."
							+ "<br/> - No recibas el producto ni des el código si no estas conforme con el producto no aceptaremos reclamos posteriores."
							+ "<br/> - Por tu seguridad no recibas el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
							+ "<br/> - Despues de recibir el producto tienes 10 dias para observar sus condiciones posterior a ese lapzo no se aceptan reclamos ni devolucion de tu dinero"+
							"<p>Cordialemente:</p>" + 
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				}	
			}
			else {
				if(buy.getYng_item().getProductPagoEnvio().equals("gratis")) {
					smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","<b>DETALLE DE LA VENTA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Este importe esta sujeto al cobro de comisiones Yingul por venta."
							+ "<br/> Costo del envio : " +buy.getShipping().getYng_Quote().getRate()+" ARS. El costo de envio se descontara posteriormente de tu saldo en YingulPay."  
							+ "<br/>--Imprimir la etiqueta de Andreani. (<a href=\"https://www.yingul.com/userFront/sales\" target=\"_blank\">Descargar etiqueta de envío</a>)"
							+ "<br/>--Preparar y embalar el paquete junto a la etiqueta." 
							+ "<br/>--Déjalo en la sucursal Andreani: "+branchAndreaniV.getLocation()+" "+branchAndreaniV.getSchedules()+" Fono: "+branchAndreaniV.getPhones()
							+ "<br/>Nos pondremos en contacto con usted cuando tu comprador recoja el producto de Andreani."+
							"<p>Cordialemente:</p>" + 
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
					if(buy.getYng_Payment().getType().equals("CASH")) {
						smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>1</td>\r\n" 
							+ "    <td>Envio</td>\r\n" 
							+ "    <td>-</td>\r\n" 
							+ "    <td>GRATIS</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Pago realizado en EFECTIVO a través de: "+buy.getYng_Payment().getCashPayment().getPaymentMethod()+"."	
							+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani:"
							+ "<br/>Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
							"<p>Cordialemente:</p>" + 
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
					}
					if(buy.getYng_Payment().getType().equals("CARD")) {
						smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>1</td>\r\n" 
							+ "    <td>Envio</td>\r\n" 
							+ "    <td>-</td>\r\n" 
							+ "    <td>GRATIS</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Pago realizado con TARJETA: "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+"."	
							+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani."
							+ "<br/> Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
							"<p>Cordialemente:</p>" + 
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
					}
				}else {
					smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","<b>DETALLE DE LA VENTA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getItemCost()+" "+buy.getYng_item().getMoney()+"</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Este importe esta sujeto al cobro de comisiones Yingul por venta."
							+ "<br/>--Imprimir la etiqueta de Andreani. (<a href=\"https://www.yingul.com/userFront/sales\" target=\"_blank\">Descargar etiqueta de envío</a>)"
							+ "<br/>--Preparar y embalar el paquete junto a la etiqueta." 
							+ "<br/>--Déjalo en la sucursal Andreani: "+branchAndreaniV.getLocation()+" "+branchAndreaniV.getSchedules()+" Fono: "+branchAndreaniV.getPhones()
							+ "<br/>Nos pondremos en contacto con usted cuando tu comprador recoja el producto de Andreani."+
							"<p>Cordialemente:</p>" + 
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
					if(buy.getYng_Payment().getType().equals("CASH")) {
						smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>1</td>\r\n" 
							+ "    <td>Envio</td>\r\n" 
							+ "    <td>-</td>\r\n" 
							+ "    <td>"+buy.getShipping().getYng_Quote().getRate()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Pago realizado en EFECTIVO a través de: "+buy.getYng_Payment().getCashPayment().getPaymentMethod()+"."	
							+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani."
							+ "<br/> Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
							"<p>Cordialemente:</p>" + 
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
					}
					if(buy.getYng_Payment().getType().equals("CARD")) {
						smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
							+ "<table border=\"1\">\r\n"  
							+ "  <tr>\r\n"
							+ "    <th width=\"10%\">CANT.</th>\r\n" 
							+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
							+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
							+ "    <th width=\"20%\">IMPORTE</th>\r\n"
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
							+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
							+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n"
							+ "    <td>1</td>\r\n" 
							+ "    <td>Envio</td>\r\n" 
							+ "    <td>-</td>\r\n" 
							+ "    <td>"+buy.getShipping().getYng_Quote().getRate()+" ARS.</td>\r\n" 
							+ "  </tr>\r\n"
							+ "  <tr>\r\n" 
							+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
							+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
							+ "  </tr>\r\n"
							+ "</table>"
							+ "<br/> Pago realizado con TARJETA: "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+"."	
							+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani."
							+ "<br/> Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
							"<p>Cordialemente:</p>" + 
		        			"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        			"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
					}
				}
			}
	    	return "save";
    	}else {
    		return "prohibited";
    	}
    }
    //@RequestMapping(value = "/createBuyCash", method = RequestMethod.POST)
    @ResponseBody
    public String createBuyCash(@Valid @RequestBody Yng_Buy buy) throws Exception {	
    			ObjectMapper mapperRequest = new ObjectMapper();
    			java.util.Date fecha = new Date();
    			System.out.println (fecha);
    			try {
    				String jsonInString = mapperRequest.writeValueAsString(buy);
    				System.out.println("jsonInString"+jsonInString);
    				Yng_YingulRequest serviceJson=new Yng_YingulRequest();
    				serviceJson.setJson(jsonInString);
    				serviceJson.setDate(fecha);
    				yingulRequestDao.save(serviceJson);
    			} catch (JsonProcessingException e1) {
    				
    				e1.printStackTrace();
    			}
    	//capturar objeto fin	
    	//backup para pago en efectivo
    	JSONObject cashBuy=null;
    	String jsonInString = "";
    	ObjectMapper mapper = new ObjectMapper();
		try {
			jsonInString = mapper.writeValueAsString(buy);
			System.out.println(jsonInString);
			jsonInString = jsonInString.replace(",\"credentialsNonExpired\":true", "");
			jsonInString = jsonInString.replace(",\"accountNonExpired\":true", "");
			jsonInString = jsonInString.replace(",\"accountNonLocked\":true", "");
			System.out.println(jsonInString);
	    	cashBuy = new JSONObject(jsonInString);
		} catch (JsonProcessingException e1) {
			
			e1.printStackTrace();
		}
    	//para setear el item
    	Yng_Item itemTemp=itemDao.findByItemId(buy.getYng_item().getItemId());
    	System.out.println(buy.getQuantity());
    	System.out.println(itemTemp.getQuantity());
    	//System.out.println(itemTemp.getQuantity());
    	/*if(itemTemp.getQuantity()<=0||!itemTemp.isEnabled()||itemTemp.getQuantity()<buy.getQuantity()) {
    		return "Sin stock";
    	}*/
    	buy.setYng_item(itemTemp);
    	//fin setear el item
    	//para setear el usuario y el vendedor 
    	Yng_User userTemp= userDao.findByUsername(buy.getUser().getUsername());
    	Yng_Country countrySw=countryDao.findByCountryId(userTemp.getYng_Ubication().getYng_Country().getCountryId());
		if(!countrySw.isToBuy()) {
			return "Tu país todavia no esta habilitado para comprar en Yingul estamos trabajando en ello";
		}
    	buy.setUser(userTemp);
    	Yng_User sellerTemp = userDao.findByUsername(itemTemp.getUser().getUsername());
    	buy.setSeller(sellerTemp);
    	//hasta aqui para el usuario
    	//pagos en efectivo 
    	if(buy.getYng_Payment().getType().equals("CASH")) {
    		if(buy.getYng_Payment().getPaymentId()!=null) {
    			Yng_Payment paymentSw= paymentDao.findByPaymentId(buy.getYng_Payment().getPaymentId());
    			Yng_Standard cashConfirm = standardDao.findByKey("PAYU_cash_confirm");
    			if(paymentSw.getStatus().equals(cashConfirm.getValue())) {
    				buy.setYng_Payment(paymentDao.save(paymentSw));
    			}else {
    				return "internError";
    			}
    		}else {
    			//Autorización de la tarjeta
		    	Yng_Payment autorized =  payUFunds.authorizeCash(buy,userTemp);
		    	//
		    	if(autorized==null) {
		    		return "problemCash";
		    	}else {	
		    		autorized.setUser(null);
		    		try {
		    			jsonInString = mapper.writeValueAsString(autorized);
		    		} catch (JsonProcessingException e1) {
		    			
		    			e1.printStackTrace();
		    		}
		    		
		    		cashBuy.put("yng_Payment", new JSONObject(jsonInString));

		    		Yng_CashPayment cashTemp = cashPaymentDao.findByCashPaymentId(autorized.getCashPayment().getCashPaymentId());
		    		cashTemp.setBuyJson(cashBuy.toString());
            		cashPaymentDao.save(cashTemp);

		    		Yng_Item itemTempE=itemDao.findByItemId(buy.getYng_item().getItemId());
		    		if(!itemTempE.getType().equals("Service")) {
		    			itemTempE.setQuantity(itemTempE.getQuantity()-buy.getQuantity());
		    		}
		    		if(itemTempE.getQuantity()<=0) {
		    			itemTempE.setEnabled(false);
		    		}
		    		itemTempE=itemDao.save(itemTempE);
		    		
		    		return "cash:"+autorized.getPaymentId();
		    	}
    		}
    	}
    	if(buy.getYng_Payment().getType().equals("CARD")) {
    		//Autorización de la tarjeta
	    	Yng_Payment autorized =  payUFunds.authorizeCard(buy,userTemp);
	    	//
	    	if(autorized==null) {
	    		return "problemCard";
	    	}else {
	    		autorized.setCashPayment(null);
	    		buy.setYng_Payment(paymentDao.save(autorized));
	    	}
    	}
    	//fin del metodo de pago
    	Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	buy.setTime(hourdateFormat.format(time));
		////////////////////////////////////////////////////////////
		System.out.println("shippingdaniel :"+ buy.getShipping().toString());
		String typeEnvio=buy.getShipping().getTypeShipping();
		Yng_BranchAndreani branchAndreaniC=new Yng_BranchAndreani();
        Yng_BranchAndreani branchAndreaniV=new Yng_BranchAndreani();
		if(buy.getShipping().getTypeShipping().equals("home")) {		
			Yng_Shipping shipping=null;
			buy.setShipping(shipping);
		}
		else {
		
		///*nuevo codigo
			
		Yng_Shipping tempShipping =new Yng_Shipping();
		Yng_Shipping ship =new Yng_Shipping();
		Yng_BranchAndreani branchAndreani=new Yng_BranchAndreani();
		ship=buy.getShipping();
		String nameMail=ship.getYng_Quote().getYng_Branch().getNameMail();
		String typeMail;
		boolean andreani=false,dhl=false,fedex=false;
		switch (nameMail.toLowerCase()) {
		    case "andreani":  andreani = true;typeMail="andreani";
		             break;
		    case "dhl":  dhl = true;typeMail="dhl";
		             break;
		    case "fedex":  fedex = true;typeMail="fedex";
		             break;
		    default: typeMail = "Invalid Mail";
		             break;
		             }
		Yng_Shipment yng_Shipment=new Yng_Shipment();
		System.out.println(nameMail.toLowerCase());
		if(typeMail.equals("andreani")) {
		tempShipping.setAndreani(andreani);
		tempShipping.setDhl(dhl);
		tempShipping.setFedex(fedex);
		tempShipping.setShippingStatus("imprecionTicket");
		Yng_Branch branchTemp=branchDao.save(buy.getShipping().getYng_Quote().getYng_Branch());
		Yng_Quote quote=new Yng_Quote();
		quote=buy.getShipping().getYng_Quote();
		quote.setYng_Item(buy.getYng_item());
		quote.setYng_User(buy.getUser());
		quote.setYng_Branch(branchTemp);
		quote=quoteDao.save(buy.getShipping().getYng_Quote());
		tempShipping.setNameContact(buy.getShipping().getNameContact());
		tempShipping.setPhoneContact(buy.getShipping().getPhoneContact());
		tempShipping.setLastName(buy.getShipping().getLastName());
		tempShipping.setYng_Quote(quote);
		//branchAndreaniDao.findByCodAndreani();
		
			
			//Logistic logistic=new Logistic();
			String link="";
			String pdf="";
			String numberAndreani="";
			try {
				 Yng_Product getProductByIdItem=new Yng_Product();
				 List<Yng_Product> productList= productDao.findAll();
			  		for (Yng_Product yng_Product : productList) {
						if(yng_Product.getYng_Item().getItemId()==quote.getYng_Item().getItemId()) {
							getProductByIdItem = yng_Product;
						}
				}
				SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
		        SAXParser sAXParser=saxParseFactory.newSAXParser();
		        Yng_Person per=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
		        List<Yng_Person> personListItem=personDao.findAll();
		        for (Yng_Person yng_Person : personListItem) {
					if(yng_Person.getYng_User().getUsername().equals(buy.getUser().getUsername())) {
						per = yng_Person;
					}
				}
		        Yng_Person perItem=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
		        for (Yng_Person yng_Person : personListItem) {
					if(yng_Person.getYng_User().getUsername().equals(buy.getSeller().getUsername())) {
						perItem = yng_Person;
					}
				}
		        //per.getYng_User().getYng_Ubication().setPostalCode(buy.getShipping().getYng_Shipment().getYng_User().getYng_Ubication().getPostalCode());
		        //per.setName(buy.getShipping().getNameContact());
		       // per.setLastname(buy.getShipping().getLastName());
		        String numV="";
		        String numC="";
    			try {
    				branchAndreaniV=logisticsController.andreaniSucursalesObject(buy.getShipping().getYng_Quote().getYng_Item().getYng_Ubication().getPostalCode(), "", "");
    				numV=branchAndreaniV.getCodAndreani();
    				System.out.println("numV:-----"+numV+" pos:"+perItem.getYng_User().getYng_Ubication().getPostalCode());
    				branchAndreaniC=logisticsController.andreaniSucursalesObject(buy.getShipping().getYng_Shipment().getYng_User().getYng_Ubication().getPostalCode(), "", "");
    				numC=branchAndreaniC.getCodAndreani();
    				System.out.println("numC:-----"+numC+" pos:"+buy.getShipping().getYng_Shipment().getYng_User().getYng_Ubication().getPostalCode());
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
				String xml=logistic.andreaniRemitenteWSDL(this.logistic.andreaniStringRe(per,tempShipping,perItem,getProductByIdItem,branchAndreaniC,branchAndreaniV,buy));
				com.valework.yingul.logistic.EnvioHandler handlerS=new com.valework.yingul.logistic.EnvioHandler();
		        
		        sAXParser.parse(new InputSource(new StringReader(xml)), handlerS);
		        ArrayList<com.valework.yingul.logistic.EnvioResponce> envios=handlerS.getEnvioResponse();
		        System.out.println("aniem");
		        for (com.valework.yingul.logistic.EnvioResponce versione : envios) {
		        	numberAndreani=versione.getNumeroAndreani();
		            System.out.println("versione.getNumero1:"+numberAndreani);
		        	}
		        System.out.println("logistic.andreaniPdfLink:"+numberAndreani);
				System.out.println("res:"+xml);
		        yng_Shipment.setRespuesta(xml);
		        int i = 0;
				System.out.println("numberAndreani  daniel :"+numberAndreani);
				System.out.println(":"+numberAndreani+":");
		        
		        
				link=logistic.andreaniPdfLink(numberAndreani +"");
		        while (link.equals(logistic.errorPDF())) {          //Condición trivial: siempre cierta
		            i++;
		            link=logistic.andreaniPdfLink(numberAndreani +"");
		            System.out.println ("Valor de i: " + i);
		            if (i==11) { break;}
		        } 
				System.out.println("linkda: "+link);
				if (link != null) {
		            //strResponse = link;
		            com.valework.yingul.logistic.ImprimirConstanciaHandler handlerI=new com.valework.yingul.logistic.ImprimirConstanciaHandler();
		            sAXParser.parse(new InputSource(new StringReader(link)), handlerI);
		            ArrayList<com.valework.yingul.logistic.ImprimirConstanciaResponse> impr=handlerI.getImprimirResponce();
		            for (com.valework.yingul.logistic.ImprimirConstanciaResponse versione : impr) {
		            	pdf=versione.getPdfLinkFile();
		                System.out.println("versione.getNumero2:"+versione.getPdfLinkFile());            
		            }
		        }
		        System.out.println("link pdf : "+pdf);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			yng_Shipment.setShipmentCod(numberAndreani);
			yng_Shipment.setTicket(pdf);
			yng_Shipment.setTypeMail(typeMail);
			yng_Shipment.setYng_Item(buy.getYng_item());
			yng_Shipment.setYng_User(buy.getUser());
			Yng_Shipment shipmentTemp=new Yng_Shipment();
			shipmentTemp=yng_Shipment;
			System.out.println("shipmentTemp"+shipmentTemp.toString());
			yng_Shipment=shipmentDao.save(shipmentTemp);
		}
			//-----fin del nuevo codigo
			tempShipping.setYng_Shipment(yng_Shipment);
		
		tempShipping.setTypeShipping(typeEnvio);
		tempShipping.setNameContact(buy.getShipping().getNameContact());
		tempShipping.setPhoneContact(buy.getShipping().getPhoneContact());
		System.out.println("tempShipping:"+tempShipping.toString());
		tempShipping=shippingDao.save(tempShipping);
		//shi
		buy.setShipping(tempShipping);
		
		buy.setShipping(shippingDao.save(buy.getShipping()));
		}
		
		
		////////////////////////////////////////
		//verificar que el stock funcione
		Yng_Item itemTemp1=itemDao.findByItemId(buy.getYng_item().getItemId());
		if(!itemTemp1.getType().equals("Service")) {
			itemTemp1.setQuantity(itemTemp1.getQuantity()-buy.getQuantity());
		}
		if(itemTemp1.getQuantity()<=0) {
			itemTemp1.setEnabled(false);
		}
		itemTemp1=itemDao.save(itemTemp1);
		buy.setYng_item(itemTemp1);
		//
    	buy=buyDao.save(buy);    	
    	Yng_Confirm confirm=new Yng_Confirm();
    	confirm.setBuy(buy);
    	confirm.setBuyerConfirm(false);
    	confirm.setSellerConfirm(false);
    	confirm.setCodeConfirm(1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
    	confirm.setStatus("pending");
    	confirm.setBuyer(buy.getUser());
    	confirm.setSeller(buy.getSeller());
    	confirm=confirmDao.save(confirm);
    	//modificar los correos para pagos no con tarjeta
    	//******************crear las notificaciones para comprador y vendedor*********************************
    	Yng_Notification sellerNotification = new Yng_Notification();
    	sellerNotification.setDate(buy.getTime());
    	sellerNotification.setDescription("¡Vendiste!");
    	sellerNotification.setItem(buy.getYng_item());
    	sellerNotification.setStatus("pending");
    	sellerNotification.setDesktopStatus("pending");
    	sellerNotification.setTitle("Acabas de vender "+buy.getYng_item().getName());
    	sellerNotification.setUrl("https://www.yingul.com/userFront/sales");
    	sellerNotification.setUser(buy.getSeller());
    	notificationDao.save(sellerNotification);
    	Yng_Notification buyerNotification = new Yng_Notification();
    	buyerNotification.setDate(buy.getTime());
    	buyerNotification.setDescription("¡Compraste!");
    	buyerNotification.setItem(buy.getYng_item());
    	buyerNotification.setStatus("pending");
    	buyerNotification.setDesktopStatus("pending");
    	buyerNotification.setTitle("Acabas de comprar "+buy.getYng_item().getName());
    	buyerNotification.setUrl("https://www.yingul.com/userFront/purchases");
    	buyerNotification.setUser(buy.getUser());
    	notificationDao.save(buyerNotification);
    	//*****************************************************************************************************
		if(typeEnvio.equals("home")) {
			smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","<b>DETALLE DE LA VENTA:</b>"
					+ "<table border=\"1\">\r\n"  
					+ "  <tr>\r\n"
					+ "    <th width=\"10%\">CANT.</th>\r\n" 
					+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
					+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
					+ "    <th width=\"20%\">IMPORTE</th>\r\n"
					+ "  </tr>\r\n"
					+ "  <tr>\r\n"
					+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
					+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
					+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
					+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
					+ "  </tr>\r\n"
					+ "  <tr>\r\n" 
					+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
					+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
					+ "  </tr>\r\n"
					+ "</table>"
					+ "<br/> Este importe esta sujeto al cobro de comisiones Yingul por venta."
					+ "<br/> Los datos del comprador son: "+"Email :"+userTemp.getEmail()+"  Teléfono : "+userTemp.getPhone()+"  Dirección: "+userTemp.getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ userTemp.getYng_Ubication().getYng_City().getName()+" Calle: "+userTemp.getYng_Ubication().getStreet()+"  Numero: "+userTemp.getYng_Ubication().getNumber()
					+ "<br/> Encuantrate con tu comprador para firmar la entrega del producto."
					+ "<br/> - Al Momento de entregar el producto al comprador ingresa <a href=\"https://www.yingul.com/confirmwos/"+confirm.getConfirmId()+"\" target=\"_blank\">aquí</a>, donde tu y tu comprador firmaran la entrega del producto en buenas condiciones "
					+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina "
					+ "<br/> - No entregues el producto sin que tu y el vendedor firmen la entrega no aceptaremos reclamos si la confirmacion no esta firmada por ambas partes"
					+ "<br/> - Por tu seguridad no entregues el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
					+ "<br/> - Despues de entregar el producto tu comprador tiene 10 dias para observar sus condiciones posterior a eso te daremos mas instrucciones para recoger tu dinero"+
					"<p>Cordialemente:</p>" + 
		        	"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        	"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
			if(buy.getYng_Payment().getType().equals("CASH")) {
				smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Pago realizado en EFECTIVO a través de: "+buy.getYng_Payment().getCashPayment().getPaymentMethod()+" Cumpla las siguientes instrucciones:."
						+ "<br/> Los datos del vendedor son: "+"Email :"+buy.getYng_item().getUser().getEmail()+"  Teléfono : "+buy.getYng_item().getUser().getPhone()+"  Dirección:"+buy.getYng_item().getUser().getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ buy.getYng_item().getUser().getYng_Ubication().getYng_City().getName()+" Calle:"+buy.getYng_item().getUser().getYng_Ubication().getStreet()+"  Numero:"+buy.getYng_item().getYng_Ubication().getNumber()
						+ "<br/> - Al Momento de recibir el producto dale este codigo a tu vendedor: "+confirm.getCodeConfirm()+" si el producto esta en buenas condiciones."
						+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina."
						+ "<br/> - No recibas el producto ni des el código si no estas conforme con el producto no aceptaremos reclamos posteriores."
						+ "<br/> - Por tu seguridad no recibas el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día."
						+ "<br/> - Despues de recibir el producto tienes 10 dias para observar sus condiciones posterior a ese lapzo no se aceptan reclamos ni devolucion de tu dinero."+
						"<p>Cordialemente:</p>" + 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
			}
			if(buy.getYng_Payment().getType().equals("CARD")) {
				smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Pago realizado con TARJETA: "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+" Cumpla las siguientes instrucciones:."
						+ "<br/> Los datos del vendedor son: "+"Email :"+buy.getYng_item().getUser().getEmail()+"  Teléfono : "+buy.getYng_item().getUser().getPhone()+"  Dirección:"+buy.getYng_item().getUser().getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ buy.getYng_item().getUser().getYng_Ubication().getYng_City().getName()+" Calle:"+buy.getYng_item().getUser().getYng_Ubication().getStreet()+"  Numero:"+buy.getYng_item().getYng_Ubication().getNumber()
						+ "<br/> - Al Momento de recibir el producto dile este codigo a tu vendedor: "+confirm.getCodeConfirm()+" si el producto esta en buenas condiciones "
						+ "<br/> - Espera el mensaje de confirmación exitosa de nuestra página."
						+ "<br/> - No recibas el producto ni des el código si no estas conforme con el producto no aceptaremos reclamos posteriores."
						+ "<br/> - Por tu seguridad no recibas el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
						+ "<br/> - Despues de recibir el producto tienes 10 dias para observar sus condiciones posterior a ese lapzo no se aceptan reclamos ni devolucion de tu dinero"+
						"<p>Cordialemente:</p>" + 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
			}	
		}
		else {
			if(buy.getYng_item().getProductPagoEnvio().equals("gratis")) {
				smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","<b>DETALLE DE LA VENTA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Este importe esta sujeto al cobro de comisiones Yingul por venta."
						+ "<br/> Costo del envio : " +buy.getShipping().getYng_Quote().getRate()+" ARS. El costo de envio se descontara posteriormente de tu saldo en YingulPay."  
						+ "<br/>--Imprimir la etiqueta de Andreani. (<a href=\"https://www.yingul.com/userFront/sales\" target=\"_blank\">Descargar etiqueta de envío</a>)"
						+ "<br/>--Preparar y embalar el paquete junto a la etiqueta." 
						+ "<br/>--Déjalo en la sucursal Andreani: "+branchAndreaniV.getLocation()+" "+branchAndreaniV.getSchedules()+" Fono: "+branchAndreaniV.getPhones()
						+ "<br/>Nos pondremos en contacto con usted cuando tu comprador recoja el producto de Andreani."+
						"<p>Cordialemente:</p>" + 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				if(buy.getYng_Payment().getType().equals("CASH")) {
					smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>1</td>\r\n" 
						+ "    <td>Envio</td>\r\n" 
						+ "    <td>-</td>\r\n" 
						+ "    <td>GRATIS</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Pago realizado en EFECTIVO a través de: "+buy.getYng_Payment().getCashPayment().getPaymentMethod()+"."	
						+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani:"
						+ "<br/>Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
						"<p>Cordialemente:</p>" + 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>" + 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				}
				if(buy.getYng_Payment().getType().equals("CARD")) {
					smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>1</td>\r\n" 
						+ "    <td>Envio</td>\r\n" 
						+ "    <td>-</td>\r\n" 
						+ "    <td>GRATIS</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Pago realizado con TARJETA: "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+"."	
						+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani."
						+ "<br/> Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
						"<p>Cordialemente:</p>"+ 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				}
			}else {
				smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","<b>DETALLE DE LA VENTA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getItemCost()+" "+buy.getYng_item().getMoney()+"</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Este importe esta sujeto al cobro de comisiones Yingul por venta."
						+ "<br/>--Imprimir la etiqueta de Andreani. (<a href=\"https://www.yingul.com/userFront/sales\" target=\"_blank\">Descargar etiqueta de envío</a>)"
						+ "<br/>--Preparar y embalar el paquete junto a la etiqueta." 
						+ "<br/>--Déjalo en la sucursal Andreani: "+branchAndreaniV.getLocation()+" "+branchAndreaniV.getSchedules()+" Fono: "+branchAndreaniV.getPhones()
						+ "<br/>Nos pondremos en contacto con usted cuando tu comprador recoja el producto de Andreani."+
						"<p>Cordialemente:</p>"+ 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				if(buy.getYng_Payment().getType().equals("CASH")) {
					smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>1</td>\r\n" 
						+ "    <td>Envio</td>\r\n" 
						+ "    <td>-</td>\r\n" 
						+ "    <td>"+buy.getShipping().getYng_Quote().getRate()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Pago realizado en EFECTIVO a través de: "+buy.getYng_Payment().getCashPayment().getPaymentMethod()+"."	
						+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani."
						+ "<br/> Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
						"<p>Cordialemente:</p>"+ 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				}
				if(buy.getYng_Payment().getType().equals("CARD")) {
					smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "<b>DETALLE DE LA COMPRA:</b>"
						+ "<table border=\"1\">\r\n"  
						+ "  <tr>\r\n"
						+ "    <th width=\"10%\">CANT.</th>\r\n" 
						+ "    <th width=\"50%\">DESCRIPCIÓN</th>\r\n" 
						+ "    <th width=\"20%\">PRECIO UNITARIO</th>\r\n"
						+ "    <th width=\"20%\">IMPORTE</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+buy.getQuantity()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getName()+"</td>\r\n" 
						+ "    <td>"+buy.getYng_item().getPrice()+" "+buy.getYng_item().getMoney()+"</td>\r\n" 
						+ "    <td>"+buy.getItemCost()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>1</td>\r\n" 
						+ "    <td>Envio</td>\r\n" 
						+ "    <td>-</td>\r\n" 
						+ "    <td>"+buy.getShipping().getYng_Quote().getRate()+" ARS.</td>\r\n" 
						+ "  </tr>\r\n"
						+ "  <tr>\r\n" 
						+ "    <th colspan=\"3\">TOTAL.</th>\r\n" 
						+ "    <th>"+buy.getCost()+" ARS</th>\r\n"
						+ "  </tr>\r\n"
						+ "</table>"
						+ "<br/> Pago realizado con TARJETA: "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+"."	
						+ "<br/> Nos pondremos en contacto con usted cuando pueda recoger el producto en Andreani."
						+ "<br/> Sucursal: "+branchAndreaniC.getLocation()+" "+branchAndreaniC.getSchedules()+" Fono: "+branchAndreaniC.getPhones()+
						"<p>Cordialemente:</p>"+ 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        		"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
				}
			}
		}
    	return "save";
    }
    
    
    
    
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@Valid @RequestBody Yng_User yng_user) throws MessagingException {	
    	Yng_User userTemp= userDao.findByUsername(yng_user.getUsername());
    	userTemp.setPhone(yng_user.getPhone());
    	userTemp.setDocumentNumber(yng_user.getDocumentNumber());
    	userTemp.setDocumentType(yng_user.getDocumentType());
    	userDao.save(userTemp);
    	return "save";
    }
    
    public String printTicketData(String numberAndreni) {
  	   String imprimirEtiqueta="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:and=\"http://www.andreani.com.ar\">\r\n" + 
  		   		"   <soapenv:Header/>\r\n" + 
  		   		"   <soapenv:Body>\r\n" + 
  		   		"      <and:ImprimirConstancia>\r\n" + 
  		   		"         <!--Optional:-->\r\n" + 
  		   		"         <and:entities>\r\n" + 
  		   		"            <!--Zero or more repetitions:-->\r\n" + 
  		   		"            <and:ParamImprimirConstancia>\r\n" + 
  		   		"               <and:NumeroAndreani>"
  		   		+ numberAndreni
  		   		+ "</and:NumeroAndreani>\r\n" + 
  		   		"            </and:ParamImprimirConstancia>\r\n" + 
  		   		"         </and:entities>\r\n" + 
  		   		"      </and:ImprimirConstancia>\r\n" + 
  		   		"   </soapenv:Body>\r\n" + 
  		   		"</soapenv:Envelope>";
  		 
  	   
  	   return ""+imprimirEtiqueta;
     }

    public Yng_Product getProductByIdItem(Long itemId) {
    	System.out.println(itemId);
    	//Yng_Item yng_Item = itemDao.findByItemId(itemId);
  		List<Yng_Product> productList= productDao.findAll();
  		for (Yng_Product yng_Product : productList) {
			if(yng_Product.getYng_Item().getItemId()==itemId) {
				return yng_Product;
			}
		}
  		return null;
      }
    @RequestMapping(value = "/updateUserUbication", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserUbication(@Valid @RequestBody Yng_User yng_user) throws MessagingException {	
    	
    	//Yng_User userTemp=new Yng_User();
    	
    	Yng_User userTemp= userDao.findByUsername(yng_user.getUsername());
    	System.out.println("userTemp:"+userTemp.getUsername());
    			Yng_Ubication ubicationTemp = new Yng_Ubication();
    			ubicationTemp.setStreet(yng_user.getYng_Ubication().getStreet());
    			ubicationTemp.setNumber(yng_user.getYng_Ubication().getNumber());
    			ubicationTemp.setPostalCode(yng_user.getYng_Ubication().getPostalCode());
    			ubicationTemp.setAditional(yng_user.getYng_Ubication().getAditional());
    			ubicationTemp.setWithinStreets(yng_user.getYng_Ubication().getWithinStreets());
    			ubicationTemp.setDepartment(yng_user.getYng_Ubication().getDepartment());
    			ubicationTemp.setYng_Province(provinceDao.findByProvinceId(yng_user.getYng_Ubication().getYng_Province().getProvinceId()));
    			ubicationTemp.setYng_City(cityDao.findByCityId(yng_user.getYng_Ubication().getYng_City().getCityId()));	
    			ubicationTemp.setYng_Country(countryDao.findByCountryId(yng_user.getYng_Ubication().getYng_Country().getCountryId()));
    			//ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(productTemp.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
    			System.out.println("Ubi:"+ubicationTemp.toString());
    			String codAndreani="";
    			
    			Yng_BranchAndreani branchAndreani=new Yng_BranchAndreani();
    			try {
    				//codAndreani=log.andreaniSucursales(ubicationTemp.getPostalCode(), "", "");
    				//codAndreani=log.andreaniSucursalesObject(ubicationTemp.getPostalCode(), "", "").getCodAndreani();
    				branchAndreani=logisticsController.andreaniSucursalesObject(ubicationTemp.getPostalCode(), "", "");
    				//branchAndreani=log.andreaniSucursalesObject(ubicationTemp.getPostalCode(), "", "");
    				codAndreani=branchAndreani.getCodAndreani();
    				branchAndreaniDao.save(branchAndreani);
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
    			ubicationTemp.setCodAndreani(""+codAndreani);
    			Yng_Ubication ubicationTempo= new Yng_Ubication();
    			System.out.println("ubicationTempo:"+
    			ubicationTempo.toString()+
    			" userTemp:"+userTemp.getUsername());
    			
    			ubicationTempo=ubicationDao.save(ubicationTemp);
    			userTemp.setYng_Ubication(ubicationTempo);
    			
    	userDao.save(userTemp);
    	return "save";
    	}

    @RequestMapping("/getPurchaseByUser/{username}")
    public List<Yng_Buy> findPurchaseByUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Buy> buyList = buyDao.findByUserOrderByBuyIdDesc(yng_User);
        for (Yng_Buy s : buyList) {
        	if(s.getYng_Payment().getYng_Card()==null) {
        		
        	}else {
        		s.getYng_Payment().getYng_Card().setNumber(s.getYng_Payment().getYng_Card().getNumber()%10000);
        		s.getYng_Payment().getYng_Card().setSecurityCode(0);
        	}
        }
        return buyList;
    }
    @RequestMapping("/getSalesByUser/{username}")
    public List<Yng_Buy> findSalesByUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Buy> buyList = buyDao.findBySellerOrderByBuyIdDesc(yng_User);
        for (Yng_Buy s : buyList) {
        	if(s.getYng_Payment().getYng_Card()==null) {
        		
        	}else {
        		s.getYng_Payment().getYng_Card().setNumber(s.getYng_Payment().getYng_Card().getNumber()%10000);
        		s.getYng_Payment().getYng_Card().setSecurityCode(0);
        	}
        }
        return buyList;

    }
    @RequestMapping("/getStateBuy/{codnumber}")
    public Yng_StateShipping getCodState(@PathVariable("codnumber") String codnumber) {
    	Yng_Standard clienteStandard = standardDao.findByKey("Cliente");
    	Yng_StateShipping stateShipping=new Yng_StateShipping();
    	GetStateSend getState = new GetStateSend();
    	try {
    		stateShipping=getState.sendState(codnumber,clienteStandard.getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    	
        return stateShipping;
    }
    
    @RequestMapping("/getTrazabBuy/{codnumber}")
    public Yng_AndreaniTrazabilidad getTrazabBuy(@PathVariable("codnumber") String codnumber) {
    	Yng_Standard clienteStandard = standardDao.findByKey("Cliente");
    	Yng_AndreaniTrazabilidad traza=new Yng_AndreaniTrazabilidad();
    	GetTraceability getState = new GetTraceability();
    	try {
    		traza=getState.sendState(codnumber,clienteStandard.getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    	
        return traza;
    }
    @RequestMapping("/getTicket/{codnumber}")
    public String getTicket(@PathVariable("codnumber") String codnumber) {
    	//Yng_StateShipping stateShipping=new Yng_StateShipping();
    	//GetStateSend getState = new GetStateSend();
    	String link = null;
    	try {
    		//getStateBuy
    		//stateShipping=getState.sendState(codnumber);
    		link=logistic.andreaniPdfLink(codnumber +"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
        SAXParser sAXParser = null;
		try {
			sAXParser = saxParseFactory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // Strin
		 String pdf = null ;
    	if (link != null) {
            //strResponse = link;
            com.valework.yingul.logistic.ImprimirConstanciaHandler handlerI=new com.valework.yingul.logistic.ImprimirConstanciaHandler();
            try {
				sAXParser.parse(new InputSource(new StringReader(link)), handlerI);
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ArrayList<com.valework.yingul.logistic.ImprimirConstanciaResponse> impr=handlerI.getImprimirResponce();
           
            for (com.valework.yingul.logistic.ImprimirConstanciaResponse versione : impr) {
            	   pdf = versione.getPdfLinkFile();
                System.out.println("versione.getNumero2:"+versione.getPdfLinkFile());            
            }
        }
    	    	
        return pdf;
    }
    
    
    
    
    @RequestMapping(value = "/createBuy2", method = RequestMethod.POST)
    @ResponseBody
    public String createBuy2(@Valid @RequestBody Yng_Buy buy) throws Exception {	
    	//para setear el item
    	Yng_Item itemTemp=itemDao.findByItemId(buy.getYng_item().getItemId());
    	buy.setYng_item(itemTemp);
    	//fin setear el item
    	//para setear el usuario y el vendedor 
    	Yng_User userTemp= userDao.findByUsername(buy.getUser().getUsername());
    	Yng_Country countrySw=countryDao.findByCountryId(userTemp.getYng_Ubication().getYng_Country().getCountryId());
    	buy.setUser(userTemp);
    	Yng_User sellerTemp = userDao.findByUsername(itemTemp.getUser().getUsername());
    	buy.setSeller(sellerTemp);
    	//hasta aqui para el usuario
    	
    	//Autorización de la tarjeta

    	
		
    	//fin del metodo de pago
    	Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	buy.setTime(hourdateFormat.format(time));
////////////////////////////////////////////////////////////
System.out.println("shippingdaniel :"+ buy.getShipping().toString());
String typeEnvio=buy.getShipping().getTypeShipping();
if(buy.getShipping().getTypeShipping().equals("home")) {

	Yng_Shipping shipping=null;
	buy.setShipping(shipping);

}
else {

///*nuevo codigo
	
Yng_Shipping tempShipping =new Yng_Shipping();

Yng_Shipping ship =new Yng_Shipping();
ship=buy.getShipping();
String nameMail=ship.getYng_Quote().getYng_Branch().getNameMail();


String typeMail;
boolean andreani=false,dhl=false,fedex=false;

switch (nameMail.toLowerCase()) {
    case "andreani":  andreani = true;typeMail="andreani";
             break;
    case "dhl":  dhl = true;typeMail="dhl";
             break;
    case "fedex":  fedex = true;typeMail="fedex";
             break;
    default: typeMail = "Invalid Mail";
             break;
             }
tempShipping.setAndreani(andreani);
tempShipping.setDhl(dhl);
tempShipping.setFedex(fedex);
Yng_Shipment yng_Shipment=new Yng_Shipment();

			if(nameMail.toLowerCase()=="fedex") {
				
				tempShipping.setShippingStatus("imprecionTicket");
				Yng_Branch branchTemp=branchDao.save(buy.getShipping().getYng_Quote().getYng_Branch());
				Yng_Quote quote=new Yng_Quote();
				quote=buy.getShipping().getYng_Quote();
				quote.setYng_Item(buy.getYng_item());
				quote.setYng_User(buy.getUser());
				quote.setYng_Branch(branchTemp);
				quote=quoteDao.save(buy.getShipping().getYng_Quote());				
				tempShipping.setYng_Quote(quote);
				 FedexXML xmlFedex=new FedexXML();
		    	  //iniciando variable de desarrolo
				    	  standard= new Yng_Standard();
				      	standard=standardService.findByKey("FedEXAuthenticationKey");
				      	FedEXAuthenticationKey= standard.getValue();
				      	
				      	standard=standardService.findByKey("FedExMeterNumber");
				      	FedExMeterNumber= standard.getValue();
				      	
				      	standard=standardService.findByKey("FedExAccountNumber");
				      	FedExAccountNumber= standard.getValue();
				      	
				      	standard=standardService.findByKey("FedexPassword");
				      	FedexPassword= standard.getValue();
				      	//inirCr
				      	xmlFedex.inirCre(FedEXAuthenticationKey, FedExMeterNumber, FedExAccountNumber, FedexPassword);
		    	  //finalizando variable de desarrolo
				      	Yng_Person per=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
				        List<Yng_Person> personList=personService.findByUser(buy.getUser());
				        for (Yng_Person yng_Person : personList) {
							System.out.println(""+yng_Person.toString());
							per=yng_Person;
						}
				        Yng_Person perItem=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
				        List<Yng_Person> personListItem=personService.findByUser(buy.getUser());
				        for (Yng_Person yng_Person : personListItem) {
							System.out.println("perItem"+yng_Person.toString());
							perItem=yng_Person;
						}
				        Yng_Product getProductByIdItem=new Yng_Product();
					   	  getProductByIdItem=getProductByIdItem(quote.getYng_Item().getItemId());
		    	 
		    		PropertyObjectHttp propertyObjectHttp = new PropertyObjectHttp();
		    		
					String cotizacion=xmlFedex.FedexShipping(per, tempShipping, perItem, getProductByIdItem);
					//obtener xml para el envio
		    		propertyObjectHttp.setBody(cotizacion);
		    		// setear el tipo de request GET, POST, PUT etc...
		    		propertyObjectHttp.setRequestMethod(propertyObjectHttp.POST);
		    		// setear el url ala que se enviara 
		    		propertyObjectHttp.setUrl("https://wsbeta.fedex.com:443/web-services");
		    			http  httoUrlcon=new http();
		    			String outputString;
		    			Yng_Shipment shippFedex = new Yng_Shipment();
		    			FedexResponce fedexR=new FedexResponce();
		    			
		    			try {
						 outputString=httoUrlcon.request(propertyObjectHttp);
						 shippFedex=fedexR.fedexShipment(outputString);							 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			//branchShipping.add(braFedex);
		    			//quoteFedex.setYng_Branch(braFedex);
				
				yng_Shipment.setShipmentCod(shippFedex.getShipmentCod());
				SellController img=new SellController();
				img.uploadPDF(""+shippFedex.getShipmentCod(), "");
				
				String pdf="https://s3-us-west-2.amazonaws.com/jsa-s3-bucketimage/image/fedexPdf/"+shippFedex.getShipmentCod()+".pdf";
				yng_Shipment.setTicket(pdf);
				yng_Shipment.setTypeMail(typeMail);
				yng_Shipment.setYng_Item(buy.getYng_item());
				yng_Shipment.setYng_User(buy.getUser());
				Yng_Shipment shipmentTemp=new Yng_Shipment();
				shipmentTemp=yng_Shipment;
				System.out.println("shipmentTemp"+shipmentTemp.toString());
				yng_Shipment=shipmentDao.save(shipmentTemp);
				
			}




			if(nameMail.toLowerCase()=="andreanidd2") {	
			
			tempShipping.setShippingStatus("imprecionTicket");
			Yng_Branch branchTemp=branchDao.save(buy.getShipping().getYng_Quote().getYng_Branch());
			Yng_Quote quote=new Yng_Quote();
			quote=buy.getShipping().getYng_Quote();
			quote.setYng_Item(buy.getYng_item());
			quote.setYng_User(buy.getUser());
			quote.setYng_Branch(branchTemp);
			
			quote=quoteDao.save(buy.getShipping().getYng_Quote());
			
			tempShipping.setYng_Quote(quote);
			
				
				Logistic logistic=new Logistic();
				String link="";
				String pdf="";
				String numberAndreani="";
				try {
					 Yng_Product getProductByIdItem=new Yng_Product();
			   	  getProductByIdItem=getProductByIdItem(quote.getYng_Item().getItemId());
					
					SAXParserFactory saxParseFactory=SAXParserFactory.newInstance();
			        SAXParser sAXParser=saxParseFactory.newSAXParser();
			        Yng_Person per=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
			        List<Yng_Person> personList=personService.findByUser(buy.getUser());
			        for (Yng_Person yng_Person : personList) {
						System.out.println(""+yng_Person.toString());
						per=yng_Person;
					}
			        Yng_Person perItem=new Yng_Person(); //personDao..findByYng_User(buy.getUser().getUserId());
			        List<Yng_Person> personListItem=personService.findByUser(buy.getUser());
			        for (Yng_Person yng_Person : personListItem) {
						System.out.println("perItem"+yng_Person.toString());
						perItem=yng_Person;
					}
					String xml=logistic.andreaniRemitenteWSDL(logistic.andreaniStringRe(per,tempShipping,perItem,getProductByIdItem,null,null,null));
			        com.valework.yingul.logistic.EnvioHandler handlerS=new com.valework.yingul.logistic.EnvioHandler();
			        
			        sAXParser.parse(new InputSource(new StringReader(xml)), handlerS);
			        ArrayList<com.valework.yingul.logistic.EnvioResponce> envios=handlerS.getEnvioResponse();
			        System.out.println("aniem");
			        for (com.valework.yingul.logistic.EnvioResponce versione : envios) {
			        	numberAndreani=versione.getNumeroAndreani();
			            System.out.println("versione.getNumero1:"+numberAndreani);
			        	}
			        System.out.println("logistic.andreaniPdfLink:"+numberAndreani);
					System.out.println("res:"+xml);
			        yng_Shipment.setRespuesta(xml);
			        int i = 0;
			System.out.println("numberAndreani  daniel :"+numberAndreani);
			System.out.println(":"+numberAndreani+":");	        
			        
					//link=logistic.andreaniPdfLink("310000003497162");
					link=logistic.andreaniPdfLink(numberAndreani +"");
			        while (link.equals(logistic.errorPDF())) {          //Condición trivial: siempre cierta
			            i++;
			            link=logistic.andreaniPdfLink(numberAndreani +"");
			            System.out.println ("Valor de i: " + i);
			            if (i==11) { break;}
			        } 
					System.out.println("linkda: "+link);
					if (link != null) {
			            //strResponse = link;
			            com.valework.yingul.logistic.ImprimirConstanciaHandler handlerI=new com.valework.yingul.logistic.ImprimirConstanciaHandler();
			            sAXParser.parse(new InputSource(new StringReader(link)), handlerI);
			            ArrayList<com.valework.yingul.logistic.ImprimirConstanciaResponse> impr=handlerI.getImprimirResponce();
			            for (com.valework.yingul.logistic.ImprimirConstanciaResponse versione : impr) {
			            	pdf=versione.getPdfLinkFile();
			                System.out.println("versione.getNumero2:"+versione.getPdfLinkFile());            
			            }
			        }
			
			        System.out.println("link pdf : "+pdf);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				yng_Shipment.setShipmentCod(numberAndreani);
				yng_Shipment.setTicket(pdf);
				yng_Shipment.setTypeMail(typeMail);
				yng_Shipment.setYng_Item(buy.getYng_item());
				yng_Shipment.setYng_User(buy.getUser());
				Yng_Shipment shipmentTemp=new Yng_Shipment();
				shipmentTemp=yng_Shipment;
				System.out.println("shipmentTemp"+shipmentTemp.toString());
				yng_Shipment=shipmentDao.save(shipmentTemp);
			}
			
	//-----fin del nuevo codigo
	tempShipping.setYng_Shipment(yng_Shipment);

tempShipping.setTypeShipping(typeEnvio);
tempShipping=shippingDao.save(tempShipping);


//shi
buy.setShipping(tempShipping);


buy.setShipping(shippingDao.save(buy.getShipping()));
}


////////////////////////////////////////
    	System.out.println("buy:"+buy.toString());
    	buy=buyDao.save(buy);    	
    	Yng_Confirm confirm=new Yng_Confirm();
    	confirm.setBuy(buy);
    	confirm.setBuyerConfirm(false);
    	confirm.setSellerConfirm(false);
    	confirm.setCodeConfirm(1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
    	confirm.setStatus("pending");
    	confirm.setBuyer(buy.getUser());
    	confirm.setSeller(buy.getSeller());
    	confirm=confirmDao.save(confirm);
    	//modificar los correos para pagos no con tarjeta
		
		if(typeEnvio.equals("home")) {
			smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA"," Se realizo la venta del producto :  "+buy.getYng_item().getName()+ "  "+"  Precio:" +buy.getYng_item().getPrice()+ "  " +"    los datos del comprador son: "+"Email :"+userTemp.getEmail()+"  Teléfono : "+userTemp.getPhone()+"  Dirección:"+buy.getYng_item().getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ buy.getYng_item().getYng_Ubication().getYng_City().getName()+" Calle:"+buy.getYng_item().getYng_Ubication().getStreet()+"  Numero:"+buy.getYng_item().getYng_Ubication().getNumber()
					+ "<br/> - Al Momento de entregar el producto al comprador ingresa <a href=\"https://www.yingul.com/confirmwos/"+confirm.getConfirmId()+"\" target=\"_blank\">aquí</a> donde tu y tu comprador firmaran la entrega del producto en buenas condiciones "
					+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina "
					+ "<br/> - No entregues el producto sin que tu y el vendedor firmen la entrega no aceptaremos reclamos si la confirmacion no esta firmada por ambas partes"
					+ "<br/> - Por tu seguridad no entregues el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
					+ "<br/> - Despues de entregar el producto tu comprador tiene 7 dias para observar sus condiciones posterior a eso te daremos mas instrucciones para recoger tu dinero"+
					"<p>Cordialemente:</p>"+ 
		        	"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        	"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
			smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "Adquirio: "+buy.getQuantity()+" "+buy.getYng_item().getName()+" a:"+buy.getCost()+" pago realizado con: "+buy.getYng_Payment().getType()+" "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+" Cumpla las siguientes instrucciones:."
					+ "<br/> - Al Momento de recibir el producto dile este codigo a tu vendedor: "+confirm.getCodeConfirm()+" si el producto esta en buenas condiciones "
					+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina "
					+ "<br/> - No recibas el producto ni des el código si no estas conforme con el producto no aceptaremos reclamos posteriores"
					+ "<br/> - Por tu seguridad no recibas el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
					+ "<br/> - Despues de recibir el producto tienes 7 dias para observar sus condiciones posterior a ese lapzo no se aceptan reclamos ni devolucion de tu dinero"+
					"<p>Cordialemente:</p>"+ 
		        	"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        	"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
		}
		else {
			smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","Se realizo la venta del producto :  "+buy.getYng_item().getName() +"  Descripción : "+buy.getYng_item().getDescription()+ "  " +"  Precio: " +buy.getYng_item().getPrice()+"   Costo del envio : " +buy.getShipping().getYng_Quote().getRate()+  
					"      --Imprimir la etiqueta de Andreani "
					+ "--Preparar y embalar el paquete junto a la etiqueta " + 
					"      --Preparar y embalar el paquete junto a la etiqueta   " + 
					"      --Déjalo en la sucursal Andreani más cercana ." + 
					"           "+buy.getShipping().getYng_Shipment().getTicket()
					+ "   Al Momento de entregar el producto en la sucursal Andreani ingresa <a href=\"https://www.yingul.com/confirmws/"+confirm.getConfirmId()+"\" target=\"_blank\">aquí</a> donde firmaras la entrega del producto en buenas condiciones"
					+ "Despues de entregar el producto Andreani tiene 2 dias para entregarlo a tu comprador "
					+ "Y tu comprador tiene 10 dias para observar sus condiciones, posterior a eso te daremos mas instrucciones para recoger tu dinero"+
					"<p>Cordialemente:</p>"+ 
		        	"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        	"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
			smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "Adquirio: "+buy.getQuantity()+" "+buy.getYng_item().getName()+" a:"+buy.getCost()+" pago realizado con: "+buy.getYng_Payment().getType()+" "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+" nos pondremos en contacto con usted lo mas pronto posible."+
					"<p>Cordialemente:</p>"+ 
		        	"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        	"<p>Su equípo de ventas Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
		}
    	return "save";
    }
}
