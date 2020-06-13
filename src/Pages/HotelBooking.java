package Pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Setup.BaseClass;

public class HotelBooking {

	WebDriver driver;
	Properties property;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div[2]/div/div/div[4]/label/p")
	WebElement noOfPersons;

	// **** Constructor of HotelBooking Page ****
	public HotelBooking(WebDriver driver) throws IOException {
		this.driver = driver;
		property = BaseClass.invoke_Property_File();
		PageFactory.initElements(driver, this);
	}

	// **** Method to set Hotel Details ****
	public void set_Hotel_Details() {
		click_Rooms_And_Guests();
		print_Adult_List();
	}

	// **** Method to click Rooms and Guests ****
	public void click_Rooms_And_Guests() {
		noOfPersons.click();
	}

	// **** Method to print Adult List ****
	public void print_Adult_List() {
		List<String> adultList = new ArrayList<>();
		List<WebElement> Elements = driver.findElements(By.xpath(property.getProperty("adultList_xpath")));
		for (int i = 0; i < Elements.size(); i++) {
			adultList.add(Elements.get(i).getText());
		}
		System.out.println("List for Adult Persons: " + adultList);
	}

}
