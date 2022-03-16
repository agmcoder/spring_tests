package es.codeurjc.ais.nitflex.selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

//@WebAppConfiguration
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
public class AddFilmTest {
	//private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@LocalServerPort
	int port;
	WebDriver driver;
	
	@BeforeAll
	public static void setupClass() {
		WebDriverManager.firefoxdriver().setup();

	}
	
	
	@BeforeEach
	public void setUp() throws Exception {

		//System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		// 1 - Abrir el navegador web
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAddFilm() throws Exception {
		driver.get("http://localhost:8080/");
	    driver.findElement(By.id("create-film")).click();
	    driver.findElement(By.name("title")).click();
	    driver.findElement(By.name("title")).clear();
	    driver.findElement(By.name("title")).sendKeys("avatar");
	    driver.findElement(By.name("year")).clear();
	    driver.findElement(By.name("year")).sendKeys("1996");
	    driver.findElement(By.name("url")).click();
	    driver.findElement(By.name("url")).click();
	    driver.findElement(By.name("url")).clear();
	    driver.findElement(By.name("url")).sendKeys("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/avatar-2-1583234102.jpg");
	    driver.findElement(By.name("synopsis")).click();
	    driver.findElement(By.name("synopsis")).clear();
	    driver.findElement(By.name("synopsis")).sendKeys("sinopsis");
	    driver.findElement(By.id("Save")).click();
	    driver.findElement(By.id("all-films")).click();
	    assertTrue(driver.findElement(By.linkText("avatar")).isEnabled());
	    driver.findElement(By.linkText("avatar")).click();
	   	
	}
	

	@After(value = "")
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
