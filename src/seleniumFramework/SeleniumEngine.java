package seleniumFramework;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import Fillo.Recordset;

public class SeleniumEngine {
	public Environment Environment;
	public Config Config;
	public String curTestID;
	public DataRow DataRow;
	public ObjectFunctions Operations;
	public static Reporter Reporter;
	private ExcelUtils xlBook;

	public SeleniumEngine(BrowserType brType, String strURL) throws Exception {
		Config = new Config();
		Config.browserName = brType;
		Config.appURL = strURL;
		Environment = new Environment();
		initialize(brType, strURL);

	}

	private void initialize(BrowserType brType, String appURL) throws Exception {
		Config.browserName = brType;
		Config.appURL = appURL;
		switch (Config.browserName) {
		case Chrome:
			System.setProperty("webdriver.chrome.driver", Config.DriversPath + "chromedriver.exe");
			Environment.driver = new ChromeDriver();
			break;
		case IE:
			System.setProperty("webdriver.ie.driver", Config.DriversPath + "IEDriverServer.exe");
			Environment.driver = new InternetExplorerDriver();
			break;
		case Safari:
			break;
		case Firefox:
			Environment.driver = new FirefoxDriver();
			break;
		}

		Environment.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Environment.driver.get(Config.appURL);
		try {
		Environment.ObjectFunction = new ObjectFunctions(this);
		this.Operations = Environment.ObjectFunction; 
		} catch (Exception e) {
			throw new Exception("Unable to create Controls object, check Controls file is available");
		}
		xlBook = new ExcelUtils();
		xlBook.open(Config.TestDataFilePath);
	}

	public void setDataRow(String strTestID) {
		this.curTestID = strTestID;
		Recordset curRow; 
		curRow = xlBook.query(String.format("select * from TestData where TestID='%s'", strTestID));

		try {
			Assert.assertTrue(curRow.next());
			this.DataRow = new DataRow(curRow);
		} catch (Exception e) {
			Assert.fail(String.format("No record found with '%s' test id.", strTestID));
		}
	}
	
	public WebElement getControl(String strObjectName) {
		return this.Environment.controlObj.getControl(strObjectName);
	}
	
	public void Quit() {
		this.Environment.driver.quit();
	}
}
