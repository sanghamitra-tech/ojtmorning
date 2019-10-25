package DriverFactory;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;
import Utilities.ScreenShot;

public class DriverScript {
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;
	public void startTest() throws Throwable
	{
		//creating reference object for excel utils methods 
		ExcelFileUtil excel= new ExcelFileUtil();
		// iterate all row in masterTestCases sheet
		
		for(int i=1;i<=excel.rowCount("MasterTestCases");i++)
		{
			String ModuleStatus="";
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				//store module name to TCModule
				String TCModule =excel.getData("MasterTestCases", i, 1);
				report=new ExtentReports("./Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
				//iterate all rows in TCModule
				for(int j=1;j<=excel.rowCount(TCModule);j++)
				{
					test=report.startTest(TCModule);
					//read al the columns from TCMocule
					String Description=excel.getData(TCModule, j, 0);
					String Object_Type=excel.getData(TCModule, j, 1);
					String Locator_Type=excel.getData(TCModule, j, 2);
					String Locator_Value=excel.getData(TCModule, j, 3);
					String Test_Data=excel.getData(TCModule, j, 4);
					
					
					//System.out.println(Description+" "+Object_Type);
					
					
					
					//calling the method from function library
					try{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							System.out.println("Executing startBroswer");
							driver=FunctionLibrary.startBroswer(driver);
							System.out.println("Executing startBroswer");
						}else if (Object_Type.equalsIgnoreCase("openApplication"))
						{
							FunctionLibrary.openApplication(driver);
							test.log(LogStatus.INFO, Description);
							System.out.println("Executing openApplication");
						}else if (Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
							System.out.println("Executing waitForElement");
						}else if (Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
							System.out.println("Executing typeAction");
						}else if (Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
							System.out.println("Executing clickAction");
						}else if (Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
							System.out.println("Executing closeBrowser");
						}
						else if(Object_Type.equalsIgnoreCase("captureData"))
						{
							FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
						test.log(LogStatus.INFO,Description);
						}else if (Object_Type.equalsIgnoreCase("captureData"))
						{
							FunctionLibrary.tableValidation(driver, Test_Data);
						test.log(LogStatus.INFO,Description);
						}
						else if (Object_Type.equalsIgnoreCase("stockCategories"))
						{
							FunctionLibrary.stockCategories(driver);
						test.log(LogStatus.INFO,Description);
						}
						else if (Object_Type.equalsIgnoreCase("tableValidation1"))
						{
							FunctionLibrary.tableValidation1(driver, Test_Data);
						test.log(LogStatus.INFO,Description);
						}
						
						//write as pass into status column
						excel.setCellData(TCModule, j, 5, "PASS");
						test.log(LogStatus.PASS, Description);
						ScreenShot.Takescreen(driver,Description);
						ModuleStatus="true";
						
					}catch(Exception e)
					{
						excel.setCellData(TCModule, j, 5, "FAIL");
						test.log(LogStatus.FAIL, Description);
						ScreenShot.Takescreen(driver,Description);
						ModuleStatus="false";
						System.out.println(e.getMessage());
						break;
					}
					if(ModuleStatus.equalsIgnoreCase("TRUE"))
					{
						excel.setCellData("MasterTestCases", i, 3, "pass");
					}else if (ModuleStatus.equalsIgnoreCase("FALSE"))
					{
						excel.setCellData("MasterTestCases", i, 3, "Fail");
					}
					report.flush();//push reports to html
					report.endTest(test);
				}
				
			}
			else
			{
				//write as not executed in master testcases in status coulmn for flag N
				excel.setCellData("MasterTestCases", i,3, "Not Executed");
				
			}
		}
	}

}
