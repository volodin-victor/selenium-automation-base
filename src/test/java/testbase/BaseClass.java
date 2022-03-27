package testbase;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import utils.ConfigReader;
import utils.Constants;

public class BaseClass {

  public static WebDriver driver;

  public static WebDriver getDriver() {
    return driver;
  }

  /**
   * Create, setup and returnWebdriver
   *
   * @return Webdriver
   */
  public static WebDriver setUp(boolean isHeadless) {
    ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);
    Dimension size = new Dimension(
        Integer.parseInt(ConfigReader.getProperty("width").toLowerCase(Locale.ROOT)),
        Integer.parseInt(ConfigReader.getProperty("height").toLowerCase(Locale.ROOT)));
    String browser = ConfigReader.getProperty("browser").toLowerCase(Locale.ROOT);
    Point position = new Point(0, 0);

    switch (browser) {
      case "chrome":
        WebDriverManager.chromedriver().setup();
        Map<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.default_content_settings.geolocation", 2);
        chromePreferences.put("download.prompt_for_download", false);
        chromePreferences.put("download.directory_upgrade", true);
        chromePreferences.put("credentials_enable_service", false);
        chromePreferences.put("password_manager_enabled", false);
        chromePreferences.put("safebrowsing.enabled", true);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.setExperimentalOption("prefs", chromePreferences);
        System.setProperty("webdriver.chrome.silentOutput", "true");
        if (isHeadless) {
          chromeOptions.setHeadless(true);
          chromeOptions.addArguments("--window-size=" + size.getWidth() + "," + size.getWidth());
          chromeOptions.addArguments("--disable-gpu");
        }
        driver = new ChromeDriver(chromeOptions);
        break;
      case "firefox":
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (isHeadless) {
          FirefoxBinary firefoxBinary = new FirefoxBinary();
          firefoxBinary.addCommandLineOptions("--headless");
          firefoxOptions.setBinary(firefoxBinary);
        }
        driver = new FirefoxDriver(firefoxOptions);
        break;
      case "safari":
        driver = new SafariDriver();
        driver.manage().window().setPosition(position);
        driver.manage().window().setSize(size);
        break;
      case "edge":
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        break;
      case "ie":
        WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
        break;
      default:
        throw new RuntimeException("Browser is not supported for " + browser);
    }
    // basic Webdriver settings
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT_TIME));
    driver.manage().window().maximize();
    driver.get(ConfigReader.getProperty("url"));
    PageInitializer.pageInit();
    return driver;
  }

  /**
   * Stop driver and exit
   */
  public static void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
