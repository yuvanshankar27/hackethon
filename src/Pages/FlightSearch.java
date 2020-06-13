package Pages;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class FlightSearch {

	WebDriver driver;
	Properties property;

	@FindBy(xpath = "//input[@id='fromCity']")
	WebElement fromCity;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div[1]/div[2]/div[1]/div/div/div/input")
	WebElement toCity;

	@FindBy(xpath = "//body/div[@id='root']/div/div/div/div/div/p/a[1]")
	WebElement searchBtn;

	@FindBy(xpath = "//*[@id='root']/div/div[2]/div/div/div[1]/ul/li[2]")
	WebElement roundTripBtn;

	@FindBy(xpath = "//span[contains(text(),'DEPARTURE')]")
	WebElement departureDate;

	@FindBy(xpath = "//span[contains(text(),'RETURN')]")
	WebElement returnDate;

	@FindBy(xpath = "//*[@id='root']/div/div[2]/div/div/div[2]/div[1]/div[5]/label")
	WebElement noOfPersons;

	@FindBy(xpath = "//*[@id='root']/div/div[2]/div/div/div[2]/div[1]/div[5]/div[1]/button")
	WebElement applyBtn;

	// **** Constructor of Flight Page ****
	public FlightSearch(WebDriver driver) throws IOException {
		this.driver = driver;
		property = BaseClass.invoke_Property_File();
		// To initialize WebElements using PageFactory Method
		PageFactory.initElements(driver, this);
	}

	// **** Method to Set Flight details ****
	public void set_Flight_Details(String fromCityName, String toCityName, String Fromdate, String Todate,
			String Adults, String children, String Infants) throws InterruptedException, ParseException {
		this.set_FromCity_and_ToCity(fromCityName, toCityName);
		this.set_Date_Of_Journey(Fromdate, departureDate);
		this.set_Date_Of_Journey(Todate, returnDate);
		this.click_No_Of_Persons(Adults, children, Infants);
		this.click_Search_Button();
	}

	// **** Method to Set From and To Cities ****
	public void set_FromCity_and_ToCity(String fromCityName, String toCityName) throws InterruptedException {
		fromCity.sendKeys(fromCityName);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.textToBePresentInElement(
				driver.findElement(By.xpath("//*[@id='react-autowhatever-1-section-0-item-0']")), fromCityName));
		driver.findElement(By.xpath(property.getProperty("autoSuggestion_xpath"))).click();
		toCity.sendKeys(toCityName);
		wait.until(ExpectedConditions.textToBePresentInElement(
				driver.findElement(By.xpath("//*[@id=\"react-autowhatever-1-section-0-item-0\"]")), toCityName));
		driver.findElement(By.xpath(property.getProperty("autoSuggestion_xpath"))).click();

	}

	// **** Method to Set Date of Travel****
	public void set_Date_Of_Journey(String date, WebElement departureDate) throws ParseException {

		Date currentDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date expectedDate = df.parse(date);
		String day = new SimpleDateFormat("dd").format(expectedDate);
		int i = Integer.parseInt(day);
		String month = new SimpleDateFormat("MMMM").format(expectedDate);
		String year = new SimpleDateFormat("yyyy").format(expectedDate);
		String monthYear = month + year;
		departureDate.click();
		while (true) {
			String displayedMonthYear = driver.findElement(By.xpath(property.getProperty("monthYear_xpath"))).getText();

			if (monthYear.equals(displayedMonthYear)) {
				driver.findElement(By.xpath("//p[text()='" + i + "']")).click();
				break;
			}

			else if (expectedDate.compareTo(currentDate) > 0) {
				WebElement element = driver.findElement(By.xpath(property.getProperty("nextMonth_xpath")));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			}

		}
	}

	// **** Method to click No of Persons ****
	public void click_No_Of_Persons(String adults, String children, String Infants) {

		noOfPersons.click();
		WebElement element = driver
				.findElement(By.xpath("//div[@class='appendBottom20']//li[contains(text(),'" + adults + "')]"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

		WebElement element1 = driver
				.findElement(By.xpath("//div[@class='makeFlex column']//li[contains(text(),'" + children + "')]"));

		executor.executeScript("arguments[0].click();", element1);
		WebElement element2 = driver.findElement(
				By.xpath("//div[@class='makeFlex column pushRight']//li[contains(text(),'" + Infants + "')]"));

		executor.executeScript("arguments[0].click();", element2);

		applyBtn.click();

	}

	// **** Method to click Search Button ****
	public void click_Search_Button() {
		searchBtn.click();
	}

	// **** Method to click RoundTrip Radio Button ****
	public void click_RoundTrip_Button() {
		roundTripBtn.click();
	}

}
