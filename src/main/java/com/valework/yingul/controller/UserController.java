package com.valework.yingul.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.imgscalr.Scalr;
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
import com.valework.yingul.dao.BranchAndreaniDao;
import com.valework.yingul.dao.BusinessDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.CountryDao;
import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.StandardDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_BranchAndreani;
import com.valework.yingul.model.Yng_Business;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.PersonService;
import com.valework.yingul.service.S3Services;
import com.valework.yingul.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired 
	UserDao userDao;
	@Autowired 
	PersonService personService;
	@Autowired 
	UserService userService;
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	S3Services s3Services;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CountryDao countryDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private UbicationDao ubicationDao;
	@Autowired
	BranchAndreaniDao branchAndreaniDao;
	@Autowired
	LogisticsController logisticsController;
	@Autowired
	PersonDao personDao;
	@Autowired
	BusinessDao businessDao;
	@Autowired
	private StandardDao standardDao;
	@RequestMapping("/{username}")
    public Yng_User findByUsername(@PathVariable("username") String username) {
        return userDao.findByUsername(username);
    }
	@RequestMapping("/person/{username}")
    public Yng_Person getPerson(@PathVariable("username") String username) {
		Yng_User yng_User = userDao.findByUsername(username); 
		List<Yng_Person> personList= personDao.findAll();
		Yng_Person person = new Yng_Person();
		for (Yng_Person yng_Person : personList) {
			if(yng_Person.getYng_User().getUsername().equals(yng_User.getUsername())) {
				person = yng_Person;
				return person;	
			}
		}
		return null;
    }
	@RequestMapping("/business/{username}")
    public Yng_Business getBusiness(@PathVariable("username") String username) {
		Yng_User yng_User = userDao.findByUsername(username); 
		return businessDao.findByUser(yng_User);
    }
	@RequestMapping("/getPersonWithAuthorization/{username}")
    public Yng_Person getPersonWithAuthorization(@PathVariable("username") String username,@RequestHeader("Authorization") String authorization) {
		Yng_User user = userDao.findByUsername(username); 
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(user.getUsername().equals(yng_User.getUsername()) && yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			List<Yng_Person> personList= personDao.findAll();
			Yng_Person person = new Yng_Person();
			for (Yng_Person yng_Person : personList) {
				if(yng_Person.getYng_User().getUsername().equals(yng_User.getUsername())) {
					person = yng_Person;
					return person;	
				}
			}
			return null;
		}else {
			return null;
		}
			
    }
	
	@RequestMapping("/getUserByPhoneNumber/{phoneNumber}")
    public Yng_User getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber,@RequestHeader("X-API-KEY") String XAPIKEY) {
		Yng_Standard api = standardDao.findByKey("BACKEND_API_KEY");
    	if(XAPIKEY.equals(api.getValue())) {
    		List<Yng_User> userList = userDao.findAll();
    		for (Yng_User user : userList) {
				if(user.getPhone()!=null) {
					if(user.getPhone().replace(" ", "").replace("+", "").replace("-", "").contains(phoneNumber)||phoneNumber.contains(user.getPhone().replace(" ", "").replace("+", "").replace("-", ""))) {
						return user;
					}
				}
			}
    	}
		return null; 
    }
	
	@RequestMapping(value = "/updateUsername", method = RequestMethod.POST)
	@ResponseBody
    public String updateUsernamePost(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		user.setUsername(user.getUsername().replace(":", ""));
		user.setUsername(user.getUsername().replace(" ", ""));
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && parts[1].equals(user.getPassword()) && encoder.matches(parts[1], yng_User.getPassword())){
			if(userService.checkUsernameExists(user.getUsername())) {
				return "username exits";
			}else {
				yng_User.setUsername(user.getUsername());
				yng_User=userDao.save(yng_User);
				smtpMailSender.send(yng_User.getEmail(), "El USERNAME del Usuario de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
	        			"<br>" + 
	        			"Su USERNAME de Yingul ha cambiado recientemente.<br>" + 
	        			"Si usted no solicitó el cambio de USERNAME, póngase en contacto con el equipo de asistencia al cliente.<br>" + 
	        			"<a href=\"https://www.yingul.com/about/contactUs\" target=\"_blank\">aquí</a>" + 
	        			"<p>Cordialemente:</p>"+ 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        		"<p>Su equípo de registro Yingul Company SRL</p>" +
						"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        				"Copyright 2018 Yigul S.R.L.. All rights reserved.");
	        	return "save";
			}
		}else {
			return "prohibited";
		}
    	
    }
	@RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
	@ResponseBody
    public String updateEmailPost(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && parts[1].equals(user.getPassword()) && encoder.matches(parts[1], yng_User.getPassword())){
			if(userService.checkEmailExists(user.getEmail().toLowerCase())) {
				return "email exits";
			}else {
				smtpMailSender.send(yng_User.getEmail(), "El Email del Usuario de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
	        			"<br>" + 
	        			"Su Email de Yingul ha cambiado recientemente.<br>" + 
	        			"Si usted no solicitó el cambio de Email, póngase en contacto con el equipo de asistencia al cliente.<br>" + 
	        			"<a href=\"https://www.yingul.com/about/contactUs\" target=\"_blank\">aquí</a>" + 
	        			"<p>Cordialemente:</p>"+ 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        		"<p>Su equípo de registro Yingul Company SRL</p>" +
						"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        				"Copyright 2018 Yigul S.R.L.. All rights reserved.");
				yng_User.setEmail(user.getEmail().toLowerCase());
				yng_User=userDao.save(yng_User);
				smtpMailSender.send(yng_User.getEmail(), "El Email del Usuario de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
	        			"<br>" + 
	        			"Su Email de Yingul ha cambiado recientemente.<br>" + 
	        			"Si usted no solicitó el cambio de Email, póngase en contacto con el equipo de asistencia al cliente.<br>" + 
	        			"<a href=\"https://www.yingul.com/about/contactUs\" target=\"_blank\">aquí</a>" + 
	        			"<p>Cordialemente:</p>"+ 
		        		"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        		"<p>Su equípo de registro Yingul Company SRL</p>" +
						"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        				"Copyright 2018 Yigul S.R.L.. All rights reserved.");
				return "save";
			}
		}else {
			return "prohibited";
		}
    	
    }
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
    public String updatePasswordPost(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		String[] password = user.getPassword().split(":");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && parts[1].equals(password[1]) && encoder.matches(parts[1], yng_User.getPassword())){
			String encryptedPassword = passwordEncoder.encode(password[0]);
			yng_User.setPassword(encryptedPassword);
			yng_User=userDao.save(yng_User);
			smtpMailSender.send(yng_User.getEmail(), "La Calve del Usuario de Yingul ha cambiado", "Estimado "+yng_User.getUsername()+":<br>" + 
        			"<br>" + 
        			"Su Clave de Yingul ha cambiado recientemente.<br>" + 
        			"Si usted no solicitó el cambio de Clave, póngase en contacto con el equipo de asistencia al cliente.<br>" + 
        			"<a href=\"https://www.yingul.com/about/contactUs\" target=\"_blank\">aquí</a>" + 
	        		"<p>Cordialemente:</p>"+ 
		        	"<p><img src=\"https://www.yingul.com/assets/images/logonaranja.jpg\" width=\"182\" height=\"182\" /></p>"+ 
		        	"<p>Su equípo de registro Yingul Company SRL</p>" +
					"<p>Consultas o dudas a: <i>info@yingul.com</i></p>" +
        			"Copyright 2018 Yigul S.R.L.. All rights reserved.");
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	@RequestMapping(value = "/updateBusinessName", method = RequestMethod.POST)
	@ResponseBody
    public String updateBusinessName(@Valid @RequestBody Yng_Business business,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			Yng_Business businessTemp = businessDao.findByUser(yng_User);
			businessTemp.setBusinessName(business.getBusinessName().toUpperCase().trim());
			businessTemp = businessDao.save(businessTemp);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	@RequestMapping(value = "/updateBusinessDocumentNumber", method = RequestMethod.POST)
	@ResponseBody
    public String updateBusinessDocumentNumber(@Valid @RequestBody Yng_Business business,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			Yng_Business businessTemp = businessDao.findByUser(yng_User);
			businessTemp.setDocumentNumber(business.getDocumentNumber());
			businessTemp.setDocumentType(business.getDocumentType());
			businessTemp = businessDao.save(businessTemp);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	
	@RequestMapping(value = "/updateUserDocument", method = RequestMethod.POST)
	@ResponseBody
    public String updateUserDocument(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			yng_User.setDocumentNumber(user.getDocumentNumber());
			yng_User.setDocumentType(user.getDocumentType());
			yng_User=userDao.save(yng_User);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	
	
	@RequestMapping(value = "/updatePhones", method = RequestMethod.POST)
	@ResponseBody
    public String updatePhonesPost(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && encoder.matches(parts[1], yng_User.getPassword())){
			yng_User.setPhone(user.getPhone());
			yng_User.setPhone2(user.getPhone2());
			yng_User=userDao.save(yng_User);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	
	
	@RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
	@ResponseBody
    public String updatePhonePost(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0])  && encoder.matches(parts[1], yng_User.getPassword())){
			yng_User.setPhone(user.getPhone());
			yng_User=userDao.save(yng_User);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	@RequestMapping(value = "/updateVideo", method = RequestMethod.POST)
	@ResponseBody
    public String updateVideoPost(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0])  && encoder.matches(parts[1], yng_User.getPassword())){
			if(user.getProfileVideo().contains("embed")){}
			else {
				//https://youtu.be/zabDFISMtJI
				if(user.getProfileVideo().contains("https://youtu.be/")) {
					user.setProfileVideo(user.getProfileVideo().replace("https://youtu.be/", "https://www.youtube.com/embed/"));
				}else {
					user.setProfileVideo("https://www.youtube.com/embed/"+user.getProfileVideo().substring(user.getProfileVideo().indexOf("=")+1));
				}
			}
			System.out.println(user.getProfileVideo());
			yng_User.setProfileVideo(user.getProfileVideo());
			yng_User=userDao.save(yng_User);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	@RequestMapping(value = "/updateProfilePhoto", method = RequestMethod.POST)
	@ResponseBody
    public String updateProfilePhoto(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException, IOException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0])  && encoder.matches(parts[1], yng_User.getPassword())){
			
			String image=user.getProfilePhoto();
			user.setProfilePhoto("");
			String extension="";
	        String nombre="";
	        byte[] bI;

			extension=image.substring(11,14);
			if(image.charAt(13)=='e') {
				extension="jpeg";
			}
			if(yng_User.getProfilePhoto().equals("profile.jpg")) {
				nombre="profilePhoto"+yng_User.getUserId()+"-0";
			}else {
				String[] index = yng_User.getProfilePhoto().split("-");
				String indexnumber=index[1];
				System.out.println(indexnumber);
				String[] indexNumber = indexnumber.split("\\.");
				System.out.println(indexNumber[0]);
				int i=Integer.parseInt(indexNumber[0]);
				i++;
				nombre="profilePhoto"+yng_User.getUserId()+"-"+i;
			}
			
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			bI=convertImage(bI);
			s3Services.uploadFile("user/userProfile/"+nombre,extension, bI);
			nombre=nombre+"."+extension;   
			yng_User.setProfilePhoto(nombre);
			yng_User=userDao.save(yng_User);
			return "save";
		}else {
			return "prohibited";
		}
    	
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
	
	@RequestMapping(value = "/updateProfileBanner", method = RequestMethod.POST)
	@ResponseBody
    public String updateProfileBanner(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0])  && encoder.matches(parts[1], yng_User.getPassword())){
			
			String image=user.getProfileBanner();
			user.setProfileBanner("");
			String extension="";
	        String nombre="";
	        byte[] bI;

			extension=image.substring(11,14);
			if(image.charAt(13)=='e') {
				extension="jpeg";
			}
			if(yng_User.getProfileBanner().equals("sampleBanner.jpg")) {
				nombre="profileBanner"+yng_User.getUserId()+"-0";
			}else {
				String[] index = yng_User.getProfileBanner().split("-");
				String indexnumber=index[1];
				System.out.println(indexnumber);
				String[] indexNumber = indexnumber.split("\\.");
				System.out.println(indexNumber[0]);
				int i=Integer.parseInt(indexNumber[0]);
				i++;
				nombre="profileBanner"+yng_User.getUserId()+"-"+i;
			}
			
			bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
			s3Services.uploadFile("user/userProfile/"+nombre,extension, bI);
			nombre=nombre+"."+extension;   
			yng_User.setProfileBanner(nombre);
			yng_User=userDao.save(yng_User);
			return "save";
		}else {
			return "prohibited";
		}
    	
    }
	@RequestMapping("/getProfilePhoto/{username}")
    public String getProfilePhoto(@PathVariable("username") String username,@RequestHeader("Authorization") String authorization) {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0])  && encoder.matches(parts[1], yng_User.getPassword())){
			return userDao.findByUsername(username).getProfilePhoto();
		}else {
			return "prohibited";
		}
    }
	
	@RequestMapping(value = "/setUserUbicationEditPersonalInfo", method = RequestMethod.POST)
	@ResponseBody
    public Yng_User newUbication(@Valid @RequestBody Yng_User user,@RequestHeader("Authorization") String authorization) throws MessagingException {
		String token =new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization));
		String[] parts = token.split(":");
		Yng_User yng_User= userDao.findByUsername(parts[0]);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		if(yng_User.getUsername().equals(parts[0]) && yng_User.getUsername().equals(user.getUsername()) && encoder.matches(parts[1], yng_User.getPassword())){
			Yng_Ubication ubicationTemp = new Yng_Ubication();
			/*ubicationTemp = user.getYng_Ubication();
			ubicationTemp.setYng_Country(countryDao.findByCountryId(user.getYng_Ubication().getYng_Country().getCountryId()));
			ubicationTemp.setYng_Province(provinceDao.findByProvinceId(user.getYng_Ubication().getYng_Province().getProvinceId()));
			ubicationTemp.setYng_City(cityDao.findByCityId(user.getYng_Ubication().getYng_City().getCityId()));
			ubicationTemp.setPostalCode(ubicationTemp.getYng_City().getCodigopostal());
			
			Yng_BranchAndreani codAndreani=new Yng_BranchAndreani();
			LogisticsController log=new LogisticsController();
			try {
				codAndreani=log.andreaniSucursales(ubicationTemp.getPostalCode(), "", "");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ubicationTemp.setCodAndreani(""+codAndreani.getCodAndreani());*/
			
			ubicationTemp=updateUserUbication(user);
			yng_User.setYng_Ubication(ubicationTemp);
			yng_User.setDocumentNumber(user.getDocumentNumber());
			yng_User.setDocumentType(user.getDocumentType());
			yng_User.setPhone(user.getPhone());
			userDao.save(yng_User);
			return yng_User;
		}else {
			return null;
		}
    	
    }
	public Yng_Ubication updateUserUbication( Yng_User yng_user) throws MessagingException {	
		System.out.println("Ubicacion llegada :"+yng_user.getYng_Ubication().toString());//ubicationTemp.toString());
	    	//Yng_User userTemp=new Yng_User();
	    	
	    	Yng_User userTemp= userDao.findByUsername(yng_user.getUsername());
	    	System.out.println("userTemp:"+userTemp.getUsername());
	    			Yng_Ubication ubicationTemp = new Yng_Ubication();
	    			ubicationTemp.setStreet(yng_user.getYng_Ubication().getStreet());
	    			ubicationTemp.setNumber(yng_user.getYng_Ubication().getNumber());
	    			
	    			ubicationTemp.setAditional(yng_user.getYng_Ubication().getAditional());
	    			ubicationTemp.setWithinStreets(yng_user.getYng_Ubication().getWithinStreets());
	    			ubicationTemp.setDepartment(yng_user.getYng_Ubication().getDepartment());
	    			ubicationTemp.setYng_Province(provinceDao.findByProvinceId(yng_user.getYng_Ubication().getYng_Province().getProvinceId()));
	    			ubicationTemp.setYng_City(cityDao.findByCityId(yng_user.getYng_Ubication().getYng_City().getCityId()));	
	    			ubicationTemp.setPostalCode(ubicationTemp.getYng_City().getCodigopostal());
	    			ubicationTemp.setYng_Country(countryDao.findByCountryId(yng_user.getYng_Ubication().getYng_Country().getCountryId()));
	    			//ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(productTemp.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
	    			System.out.println("Ubi:"+ubicationTemp.toString());
	    			String codAndreani="";
	    			LogisticsController log=new LogisticsController();
	    			Yng_BranchAndreani branchAndreani=new Yng_BranchAndreani();
	    			try {
	    				//codAndreani=log.andreaniSucursales(ubicationTemp.getPostalCode(), "", "");
	    				//codAndreani=log.andreaniSucursalesObject(ubicationTemp.getPostalCode(), "", "").getCodAndreani();
	    				branchAndreani=logisticsController.andreaniSucursalesObject(ubicationTemp.getPostalCode(), "", "");
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
	    	return ubicationTempo;
	    	}

}
