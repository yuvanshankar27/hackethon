package TestCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.Assert;

import org.testng.ITestResult;

import java.io.IOException;
import java.text.ParseException;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeClass;

import Pages.*;
import Setup.*;

public class ValidTestCases extends BaseClass {
	LoginPage objLogin;
	HomePage objHomePage;
	FlightSearch objFlightSearch;
	CabSearch objCabSearch;
	HotelBooking objHotelBooking;
	String message;
	TestData data = new TestData();
	public Properties property;

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

	// ** Test to verify URL **
	@Test(priority = 1)
	public void test_URL_Appear_Correct() throws IOException {

		logger = extent.createTest("test_URL_Appear_Correct");
		logger.assignCategory("Smoke Tests");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Website URL", ExtentColor.CYAN));
		message = "URL verification";
		Assert.assertTrue(driver.getCurrentUrl().contains(data.get_Data("Login Page", "URL")));// TC-1
	}

	// ** Test to verify HomePage Title **
	@Test(priority = 2)
	public void test_Home_Page_Title() throws IOException {

		logger = extent.createTest("test_Home_Page_Title");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Home Page Title", ExtentColor.CYAN));
		message = "Home Page Title verification";
		Assert.assertTrue(driver.getTitle().contains(data.get_Data("Login Page", "HomePageTitle")));// TC-2
	}

	// ** Test to verify SigIn button is Enabled **
	@Test(priority = 3)
	public void test_SignIn_Button() throws IOException, InterruptedException, ParseException {

		logger = extent.createTest("test_SignIn_Button");
		logger.assignCategory("Smoke Tests");
		logger.assignCategory("Regression Tests");
		objHomePage = new HomePage(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - SignIn Button is Enabled", ExtentColor.CYAN));
		message = "SignIn Button Enabled";
		Assert.assertTrue(objHomePage.click_SignIn_Button());
	}

	// ** Test to perform and verify Login **
	@Test(priority = 4)
	public void test_LogIn_Details() throws IOException {
		logger = extent.createTest("test_LogIn_Details");
		logger.assignCategory("Smoke Tests");
		logger.assignCategory("Regression Tests");
		objLogin = new LoginPage(driver);
		objLogin.LogIn(data.get_Data("Login Page", "Username"), data.get_Data("Login Page", "Password"));
		logger.log(Status.INFO, MarkupHelper.createLabel(
				"Test to Verify - Username is displayed in the HomePage after successfull LogIn", ExtentColor.CYAN));
		message = "Login Verification";
		Assert.assertTrue(objHomePage.get_LoggedIn_UserName().contains(data.get_Data("Login Page", "DashBoardName")));// TC-3

	}

	// ** Test to verify FlightIcon is Enabled **
	@Test(priority = 5)
	public void test_Flight_Icon() throws IOException, InterruptedException {
		property = BaseClass.invoke_Property_File();
		logger = extent.createTest("test_flight_Icon");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Flight Icon is Selected", ExtentColor.CYAN));
		message = "Flight Icon Enabled";
		Assert.assertTrue(driver.findElement(By.xpath(property.getProperty("flight_icon_xpath"))).getAttribute("class")
				.contains(property.getProperty("flight_icon_class")));// TC-4

	}

	// ** Test to verify CabIcon is Enabled **
	@Test(priority = 6)
	public void test_Cab_Icon() throws ParseException, IOException {

		logger = extent.createTest("test_Cab_Icon");
		logger.assignCategory("Smoke Tests");
		logger.assignCategory("Regression Tests");
		objHomePage = new HomePage(driver);
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Cab Icon is Enabled", ExtentColor.CYAN));
		message = "Cab Icon Enabled";
		Assert.assertTrue(objHomePage.click_Cab_Icon());// TC-5
	}

	// ** Test to verify CabPage Title **
	@Test(priority = 7)
	public void test_Cab_Page_Title() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_Cab_Page_Title");
		logger.assignCategory("Regression Tests");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Cab Page Title", ExtentColor.CYAN));
		message = "Cab Page Title verification";
		Assert.assertTrue(driver.getTitle().contains(data.get_Data("Cab Page", "CabPageTitle")));// TC-6
	}

	// ** Test to verify OutstationOneWay TripType is selected by default **
	@Test(priority = 8)
	public void test_OutstationOneWay_TripType() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_TripType_Button");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO, MarkupHelper
				.createLabel("Test to Verify - OutstationOneWay TripType Radio Button is Selected", ExtentColor.CYAN));
		message = "OutstationOneWay TripType Radio Button Selected";
		Assert.assertTrue(driver.findElement(By.xpath(property.getProperty("tripType_xpath"))).getAttribute("data-cy")
				.contains(property.getProperty("tripType_data_cy")));// TC-7
	}

	// ** Test to set Cab Details **
	@Test(priority = 9)
	public void test_Set_Cab_Details() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_Set_Cab_Details");
		logger.assignCategory("Smoke Tests");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Cab Details are set successfully", ExtentColor.CYAN));
		message = "Set Cab Details";
		objHomePage = new HomePage(driver);
		objCabSearch = new CabSearch(driver);
		objCabSearch.set_Cab_Details(data.get_Data("Cab Page", "FromPlace"), data.get_Data("Cab Page", "ToPlace"),
				data.get_Data("Cab Page", "DateOfTravel"), data.get_Data("Cab Page", "PickupTime"));// TC- 8-12

	}

	// ** Test to verify Cab Details and to get Cab name and cost **
	@Test(priority = 10)
	public void test_To_Check_Cab_Details() throws InterruptedException, IOException, ParseException {
		logger = extent.createTest("test_To_Check_Cab_Details");
		logger.assignCategory("Regression Tests");
		objCabSearch = new CabSearch(driver);

		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verfiy - Trip Type", ExtentColor.CYAN));
		message = "Trip type verification";
		Assert.assertTrue(objCabSearch.check_Trip_Type().contains(data.get_Data("Cab Page", "TripType")));

		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Departure City", ExtentColor.CYAN));
		message = "Departure City verification";
		Assert.assertTrue(objCabSearch.check_From_City().contains(data.get_Data("Cab Page", "FromPlace")));

		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Arrival City", ExtentColor.CYAN));
		message = "Arrival City verification";
		Assert.assertTrue(objCabSearch.check_To_City().contains(data.get_Data("Cab Page", "ToPlace")));

		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Pickup Time", ExtentColor.CYAN));
		message = "Pickup time verification";
		Assert.assertTrue(objCabSearch.check_Pickup_Time().contains(data.get_Data("Cab Page", "PickupTime")));

		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Date of Travel", ExtentColor.CYAN));
		message = "Date of Travel verification";
		Assert.assertEquals(objCabSearch.check_Date(data.get_Data("Cab Page", "DateOfTravel")),
				objCabSearch.check_Date_Of_Travel());

		logger.log(Status.INFO, MarkupHelper.createLabel("Printing Car Name and its Estimated Cost", ExtentColor.CYAN));
		message = "Finding CarName and its Estimated Cost";
		System.out.println("Car Name: " + objCabSearch.get_Car_Name());
		System.out.println("Estimated Cost: " + objCabSearch.get_Car_Cost());
		message = "Cab Details verification";

	}

	// ** Test to verify FlightIcon is Enabled **
	@Test(priority = 11)
	public void test_Flight_Button() throws IOException, InterruptedException {
		logger = extent.createTest("test_Flight_Button");
		logger.assignCategory("Regression Tests");
		objHomePage = new HomePage(driver);
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Flight Icon is Enabled", ExtentColor.CYAN));
		message = "Flight Icon Enabled";
		Assert.assertTrue(objHomePage.click_Flight_Icon());// TC-20

	}

	// ** Test to verify FlightPage Title **
	@Test(priority = 12)
	public void test_Flight_Page_Title() throws IOException, InterruptedException {
		logger = extent.createTest("test_Flight_Page_Title");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO, MarkupHelper.createLabel("Test to Verify - Flight Page Title", ExtentColor.CYAN));
		message = "Flight Page Title verification";
		Assert.assertTrue(driver.getTitle().contains(data.get_Data("Flight Page", "FlightPageTitle")));

	}

	// ** Test to set Flight Details **
	@Test(priority = 13)
	public void test_Set_Flight_Details() throws IOException, InterruptedException, ParseException {
		logger = extent.createTest("test_Set_Flight_Details");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Flight Details are set successfully", ExtentColor.CYAN));
		message = "Set Flight Details";
		objFlightSearch = new FlightSearch(driver);
		objFlightSearch.click_RoundTrip_Button();// TC-21
		objFlightSearch.set_Flight_Details(data.get_Data("Flight Page", "FromCity"),
				data.get_Data("Flight Page", "ToCity"), data.get_Data("Flight Page", "DepartureDate"),
				data.get_Data("Flight Page", "ReturnDate"), data.get_Data("Flight Page", "AdultsCount"),
				data.get_Data("Flight Page", "ChildrenCount"), data.get_Data("Flight Page", "InfantsCount"));

	}

	// ** Test to verify HoteBooking Icon is Enabled **
	@Test(priority = 14)
	public void test_Hotel_Booking_Icon() throws IOException, InterruptedException {
		logger = extent.createTest("test_Hotel_Booking_Icon");
		logger.assignCategory("Regression Tests");
		objHomePage = new HomePage(driver);
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Hotel Booking Icon is Enabled", ExtentColor.CYAN));
		message = "Hotel Booking Icon Enabled";
		Assert.assertTrue(objHomePage.click_HotelBooking_Icon());
	}

	// ** Test to verify HoteBooking Page Title **
	@Test(priority = 15)
	public void test_Hotel_Booking_Page_Title() throws IOException, InterruptedException {
		logger = extent.createTest("test_Hotel_Booking_Page_Title");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO,
				MarkupHelper.createLabel("Test to Verify - Hotel Booking Page Title", ExtentColor.CYAN));
		message = "Hotel Booking Page Title verification";
		Assert.assertTrue(driver.getTitle().contains(data.get_Data("Hotel Page", "HotelPageTitle")));

	}

	// ** Test to Get AdultList Details **
	@Test(priority = 16)
	public void test_Hotel_Booking() throws IOException, InterruptedException {
		logger = extent.createTest("test_Hotel_Booking");
		logger.assignCategory("Regression Tests");
		logger.log(Status.INFO, MarkupHelper.createLabel("Get Adult List Details", ExtentColor.CYAN));
		message = "Getting Adult List Details";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objHotelBooking = new HotelBooking(driver);
		objHotelBooking.set_Hotel_Details();
		message = "Printing Adult List Details";
	}

	// ** Method to close the Browser **
	@AfterClass
	public void terminate_Browser() {
		driver.quit();
	}

}