package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
    static Properties properties = null;

    public static String getConfigField(String filed) {
        try {
            if(properties == null){
                FileReader reader = new FileReader("config");
                properties = new Properties();
                properties.load(reader);
            }
            return properties.getProperty(filed);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
