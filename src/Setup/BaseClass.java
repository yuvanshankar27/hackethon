package Setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseClass {
	public static WebDriver driver;
	public static RemoteWebDriver Remotedriver;
	public static Properties property;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static DesiredCapabilities capabilities;

	// **** Method to Invoke browser ****
	public static WebDriver invoke_Browser() throws IOException {

		property = invoke_Property_File();

		if (property.getProperty("runType").equalsIgnoreCase("Grid")) {

			if (property.getProperty("browserName").equalsIgnoreCase("firefox")) {
				capabilities = DesiredCapabilities.firefox();
			}
			// Check if parameter passed as 'chrome'
			else if (property.getProperty("browserName").equalsIgnoreCase("chrome")) {
				capabilities = DesiredCapabilities.chrome();
			} else if (property.getProperty("browserName").equalsIgnoreCase("Internet Explorer")) {
				capabilities = DesiredCapabilities.internetExplorer();
			} else if (property.getProperty("browserName").equalsIgnoreCase("opera")) {
				capabilities = DesiredCapabilities.opera();
			} else if (property.getProperty("browserName").equalsIgnoreCase("edge")) {
				capabilities = DesiredCapabilities.edge();
			}

			Remotedriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			Remotedriver.manage().window().maximize();
			Remotedriver.navigate().to(property.getProperty("website_URL"));
			return Remotedriver;

		} else {

			if (property.getProperty("browserName").equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\Webdrivers\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (property.getProperty("browserName").equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\Webdrivers\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (property.getProperty("browserName").equalsIgnoreCase("Internet Explorer")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\Webdrivers\\IEDriverServer.exe");

				driver = new InternetExplorerDriver();
			} else if (property.getProperty("browserName").equalsIgnoreCase("opera")) {
				System.setProperty("webdriver.opera.driver",
						System.getProperty("user.dir") + "\\Webdrivers\\operadriver.exe");
				driver = new OperaDriver();
			} else if (property.getProperty("browserName").equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "\\Webdrivers\\msedgedriver.exe");
				driver = new EdgeDriver();
			}
			driver.manage().window().maximize();
			driver.get(property.getProperty("website_URL"));
			return driver;

		}

	}

	// **** Method to generate ExtentReport ****
	@BeforeSuite
	public static void generate_ExtentReport() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Project", "Automation On MakeMyTrip Using Selenium");
		extent.setSystemInfo("Technology Used",
				"TestNG, Maven, POM, Property File, Grid, JS Executor, Hybrid Framework");
		extent.setSystemInfo("Report by", "Sriram , Vignesh , Yuvan, Sakthi,  Kalai");
		htmlReporter.config().setDocumentTitle("Extent Test Report ");
		htmlReporter.config().setReportName("Test - Make My Trip");
		htmlReporter.config().setTheme(Theme.DARK);
	}

	// **** Method to invoke Property File ****
	public static Properties invoke_Property_File() throws IOException {
		InputStream readFile = new FileInputStream(
				new File(System.getProperty("user.dir") + "\\PropertyFile.properties"));
		property = new Properties();
		property.load(readFile);
		return property;
	}

	// **** Method to get ScreenShot ****
	public static String get_ScreenShot_Of_Failed_TestCase(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}