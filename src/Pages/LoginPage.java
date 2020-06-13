package Pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Setup.BaseClass;

public class LoginPage {

	WebDriver driver;
	Properties property;

	@FindBy(xpath = "//input[@id='username']")
	WebElement userName;

	@FindBy(xpath = "//input[@id='password']")
	WebElement password;

	// **** Constructor of LoginPage ****
	public LoginPage(WebDriver driver) throws IOException {
		this.driver = driver;
		property = BaseClass.invoke_Property_File();
		// To initialize WebElements using PageFactory Method
		PageFactory.initElements(driver, this);
	}

	// **** Method to perform LogIn ****
	public void LogIn(String strUserName, String strPasword) {
		this.set_UserName(strUserName);
		this.click_Login_Via_Password();
		this.set_Password(strPasword);
		this.click_LogIn_Button();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@data-cy=\"loggedInUser\"]")));

	}

	// **** Method to set Username ****
	public void set_UserName(String strUserName) {
		driver.findElement(By.xpath(property.getProperty("userName_xpath"))).sendKeys(strUserName);
		driver.findElement(By.xpath(property.getProperty("userName_xpath"))).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath(property.getProperty("continueButton_xpath"))).click();
	}

	// **** Method to click viaPassword Button ****
	public void click_Login_Via_Password() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(property.getProperty("loginViaPassword_xpath"))));

		driver.findElement(By.xpath(property.getProperty("loginViaPassword_xpath"))).click();
	}

	// **** Method to set Password ****
	public void set_Password(String strPassword) {
		driver.findElement(By.xpath(property.getProperty("passWord_xpath"))).sendKeys(strPassword);
	}

	// **** Method to click LogIn Button ****
	public void click_LogIn_Button() {
		driver.findElement(By.xpath(property.getProperty("loginButton_xpath"))).click();
	}

	// **** Method to clear Username TextBox ****
	public void clear_UserName_TxtBox() {
		userName.clear();
	}

	// **** Method to clear Password TextBox ****
	public void clear_Password_TxtBox() {
		password.clear();
	}
}