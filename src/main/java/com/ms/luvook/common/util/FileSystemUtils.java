package com.ms.luvook.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileSystemUtils {
//	public static final String STATIC_FILE_PATH = "C:/Users/ylland_IT2/git/luvook/src/main/resources/static/";
	public static final String STATIC_FILE_PATH = "C:/Users/vivie/Desktop/Something/Programming/IntellijSpace/luvook/src/main/resources/static/";

	public static String save(MultipartFile profileImg, String dir) {
		String name = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
		return save(profileImg, dir, name);
	}
	
	public static String save(MultipartFile profileImg, String dir, String name) {
		if(dir == null){
			dir = "";
		}
		Path path = Paths.get(STATIC_FILE_PATH + dir);
		File file = path.toFile();
		
		if(!file.exists()){
			file.mkdirs();
		}
		
        try {
          Files.copy(profileImg.getInputStream(), path.resolve(name));
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
		
        return name;
	}
}