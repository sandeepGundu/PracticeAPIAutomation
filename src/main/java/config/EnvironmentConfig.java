package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentConfig
{
    private static final Logger logger = LogManager.getLogger(EnvironmentConfig.class);
    private static Properties properties;

    // Base URL constant
    public static String BASE_URL;

    static {
        try {
            // Load the environment.properties file
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config/environment.properties");
            properties.load(fileInputStream);

            // Initialize the BASE_URL constant
            BASE_URL = properties.getProperty("base.url");
        } catch (IOException e) {

            logger.error(e.toString());

            throw new RuntimeException("Failed to load environment.properties file");
        }
    }

    // Get a property value by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Get the base URL
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    // Get the environment name
    public static String getEnvironment() {
        return getProperty("environment");
    }
}
