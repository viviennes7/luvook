package com.ms.luvook.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileSystemUtils {
//	private static final Path PATH = Paths.get("C:/Users/ylland_IT2/git/luvook/src/main/resources/static/img/profile");
	private static final String PATH = "C:/Users/ylland_IT2/git/luvook/src/main/resources/static/img/profile/";

	public static String save(byte[] bytes, String dir) {
		String name = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
		return save(bytes, dir, name);
	}
	
	public static String save(byte[] bytes, String dir, String name) {
		if(dir == null){
			dir = "";
		}
		Path path = Paths.get(PATH + dir);
		File file = path.toFile();
		
		if(!file.exists()){
			file.mkdirs();
		}
		
		try {
			Files.copy(new ByteArrayInputStream(bytes), path.resolve(name));
			return name;
		} catch (IOException e) {
			throw new IllegalStateException("Failed to save file " + name, e);
		}
	}
}