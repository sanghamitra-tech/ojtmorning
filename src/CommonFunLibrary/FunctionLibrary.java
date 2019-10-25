package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
	public static WebDriver driver;
	//method for openbrowser
	public static WebDriver startBroswer(WebDriver driver) throws Throwable{
		if(PropertyFileUtil.getValueForkey("Browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D:\\mrng930batch\\ERP_Stock\\CommonJars\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		else if (PropertyFileUtil.getValueForkey("Browser").equalsIgnoreCase("firefox")) {
			
		}else if (PropertyFileUtil.getValueForkey("Browser").equalsIgnoreCase("ie")) {
			
		}else{
			System.out.println("no browser is matching");
		}
		return driver;
	}
	//lunching url
	public static void openApplication(WebDriver driver)throws Throwable{
		driver.get(PropertyFileUtil.getValueForkey("url"));
		driver.manage().window().maximize();
	}
	//method for waitforElement
	public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String waittime)
	{
		WebDriverWait mywait=new WebDriverWait(driver,Integer.parseInt(waittime));
		if(locatortype.equalsIgnoreCase("id"))
				
				{
					mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
				}
				else if(locatortype.equalsIgnoreCase("name"))
				{
					mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
			
				}
				else if(locatortype.equalsIgnoreCase("xpath"))
				{
					mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
				}
				else if(locatortype.equalsIgnoreCase("css"))
				{
					mywait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorvalue)));
				}
		else {
			System.out.println("unable to wait for element");
		}		
	}
	//method for typeActions
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata){
		if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
			
		}
		else if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
		}
		else if ( locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
		}
		else
		{
			System.out.println("unable to execute type action method");
		}
	}
	//method for click action
	public static void clickAction(WebDriver driver,String locatortype,String locatorvalue){
		if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
		}
		else if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).click();
		}else if (locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}else if(locatortype.equalsIgnoreCase("css"))
		{
			driver.findElement(By.cssSelector(locatorvalue)).click();	
		}
		
		else{
			System.out.println("unable to execute click action method");
		}
	}
//method for close browser
	public static void closeBrowser( WebDriver driver)
	{
    driver.close();		
	}
	//method for date generate
	public static String generateDate()

	{
		Date date= new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_DD_SS");
		return sdf.format(date);
	}
	//capture data into notepad
	public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable{
		String supplierdata="";
		if(locatortype.equalsIgnoreCase("id")){
			supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		else if (locatortype.equalsIgnoreCase("name")){
			supplierdata=driver.findElement(By.name(locatorvalue)).getAttribute("value");	
		}else if  (locatortype.equalsIgnoreCase("xpath")){
			supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");	
		}
		FileWriter fr=new FileWriter("D:\\mrng930batch\\ERP_Stock\\CaptureData\\supplier.txt");
		BufferedWriter bw=new BufferedWriter(fr);
		bw.write(supplierdata);
		bw.flush();//for pushing code
		bw.close();
	}
	//table validation or supplier creation
	public static void tableValidation(WebDriver driver,String column) throws Throwable{
		FileReader fr=new FileReader("D:\\mrng930batch\\ERP_Stock\\CaptureData\\supplier.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		//convert column into integer
		int column1=Integer.parseInt(column);
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box"))).isDisplayed()){
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("click-search"))).click();
			Thread.sleep(5000);
		}
		else{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("Click-searchpanel"))).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("click-search"))).click();
			Thread.sleep(5000);
		}
		
		//table validation
		WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("sup-table")));
		List<WebElement> rows=table.findElements((By.tagName("tr")));
		System.out.println("no of rows are::"+rows.size());
		for(int i=1;i<=rows.size();i++){
			String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column1+"]/div/span/span")).getText();
			Thread.sleep(5000);
			Assert.assertEquals(Exp_data, Act_data,"supplier number is not matching");
			System.out.println(Exp_data+" "+Act_data);
			break;
			}
		
		
	}
	//mouse click for stoc items
	public static void stockCategories(WebDriver driver) throws Throwable
	{
		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_items']//a[contains(text(),'Stock Items')]"))).perform();
		Thread.sleep(2000);
		ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_categories']//a[contains(text(),'Stock Categories')]"))).click().perform();
		Thread.sleep(3000);
	}
	//table validation for stock items
	public static void tableValidation1(WebDriver driver,String Exp_data) throws Throwable
	{
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box1"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box1"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box1"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("click-search1"))).click();
		}
		else{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("Click-searchpanel1"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box1"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search-box1"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("click-search1"))).click();
		}
		WebElement table= driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("cat-table")));
		List<WebElement> rows=table.findElements((By.tagName("tr")));
		System.out.println("no of rows are::"+rows.size());
		for(int i=1;i<rows.size();i++){
			//get table text in acolumn
			String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
			Assert.assertEquals(Exp_data,Act_data,"Data is not matching");
			System.out.println(Exp_data+"  "+Act_data);
			break;	
		}
	}
}
