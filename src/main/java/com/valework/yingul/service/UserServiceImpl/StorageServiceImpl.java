package com.valework.yingul.service.UserServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.valework.yingul.service.S3Services;
import com.valework.yingul.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {
		
	@Autowired
	S3Services s3Services;
		
    @Override
    public String store(MultipartFile file,String nombre) {
    	//String name= file.getOriginalFilename();
    	String name=nombre+"."+getExtension(file.getOriginalFilename());
    	File convFile = new File(file.getOriginalFilename());
        try {
			convFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(convFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			fos.write(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println("---------------- START UPLOAD FILE ----------------");
		s3Services.uploadFile(name, convFile);
		return name;
    }
    public static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
              return "";
        } else {
              return filename.substring(index + 1);
        }
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}
}