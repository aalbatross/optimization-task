package org.optimization.service.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * Client Configuration class loading application properties for base url.
 *
 */
public class Configuration {

  private static final String BASE_URL_PATH = "base.url.path";
  private Properties props = new Properties();

  private final File file =
      new File(
          Configuration.class.getClassLoader().getResource("application.properties").getFile());

  private Configuration() {}

  public static Configuration withConfigurationFile(String filePath) {
    Configuration config = new Configuration();
    File newFile = new File(filePath);
    try {
      if (newFile.exists()) {
        try (FileInputStream fis = new FileInputStream(newFile); ) {
          config.props.load(fis);
        }
      } else {
        try (FileInputStream fis = new FileInputStream(config.file); ) {
          config.props.load(fis);
        }
      }
    } catch (IOException ex) {
      throw new RuntimeException("Cannot load properties", ex);
    }
    return config;
  }

  public String baseURL() {
    return props.getProperty(BASE_URL_PATH);
  }
}
