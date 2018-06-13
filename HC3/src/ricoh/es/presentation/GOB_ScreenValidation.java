package ricoh.es.presentation;



import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ricoh.es.presentation.utils.*;

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
	/*
	String time;
	String suitcase;
	String testcase;
	String step;
	String result;
	String errorMsg;
	String screenshot;
	*/
	private ricoh.es.presentation.utils.Check check;
	
	private Excel excel;
	private String[] steps;

	int nStep = 0;
	
	WebDriver driver;
	
  @Test(dataProvider = "dp",groups = { "Validación pantalla","GOB" })
	public void screenValidation(String date, String hour, String minute) {
	  
		steps = new String[]{
				"1. Open '"+this.browser+"' browser and go to '"+this.url+"' url",              
				"2. Login using user='"+this.user+"' and password='"+this.password+"'",         
				"3. Menu '"+this.menu+"' is clicked. Submenu '"+this.submenu1+"' is displayed", 
				"4. Menu '"+this.menu+"' is clicked. Submenu '"+this.submenu2+"' is displayed", 
				"5. Menu '"+this.menu+"' is clicked. Submenu '"+this.submenu3+"' is displayed", 
				"6. Menu '"+this.menu+"' is clicked. Submenu '"+this.submenu4+"' is displayed", 
				"7. Click '"+this.submenu1+"', screen is correctly displayed",                  
				"8. Filtes 'Data' and 'Hora' are displayed",	                                
				"9. TextFields 'Data' and 'Hora' are enable",                                   
				"10. Checklist from results is disabled until a filter is done",	            
				"11. Click filter option. The values from filter are shown in the graph screen"};
		
		Utils.deleteScreenshot(TESTNAME);
	  
		initializeExcel();
	  
		driver = openBrowser(this.browser);
		
		try {
	
			WebDriverWait wait = setUp(driver,this.url);

			// Step 1:
			/**
			 * 1. Open '#browser#' browser and go to '#url#' url
			 */
			check = ricoh.es.domain.Login.submit(wait, this.user, this.password,false);
			excelReport("Start suitcase screenValidation",
					"Start testcase screenValidation",check.isError(),nStep++,true,check.getMsgError());
			Assert.assertFalse(check.isError(), "Browser is not correctly opened");
			
			check.getElement().click();
			
			/**
			 * 2. Login using user='#user#' and password='#password#'
			 */
			check = ricoh.es.domain.Select.element(wait, By.linkText(this.menu), false);
			excelReport(null,null,check.isError(),nStep++,true,check.getMsgError());
			Assert.assertFalse(check.isError(), "Login is not possible");

			// Step 2:	
			check.getElement().click();
		
			/**
			 * 3-6. Menu '#menu#' is clicked. Submenu '#submenu#' is displayed 
			 */
			String[] submenus = new String[]{this.submenu1,this.submenu2,this.submenu3,this.submenu4};
			for (String submenu : submenus) {
				check = ricoh.es.domain.Select.element(wait, By.linkText(submenu), false);
	
				excelReport(null,null,check.isError(),nStep++,true,check.getMsgError());
				Assert.assertFalse(check.isError(), "Submenu '"+submenu+"' is not shown");
			}
	
			// Step 3:	
			/**
			 * 7. Click '#submenu1#', screen is correctly displayed 
			 */
			check = ricoh.es.domain.Select.element(wait, By.linkText(this.submenu1), true);
			excelReport(null,null,check.isError(),nStep++,true,check.getMsgError());
			Assert.assertFalse(check.isError(), "Screen from submenu '"+this.submenu1+"' is not displayed");
	
			/********************************************************************************
			 * A webview is displayed into another webview. It goes to the internal webview */
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("frmFrame")));
			driver.get(element.getAttribute("src"));
			/********************************************************************************/
	
			// Step 4:
			/**
			 * 8. Filtes 'Data' and 'Hora' are displayed
			 */
			check = ricoh.es.domain.Filter.setAndCheck(wait, date, hour, minute,false);
			excelReport(null,null,check.isError(),nStep++,true,check.getMsgError());
			Assert.assertFalse(check.isError(), "Filtes 'Data' and 'Hora' aren't displayed");
	
			/**
			 * 9. TextFields 'Data' and 'Hora' are enable
			 */
			boolean areDisabled = !(ricoh.es.domain.Select.element(wait, By.id("dataFrom"), false).getElement().isEnabled()
					&& ricoh.es.domain.Select.element(wait, By.id("horaFrom"), false).getElement().isEnabled());
			excelReport(null,null,areDisabled,nStep++,true,"TextFields 'Data' or/and 'Hora' aren't enable");
			Assert.assertFalse(check.isError(), "TextFields 'Data' or/and 'Hora' aren't enable");

			check = ricoh.es.domain.Select.element(wait,By.xpath("//*[@id=\"control1\"]/div/div/div"), false);

			/**
			 * 10. Checklist from results is disabled until a filter is done
			 */
			boolean isEnable = !(Boolean.parseBoolean(check.getElement().getAttribute("aria-disabled")));
			excelReport(null,null,isEnable,nStep++,true,"Result checklist isnt't disable");
			Assert.assertFalse(check.isError(), "Result checklist isnt't disable");
			
			// Step 5:
	
			/**
			 * 11. Click filter option. The values from filter are shown in a graph screen
			 */
			check = ricoh.es.domain.Select.element(wait,By.id("ibtFiltra"), true);
			excelReport(null,null,false,nStep++,false,null);
			

		} finally {
			closeBrowser(driver, false);
			
			Utils.openDirectory(PATH_DESTIN);
		}
  }
  public void initializeExcel() {
		excel = new Excel(PATH_ORIGIN, PATH_DESTIN, SHEET);
		
		String[] results = {OK,FAIL,BLOKED,SEE_IMAGE};
		int[] color = {	IndexedColors.GREEN.index,
						IndexedColors.RED.index,
						IndexedColors.VIOLET.index,
						IndexedColors.ORANGE.index};
		excel.setColorToCell(results, color, "E2:E200", SHEET);
		excel.setColorToCell(new String[] {""}, new int[] {IndexedColors.LIGHT_YELLOW.index}, "B2:C200", SHEET,false);

  }

  public void excelReport(String suitcase,String testcase,boolean error,int nStep,boolean ckeckError,String errorMsg) {
	  
		String step;
		String result;
		String screenshot;

		if(suitcase==null) {
			suitcase = "";
		} 
		if(testcase==null) {
			testcase = "";
		} 
		step = steps[nStep++];//9. Checklist from results is disabled until a filter is done
		
		if(!ckeckError) {
			result = SEE_IMAGE;
			errorMsg = "";
			screenshot = Utils.createScreenshot(driver,TESTNAME,"image"+nStep);
		}else if(error) {
			result = FAIL;
			screenshot = Utils.createScreenshot(driver,TESTNAME,"image"+nStep+"_fail");
		}else {
			result = OK;
			errorMsg = "";
			screenshot = Utils.createScreenshot(driver,TESTNAME,"image"+nStep+"_ok");
		}
		
	
		excel.writeCol(++row, 0, 
				new Object[]{Utils.currentTime("yyyy-MM-dd HH:mm:ss.SSS"),
						suitcase,testcase,step,result,errorMsg,screenshot}, SHEET);
		
		if(error && ckeckError) {
			for(int i = (row+1);i<steps.length;i++) {
				excel.writeCol(++row, 0, 
						new Object[]{Utils.currentTime("yyyy-MM-dd HH:mm:ss.SSS"),
								null,null,steps[i],BLOKED,null,""}, SHEET);
			}
		}
  }


}
