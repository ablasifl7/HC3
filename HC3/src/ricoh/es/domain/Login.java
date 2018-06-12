package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
	public static void submit(WebDriverWait wait,String user,String password) {
		WebElement element;

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtUsuari")));
		element.clear();
		element.sendKeys(user);
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPassword")));
		element.clear();
		element.sendKeys(password);
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ibtnEnviar")));
		element.click();
	}
	
	

}
