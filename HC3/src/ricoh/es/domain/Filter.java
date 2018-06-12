package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Filter {
	public static void setAndCheck(WebDriverWait wait,String date,String hour,String minute) {
		WebElement element;
		
	    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dataFrom")));
		Assert.assertTrue(element.isEnabled());
		element.clear();
		element.sendKeys(date);
		
	    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("horaFrom")));
		Assert.assertTrue(element.isEnabled());
		element.clear();
		element.sendKeys(hour);
		
	    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("minFrom")));
		Assert.assertTrue(element.isEnabled());
		element.clear();
		element.sendKeys(minute);
	}

}
