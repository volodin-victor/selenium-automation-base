package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;
import testbase.BaseClass;
import testbase.PageInitializer;

public class CommonMethods extends PageInitializer {

  /**
   * Method clears the textbox if it's not empty and sends another text
   *
   * @param element WebElement
   * @param text    String
   */
  public static void sendText(WebElement element, String text) {
    element.clear();
    element.sendKeys(text);
  }

  /**
   * Method checks if radio/checkbox is enabled and then clicks on the element that has the value
   *
   * @param listOfElement List of WebElement
   * @param value         String value which we search to click on
   */
  public static void clickRadioOrCheckbox(List<WebElement> listOfElement, String value) {
    String actualValue;
    for (WebElement element : listOfElement) {
      actualValue = element.getAttribute("value").trim();
      if (element.isEnabled() && actualValue.equals(value)) {
        element.click();
        break;
      }
    }
  }

  /**
   * Method checks if the text is found in the dropdown element and only then selects it
   *
   * @param element      Drop-down WebElement
   * @param textToSelect Text which need to select
   */
  public static void selectDropdown(WebElement element, String textToSelect) {
    try {
      Select select = new Select(element);
      List<WebElement> options = select.getOptions();

      for (WebElement wElement : options) {
        if (wElement.getText().equals(textToSelect)) {
          select.selectByVisibleText(textToSelect);
          break;
        }
      }
    } catch (UnexpectedTagNameException e) {
      e.printStackTrace();
    }
  }

  /**
   * Methods checks if index of element is valid and then select it
   *
   * @param element Drop-down WebElement
   * @param index   Index to be selected
   */
  public static void selectDropdown(WebElement element, int index) {
    try {
      Select select = new Select(element);
      int size = select.getOptions().size();

      if (size > index) {
        select.selectByIndex(index);
      }
    } catch (UnexpectedTagNameException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method accepted Alerts and catches if alert is not presented
   */
  public static void acceptAlert() {
    try {
      Alert alert = BaseClass.getDriver().switchTo().alert();
      alert.accept();
    } catch (NoAlertPresentException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method dismissed Alerts and catches if alert is not presented
   */
  public static void dismissAlert() {
    try {
      Alert alert = BaseClass.getDriver().switchTo().alert();
      alert.dismiss();
    } catch (NoAlertPresentException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method returns Alert message or Null and catches exceptions if no alert is present
   *
   * @return Alert text or Null
   */
  public static String getAlertTest() {
    String alertText = null;

    try {
      Alert alert = BaseClass.getDriver().switchTo().alert();
      alertText = alert.getText();
    } catch (NoAlertPresentException e) {
      e.printStackTrace();
    }

    return alertText;
  }

  /**
   * Method sends text to the Alert  and handles exceptions if no alert is present
   *
   * @param text Text to be sent
   */
  public static void sendAlertText(String text) {
    try {
      Alert alert = BaseClass.getDriver().switchTo().alert();
      alert.sendKeys(text);
    } catch (NoAlertPresentException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method switches to a frame by using name or id of frame
   *
   * @param nameOrId Name or ID of frame
   */
  public static void switchToFrame(String nameOrId) {
    try {
      BaseClass.getDriver().switchTo().frame(nameOrId);
    } catch (NoSuchFrameException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method switches to a frame by using name or id of frame
   *
   * @param index integer number index of frame
   */
  public static void switchToFrame(int index) {
    try {
      BaseClass.getDriver().switchTo().frame(index);
    } catch (NoSuchFrameException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method switches to a frame by using WebElement
   *
   * @param element WebElement to be switched to
   */
  public static void switchToFrame(WebElement element) {
    try {
      BaseClass.getDriver().switchTo().frame(element);
    } catch (NoSuchFrameException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method switches focus to a child window
   */
  public static void switchToChildWindow() {
    String mainWindow = BaseClass.getDriver().getWindowHandle();
    Set<String> windows = BaseClass.getDriver().getWindowHandles();
    for (String window : windows) {
      if (window.equals(mainWindow)) {
        BaseClass.getDriver().switchTo().window(window);
      }
    }
  }

  /**
   * Methods creates WebDriverWait object and returns it
   *
   * @return WebDriverWait object
   */
  public static WebDriverWait getWaitObject() {
    return new WebDriverWait(BaseClass.getDriver(),
        Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
  }

  /**
   * Methods waits for item to be clickable
   *
   * @param element WebElement
   * @return WebElement to be waited for clickability
   */
  public static WebElement waitForClickability(WebElement element) {
    return getWaitObject().until(ExpectedConditions.elementToBeClickable(element));
  }

  /**
   * Methods waits for item to be visible
   *
   * @param element WebElement
   * @return WebElement to be waited for visibility
   */
  public static WebElement waitForVisibility(WebElement element) {
    return getWaitObject().until(ExpectedConditions.visibilityOf(element));
  }

  /**
   * This method click in an element and has wait implemented on it
   *
   * @param element WebElement to be clicked
   */
  public static void click(WebElement element) {
    waitForClickability(element);
    element.click();
  }

  /**
   * Method waits for time in seconds
   *
   * @param seconds integer count of seconds
   */
  public static void wait(int seconds) {
    try {
      Thread.sleep(seconds * 1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method casts WebDriver objects into JavascriptExecutor objects and return it
   *
   * @return JavascriptExecutor object
   */
  public static JavascriptExecutor getJsObject() {
    JavascriptExecutor js = (JavascriptExecutor) BaseClass.getDriver();
    return js;
  }

  /**
   * Method clicks on the element passed to it using JavaScript
   *
   * @param element WebElement to be clicked using JavaScript
   */
  public static void jsClick(WebElement element) {
    String script = "arguments[0].click()";
    getJsObject().executeScript(script, element);
  }

  /**
   * Method scrolls the page until the WebElement passed to it becomes visible
   *
   * @param element WebElement to be scrolled to
   */
  public static void scrollToElement(WebElement element) {
    String script = "arguments[0].scrollIntoView(true)";
    getJsObject().executeScript(script, element);
  }

  /**
   * Method scrolls the page down based on the passed pixels parameter
   *
   * @param pixels Count of pixels
   */
  public static void scrollDown(int pixels) {
    String script = "window.scrollBy(0," + pixels + ")";
    getJsObject().executeScript(script);
  }

  /**
   * Method scrolls the page down based on the passed pixels parameter
   *
   * @param pixels Count of pixels
   */
  public static void scrollUp(int pixels) {
    String script = "window.scrollBy(0,-" + pixels + ")";
    getJsObject().executeScript(script);
  }

  /**
   * Method selects a date from the calendar
   *
   * @param elements List of WebElements
   * @param date     specific date
   */
  public static void selectCalendarDate(List<WebElement> elements, String date) {
    for (WebElement day : elements) {
      if (day.isEnabled()) {
        if (day.getText().equals(date)) {
          day.click();
          break;
        }
      }
    }
  }

  /**
   * Method takes, handles and saves screenshot
   *
   * @param filename file name
   * @return bytes
   */
  public static byte[] takeScreenshot(String filename) {
    TakesScreenshot takeScreenSht = (TakesScreenshot) BaseClass.getDriver();

    byte[] pictureByte = takeScreenSht.getScreenshotAs(OutputType.BYTES);

    File pictureFile = takeScreenSht.getScreenshotAs(OutputType.FILE);
    String fileDest = Constants.SCREENSHOT_FILEPATH + filename + getTimeStamp()
        + "." + ConfigReader.getProperty("screenshotFormat");

    try {
      FileUtils.copyFile(pictureFile, new File(fileDest));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return pictureByte;
  }

  /**
   * Method performs formatting current time stamp into
   *
   * @return Current timestamp in a string
   */
  public static String getTimeStamp() {
    Date date = new Date();
    SimpleDateFormat sDateFormat = new SimpleDateFormat(ConfigReader.getProperty("timestampFormat")
    );
    return sDateFormat.format(date);
  }

  /**
   * Method takes parameter width from properties and casts it in short and returns as an integer
   *
   * @return screen width
   */
  public static int scrWidth() {
    String width = ConfigReader.getProperty("width");
    return Short.parseShort(width);
  }

  /**
   * Method takes parameter width from properties and casts it in short and returns as an integer
   *
   * @return screen height
   */
  public static int scrHeight() {
    String width = ConfigReader.getProperty("height");
    return Short.parseShort(width);
  }
}
