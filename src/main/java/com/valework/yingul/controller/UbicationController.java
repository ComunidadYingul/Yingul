package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.model.Yng_Ambient;
import com.valework.yingul.model.Yng_Amenities;
import com.valework.yingul.model.Yng_Barrio;
import com.valework.yingul.model.Yng_City;
import com.valework.yingul.model.Yng_Confort;
import com.valework.yingul.model.Yng_Equipment;
import com.valework.yingul.model.Yng_Exterior;
import com.valework.yingul.model.Yng_Province;
import com.valework.yingul.model.Yng_Security;
import com.valework.yingul.model.Yng_Sound;
import com.valework.yingul.service.AmbientProperty;
import com.valework.yingul.service.AmenitiesProperty;
import com.valework.yingul.service.BarrioService;
import com.valework.yingul.service.CityService;
import com.valework.yingul.service.ConfortMotorized;
import com.valework.yingul.service.EquipmentMotorized;
import com.valework.yingul.service.ExteriorMotorized;
import com.valework.yingul.service.ProvinceService;
import com.valework.yingul.service.SecurityMotorized;
import com.valework.yingul.service.SoundMotorized;

@RestController
@RequestMapping("/ubication")
public class UbicationController {
	
	@Autowired
    private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private BarrioService barrioService;
	
	@RequestMapping("/province/all")
    public List<Yng_Province> findProvinceList() {
        List<Yng_Province> provinceList = provinceService.findAll();
        return provinceList;
    }
    @RequestMapping("/city/{provinceId}")
    public List<Yng_City> findCityListByProvince(@PathVariable("provinceId") int provinceId) {
    	Yng_Province yng_Province = provinceService.findByProvinceId(provinceId);
        List<Yng_City> cityList = cityService.findByProvince(yng_Province);
        return cityList;
    }
    @RequestMapping("/barrio/{cityId}")
    public List<Yng_Barrio> findBarrioListByCity(@PathVariable("cityId") int cityId) {
    	Yng_City yng_City = cityService.findByCityId(cityId);
        List<Yng_Barrio> barrioList = barrioService.findByCity(yng_City);
        return barrioList;
    }
    
    
    @Autowired
    private AmbientProperty ambientProperty;
    
    @Autowired
	private SecurityMotorized securityMotorized;
    
    @Autowired
    private ConfortMotorized confortMotorized;
    
    @Autowired
    private EquipmentMotorized equipmentMotorized;

    @Autowired
    private SoundMotorized soundMotorized;
    
    @Autowired
    private ExteriorMotorized exteriorMotorized;
    
    @Autowired
    private AmenitiesProperty amenitiesProperties;
    
	@RequestMapping("/security")
    public List<Yng_Security> findSecurytiList() {
        List<Yng_Security> securityList = securityMotorized.findAll();
        return securityList;
    }
    
    @RequestMapping("/confort")
    public List<Yng_Confort> findConfortList() {
        List<Yng_Confort> confortList = confortMotorized.findAll();
        
        return confortList;
    }
    
	@RequestMapping("/equipment")
    public List<Yng_Equipment> findEquipmentList() {
        List<Yng_Equipment> equipmentList = this.equipmentMotorized.findAll();
        return equipmentList;
    }
    
	@RequestMapping("/sound")
    public List<Yng_Sound> findSoundList() {
        List<Yng_Sound> soundList = this.soundMotorized.findAll();
        return soundList;
    }
    
    @RequestMapping("/exterior")
    public List<Yng_Exterior> findExteriorList(){
    	List<Yng_Exterior> exteriorList=this.exteriorMotorized.findAll();
    	return exteriorList;
    }
    
    @RequestMapping("/amenities")
    public List<Yng_Amenities> findAmenitiesList(){
    	List<Yng_Amenities> amenitiesList=this.amenitiesProperties.findAll();
    	return amenitiesList;
    }
    
    @RequestMapping("/ambient")
    public List<Yng_Ambient> findAmbientList(){
    	List<Yng_Ambient> ambientList=this.ambientProperty.findAll();
    	return ambientList;
    }
    
    @RequestMapping("/cp/{cp}")
    public List<Yng_City> findCityListByProvinceCP(@PathVariable("cp") int cp) {
    	//Yng_Province yng_Province = provinceService.findByProvinceId(provinceId);
        List<Yng_City> cityList = cityService.findByProvince2(cp);//.findByProvince(yng_Province);
        System.out.println(""+cp);
        return cityList;
    }
}
