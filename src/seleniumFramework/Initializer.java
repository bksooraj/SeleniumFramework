package seleniumFramework;

import static seleniumFramework.Utilities.Environment.currentRecord;
import static seleniumFramework.Utilities.Environment.driver;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import seleniumFramework.Enumerators.BrowserType;
import seleniumFramework.Utilities.Environment;
import seleniumFramework.Utilities.ExcelUtils;
import seleniumFramework.Utilities.ObjectFunctions;
import seleniumFramework.Utilities.Reporter;

public class Initializer {
	public static void initialize(BrowserType brType, String appURL) {
		Config.browserName = brType;
		Config.appURL = appURL;
		switch (Config.browserName) {
		case Chrome:
			System.setProperty("webdriver.chrome.driver", "dependencies/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case IE:
			System.setProperty("webdriver.ie.driver", "dependencies/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;
		case Safari:
			break;
		case Firefox:
			driver = new FirefoxDriver();
			break;
		}

		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.get(Config.appURL);
		Environment.ObjectFunction = new ObjectFunctions();
		Environment.debug = false;
		try {
			Environment.controlObj = new Controls();
		} catch (Exception e) {
			Reporter.Log("Unable to locate Controls file");
			Assert.fail("Unable to locate Controls file");
			e.printStackTrace();
			return;
		}

		ExcelUtils xlBook = new ExcelUtils();
		xlBook.open(Config.TestDataFilePath);

		Environment.curRow = xlBook.query(String.format("select * from TestData where TestID='%s'", currentRecord));

		try {
			Assert.assertTrue(Environment.curRow.next());
		} catch (Exception e) {
			Assert.fail(String.format("No record found with '%s' test id.", currentRecord));
		}
	}
}
