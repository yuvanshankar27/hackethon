package Pages;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Setup.BaseClass;
import Setup.TestData;

public class CabSearch {

	WebDriver driver;
	Properties property;
	TestData data = new TestData();

	@FindBy(id = "fromCity")
	WebElement fromCity;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div[2]/div[1]/div/div/div/input")
	WebElement toCity;

	@FindBy(xpath = "//span[contains(text(),'DEPARTURE')]")
	WebElement departureDate;

	@FindBy(xpath = "//span[contains(text(),'RETURN')]")
	WebElement returnDate;

	@FindBy(xpath = "//body/div[@id='root']/div/div/div/div/div/p/a[1]")
	WebElement searchBtn;

	@FindBy(xpath = "//span[contains(text(),'PICKUP-TIME')]")
	WebElement pickupTime;

	@FindBy(xpath = "//input[@id='tripType']")
	WebElement tripTypeBtn;

	@FindBy(xpath = "//input[@id=\"fromCity\"]")
	WebElement fromCityCheck;

	// **** Constructor of Cab Search ****
	public CabSearch(WebDriver driver) throws IOException {
		this.driver = driver;
		property = BaseClass.invoke_Property_File();
		PageFactory.initElements(driver, this);
	}

	// **** Method to Set Cab details ****
	public void set_Cab_Details(String fromCityName, String toCityName, String date, String time)
			throws InterruptedException, ParseException {
		this.set_FromCity_And_ToCity(fromCityName, toCityName);
		this.set_Date_Of_Departure(date);
		this.set_PickUp_Time(time);
		this.click_SearchButton();
	}

	// **** Method to Set From and To Cities ****
	public void set_FromCity_And_ToCity(String fromCityName, String toCityName) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(fromCity));
		fromCity.sendKeys(fromCityName);
		String[] check = fromCityName.split(",");
		wait.until(ExpectedConditions.textToBePresentInElement(
				driver.findElement(By.xpath(property.getProperty("autoSuggestion_xpath"))), check[0]));
		driver.findElement(By.xpath(property.getProperty("autoSuggestion_xpath"))).click();
		toCity.sendKeys(toCityName);
		wait.until(ExpectedConditions.textToBePresentInElement(
				driver.findElement(By.xpath(property.getProperty("autoSuggestion_xpath"))), toCityName));
		driver.findElement(By.xpath(property.getProperty("autoSuggestion_xpath"))).click();

	}

	// **** Method to Set Date of Departure ****
	public void set_Date_Of_Departure(String date) throws ParseException {

		Date currentDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date expectedDate = df.parse(date);
		String day = new SimpleDateFormat("dd").format(expectedDate);
		int i = Integer.parseInt(day);
		String month = new SimpleDateFormat("MMMM").format(expectedDate);
		String year = new SimpleDateFormat("yyyy").format(expectedDate);
		String monthYear = month + " " + year;
		departureDate.click();
		while (true) {
			String displayedMonthYear = driver.findElement(By.xpath(property.getProperty("fromMonthYear_xpath")))
					.getText();

			if (monthYear.equals(displayedMonthYear)) {
				driver.findElement(By.xpath("//div[contains(text(),'" + i + "')]")).click();
				break;
			}

			else if (expectedDate.compareTo(currentDate) > 0) {
				WebElement element = driver.findElement(By.xpath(property.getProperty("fromNextMonth_xpath")));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			}

		}

	}

	// **** Method to Set Date of Return ****
	public void set_Date_Of_Return(String date) throws ParseException {
		Date currentDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date expectedDate = df.parse(date);
		String day = new SimpleDateFormat("dd").format(expectedDate);
		int i = Integer.parseInt(day);
		String month = new SimpleDateFormat("MMMM").format(expectedDate);
		String year = new SimpleDateFormat("yyyy").format(expectedDate);
		String monthYear = month + year;
		returnDate.click();
		while (true) {
			String displayedMonthYear = driver.findElement(By.xpath(property.getProperty("toMonthYear_xpath")))
					.getText();
			if (monthYear.equals(displayedMonthYear)) {

				driver.findElement(By.xpath("//div[text()='" + i + "']")).click();
				break;
			}

			else if (expectedDate.compareTo(currentDate) > 0) {
				WebElement element = driver.findElement(By.xpath(property.getProperty("toNextMonth_xpath")));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			}

		}

	}

	// **** Method to Set Pickup Time ****
	public void set_PickUp_Time(String Value) throws InterruptedException, ParseException {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", pickupTime);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		Date expdate = sdf.parse(Value);
		String ReqTime = sdf.format(expdate);
		List<WebElement> element = driver.findElements(By.xpath("//li[contains(text(),'" + ReqTime + "')]"));

		if (Value.endsWith("AM")) {
			executor.executeScript("arguments[0].click();", element.get(0));
		}

		else
			executor.executeScript("arguments[0].click();", element.get(1));

	}

	// **** Method to click Search Button ****
	public void click_SearchButton() {
		searchBtn.click();
	}

	// **** Method to format Date of Journey in Data ****
	public String check_Date(String check) throws ParseException {
		SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, dd MMM yyyy");
		Date date = dateParser.parse(check);
		String strDate = dateFormatter.format(date);
		return strDate;
	}

	// **** Method to verify TripType ****
	public String check_Trip_Type() {
		return driver.findElement(By.xpath(property.getProperty("checkTripType_xpath"))).getAttribute("value");

	}

	// **** Method to verify FromCity ****
	public String check_From_City() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeToBe(fromCity, "value", data.get_Data("Cab Page", "FromPlace")));
		return driver.findElement(By.xpath(property.getProperty("checkFromCity_xpath"))).getAttribute("value");

	}

	// **** Method to verify ToCity ****
	public String check_To_City() {
		return driver.findElement(By.xpath(property.getProperty("checkToCity_xpath"))).getAttribute("value");
	}

	// **** Method to verify PickUp Time ****
	public String check_Pickup_Time() {
		return driver.findElement(By.xpath(property.getProperty("checkPickupTime_xpath"))).getAttribute("value");
	}

	// **** Method to verify Date of Travel ****
	public String check_Date_Of_Travel() {
		return driver.findElement(By.xpath(property.getProperty("checkDateOfTravel_xpath"))).getText();
	}

	// **** Method to get Car Name ****
	public String get_Car_Name() {
		WebElement carName = driver.findElement(By.xpath(property.getProperty("carName_xpath")));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView(true);", carName);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(carName));
		return carName.getText();
	}

	// **** Method to get Car Cost ****
	public String get_Car_Cost() {
		return driver.findElement(By.xpath(property.getProperty("carCost_xpath"))).getText();
	}
}