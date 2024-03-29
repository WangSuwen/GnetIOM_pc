package com.gnet.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static String getKeyValue(String key){
		String message = "";
		try {
			InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("global.properties");
			Properties props = new Properties(); 
			props.load(is);
			message = props.getProperty(key); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return message;
	}
}
