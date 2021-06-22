package com.spotify.util;

import java.util.Properties;

public class ConfigLoader {

	private final  Properties prop;
	private static ConfigLoader load = null;

	private ConfigLoader()  {
		prop = PropertyLoader.getProperty("/Users/suneela/EclipeWorkSpace/Automation/RestAssuredTestNG/Resource/GlobalProperty.properties");
	}

	public static ConfigLoader getInstance() {
		if (load == null) {
			load = new ConfigLoader();
		}
		return load;
	}

	public  String getProperty(String key) {
		return prop.getProperty(key);
	}

}
