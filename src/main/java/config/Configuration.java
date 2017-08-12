package config;


import java.io.IOException;
import java.util.Properties;

/**
 * The Configuration class.
 *
 * @author Alireza Aghamohammadi
 */
public class Configuration {
  private static final String CONFIGURATION_FILE = "config.properties";
  private static Properties properties;

  /**
   * The constant DATABASE_NAME.
   */
  public static final Setting<String> DATABASE_NAME = () -> properties.getProperty("dbName");

  /**
   * The constant DATABASE_USER.
   */
  public static final Setting<String> DATABASE_USER = () -> properties.getProperty("dbUser");

  /**
   * The constant DATABASE_PASSWORD.
   */
  public static final Setting<String> DATABASE_PASSWORD = () -> properties.getProperty("dbPass");

  /**
   * The constant TABLE_NAME.
   */
  public static final Setting<String> TABLE_NAME = () -> properties.getProperty("tableName");
  /**
   * The constant COLUMN_NAME.
   */
  public static final Setting<String> COLUMN_NAME = () -> properties.getProperty("columnName");
  /**
   * The constant TAG_FILTER.
   */
  public static final Setting<String> TAG_FILTER = () -> properties.getProperty("tagFilter");

  static {
    properties = new Properties();
    try {
      properties.load(Configuration.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE)); //load config.properties file
    } catch (IOException e) {
      System.err.println("Loading configuration file " + CONFIGURATION_FILE + " failed");
    }
  }

  /**
   * make sure there is no instance of this class can create.
   */
  private Configuration() {

  }

  /**
   * The interface Setting.
   *
   * @param <T> the type parameter
   */
  @FunctionalInterface
  public interface Setting<T> {
    /**
     * Get t.
     *
     * @return the t
     */
    T get();
  }

}
