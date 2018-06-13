package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Select {
	public static ricoh.es.presentation.utils.Check element(WebDriverWait wait,By by,boolean click){
		WebElement element;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if(click) {
				element.click();
			}
		} catch (Exception e) {
			
			return new ricoh.es.presentation.utils.Check(null, true, e.getMessage());
		}
		return new ricoh.es.presentation.utils.Check(element, false, null);
		
	}

}
