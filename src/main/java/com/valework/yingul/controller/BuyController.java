package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.PayUFunds;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.BuyDao;
import com.valework.yingul.dao.CardDao;
import com.valework.yingul.dao.CardProviderDao;
import com.valework.yingul.dao.ConfirmDao;
import com.valework.yingul.dao.EnvioDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.ListCreditCardDao;
import com.valework.yingul.dao.PaymentDao;
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
import com.valework.yingul.model.Yng_Confirm;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ListCreditCard;
import com.valework.yingul.model.Yng_Payment;
import com.valework.yingul.model.Yng_Shipping;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.CardService;
import com.valework.yingul.service.CreditCardProviderService;
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
tempShipping.setTypeShipping(typeEnvio);
tempShipping=shippingDao.save(tempShipping);


//shi
buy.setShipping(tempShipping);


buy.setShipping(shippingDao.save(buy.getShipping()));
}


////////////////////////////////////////
    	
    	
    	Yng_Confirm confirm=new Yng_Confirm();
    	confirm.setBuy(buyDao.save(buy));
    	confirm.setBuyerConfirm(false);
    	confirm.setSellerConfirm(false);
    	confirm.setCodeConfirm(1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
    	confirm.setStatus("pending");
    	confirm=confirmDao.save(confirm);
    	//modificar los correos para pagos no con tarjeta
		
		if(typeEnvio.equals("home")) {
			smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA"," Se realizo la venta del producto :  "+buy.getYng_item().getName()+ "  "+"  Precio:" +buy.getYng_item().getPrice()+ "  " +"    los datos del comprador son: "+"Email :"+userTemp.getEmail()+"  Teléfono : "+userTemp.getPhone()+"  Dirección:"+buy.getYng_item().getYng_Ubication().getYng_Province().getName()+ "  Ciudad: "+ buy.getYng_item().getYng_Ubication().getYng_City().getName()+" Calle:"+buy.getYng_item().getYng_Ubication().getStreet()+"  Numero:"+buy.getYng_item().getYng_Ubication().getNumber()
					+ "Al Momento de entregar el producto al comprador ingresa a: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/confirmwos/"+confirm.getConfirmId()+" donde tu y tu comprador firmaran la entrega del producto en buenas condiciones"
					+ "No entregues el producto sin que tu y el vendedor firmen la entrega no aceptaremos reclamos si la confirmacion no esta firmada por ambas partes"
					+ "Por tu seguridad no entregues el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
					+ "Despues de entregar el producto tu comprador tiene 7 dias para observar sus condiciones posterior a eso te daremos mas instrucciones para recoger tu dinero");
			smtpMailSender.send(userTemp.getEmail(), "COMPRA EXITOSA", "Adquirio: "+buy.getQuantity()+" "+buy.getYng_item().getName()+" a:"+buy.getCost()+" pago realizado con: "+buy.getYng_Payment().getType()+" "+buy.getYng_Payment().getYng_Card().getProvider()+" terminada en: "+buy.getYng_Payment().getYng_Card().getNumber()%10000+" nos pondremos en contacto con usted lo mas pronto posible."
					+ "Al Momento de recibir el producto dile este codigo a tu vendedor: "+confirm.getCodeConfirm()+"si el producto esta en buenas condiciones"
					+ "No recibas el producto ni des el código si no estas conforme con el producto no aceptaremos reclamos posteriores"
					+ "Por tu seguridad no recibas el producto en lugares desconocidos o solitarios ni en la noche hazlo en un lugar de confianza, concurrido y en el día"
					+ "Despues de recibir el producto tienes 7 dias para observar sus condiciones posterior a ese lapzo no se aceptan reclamos ni devolucion de tu dinero");
		}
		else {
			smtpMailSender.send(buy.getYng_item().getUser().getEmail(), "VENTA EXITOSA","Se realizo la venta del producto :  "+buy.getYng_item().getName() +"  Descripción : "+buy.getYng_item().getDescription()+ "  " +"  Precio: " +buy.getYng_item().getPrice()+"   Costo del envio : " +buy.getShipping().getYng_envio().getTarifa()+  
					"      --Imprimir la etiqueta de Andreani "
					+ "--Preparar y embalar el paquete junto a la etiqueta " + 
					"      --Preparar y embalar el paquete junto a la etiqueta   " + 
					"      --Déjalo en la sucursal Andreani más cercana ." + 
					"           "+buy.getShipping().getYng_envio().getPdfLink()
					+ "Al Momento de entregar el producto en la sucursal Andreani ingresa a: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/confirmws/"+confirm.getConfirmId()+" donde firmaras la entrega del producto en buenas condiciones"
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
}
