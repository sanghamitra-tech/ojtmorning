package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CommonFunLibrary.ERP_Functions;
import Utilities.ExcelFileUtil;

public class DataDrivenFramework {
	WebDriver driver;
	//creating reference object for function library
	ERP_Functions erp=new ERP_Functions();
	@BeforeTest
	public void lunchapp()
	{
		String app=erp.lunchurl("http://webapp.qedge.com");
		System.out.println(app);
		//calling login
		String login=erp.verifyLogin("admin","master");
		System.out.println(login);	
	}
	@Test
	public void suppliercreation() throws Throwable{
		//to call excel util method
		ExcelFileUtil xl=new ExcelFileUtil();
		//count no.of rows in a sheet
		int rc=xl.rowCount("Supplier");
		//count no. of columns
		int cc=xl.colCount("Supplier");
		Reporter.log("no of rows are::"+rc+""+"no of columns are ::"+cc,true);
		for(int i=1;i<=rc;i++)
		{
			String sname=xl.getData("Supplier",i, 0);
			String address=xl.getData("Supplier",i, 1);
			String city=xl.getData("Supplier",i, 2);
			String country=xl.getData("Supplier",i, 3);
			String cperson=xl.getData("Supplier",i, 4);
			String pnumber=xl.getData("Supplier",i, 5);
			String mail=xl.getData("Supplier",i, 6);
			String mnumber=xl.getData("Supplier",i, 7);
			String note=xl.getData("Supplier",i, 8);
			String result=erp.verifysupplier(sname, address, city, country, cperson, pnumber, mail, mnumber, note);
			xl.setCellData("Supplier", i, 9,result);
			
		}
	}
	@AfterTest
	public void logout() throws Throwable
	{
		String logouapp=erp.verifyLogout();
		System.out.println(logouapp);
	}
	
	
	

}
