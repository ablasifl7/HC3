package ricoh.es.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;
import ricoh.es.domain.utils.Constants;

public class HostCheck extends Constants {
	public static void file(WebDriverWait wait) {
		
		boolean hostFile = ricoh.es.presentation.utils.Utils.elementExist(wait, By
				.xpath(XPATH_HC3_GOB));


		Assert.assertEquals("Could not access the web. Check host file on '"+Constants.HOST+
				"' path",true, hostFile);
	}
	
	public static void file(WebDriver driver) {

		Assert.assertEquals("Could not access the web. Check host file on '"+Constants.HOST+
				"' path",true, driver.findElement(By.xpath(XPATH_HC3_GOB)).isDisplayed());
	}
	
	

}
