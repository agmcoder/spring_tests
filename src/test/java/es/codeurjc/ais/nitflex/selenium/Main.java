package es.codeurjc.ais.nitflex.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

	public static void main(String[] args) {
		//l0e -n Ciounfmigu rWar eel bdrDiverri vdeel rn:av Jegaadvora
		System.setProperty("webdriver.chrome.driver",
		"drivers/chromedriver.exe");
		//1 - Abrir el navegador web
		WebDriver driver = new ChromeDriver();
		//2 - Abrir una página web
		driver.get("https://wikipedia.org");
		//3 - Localizar elementos en la página
		WebElement searchInput = driver.findElement(By.name("search"));
		//4 - Interactuar con los elementos
		searchInput.sendKeys("Rick Astley");
		searchInput.submit();
		//5 - Esperar a que ciertos elementos estén disponibles
		WebDriverWait wait = new WebDriverWait(driver, 60);
		
		//7 - Cerrar el navegador
		driver.quit();

	}

}
