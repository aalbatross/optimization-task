package org.optimization.service.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

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
      if (newFile.exists()) config.props.load(new FileInputStream(newFile));
      else config.props.load(new FileInputStream(config.file));
    } catch (Exception ex) {
      throw new RuntimeException("Cannot load " + newFile.getAbsolutePath(), ex);
    }
    return config;
  }

  public String baseURL() {
    return props.getProperty(BASE_URL_PATH);
  }
}
