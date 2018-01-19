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
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.QueryDao;
import com.valework.yingul.dao.UserDao;
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
	
	@RequestMapping("/Number/{username}")
    public int numberQueryByUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Query> queryList = queryService.findByUser(yng_User);
        return queryList.size();
    }
	@RequestMapping("/Queries/{username}")
    public List<Yng_Query> findQueriesByUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Query> queryList = queryService.findByUser(yng_User);
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
    	try {
			smtpMailSender.send(queryTemp.getUser().getEmail(), "Respuesta sobre "+queryTemp.getYng_Item().getName(), queryTemp.getYng_Item().getUser().getUsername()+" respondio!!! sobre" +queryTemp.getYng_Item().getName()+". Puedes ver la repuesta en: http://yingulportal-env.nirtpkkpjp.us-west-2.elasticbeanstalk.com/itemDetail/"+queryTemp.getYng_Item().getItemId());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//fin de la fecha de respuesta
    	queryDao.save(queryTemp);
		return "save";
    }

}
