package com.valework.yingul.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.NotificationDao;
import com.valework.yingul.dao.QueryDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Notification;
import com.valework.yingul.model.Yng_Query;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.QueryService;

@RestController
@RequestMapping("/query")
public class QueryController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired 
	UserDao userDao; 
	@Autowired
	QueryDao queryDao;
	@Autowired
	QueryService queryService;
	@Autowired
	ItemDao itemDao;
	@Autowired
	NotificationDao notificationDao;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
    public String createQuery(@Valid @RequestBody Yng_Query query) throws MessagingException {
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
    	query.setSeller(userDao.findByUsername(query.getYng_Item().getUser().getUsername()));
    	query.setStatus("pending");
    	//filtro para que no se pueda comentar un item propio
    	if(query.getUser().getUsername()==query.getYng_Item().getUser().getUsername()) {
    		return "no puedes comentar producos, servicios, inmuebles o vehiculos propios";
    	}
    	else {
			queryDao.save(query);
			Yng_Notification sellerNotification = new Yng_Notification();
	    	sellerNotification.setDate(query.getDate());
	    	sellerNotification.setTitle("¡Consulta!");
	    	sellerNotification.setItem(query.getYng_Item());
	    	sellerNotification.setStatus("pending");
	    	sellerNotification.setDesktopStatus("pending");
	    	sellerNotification.setDescription("Te acaban de consultar por:  "+query.getYng_Item().getName()+"... "+query.getQuery());
	    	sellerNotification.setUrl("https://www.yingul.com/userFront/sales/query");
	    	sellerNotification.setUser(query.getYng_Item().getUser());
	    	notificationDao.save(sellerNotification);
		    try {
				smtpMailSender.send(query.getYng_Item().getUser().getEmail(), "Consulta urgente sobre su Item", query.getUser().getUsername()+" pregunto "+query.getQuery()+" sobre el Item "+query.getYng_Item().getName()+". Puedes responder las consultas en: https://www.yingul.com/userFront/sales/query"
				+ "<p>Cordialemente:</p>\r\n"  
				+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
				+ "<p>Su equípo de consultas Yingul Company SRL</p>" +
				"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "save";
    	}
    }
	
	@RequestMapping("/Number/{username}")
    public int numberQueryByUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Query> queryList = queryService.findByUser(yng_User);
        return queryList.size();
    }
    @RequestMapping("/queryBySeller/{username}")
    public List<Yng_Query> findQueriesBySeller(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Query> queryList = queryDao.findBySellerOrderByQueryId(yng_User);
        return queryList;
    }
    @RequestMapping("/queryByBuyer/{username}")
    public List<Yng_Query> findQueriesByBuyer(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Query> queryList = queryDao.findByUserOrderByQueryIdAsc(yng_User);
        return queryList;
    }
    @RequestMapping("/queryByItemAndBuyer/{itemId}/{username}")
    public List<Yng_Query> queryByItemAndBuyer(@PathVariable("username") String username,@PathVariable("itemId") Long itemId) {
    	Yng_User yng_User = userDao.findByUsername(username);
    	Set<Yng_Query> queriList = new HashSet<>();
    	//queriList=LinkedHashSet(queriList);
        List<Yng_Query> queryList = queryDao.findByUserOrderByQueryIdAsc(yng_User);
        for (Yng_Query yng_Query : queryList) {
			if(yng_Query.getYng_Item().getItemId().equals(itemId)) {
				System.out.println(yng_Query.getQueryId());
				queriList.add(yng_Query);
			}
		}
        List<Yng_Query> finalQ = queriList.stream().sorted((e1, e2) -> 
        Long.compare(e1.getQueryId(),e2.getQueryId())).collect(Collectors.toList());

        return finalQ;
    }
    @RequestMapping("/queryBySellerAndStatus/{username}/{status}")
    public List<Yng_Query> findQueriesBySellerAndStatus(@PathVariable("username") String username,@PathVariable("status") String status) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Query> queryList = queryDao.findBySellerAndStatusOrderByQueryId(yng_User,status);
        return queryList;
    }
    @RequestMapping("/queryByBuyerAndStatus/{username}/{status}")
    public List<Yng_Query> findQueriesByBuyerAndStatus(@PathVariable("username") String username,@PathVariable("status") String status) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Query> queryList = queryDao.findByUserAndStatusOrderByQueryId(yng_User,status);
        return queryList;
    }
    //este metodo tambien deberia pedir autenticacion basica o algun metodo de seguridad
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
	@ResponseBody
    public String answerQueryPost(@Valid @RequestBody Yng_Query query) throws MessagingException {
    	Yng_Query queryTemp = queryDao.findByQueryId(query.getQueryId());
    	queryTemp.setAnswer(" ");
    	//filtro de comentarios
    	String s = query.getAnswer();
    	String[] words = s.split("\\s+");
    	query.setQuery("");
    	for (int i = 0; i < words.length; i++) {
    		if(words[i].indexOf('@')==-1||words[i].indexOf(".com")==-1||words[i].indexOf("ull")==-1) {
    			queryTemp.setAnswer(queryTemp.getAnswer()+words[i]+" ");
    		}
    	}
    	//fin del filtro de comentarios
    	//fecha de la respuesta
    	Date date = new Date();
    	DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	queryTemp.setDate(hourdateFormat.format(date));
    	queryTemp.setStatus("responded");
    	try {
			smtpMailSender.send(queryTemp.getUser().getEmail(), "Respuesta sobre "+queryTemp.getYng_Item().getName(), queryTemp.getYng_Item().getUser().getUsername()+" respondio!!! sobre" +queryTemp.getYng_Item().getName()+". Puedes ver la repuesta en: https://www.yingul.com/itemDetail/"+queryTemp.getYng_Item().getItemId()+" o ver todas las respuestas en https://www.yingul.com/userFront/purchases/query"
			+ "<p>Cordialemente:</p>\r\n"  
			+ "<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>\r\n" 
			+ "<p>Su equípo de consultas Yingul Company SRL</p>" +
			"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//fin de la fecha de respuesta
    	queryDao.save(queryTemp);
    	Yng_Notification buyerNotification = new Yng_Notification();
    	buyerNotification.setDate(query.getDate());
    	buyerNotification.setTitle("¡Respuesta!");
    	buyerNotification.setItem(query.getYng_Item());
    	buyerNotification.setStatus("pending");
    	buyerNotification.setDesktopStatus("pending");
    	buyerNotification.setDescription("Te acaban de responder por:  "+query.getYng_Item().getName()+"... "+query.getAnswer());
    	buyerNotification.setUrl("https://www.yingul.com/userFront/purchases/query");
    	buyerNotification.setUser(query.getUser());
    	notificationDao.save(buyerNotification);
		return "save";
    }
    @RequestMapping("/delete/{queryId}")
    public String deleteQuery(@PathVariable("queryId") Long queryId,@RequestHeader("Authorization") String authorization) {
		Yng_Query query=queryDao.findByQueryId(queryId);
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User = query.getSeller();
		Yng_User yng_User1 = query.getUser();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		if((yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword()))||(yng_User1.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User1.getPassword()))){
			queryDao.delete(query);
			return "save";
		}
		else {
			return "algo salio mal vuelve a intentarlo";
		}
    }
    

}
