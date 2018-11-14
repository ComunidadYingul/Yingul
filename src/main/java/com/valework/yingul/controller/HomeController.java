package com.valework.yingul.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.dao.BusinessDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.RoleDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.security.Yng_UserRole;
import com.valework.yingul.service.BusinessService;
import com.valework.yingul.service.PersonService;
import com.valework.yingul.service.UserService;

@Controller
public class HomeController {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userService;
	@Autowired 
	private PersonService personService;
	@Autowired
    private RoleDao roleDao;
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
	BarrioDao barrioDao;
	@Autowired 
	CityDao cityDao;
	@Autowired 
	ProvinceDao provinceDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired 
	UbicationDao ubicationDao;
	@Autowired 
	AccountDao accountDao;
	@Autowired 
	StandardDao standardDao;
	@Autowired
	BusinessDao businessDao;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
    public String signupPost(@Valid @RequestBody Yng_Person person) throws MessagingException {
		Yng_User user=person.getYng_User();
		user.setYng_Ubication(null);
		user.setPassword(user.getPassword().trim());
		String password= user.getPassword().trim();
		String usernameTemp=person.getName()+person.getLastname().replace(" ", "");
		user.setUsername(cleanString(usernameTemp).replace("%20",""));
		LOG.info(user.getUsername());
		if(userService.checkUsernameExists(user.getUsername())) {
			LOG.info("existe"+user.getUsername());
			int aux=0;
			while(userService.checkUsernameExists(user.getUsername())){
				aux++;
				user.setUsername(user.getUsername()+aux);
				LOG.info(user.getUsername());
			}
		}
		LOG.info(user.getUsername());
		user.setEmail(user.getEmail().trim().toLowerCase());
		if (userService.checkEmailExists(user.getEmail())) {
			System.out.println("llego :email exist");
            return "email exist";
        } else {     	
        	Set<Yng_UserRole> userRoles = new HashSet<>();
            userRoles.add(new Yng_UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            Yng_User temp = userService.findByEmail(user.getEmail());
            personService.createPerson(person, temp);
            //estoy aumentado la cuenta para el usuario cuando se registra como nuevo
            Yng_Account account = new Yng_Account();   
            account.setAccountNonExpired(true);
            account.setAccountNonLocked(true);
            account.setAvailableMoney(0);
            account.setCurrency("ARS");
            account.setReleasedMoney(0);
            account.setWithheldMoney(0);
            account.setUser(temp);
            accountDao.save(account);
            //
            smtpMailSender.send(user.getEmail(), "Autenticado exitosamente", "<h2>Hola "+person.getName()+",</h2>\r\n" + 
            		"<p>Yingul Company te da la bienvenida a la plataforma más segura y confiable para comprar y vender por internet.</p>\r\n" + 
            		"<p>Hoy, aceptaste los&nbsp;<strong>Términos y Condiciones Generales (TCG)&nbsp;</strong>para usuarios Yingul, Para ver su contenido, haz click <a href=\"https://www.yingul.com/assets/images/terminos-y-condiciones-de-uso.pdf\" target=\"_blank\">aquí</a>.</p>\r\n" + 
            		"<p>Puede iniciar sesión en nuestro sistema en&nbsp;<a href=\"https://www.yingul.com/login\" target=\"_blank\" rel=\"noopener noreferrer\">aquí</a>, usando la siguiente información:<br />&nbsp; &nbsp; Correo: "+temp.getEmail()+"<br />&nbsp;&nbsp;&nbsp;&nbsp;Contraseña: "+password+"<br />&nbsp; &nbsp; Nombre de usuario:&nbsp;"+temp.getUsername()+"</p>\r\n" + 
            		"<p>Atentamente:</p>\r\n" + 
            		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" alt=\"\" width=\"182\" height=\"182\" /></p>\r\n" + 
            		"<p>Su equípo de autenticación Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
            return "save";
        }
    }
	
	@RequestMapping(value = "/business", method = RequestMethod.POST)
	@ResponseBody
    public String signupBusinessPost(@Valid @RequestBody JSONObject objectForBusiness) throws MessagingException, JsonParseException, JsonMappingException {
		JSONObject personObj = objectForBusiness.optJSONObject("person");
		JSONObject businessObj = objectForBusiness.optJSONObject("business");
		ObjectMapper mapper = new ObjectMapper();
        
		Yng_Person person = new Yng_Person();
        Yng_Business business = new Yng_Business();
        
		try {
			business = mapper.readValue(String.valueOf(businessObj), Yng_Business.class);
			person = mapper.readValue(String.valueOf(personObj), Yng_Person.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "problem";
		}
        
		Yng_User user=person.getYng_User();
		user.setYng_Ubication(null);
		user.setPassword(user.getPassword().trim());
		String password= user.getPassword().trim();
		String usernameTemp=person.getName()+person.getLastname().replace(" ", "");
		user.setUsername(cleanString(usernameTemp).replace("%20",""));
		LOG.info(user.getUsername());
		if(userService.checkUsernameExists(user.getUsername())) {
			LOG.info("existe"+user.getUsername());
			int aux=0;
			while(userService.checkUsernameExists(user.getUsername())){
				aux++;
				user.setUsername(user.getUsername()+aux);
				LOG.info(user.getUsername());
			}
		}
		LOG.info(user.getUsername());
		user.setEmail(user.getEmail().trim().toLowerCase());
		if (userService.checkEmailExists(user.getEmail())) {
			System.out.println("llego :email exist");
            return "email exist";
        } else {     	
        	Set<Yng_UserRole> userRoles = new HashSet<>();
            userRoles.add(new Yng_UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            Yng_User temp = userService.findByEmail(user.getEmail());
            personService.createPerson(person, temp);
            //estoy aumentado la cuenta para el usuario cuando se registra como nuevo
            Yng_Account account = new Yng_Account();   
            account.setAccountNonExpired(true);
            account.setAccountNonLocked(true);
            account.setAvailableMoney(0);
            account.setCurrency("ARS");
            account.setReleasedMoney(0);
            account.setWithheldMoney(0);
            account.setUser(temp);
            accountDao.save(account);
            //
            business.setBusinessName(business.getBusinessName().toUpperCase());
            business.setUser(user);
            businessDao.save(business);
            smtpMailSender.send(user.getEmail(), "Autenticado exitosamente", "<p><strong>Hola "+business.getBusinessName()+",</strong></p>\r\n" + 
            		"<p>Yingul Company te da la bienvenida a la plataforma más segura y confiable para comprar y vender por internet.</p>\r\n" + 
            		"<p>Hoy, aceptaste los&nbsp;<strong>Términos y Condiciones Generales (TCG)&nbsp;</strong>para empresas Yingul, Para ver su contenido, haz click <a href=\"https://www.yingul.com/assets/images/terminos-y-condiciones-de-uso.pdf\" target=\"_blank\" rel=\"noopener\">aquí</a>.</p>\r\n" + 
            		"<p>Puede iniciar sesión en nuestro sistema en&nbsp;<a href=\"https://www.yingul.com/login\" target=\"_blank\" rel=\"noopener noreferrer\">aquí</a>, usando la siguiente información:<br />&nbsp; &nbsp; Correo: "+temp.getEmail()+"<br />&nbsp;&nbsp;&nbsp;&nbsp;Contraseña: "+password+"<br />&nbsp; &nbsp; Nombre de usuario:&nbsp;"+temp.getUsername()+"</p>\r\n" + 
            		"<p>Atentamente:</p>\r\n" + 
            		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" alt=\"\" width=\"182\" height=\"182\" /></p>\r\n" + 
            		"<p>Su equípo de autenticación Yingul Company SRL</p>" +
							"<p>Consultas o dudas a: <i>info@yingul.com</i></p>");
            return "save";
        }
    }
	
	@RequestMapping("/userFront")
	@ResponseBody
	public String userFront(Principal principal, Model model) {
        return principal.getName().toString();
    }
	
	@RequestMapping(value = "/auth/login", method = RequestMethod.POST)
	@ResponseBody
    public Yng_User login(@Valid @RequestBody Yng_User user,@RequestHeader("X-API-KEY") String XAPIKEY) throws MessagingException {
		Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
		if(XAPIKEY.equals(api.getValue())) {
			Yng_User logged = new Yng_User();
			if(userService.checkEmailExists(user.getUsername())) {
				logged= userService.findByEmail(user.getUsername());
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
				if(logged.getEmail().equals(user.getUsername()) && encoder.matches(user.getPassword(), logged.getPassword())){
					return logged;
				}else {
					return null;
				}
			}else {
				if(userService.checkUsernameExists(user.getUsername())) {
					logged= userService.findByUsername(user.getUsername());
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
					if(logged.getUsername().equals(user.getUsername()) && encoder.matches(user.getPassword(), logged.getPassword())){
						return logged;
					}else {
						return null;
					}
				}else {
					return null;
				}
			}	
		}else {
			return null;
		}
		
    }
	public static String cleanString(String texto) {
        texto = texto.trim().replace(" ","%20");
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }
	
}
