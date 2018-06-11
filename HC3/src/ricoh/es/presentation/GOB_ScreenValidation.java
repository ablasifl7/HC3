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

public class GOB_ScreenValidation extends Utils {
	
	private String url,browser;
	private String user,password;
	private String menu,submenu1,submenu2,submenu3,submenu4;
	
	final private String TESTNAME = "ScreenValidation";
	
	private WebElement element;
	

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
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtUsuari")));
			element.clear();
			element.sendKeys(this.user);
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPassword")));
			element.clear();
			element.sendKeys(this.password);
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ibtnEnviar")));
			element.click();
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(this.menu)));
			
			Assert.assertTrue(element.isDisplayed());
			
			// Step 2:	
			element.click();

			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(this.submenu1)));
			Assert.assertTrue(element.isDisplayed());
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(this.submenu2)));
			Assert.assertTrue(element.isDisplayed());
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(this.submenu3)));
			Assert.assertTrue(element.isDisplayed());
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(this.submenu4)));
			Assert.assertTrue(element.isDisplayed());
	

		  
			// Step 3:	
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(this.submenu1)));
			element.click();
			
			Utils.createScreenshot(driver,TESTNAME,this.submenu1);
			
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("frmFrame")));
			Assert.assertTrue(element.isDisplayed());
			driver.get(element.getAttribute("src"));

			// Step 4:
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
			
			String testName = "graphyc_before_"+"aria-disabled="+element.getAttribute("aria-disabled")+"_"+date.replace("/", "")+"_"+hour;
			Utils.createScreenshot(driver,TESTNAME,testName);
			
			// Step 5:
		    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ibtFiltra")));
		    element.click();

			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"control1\"]/div/div/div")));

			
			testName = "graphyc_after_"+"aria-disabled="+element.getAttribute("aria-disabled")+"_"+date.replace("/", "")+"_"+hour;
			Utils.createScreenshot(driver,TESTNAME,testName);
	

		} finally {
			closeBrowser(driver, false);
		}
  }


}
