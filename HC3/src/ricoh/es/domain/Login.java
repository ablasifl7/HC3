package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
	public static void submit(WebDriverWait wait,String email,String password) {
		WebElement element;

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		
		element.clear();

		element.sendKeys(email);

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-submit")));

		element.click();
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		
		element.clear();
		
		element.sendKeys(password);
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-submit")));
		
		element.submit();
		
		

	
	}
	
	

}
