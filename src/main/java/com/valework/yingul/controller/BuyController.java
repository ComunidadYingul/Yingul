package com.valework.yingul.controller;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.PayUFunds;
import org.testng.Assert;
import org.xml.sax.InputSource;

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.BranchDao;
import com.valework.yingul.dao.BuyDao;
import com.valework.yingul.dao.CardDao;
import com.valework.yingul.dao.CardProviderDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.CountryDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.EnvioDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.ListCreditCardDao;
import com.valework.yingul.dao.PaymentDao;

import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.QuoteDao;
import com.valework.yingul.dao.RequestBodyDao;
import com.valework.yingul.dao.RequestDao;
import com.valework.yingul.dao.ResponseBodyDao;
import com.valework.yingul.dao.ResponseDao;
import com.valework.yingul.dao.ResponseHeaderDao;
import com.valework.yingul.dao.ShipmentDao;
import com.valework.yingul.dao.ShippingDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.logistic.GetStateSend;
import com.valework.yingul.logistic.Logistic;
import com.valework.yingul.model.Yng_Branch;
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_CardProvider;
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Country;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ListCreditCard;
import com.valework.yingul.model.Yng_Payment;

import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Quote;
import com.valework.yingul.model.Yng_Request;
import com.valework.yingul.model.Yng_RequestBody;
import com.valework.yingul.model.Yng_Response;
import com.valework.yingul.model.Yng_ResponseBody;
import com.valework.yingul.model.Yng_ResponseHeader;
import com.valework.yingul.model.Yng_Shipment;
import com.valework.yingul.model.Yng_Shipping;
import com.valework.yingul.model.Yng_StateShipping;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.CardService;
import com.valework.yingul.service.CreditCardProviderService;
import com.valework.yingul.service.ProductService;
import com.valework.yingul.VisaFunds;
import com.valework.yingul.util.VisaAPIClient;
import andreaniapis.*;
import org.apache.http.client.methods.CloseableHttpResponse;

@RestController
@RequestMapping("/buy")
public class BuyController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
    private ListCreditCardDao listCreditCardDao;
	@Autowired
	private CreditCardProviderService creditCardProviderService;
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
	CardProviderDao cardProviderDao;
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
	@RequestMapping("/listCreditCard/all")
    public List<Yng_ListCreditCard> findProvinceList() {
        List<Yng_ListCreditCard> creditCardList = listCreditCardDao.findAll();
        return creditCardList;
    }

    @RequestMapping("/getCreditCardProvider/{listCreditCardId}")
    public List<Yng_CardProvider> findProviderByCreditCard(@PathVariable("listCreditCardId") Long listCreditCardId) {
    	Yng_ListCreditCard yng_ListCreditCard = listCreditCardDao.findByListCreditCardId(listCreditCardId);
        return creditCardProviderService.findByListCreditCard(yng_ListCreditCard);
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
        if(yng_User.getPhone()==null||(yng_User.getDocumentNumber().equals("")||yng_User.getDocumentNumber()==null)) {
        	
        	return false;
        }
        else {
        	return true;
        }
    }
    
    @RequestMapping(value = "/createBuy", method = RequestMethod.POST)
    @ResponseBody
    public String createBuy(@Valid @RequestBody Yng_Buy buy) throws Exception {	
    	//para setear el item
    	Yng_Item itemTemp=itemDao.findByItemId(buy.getYng_item().getItemId());
    	buy.setYng_item(itemTemp);
    	//fin setear el item
    	//para setear el usuario y el vendedor 
    	Yng_User userTemp= userDao.findByUsername(buy.getUser().getUsername());
    	Yng_Country countrySw=countryDao.findByCountryId(userTemp.getYng_Ubication().getYng_Country().getCountryId());
		if(!countrySw.isToBuy()) {
			return "Tu país todavia no esta habilitado para comprar en Yingul estamos trabjando en ello";
		}
    	buy.setUser(userTemp);
    	Yng_User sellerTemp = userDao.findByUsername(itemTemp.getUser().getUsername());
    	buy.setSeller(sellerTemp);
    	//hasta aqui para el usuario
    	
    	//Autorización de la tarjeta
    	Yng_Payment autorized =  payUFunds.authorizeCard(buy,userTemp);
    	//
    	if(autorized==null) {
    		return "problemCard";
    	}
    	
		buy.setYng_Payment(paymentDao.save(autorized));
    	//fin del metodo de pago
    	Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	buy.setTime(hourdateFormat.format(time));
////////////////////////////////////////////////////////////
System.out.println("shippingdaniel :"+ buy.getShipping().toString());
String typeEnvio=buy.getShipping().getTypeShipping();
if(buy.getShipping().getTypeShipping().equals("home")) {
//buy.setShipping();
//buy.getShipping().setYng_envio(null);
//buy.setShipping(shippingDao.save(buy.getShipping()));
	Yng_Shipping shipping=null;
	buy.setShipping(shipping);
}
else {
/*com.valework.yingul.model.Yng_Envio tempEnvio=buy.getShipping().getYng_envio();
com.valework.yingul.model.Yng_Envio yi=tempEnvio;
AndreaniApis andrea=new AndreaniApis();

andreaniapis.Yng_Envio com=new andreaniapis.Yng_Envio();
tempEnvio.setContrato("400006711");
com.setProvincia(yi.getProvincia());
com.setLocalidad(yi.getLocalidad());
com.setCodigoPostalDestino(yi.getCodigoPostalDestino());
com.setCalle(yi.getCalle());
com.setNumero(yi.getNumero());
com.setSucursalRetiro(yi.getSucursalRetiro());
com.setSucursalCliente(yi.getSucursalCliente());
com.setNombreApellido(yi.getNombreApellido());
com.setNombreApellidoAlternativo(yi.getNombreApellidoAlternativo());
com.setTipoDocumento(yi.getTipoDocumento());
com.setNumeroDocumento(yi.getNumeroDocumento());
com.setEmail(yi.getEmail());
com.setNumeroCelular(yi.getNumeroCelular());
com.setNumeroTelefono(yi.getNumeroTelefono());
com.setContrato("400006711");
com.setNumeroTransaccion(yi.getNumeroTransaccion());
com.setTarifa(yi.getTarifa());
com.setValorACobrar(yi.getValorACobrar());
com.setCategoriaDistancia(yi.getCategoriaDistancia());
com.setCategoriaFacturacion(yi.getCategoriaFacturacion());
com.setCategoriaPeso(yi.getCategoriaPeso());
com.setDetalleProductosEntrega(yi.getDetalleProductosEntrega());
com.setDetalleProductosRetiro(yi.getDetalleProductosRetiro());
com.setVolumen(yi.getVolumen());
com.setValorDeclarado(yi.getValorDeclarado());
com.setPeso(yi.getPeso());
String codAndreani="";

codAndreani=andrea.confirmarEnvio(com);
tempEnvio.setNumeroAndreani(codAndreani);
String pdfLink="";

pdfLink=andrea.linkPdf(codAndreani);

tempEnvio.setPdfLink(pdfLink);


com.valework.yingul.model.Yng_Envio tempE=envioDao.save(tempEnvio);*/
///*nuevo codigo
Yng_Shipping tempShipping =new Yng_Shipping();
//tempShipping.setYng_envio(tempE);
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

tempShipping.setShippingStatus("imprecionTicket");
Yng_Branch branchTemp=branchDao.save(buy.getShipping().getYng_Quote().getYng_Branch());
Yng_Quote quote=new Yng_Quote();
quote=buy.getShipping().getYng_Quote();
quote.setYng_Item(buy.getYng_item());
quote.setYng_User(buy.getUser());
quote.setYng_Branch(branchTemp);

quote=quoteDao.save(buy.getShipping().getYng_Quote());

tempShipping.setYng_Quote(quote);

	Yng_Shipment yng_Shipment=new Yng_Shipment();
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
		String xml=logistic.andreaniRemitenteWSDL(logistic.andreaniStringRe(per,tempShipping,perItem,getProductByIdItem));
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
					+ "<br/> - Al Momento de entregar el producto al comprador ingresa a: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/confirmwos/"+confirm.getConfirmId()+" donde tu y tu comprador firmaran la entrega del producto en buenas condiciones "
					+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina "
					+ "<br/> - No entregues el producto sin que tu y el vendedor firmen la entrega no aceptaremos reclamos si la confirmacion no esta firmada por ambas partes"
					+ "<br/> - Por tu seguridad no entregues el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
					+ "<br/> - Despues de entregar el producto tu comprador tiene 7 dias para observar sus condiciones posterior a eso te daremos mas instrucciones para recoger tu dinero");
			smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "Adquirio: "+buy.getQuantity()+" "+buy.getYng_item().getName()+" a:"+buy.getCost()+" pago realizado con: "+buy.getYng_Payment().getType()+" "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+" Cumpla las siguientes instrucciones:."
					+ "<br/> - Al Momento de recibir el producto dile este codigo a tu vendedor: "+confirm.getCodeConfirm()+" si el producto esta en buenas condiciones "
					+ "<br/> - Espera el mensaje de confirmacion exitosa de nuestra pagina "
					+ "<br/> - No recibas el producto ni des el código si no estas conforme con el producto no aceptaremos reclamos posteriores"
					+ "<br/> - Por tu seguridad no recibas el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
					+ "<br/> - Despues de recibir el producto tienes 7 dias para observar sus condiciones posterior a ese lapzo no se aceptan reclamos ni devolucion de tu dinero");
		}
		else {
			smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","Se realizo la venta del producto :  "+buy.getYng_item().getName() +"  Descripción : "+buy.getYng_item().getDescription()+ "  " +"  Precio: " +buy.getYng_item().getPrice()+"   Costo del envio : " +buy.getShipping().getYng_Quote().getRate()+  
					"      --Imprimir la etiqueta de Andreani "
					+ "--Preparar y embalar el paquete junto a la etiqueta " + 
					"      --Preparar y embalar el paquete junto a la etiqueta   " + 
					"      --Déjalo en la sucursal Andreani más cercana ." + 
					"           "+buy.getShipping().getYng_Shipment().getTicket()
					+ "   Al Momento de entregar el producto en la sucursal Andreani ingresa a: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/confirmws/"+confirm.getConfirmId()+" donde firmaras la entrega del producto en buenas condiciones"
					+ "Despues de entregar el producto Andreani tiene 2 dias para entregarlo a tu comprador "
					+ "Y tu comprador tiene 7 dias para observar sus condiciones, posterior a eso te daremos mas instrucciones para recoger tu dinero");
			smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "Adquirio: "+buy.getQuantity()+" "+buy.getYng_item().getName()+" a:"+buy.getCost()+" pago realizado con: "+buy.getYng_Payment().getType()+" "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+" nos pondremos en contacto con usted lo mas pronto posible.");
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
  		Yng_Item yng_Item = itemDao.findByItemId(itemId);
  		List<Yng_Product> productList= productService.findByItem(yng_Item);
  		Yng_Product product = productList.get(0);
  		System.out.println("pro: "+product);
  		return product;	
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
    			LogisticsController log=new LogisticsController();
    			try {
    				codAndreani=log.andreaniSucursales(ubicationTemp.getPostalCode(), "", "");
    			} catch (Exception e1) {
    				// TODO Auto-generated catch block
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
        	s.setYng_Payment(null);
        }
        return buyList;
    }
    @RequestMapping("/getSalesByUser/{username}")
    public List<Yng_Buy> findSalesByUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Buy> buyList = buyDao.findBySellerOrderByBuyIdDesc(yng_User);
        for (Yng_Buy s : buyList) {
        	s.setYng_Payment(null);
        }
        return buyList;

    }
    @RequestMapping("/getStateBuy/{codnumber}")
    public Yng_StateShipping getCodState(@PathVariable("codnumber") String codnumber) {
    	Yng_StateShipping stateShipping=new Yng_StateShipping();
    	GetStateSend getState = new GetStateSend();
    	try {
    		stateShipping=getState.sendState(codnumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    	
        return stateShipping;
    }
}
