package org.linagora.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {

	public Properties getProperties(String configName) throws Exception{
		if(configName == null)
			throw new Exception("The property file name can't be null");

		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			String propFileName = configName;
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null)
				prop.load(inputStream);
			else 
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return prop;
	}
	
}
