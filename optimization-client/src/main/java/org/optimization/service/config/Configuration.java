package org.optimization.service.config;

import java.util.Objects;
import java.util.Properties;

/** Client Configuration class loading application properties for base url. */
public class Configuration {

  public static final String BASE_URL_PATH = "base.url.path";
  public static final String ADMIN_USERNAME = "admin.username";
  public static final String ADMIN_PASSWORD = "admin.password";
  private Properties props = new Properties();

  private Configuration(Properties props) {
    this.props = props;
  }

  public static Configuration create(Properties props) {
    Objects.requireNonNull(props);
    if (!props.containsKey(BASE_URL_PATH))
      throw new IllegalStateException("Properties donot have base.url.path defined");
    return new Configuration(props);
  }

  public Configuration addProperties(String key, String value) {
    props.put(key, value);
    return this;
  }

  public Properties properties() {
    return props;
  }

  public String baseURL() {
    return props.getProperty(BASE_URL_PATH);
  }
}
