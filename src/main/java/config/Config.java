package config;

import javax.ws.rs.core.Application;
import java.io.*;
import java.util.Properties;

public class Config {
    static Properties properties = new Properties();

    public static String getConfigField(String filed) {
        try {
            if(properties.isEmpty()){
                final String propFileName = "config.properties";
                InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(propFileName);
                if (inputStream == null) {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
                properties.load(inputStream);
            }
            return properties.getProperty(filed);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}