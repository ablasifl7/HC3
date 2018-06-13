package ricoh.es.presentation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils extends Constants{
	public static WebDriverWait setUp(WebDriver driver,String url) {
		driver.get(url);
		driver.manage().window().maximize();
		//ricoh.es.domain.HostCheck.file(driver);
		return new WebDriverWait(driver, TimeOut);
	}
	
	public static WebDriver openBrowser(String brower) {
		 WebDriver driver;
	
		 switch(brower) {
		 	case "firefox":
		 		driver = new FirefoxDriver();
		 		break;
		 	case "chrome":
		 		driver = new ChromeDriver();
		 		break;
		 	case "ie":
		 		driver = new InternetExplorerDriver();
		 		break;
		 	default:
		 		throw new IllegalArgumentException("Invalid browser name: " + brower);
		 }
		 
		 return driver;
	}
	public static void closeBrowser(WebDriver driver,boolean quit) {
		if(quit) {
			driver.quit();
		}else {
			driver.close();
		}
	}

	
	
	public static void waitingTime(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String  currentTime() {
		Calendar cal = null;
		SimpleDateFormat sdf = null;
		cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
	}
	
	public static String  currentTime(String format) {
		//example: 'yyyy-MM-dd HH:mm:ss.SSS'
		Calendar cal = null;
		SimpleDateFormat sdf = null;
		cal = Calendar.getInstance();
        sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
	}
	
	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public static String closeAlertAndGetItsText(WebDriver driver, boolean acceptNextAlert) {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
	public static void deleteScreenshot(String testName) {
	    String imagesLocation;

        imagesLocation = (new File("")).getAbsolutePath().concat("\\screenShot\\");
        
        File f = new File(imagesLocation +  testName);
        File[] fList = f.listFiles();
        for(File fl:fList) {
        	fl.delete();
        }
        
	}
	
	
	public static String createScreenshot(WebDriver driver,String testName,String imageName) {

		waitingTime(500);
		
	    String imagesLocation;
	    String filename;

        imagesLocation = (new File("")).getAbsolutePath().concat("\\screenShot\\");
        filename = imagesLocation +  testName +"\\"+imageName + ".jpg";

        File newFile = new File(imagesLocation);
        newFile.mkdirs(); // Insure directory is there
        
        try {
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(filename).getAbsoluteFile(), true);
			return "file:///"+filename;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
        return null;

	}
    public static void openDirectory(String path) {
        try {
            java.awt.Desktop.getDesktop().open(new java.io.File(path));
        } catch (java.io.IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static int row = 0;

}
