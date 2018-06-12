package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Menu {
	public static WebElement checkMenu(WebDriverWait wait,String menu) {	
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(menu)));
		Assert.assertTrue(element.isDisplayed());
		return element;
	}
	
	public static void checkSubmenu(WebDriverWait wait,String[] submenus) {
		for(String submenu:submenus) {
			WebElement element;
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(submenu)));
			Assert.assertTrue(element.isDisplayed(),"Submenu "+submenu+" is not displayed");
		}
	}

}
