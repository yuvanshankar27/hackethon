package TestCases;

import java.io.IOException;
import java.text.ParseException;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import Pages.CabSearch;
import Pages.FlightSearch;
import Pages.GiftCards;
import Pages.HomePage;
import Pages.HotelBooking;
import Pages.LoginPage;
import Setup.BaseClass;
import Setup.ExportToExcel;
import Setup.TestData;

public class InvalidTestCases extends BaseClass {
	WebDriver driver;
	LoginPage objLogin;
	HomePage objHomePage;
	FlightSearch objFlightSearch;
	CabSearch objCabSearch;
	TestData data = new TestData();
	public Properties property;
	HotelBooking objHotelBooking;
	GiftCards objGiftCards;
	String message;

	// ** Method to get Driver Instance **
	@BeforeClass
	public void get_Driver_Instance() throws IOException {

		driver = BaseClass.invoke_Browser();

	}

	// ** Method to get Test Results **
			@AfterMethod
			public void get_Test_Case_Result(ITestResult result) throws Exception {
				ExportToExcel objExcelFile = new ExportToExcel();
				property = BaseClass.invoke_Property_File();
				if (result.getStatus() == ITestResult.FAILURE) {
					logger.log(Status.FAIL, MarkupHelper.createLabel(message + " - FAILED", ExtentColor.RED));
					logger.fail(result.getThrowable());
					String screenshotPath = BaseClass.get_ScreenShot_Of_Failed_TestCase(driver, result.getName());
					logger.fail("Failed Test Case Snapshot is attached below " + logger.addScreenCaptureFromPath(screenshotPath));

					objExcelFile.write_Into_Excel(property.getProperty("fileName"), property.getProperty("sheetName"),
							result.getName(), "Fail");
				} else if (result.getStatus() == ITestResult.SKIP) {
					logger.log(Status.SKIP, MarkupHelper.createLabel(message + " - SKIPPED", ExtentColor.ORANGE));
					logger.skip(result.getThrowable());
					objExcelFile.write_Into_Excel(property.getProperty("fileName"), property.getProperty("sheetName"),
							result.getName(), "Skip");
				} else if (result.getStatus() == ITestResult.SUCCESS) {
					logger.log(Status.PASS, MarkupHelper.createLabel(message + " - PASSED", ExtentColor.GREEN));
					objExcelFile.write_Into_Excel(property.getProperty("fileName"), property.getProperty("sheetName"),
							result.getName(), "Pass");
				}

			}

	// ** Test to verify Error Message of Invalid Username **
	 @Test(priority = 0)
	public void test_Invalid_Username_Details() throws IOException, InterruptedException {
		logger = extent.createTest("test_Invalid_Username_Details");
		logger.assignCategory("Regression Tests");
		objLogin = new LoginPage(driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		objHomePage = new HomePage(driver);
		objHomePage.click_SignIn_Button();
		property = BaseClass.invoke_Property_File();
		objLogin.set_UserName(data.get_Data("Login Page", "InvalidUserName"));
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Error Message of Invalid Usernmae", ExtentColor.CYAN));
		message = "Displaying Error Message of Invalid Username";
		Assert.assertEquals(driver.findElement(By.xpath(property.getProperty("userNameErrorMessage_xpath"))).getText(),
				property.getProperty("userNameErrorMessage"));// TC-14
		objLogin.clear_UserName_TxtBox();
		objLogin.set_UserName(data.get_Data("Login Page", "Username"));
		objLogin.click_Login_Via_Password();

	}

	// ** Test to verify Error Message of Invalid Password **
	 @Test(priority = 1)
	public void test_Invalid_Password_Details() throws IOException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		logger = extent.createTest("test_Invalid_Password_Details");
		logger.assignCategory("Regression Tests");
		objLogin.set_Password(data.get_Data("Login Page", "InvalidPassWord"));
		objLogin.click_LogIn_Button();
		property = BaseClass.invoke_Property_File();
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Error Message of Invalid Password", ExtentColor.CYAN));
		message = "Displaying Error Message of Invalid Password";
		Assert.assertEquals(driver.findElement(By.xpath(property.getProperty("passwordErrorMessage_xpath"))).getText(),
				property.getProperty("passwordErrorMessage"));// TC-15
		objLogin.clear_Password_TxtBox();
		objLogin.set_Password(data.get_Data("Login Page", "Password"));
		objLogin.click_LogIn_Button();
		objHomePage = new HomePage(driver);
		objHomePage.wait_For_Login();

	}

	// ** Test to verify Error Message of Same Location of Travel **
	@Test(priority = 2)
	public void test_Same_Location_Of_Journey() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_Same_Location_Of_Journey");
		logger.assignCategory("Regression Tests");
		property = BaseClass.invoke_Property_File();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		objHomePage = new HomePage(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		objHomePage.click_Cab_Icon();
		objCabSearch = new CabSearch(driver);
		js.executeScript("return document.readyState").toString().equals("complete");
		objCabSearch.set_FromCity_And_ToCity(data.get_Data("Cab Page", "InvalidFromPlace"),
				data.get_Data("Cab Page", "InvalidToPlace"));
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Error Message of Same From and To Location",
				ExtentColor.CYAN));
		message = "Displaying Error Message of Same Location";
		Assert.assertEquals(property.getProperty("samePlaceErrorMessage"),
				driver.findElement(By.xpath(property.getProperty("samePlaceErrorMessage_xpath"))).getText());// TC-16

	}

	// ** Test to verify Error Message for Invalid Date of Journey **
	@Test(priority = 3)
	public void test_Invalid_Date_Of_Journey() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_Invalid_Date_Of_Journey");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Invalid Date of Journey is Disabled", ExtentColor.CYAN));
		message = "Displaying Error Message of Invalid Date";
		Assert.assertTrue(driver.findElement(By.xpath(property.getProperty("invalidDate_xpath"))).isDisplayed());// TC-17
		objCabSearch.set_Date_Of_Departure(data.get_Data("Cab Page", "DateOfTravel"));

	}

	// ** Test to verify ReturnDate is Disabled **
	@Test(priority = 4)
	public void test_ReturnDate_Disabled() throws IOException, InterruptedException {
		logger = extent.createTest("test_ReturnDate_Disabled");
		logger.assignCategory("Regression Tests");
		objCabSearch = new CabSearch(driver);
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Return Date of Journey is Disabled", ExtentColor.CYAN));
		message = "Return Date Disabled";
		Assert.assertEquals(property.getProperty("returnDateErrorMessage"),
				driver.findElement(By.xpath(property.getProperty("returnDateErrorMessage_xpath"))).getText());// TC-18

	}

	// ** Test to verify OutstationTripType is Selected **
	@Test(priority = 5)
	public void test_OutstationTrip_Selected() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_OutstationTrip_Selected");
		logger.assignCategory("Regression Tests");
		objCabSearch = new CabSearch(driver);
		objCabSearch.set_Date_Of_Return(data.get_Data("Cab Page", "ReturnDate"));
		logger.log(Status.INFO, MarkupHelper.createLabel(
				"Test to Verify - OutstationTrip Radio Button is Selected if Return Date is Set", ExtentColor.CYAN));
		message = "OutstationTrip Type Selected";
		Assert.assertTrue(driver.findElement(By.xpath(property.getProperty("outStation_xpath"))).isDisplayed());

	}

	// ** Test to verify Error Message of Invalid EmailId **
	@Test(priority = 6)
	public void test_GiftCard() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_GiftCard");
		logger.assignCategory("Regression Tests");
		objHomePage = new HomePage(driver);
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Giftcard Icon is Enabled", ExtentColor.CYAN));
		message = "GiftCard Icon Enabled";
		Assert.assertTrue(objHomePage.click_GiftCard_Icon());
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Giftcard page title", ExtentColor.CYAN));
		message = "GiftCard Page Title verification";
		Assert.assertTrue(driver.getTitle().contains(data.get_Data("GiftCard Page", "GiftcardPageTitle")));
		objGiftCards = new GiftCards(driver);
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Get the Error Message if EmailId is invalid", ExtentColor.CYAN));
		message = "Displaying Error Message of Invalid EmailId";
		objGiftCards.set_GiftCard_Details(data.get_Data("GiftCard Page", "RecipientName"),
				data.get_Data("GiftCard Page", "GiftCardDate"), data.get_Data("GiftCard Page", "GiftCardEmailId"),
				data.get_Data("GiftCard Page", "GiftAmount"));
		message = "Getting Error Message of Invalid EmailId";

	}

	// ** Method to close the Browser **
	@AfterClass
	public void terminate_Browser() {
		driver.quit();
	}

	// ** Method to end the Report **
	@AfterSuite
	public void terminate_Report() {

		extent.flush();
	}

}