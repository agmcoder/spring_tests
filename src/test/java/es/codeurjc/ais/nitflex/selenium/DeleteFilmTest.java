package es.codeurjc.ais.nitflex.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.annotation.After;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.web.WebAppConfiguration;

import es.codeurjc.ais.nitflex.Application;
import io.github.bonigarcia.wdm.WebDriverManager;

//parar correr el test es necesario levantar el servidor de la aplicación.
//@WebAppConfiguration
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
public class DeleteFilmTest {
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
    public void deleteFilmTest() throws Exception {
        driver.get("http://localhost:8080/");
        driver.findElement(By.id("create-film")).click();
        driver.findElement(By.name("title")).click();
        driver.findElement(By.name("title")).clear();
        driver.findElement(By.name("title")).sendKeys("jumanji");
        driver.findElement(By.name("year")).clear();
        driver.findElement(By.name("year")).sendKeys("1991");
        driver.findElement(By.name("url")).click();
        driver.findElement(By.name("url")).click();
        driver.findElement(By.name("url")).clear();
        driver.findElement(By.name("url")).sendKeys("https://www.themoviedb.org/t/p/w1280/qHInRuEE3Fqrhy3XqKyq0x1oo1N.jpg");
        driver.findElement(By.name("synopsis")).click();
        driver.findElement(By.name("synopsis")).clear();
        driver.findElement(By.name("synopsis")).sendKeys("juego apocaliptico");
        driver.findElement(By.id("Save")).click();
        driver.findElement(By.id("remove-film")).click();
        assertTrue(driver.findElement(By.id("message")).isDisplayed());
        //assertThat(,driver.findElement(By.id("message")));
        driver.findElement(By.id("all-films")).click();
        try {
            driver.findElement(By.partialLinkText("jumanji")).isEnabled();
            fail("no deberia haber clicado nada");
        } catch (NoSuchElementException e) {
            Logger loger = Logger.getLogger("myLogger");
            loger.error("no ha encontrado ningun link de jumanji ---->" + e.getMessage());
        }
    }
    @AfterEach
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
