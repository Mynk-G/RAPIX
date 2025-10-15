package in.rapix.tech.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

    private static final String BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/env/";
    private static final Logger LOG = LogManager.getLogger(PropertyFileReader.class);

    private static Properties appProps;
    private static Properties envProps;

    static {
        loadApplicationProperties();
        loadEnvironmentProperties();
    }

    private static void loadApplicationProperties() {
        appProps = new Properties();
        String appFile = BASE_PATH + "application.properties";

        try (FileInputStream fis = new FileInputStream(appFile)) {
            appProps.load(fis);
            LOG.info("Loaded application properties from: " + appFile);
        } catch (IOException e) {
            LOG.error("Error loading application.properties file", e);
        }
    }

    private static void loadEnvironmentProperties() {
        String environment = appProps.getProperty("ENVIRONMENT");

        if (environment == null || environment.isEmpty()) {
            LOG.warn("ENVIRONMENT not found in application.properties — defaulting to 'test'");
            environment = "test";
        }

        // Convert ENVIRONMENT name into actual file name
        String envFileName = "application-" + environment.replace("env", "").replace("-", "") + ".properties";
        String envFilePath = BASE_PATH + envFileName;

        try (FileInputStream fis = new FileInputStream(envFilePath)) {
            appProps.load(fis);
            LOG.info("Loaded environment properties from: " + envFilePath);
        } catch (IOException e) {
            LOG.error("Error loading environment properties: " + envFilePath, e);
        }
    }

    public static String getProperty(String key) {
        return appProps.getProperty(key);
    }

    public static String getEnvProperty(String key) {
        return envProps.getProperty(key);
    }
}
