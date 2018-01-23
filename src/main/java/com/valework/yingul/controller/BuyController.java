package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.testng.Assert;

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.BuyDao;
import com.valework.yingul.dao.CardDao;
import com.valework.yingul.dao.CardProviderDao;
import com.valework.yingul.dao.EnvioDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.ListCreditCardDao;
import com.valework.yingul.dao.PaymentMethodDao;
import com.valework.yingul.dao.RequestBodyDao;
import com.valework.yingul.dao.RequestDao;
import com.valework.yingul.dao.ResponseBodyDao;
import com.valework.yingul.dao.ResponseDao;
import com.valework.yingul.dao.ResponseHeaderDao;
import com.valework.yingul.dao.ShippingDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_CardProvider;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_ItemImage;
import com.valework.yingul.model.Yng_ListCreditCard;
import com.valework.yingul.model.Yng_PaymentMethod;
import com.valework.yingul.model.Yng_Request;
import com.valework.yingul.model.Yng_RequestBody;
import com.valework.yingul.model.Yng_Response;
import com.valework.yingul.model.Yng_ResponseBody;
import com.valework.yingul.model.Yng_ResponseHeader;
import com.valework.yingul.model.Yng_Shipping;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.CardService;
import com.valework.yingul.service.CreditCardProviderService;
import com.valework.yingul.VisaFunds;
import com.valework.yingul.util.VisaAPIClient;

import andreaniapis.AndreaniApis;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;



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
	PaymentMethodDao paymentMethodDao;
	@Autowired
	BuyDao buyDao;
	@Autowired 
	CardService cardService;
	//visacybersource
	@Autowired 
	VisaFunds visaFunds;
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
	ResponseBodyDao responseBodyDao;
	@Autowired 
	RequestBodyDao requestBodyDao;
	//visacybersource
	@Autowired
	ShippingDao shippingDao;
	 @Autowired
	 EnvioDao  envioDao;
	@Autowired 
	CardProviderDao cardProviderDao;
	
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
        if(yng_User.getPhone()==null) {
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
   
    	//para setear el usuario
    	Yng_User userTemp= userDao.findByUsername(buy.getUser().getUsername());
    	buy.setUser(userTemp);
    	//hasta aqui para el usuario
    	
    	//setear metodo de pago y realizar el cobro 
    	//primero verificamos la tarjeta y la guardamos
    	//verificar la tarjeta
    	response= visaFunds.salesTransaction(buy.getYng_PaymentMethod().getYng_Card().getNumber(),buy.getCost(),buy.getYng_PaymentMethod().getYng_Card().getDueMonth(),buy.getYng_PaymentMethod().getYng_Card().getDueYear()%100);
    	//guardar la consulta (request)
    	Yng_Request requestTemp=new Yng_Request();
    	requestTemp.setURI("https://sandbox.api.visa.com/cybersource/payments/v1/sales?apikey=NNE6A81AKKK49ODK98SQ21P_n9HIwyoueaU4Wx1IteueI8GLc");
    	requestTemp.setInfo("Payment Authorization Test");
    	//
    	//guardar la respuesta (response)
    	Yng_Response responseTemp= visaAPIClient.logResponse(response);
    	Set<Yng_ResponseHeader> responseHeader=responseTemp.getResponseHeader();
    	Set<Yng_ResponseBody> responseBody=responseTemp.getResponseBody();
    	responseTemp.setResponseHeader(null);
    	responseTemp.setResponseBody(null);
    	responseTemp=responseDao.save(responseTemp);
    	requestTemp.setYng_Response(responseTemp);
    	requestTemp=requestDao.save(requestTemp);
    	//json para el request
    	String prem="";
		if(buy.getYng_PaymentMethod().getYng_Card().getDueMonth()<10) {
			prem="0";
		}
    	String requestBodyString="{\"amount\": \""+buy.getCost()+"\","
        + "\"currency\": \"USD\","
        + "\"payment\": {"
            + "\"cardNumber\": \""+buy.getYng_PaymentMethod().getYng_Card().getNumber()+"\","
            + "\"cardExpirationMonth\": \""+prem+buy.getYng_PaymentMethod().getYng_Card().getDueMonth()+"\","
            + "\"cardExpirationYear\": \"20"+buy.getYng_PaymentMethod().getYng_Card().getDueYear()%100+"\""
            + "}"
        + "}";
    	JSONObject  jObject = new JSONObject(requestBodyString);
        Map<String,String> map = new HashMap<String,String>();
        Iterator iter = jObject.keys();
        while(iter.hasNext()){
            String key = (String)iter.next();
            String value= jObject.get(key).toString();
            map.put(key,value);
            Yng_RequestBody rbTemp= new Yng_RequestBody();
            rbTemp.setKey(key);
            rbTemp.setValue(value);
            rbTemp.setRequest(requestTemp);
            requestBodyDao.save(rbTemp);
        }
    	//
    	for (Yng_ResponseHeader s : responseHeader) {
    		s.setResponse(responseTemp);
    		responseHeaderDao.save(s);
    	}
    	for(Yng_ResponseBody t:responseBody) {
    		t.setResponse(responseTemp);
    		responseBodyDao.save(t);
    	}
    	//fin 
    	System.out.println("status"+response.getStatusLine().getStatusCode());
    	System.out.println("code"+HttpStatus.SC_CREATED);
    	if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_CREATED) {
    		return "problemCard";
    	}
    	Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
        response.close();
    	//y realizar el cobro a la tarjeta

    	//guardar la tarjeta
		Yng_Card cardTemp=buy.getYng_PaymentMethod().getYng_Card();
		cardTemp.setFullName(cardTemp.getFullName().trim().toUpperCase());
		cardTemp.setUser(userTemp);
		cardTemp.setDueYear(cardTemp.getDueYear()%100);
		if(cardTemp.getType().toString().equals("DEBIT"))
		{
			cardTemp.setYng_CardProvider(null);
		}
		else {
			cardTemp.setYng_CardProvider(cardProviderDao.findByCardProviderId(cardTemp.getYng_CardProvider().getCardProviderId()));
		}
		Yng_PaymentMethod paymentMethodTemp=buy.getYng_PaymentMethod();
		//para ver si la tarjeta existe 
		if (null == cardDao.findByNumberAndUser(cardTemp.getNumber(),buy.getUser())) {
			paymentMethodTemp.setYng_Card(cardDao.save(cardTemp)); 
        }
		else {
			paymentMethodTemp.setYng_Card(cardTemp);
		}
		paymentMethodTemp.setYng_Request(requestTemp);
		buy.setYng_PaymentMethod(paymentMethodDao.save(paymentMethodTemp));
    	//fin del metodo de pago
    	Date time = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	buy.setTime(hourdateFormat.format(time));
////////////////////////////////////////////////////////////
System.out.println("shippingdaniel :"+ buy.getShipping().toString());
String typeEnvio=buy.getShipping().getTypeShipping();
if(buy.getShipping().getTypeShipping().equals("home")) {
//buy.setShipping();
buy.getShipping().setYng_envio(null);
buy.setShipping(shippingDao.save(buy.getShipping()));

}
else {
com.valework.yingul.model.Yng_Envio tempEnvio=buy.getShipping().getYng_envio();
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


com.valework.yingul.model.Yng_Envio tempE=envioDao.save(tempEnvio);
Yng_Shipping tempShipping =new Yng_Shipping();
tempShipping.setYng_envio(tempE);
tempShipping=shippingDao.save(tempShipping);


//shi
buy.setShipping(tempShipping);


buy.setShipping(shippingDao.save(buy.getShipping()));
}


////////////////////////////////////////
    	
    	
    	
    	buyDao.save(buy);
    	//modificar los correos para pagos no con tarjeta
    	System.out.print(buy.getYng_PaymentMethod().getYng_Card().getNumber()%10000);
    	try {
			smtpMailSender.send(userTemp.getEmail(), "Compra exitosa", "Adquirio: "+buy.getQuantity()+" "+buy.getYng_item().getName()+" a:"+buy.getCost()+" pago realizado con: "+buy.getYng_PaymentMethod().getType()+" "+buy.getYng_PaymentMethod().getYng_Card().getProvider()+" terminada en: "+buy.getYng_PaymentMethod().getYng_Card().getNumber()%10000+" nos pondremos en contacto con usted lo mas pronto posible.");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "save";
    }
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@Valid @RequestBody Yng_User yng_user) throws MessagingException {	
    	Yng_User userTemp= userDao.findByUsername(yng_user.getUsername());
    	userTemp.setPhone(yng_user.getPhone());
    	userDao.save(userTemp);
    	return "save";
    }
    

}
