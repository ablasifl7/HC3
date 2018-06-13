package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Filter {
	public static ricoh.es.presentation.utils.Check setAndCheck(WebDriverWait wait, String date, String hour,
			String minute, boolean click) {
		WebElement element;

		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dataFrom")));
			element.clear();
			element.sendKeys(date);

			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("horaFrom")));
			element.clear();
			element.sendKeys(hour);

			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("minFrom")));
			element.clear();
			element.sendKeys(minute);

			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ibtFiltra")));
			element.click();
			if (click) {
				element.click();
			}
		} catch (Exception e) {

			return new ricoh.es.presentation.utils.Check(null, true, e.getMessage());
		}
		return new ricoh.es.presentation.utils.Check(element, false, null);
	}

}
