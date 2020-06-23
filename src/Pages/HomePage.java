package Pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Setup.BaseClass;
import Setup.TestData;

public class HomePage {

	// To Initialize WebElements using PageFactory method
	WebDriver driver;
	Properties property;

	TestData data = new TestData();

	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[6]/div[1]/p[1]")
	WebElement loggedInUserName;

	@FindBy(xpath = "//div[@id='SW']//li[7]//a[1]")
	WebElement cabIcon;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div/div/nav/ul/li[1]/a/span[1]")
	WebElement flightIcon;

	@FindBy(xpath = "//li[contains(@class,'menu_Hotels')]//a[contains(@class,'makeFlex hrtlCenter column')]")
	WebElement hotelBookingIcon;

	@FindBy(xpath = "//li[contains(@class,'menu_Giftcards')]//a[contains(@class,'makeFlex hrtlCenter column')]")
	WebElement giftCardIcon;

	@FindBy(xpath = "//li[6]//div[1]//p[1]")
	WebElement signInBtn;

	// **** Constructor of HomePage ****
	public HomePage(WebDriver driver) throws IOException {
		this.driver = driver;
		property = BaseClass.invoke_Property_File();
		PageFactory.initElements(driver, this);
	}

	// **** Method to click SignIn Button ****
	public boolean click_SignIn_Button() {
		boolean checkBtn = signInBtn.isEnabled();
		Actions action=new Actions(driver);
		WebElement logIn =driver.findElement(By.xpath(property.getProperty("login_or_CreateAcc_xpath")));
		action.moveToElement(logIn).moveToElement(driver.findElement(By.xpath(property.getProperty("login_via_phone")))).click().build().perform();
		return checkBtn;
	}

	// **** Method to get LoggedIn Username ****
	public String get_LoggedIn_UserName() {
		return loggedInUserName.getText();
	}

	// **** Method to click Cab Icon ****
	public boolean click_Cab_Icon() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(cabIcon));
		boolean checkBtn = cabIcon.isEnabled();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", cabIcon);
		return checkBtn;
	}

	// **** Method to click Flight Icon ****
	public boolean click_Flight_Icon() {
		boolean checkBtn = flightIcon.isEnabled();
		flightIcon.click();
		return checkBtn;
	}

	// **** Method to click HotelBooking Icon ****
	public boolean click_HotelBooking_Icon() {
		boolean checkBtn = hotelBookingIcon.isEnabled();
		hotelBookingIcon.click();
		return checkBtn;
	}

	// **** Method to click GiftCard Icon ****
	public boolean click_GiftCard_Icon() {
		boolean checkBtn = giftCardIcon.isEnabled();
		giftCardIcon.click();
		// driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return checkBtn;

	}

	// **** Method to wait for Logging In ****
	public void wait_For_Login() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.textToBePresentInElement(loggedInUserName,
				data.get_Data("Login Page", "DashBoardName")));
	}

}
