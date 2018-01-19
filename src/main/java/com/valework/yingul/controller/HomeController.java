package com.valework.yingul.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.RoleDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_Person;
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
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
    public String signupPost(@Valid @RequestBody Yng_Person person) throws MessagingException {
		Yng_User user=person.getYng_User();
		user.setYng_Ubication(null);
		String password= user.getPassword();
		user.setUsername(person.getName()+person.getLastname());
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
        	Set<Yng_UserRole> userRoles = new HashSet<>();
            userRoles.add(new Yng_UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            Yng_User temp = userService.findByEmail(user.getEmail());
            personService.createPerson(person, temp);
            smtpMailSender.send(user.getEmail(), "Autenticado exitosamente", "Ya esta autenticado su password es:"+password);
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
	
	
}
