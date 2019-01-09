package com.edr.utils;

import java.util.Properties;

public class Config {

	  //private static final String APP_PROPERTIES = "/application.properties";

	  private static Properties props = new Properties();
	  public static void load(){
	    try {
	        props.load(Config.class.getResourceAsStream("/application.properties"));	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	  }
	  public static String getProperty(String key) {
	    return props.getProperty(key);
	  }
}
