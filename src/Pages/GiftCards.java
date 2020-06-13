package Pages;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Setup.BaseClass;

public class GiftCards {
	WebDriver driver;
	Properties property;

	@FindBy(xpath = "/html/body/header/div[1]/div/ul/li[5]/a")
	WebElement groupGiftingBtn;

	@FindBy(xpath = "//body/div/form/div/div/div/div/div/span[1]")
	WebElement targetDate;

	@FindBy(xpath = "//div[2]//div[5]//div[1]//label[1]//input[1]")
	WebElement termsAndConditions;

	@FindBy(xpath = "//*[@id=\"amount\"]")
	WebElement giftCardAmount;

	@FindBy(xpath = "//*[@id=\"recipient\"]")
	WebElement recipientName;

	@FindBy(id = "next1")
	WebElement nextBtn1;

	@FindBy(id = "next2")
	WebElement nextBtn2;

	@FindBy(id = "recipientemail")
	WebElement eMail;

	// **** Constructor of Gift Page ****
	public GiftCards(WebDriver driver) throws IOException {
		this.driver = driver;
		property = BaseClass.invoke_Property_File();
		PageFactory.initElements(driver, this);
	}

	// **** Method to set Giftcard Details ****
	public void set_GiftCard_Details(String Recipientname, String date, String EmailId, String GiftAmount)
			throws InterruptedException, ParseException {

		click_Group_Gifting_Button();
		set_GiftCard_Amount(GiftAmount);
		set_Date(date);
		click_Terms_And_Conditions_CheckBox();
		click_Next_Button1();
		set_Recipient_Name(Recipientname);
		set_Email(EmailId);
		click_Next_Button2();
		get_Flash_message();

	}

	// **** Method to click Group Gifting Button ****
	public void click_Group_Gifting_Button() {
		JavascriptExecutor executor1 = (JavascriptExecutor) driver;
		executor1.executeScript("arguments[0].click();", groupGiftingBtn);

	}

	// **** Method to set GiftCard Amount ****
	public void set_GiftCard_Amount(String GiftAmount) {

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.textToBePresentInElement(
				driver.findElement(By.xpath(property.getProperty("selectedGiftCard_xpath"))),
				property.getProperty("selectedGiftCard_Message")));
		wait.until(ExpectedConditions.visibilityOf(giftCardAmount));
		giftCardAmount.sendKeys(GiftAmount);

	}

	// **** Method to set Date ****
	public void set_Date(String date) throws InterruptedException, ParseException {

		Date currentDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date expectedDate = df.parse(date);
		String day = new SimpleDateFormat("dd").format(expectedDate);
		int i = Integer.parseInt(day);
		String month = new SimpleDateFormat("MMMM").format(expectedDate);
		String year = new SimpleDateFormat("yyyy").format(expectedDate);
		String monthYear = month + " " + year;
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", targetDate);
		while (true) {

			String displayedMonthYear = driver.findElement(By.xpath(property.getProperty("giftCardMonthYear_xpath")))
					.getText();
			if (monthYear.equals(displayedMonthYear)) {
				WebElement element = driver.findElement(By.xpath("//td[text()='" + i + "']"));
				executor.executeScript("arguments[0].click();", element);
				break;
			}

			else if (expectedDate.compareTo(currentDate) > 0) {
				WebElement element = driver.findElement(By.xpath(property.getProperty("giftCardNextMonth_xpath")));
				executor.executeScript("arguments[0].click();", element);
			}

		}

	}

	// **** Method to click Terms and conditions Checkbox ****
	public void click_Terms_And_Conditions_CheckBox() {
		JavascriptExecutor executor1 = (JavascriptExecutor) driver;
		executor1.executeScript("arguments[0].click();", termsAndConditions);
	}

	// **** Method to click NextBtn 1 ****
	public void click_Next_Button1() {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", nextBtn1);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

	}

	// **** Method to set Recipient Name ****
	public void set_Recipient_Name(String Name) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(recipientName));
		recipientName.sendKeys(Name);

	}

	// **** Method to enter Invalid EmailId****
	public void set_Email(String Emailid) {
		eMail.sendKeys(Emailid);

	}

	// **** Method to click NextBtn 2 ****
	public void click_Next_Button2() {
		JavascriptExecutor executor1 = (JavascriptExecutor) driver;
		executor1.executeScript("arguments[0].click();", nextBtn2);
	}

	// **** Method to get Error Message ****
	public void get_Flash_message() {
		System.out.println("Error Message for Invalid EmailId: "
				+ driver.findElement(By.xpath(property.getProperty("flashMessage_xpath"))).getText());
		JavascriptExecutor executor1 = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(property.getProperty("okayBtn_xpath")));
		executor1.executeScript("arguments[0].click();", element);
	}

}