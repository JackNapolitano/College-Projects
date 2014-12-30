import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AddUserImage {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://soda-machines.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddUserImage() throws Exception {
    driver.get(baseUrl + "");
    driver.findElement(By.id("mod_login_username")).clear();
    driver.findElement(By.id("mod_login_username")).sendKeys("jon18");
    driver.findElement(By.id("mod_login_password")).clear();
    driver.findElement(By.id("mod_login_password")).sendKeys("softwaretest");
    driver.findElement(By.name("Submit")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if ("Hi, jon18".equals(driver.findElement(By.id("mod_login_greeting")).getText())) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    driver.findElement(By.linkText("My Profile")).click();
    driver.get(baseUrl + "index.php?option=com_comprofiler&Itemid=24&task=userAvatar");
    driver.findElement(By.xpath("(//input[@name='newavatar'])[8]")).click();
    driver.findElement(By.xpath("//input[@value='Confirm Choice']")).click();
    driver.findElement(By.cssSelector("a.mainlevel")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
