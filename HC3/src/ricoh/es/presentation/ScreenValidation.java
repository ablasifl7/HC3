package ricoh.es.presentation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ricoh.es.presentation.utils.Utils;

public class ScreenValidation extends Utils {
	
	private String url,browser;
	private String user,password;
	private String menu,submenu1,submenu2,submenu3,submenu4;

	@DataProvider(name = "dp")
	public Object[][] createData(ITestContext  context) {
		 String date_ = context.getCurrentXmlTest().getParameter("date");
		 String hours = context.getCurrentXmlTest().getParameter("hour");
		 String minutes = context.getCurrentXmlTest().getParameter("minute");
		 String[] date = date_.split(",",-1);
		 String[] hour = hours.split(",",-1);
		 String[] minute = minutes.split(",",-1);
			Object[][] ob = new Object[date.length][3]; 
			for(int i=0;i<date.length;i++){
				ob[i][0] = date[i];
				ob[i][1] = hour[i];
				ob[i][2] = minute[i];
			}

		 this.browser = context.getCurrentXmlTest().getParameter("browser");
		 this.url = context.getCurrentXmlTest().getParameter("url");
		 
		 this.user = context.getCurrentXmlTest().getParameter("user");
		 this.password = context.getCurrentXmlTest().getParameter("password");
		 
		 this.menu = context.getCurrentXmlTest().getParameter("menu");
		 this.submenu1 = context.getCurrentXmlTest().getParameter("submenu1");
		 this.submenu2 = context.getCurrentXmlTest().getParameter("submenu2");
		 this.submenu3 = context.getCurrentXmlTest().getParameter("submenu3");
		 this.submenu4 = context.getCurrentXmlTest().getParameter("submenu4");
	
		return ob;
	}
	
  @Test(dataProvider = "dp",groups = { "Validación pantalla" })
	public void testCase1(String date, String hour, String minute) {
		WebDriver driver = openBrowser(this.browser);
		try {
	
			WebDriverWait wait = setUp(driver,this.url);
			// Step 1:
			Utils.addTextToField(wait, By.name(NAME_UESR), this.user);
			Utils.addTextToField(wait, By.name(NAME_PASS), this.password);
			Utils.clickElement(wait,By.id(ID_ACCESS));
		
			Assert.assertTrue(Utils.elementExist(wait, By.linkText(this.menu)),ERROR_MENU_MSG);
			// Step 2:	
		    Utils.clickElement(wait,By.linkText(this.menu));
		
		    Assert.assertTrue(
		    		Utils.elementExist(wait,By.linkText(this.submenu1)) &&
		    		Utils.elementExist(wait,By.linkText(this.submenu2)) &&
		    		Utils.elementExist(wait,By.linkText(this.submenu3)) &&
		    		Utils.elementExist(wait,By.linkText(this.submenu4))
		    		, ERROR_SUBMENU_MSG);
		  
			// Step 3:	

		    Utils.clickElement(wait,By.linkText(this.submenu1));


		    
		    Assert.assertTrue(Utils
		    		.elementExist(wait,By.id("frmFrame")),ERROR_SCREEN_MSG.replace("#", this.submenu1));
			// Step 3:
	
			driver.get(wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("frmFrame"))).getAttribute("src"));
			// Step 4:
		    Assert.assertTrue(
		    		Utils.elementExist(wait,By.id("dataFrom")) &&
		    		Utils.elementExist(wait,By.id("horaFrom")),
		    		"Filtes 'Data' and 'Hora' is not displayed");
		    
		    Utils.clickElement(wait,By.id("dataFrom"));
		    Utils.addTextToField(wait, By.name("dataFrom"), "07/06/2018");
		    Utils.clickElement(wait,By.id("horaFrom"));
		    Utils.addTextToField(wait, By.name("horaFrom"), "01");
		    Utils.clickElement(wait,By.id("minFrom"));
		    Utils.addTextToField(wait, By.name("minFrom"), "01");
		    
		    Utils.clickElement(wait,By.id("ibtFiltra"));
		    
		    // //*[@id="control1"]/div/div/div
		    // /html/body/table/tbody/tr[2]/td/div[1]/div/div/div
		    
			WebElement eAllServices = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("/html/body/table/tbody/tr[2]/td/div[1]/div/div/div")));

			System.out.println(eAllServices.isEnabled());
			
		    Utils.clickElement(wait,By.id("dataFrom"));
		    Utils.addTextToField(wait, By.name("dataFrom"), "07/06/2018");
		    Utils.clickElement(wait,By.id("horaFrom"));
		    Utils.addTextToField(wait, By.name("horaFrom"), "12");
		    Utils.clickElement(wait,By.id("minFrom"));
		    Utils.addTextToField(wait, By.name("minFrom"), "");
		    
		    Utils.clickElement(wait,By.id("ibtFiltra"));
		    
			eAllServices = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("/html/body/table/tbody/tr[2]/td/div[1]/div/div/div")));

			System.out.println(eAllServices.isEnabled());
			
			driver.navigate().back();
			
		
			
			waitingTime(5000);



		} finally {
			closeBrowser(driver, false);
		}
  }


}
