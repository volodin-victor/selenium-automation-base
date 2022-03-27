package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

  private static Properties properties;

  /**
   * Method accepts file path and read properties
   *
   * @param filePath
   */
  public static void readProperties(String filePath) {
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(filePath);
      properties = new Properties();
      properties.load(fileInputStream);
      fileInputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method returns the value for a specific key
   *
   * @param key
   * @return String value
   */
  public static String getProperty(String key) {
    return properties.getProperty(key);
  }
}
