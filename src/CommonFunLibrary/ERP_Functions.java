
package CommonFunLibrary;

import java.awt.RenderingHints.Key;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ERP_Functions {
	WebDriver driver;
	//method for lunching url
	public String lunchurl(String url)
	{
	String res="";
	System.setProperty("webdriver.chrome.driver", "CommonJars/chromedriver.exe");
	driver=new ChromeDriver();
	driver.get(url);
	//verify login button is displayed
	if(driver.findElement(By.name("btnsubmit")).isDisplayed())
	{
		res="Application lunch success";
	}
	else
	{
		res="Application lunch fail";
	}
		return res;	
	}
	
	public String verifyLogin(String username,String password )
	{
		String res="";
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys(username);;
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);;
		driver.findElement(By.name("btnsubmit")).click();
		if(driver.findElement(By.id("mi_logout")).isDisplayed())
		{
			res="Login Success";
		}
		else
		{
			res="login fail";
		}
           return res;
	}
	//method for logout 
	public String verifyLogout() throws Throwable
	{
		String res;
		driver.findElement(By.id("mi_logout")).click();
		Thread.sleep(4000);
		if(driver.findElement(By.name("btnsubmit")).isDisplayed())
		{
			res="Application logout success";
		}
		else
		{
			res="Application logout fail";
		}
			return res;	
	}

	//verify supplier creation 
	public String verifysupplier(String sname,String address,String city,
			String country,String cperson,String pnumber,String email,String mnumber,
			String notes)throws Throwable
		{
			String res="";
		driver.findElement(By.linkText("Suppliers")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@class='panel-heading ewGridUpperPanel']//span[@class='glyphicon glyphicon-plus ewIcon']")).click();
		Thread.sleep(3000);
		//get siupplier number
		String Exp_data=driver.findElement(By.name("x_Supplier_Number")).getAttribute("value");
		driver.findElement(By.name("x_Supplier_Name")).sendKeys(sname);
		driver.findElement(By.name("x_Address")).sendKeys(address);
		driver.findElement(By.name("x_City")).sendKeys(city);
		driver.findElement(By.name("x_Country")).sendKeys(country);
		driver.findElement(By.name("x_Contact_Person")).sendKeys(cperson);
		driver.findElement(By.name("x_Phone_Number")).sendKeys(pnumber);
		driver.findElement(By.name("x__Email")).sendKeys(email);
		driver.findElement(By.name("x_Mobile_Number")).sendKeys(mnumber);
		driver.findElement(By.name("x_Notes")).sendKeys(notes);
		driver.findElement(By.name("btnAction")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'OK!')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@class='ajs-button btn btn-primary']")).click();
		Thread.sleep(2000);
		if(driver.findElement(By.xpath("//input[@id='psearch']")).isDisplayed())
		{
		driver.findElement(By.xpath("//input[@id='psearch']")).clear();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='psearch']")).sendKeys(Exp_data);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@id='btnsubmit']")).click();
		Thread.sleep(2000);
		}
		else{
		driver.findElement(By.xpath("//span[@class='glyphicon glyphicon-search ewIcon']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='psearch']")).clear();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='psearch']")).sendKeys(Exp_data);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@id='btnsubmit']")).click();
		Thread.sleep(2000);
		}
		//get supplier number from table
		String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Thread.sleep(4000);
		System.out.println(Exp_data+" "+Act_data);
		if(Exp_data.equals(Act_data))
		{
		res="Supplier creation is success";
		}
		else{
		res="Supplier creation is Fail";
		}
		return res;
		}
}

