package com.excilys.computerdatabase.integration;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class IntegrationTestBasic {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testIntegrationTestBasic() throws Exception {
    driver.get(baseUrl + "/Cdb/login");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("submit")).click();
    driver.findElement(By.linkText("»")).click();
    driver.findElement(By.linkText("3")).click();
    driver.findElement(By.linkText("Macintosh II")).click();
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Acorn computer");
    driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("aaaa");
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("10/10/2012");
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("ACVS");
    driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Amiga Corporation");
    driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
    driver.findElement(By.linkText("Ma base de données d'ordinateurs")).click();
    driver.findElement(By.cssSelector("a.fa.fa-arrow-down")).click();
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
