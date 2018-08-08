package com.valework.yingul.controller;

import java.security.Principal;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

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

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.AccountDao;
import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.RoleDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Account;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Ubication;
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
	private BusinessService businessService;
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

	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
    public String signupPost(@Valid @RequestBody Yng_Person person) throws MessagingException {
		Yng_User user=person.getYng_User();
		user.setYng_Ubication(null);
		user.setPassword(user.getPassword().trim());
		String password= user.getPassword().trim();
		user.setUsername((cleanString(person.getName())+cleanString(person.getLastname())).replace(" ",""));
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
            smtpMailSender.send(user.getEmail(), "Autenticado exitosamente", "Ya esta autenticado su password es:"+password);
            System.out.println("llego :save");
            return "save";
        }
    }
	
	@RequestMapping(value = "/business", method = RequestMethod.POST)
	@ResponseBody
    public String signupBusinessPost(@Valid @RequestBody Yng_Business business) throws MessagingException {
		Yng_User user=business.getYng_User();
		System.out.println("11111");
		//user.setYng_Ubication(null);
		String password= user.getPassword();
		user.setUsername(business.getName()+business.getSocialName());
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
		if (userService.checkEmailExists(user.getEmail())) {
            return "email exist";
        } else { 

        Yng_Ubication ubicationTemp = new Yng_Ubication();
   		ubicationTemp.setStreet(business.getYng_User().getYng_Ubication().getStreet());
   		ubicationTemp.setNumber(business.getYng_User().getYng_Ubication().getNumber());
   		ubicationTemp.setPostalCode(business.getYng_User().getYng_Ubication().getPostalCode());
   		ubicationTemp.setAditional(business.getYng_User().getYng_Ubication().getAditional());
   		ubicationTemp.setYng_Province(provinceDao.findByProvinceId(business.getYng_User().getYng_Ubication().getYng_Province().getProvinceId()));
   		ubicationTemp.setYng_City(cityDao.findByCityId(business.getYng_User().getYng_Ubication().getYng_City().getCityId()));	
   		ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(business.getYng_User().getYng_Ubication().getYng_Barrio().getBarrioId()));
           Yng_Ubication ubicationTempo=ubicationDao.save(ubicationTemp);
           user.setYng_Ubication(ubicationTempo);
        	
        	
        	
        	
        	Set<Yng_UserRole> userRoles = new HashSet<>();
            userRoles.add(new Yng_UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            Yng_User temp = userService.findByEmail(user.getEmail());
            
         
            
            businessService.createBusiness(business, temp);
            
            smtpMailSender.send(user.getEmail(), "Autenticado exitosamente", "Ya esta autenticado su password es:"+password);
            return "save";
        }
		//return "save";
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
