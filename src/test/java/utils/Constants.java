package utils;

public class Constants {
    public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
    public static final String GECKO_DRIVER_PATH = System.getProperty("user.dir") + "/drivers/geckodriver.exe";
    public static final String CONFIGURATION_FILEPATH = System.getProperty("user.dir") + "/src/test/resources/configs/configuration.properties";
    public static final String SCREENSHOT_FILEPATH = System.getProperty("user.dir") + "/screenshots/";
    public static final int EXPLICIT_WAIT_TIME = 30;
    public static final int IMPLICIT_WAIT_TIME = 10;
}
