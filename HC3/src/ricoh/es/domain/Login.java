package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
	public static ricoh.es.presentation.utils.Check submit(WebDriverWait wait,String user,String password,boolean click) {
		WebElement element;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtUsuari")));
			element.clear();
			element.sendKeys(user);
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPassword")));
			element.clear();
			element.sendKeys(password);
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ibtnEnviar")));
			if(click) {
				element.click();
			}
		} catch (Exception e) {
			
			return new ricoh.es.presentation.utils.Check(null, true, e.getMessage());
		}
		return new ricoh.es.presentation.utils.Check(element, false, null);
	}
	
	

}
