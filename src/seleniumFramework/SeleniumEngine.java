package seleniumFramework;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import Fillo.Recordset;

public class SeleniumEngine {
	public Environment Environment;
	public Config Config;
	public String curTestID;
	public DataRow DataRow;
	public ObjectFunctions Operations;
	public WinObjectsFunctions WinOperations;
	//public Optional Optional;
	public static Reporter Reporter;
	private ExcelUtils xlBook;
	public static boolean newBrowser = true;

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
		boolean blnLaunchBrowser = true;
		Properties prop;
		if (!newBrowser) {
			prop = SessionDetails.getSessionDetails();
			blnLaunchBrowser = false;
		} else {
			prop = null;
			blnLaunchBrowser = true;
		}
		switch (Config.browserName)

		{
		case Chrome:
			System.setProperty("webdriver.chrome.driver", Config.DriversPath + "chromedriver.exe");

			if (prop == null) {
				blnLaunchBrowser = true;
			} else {
				try {
					Capabilities cp = DesiredCapabilities.chrome();
					RemoteWebDriverEx rwdx = new RemoteWebDriverEx(new URL(prop.getProperty(SessionDetails.HOST_NAME)),
							cp);
					rwdx.close();
					rwdx.setOldSession(prop.getProperty(SessionDetails.SESSION_NAME));
					Environment.driver = (WebDriver) rwdx;
					blnLaunchBrowser = false;
				} catch (Exception e) {
					blnLaunchBrowser = true;
				}
			}
			if (blnLaunchBrowser) {
				ChromeDriverService cds = ChromeDriverService.createDefaultService();
				Capabilities cp = DesiredCapabilities.chrome();
				WebDriver objWebDriver = new ChromeDriver(cds, cp);
				RemoteWebDriver rwd = (RemoteWebDriver) objWebDriver;
				SessionDetails.saveSessionDetails(rwd.getSessionId().toString(), cds.getUrl(), brType);
				Environment.driver = (WebDriver) rwd;
				Environment.driver.get(Config.appURL);
			}
			break;
		case IE:
			System.setProperty("webdriver.ie.driver", Config.DriversPath + "IEDriverServer.exe");

			if (prop == null) {
				seleniumFramework.Reporter.Log("No Session details, Start new Internet explorer");
				blnLaunchBrowser = true;
			} else {
				try {
					seleniumFramework.Reporter.Log("Starting Internet explorer with existing Driver Server");
					Capabilities cp = DesiredCapabilities.internetExplorer();
					RemoteWebDriverEx rwdx = new RemoteWebDriverEx(new URL(prop.getProperty(SessionDetails.HOST_NAME)),
							cp);
					rwdx.close();
					rwdx.setOldSession(prop.getProperty(SessionDetails.SESSION_NAME));
					Environment.driver = (WebDriver) rwdx;
					blnLaunchBrowser = false;
				} catch (Exception e) {
					seleniumFramework.Reporter.Log("Issue while connecting to existing Internet explorer");
					blnLaunchBrowser = true;
				}
			}
			if (blnLaunchBrowser) {
				seleniumFramework.Reporter.Log("Launching Internet Explorer");
				InternetExplorerDriverService ids = InternetExplorerDriverService.createDefaultService();
				Capabilities cp = DesiredCapabilities.internetExplorer();
				WebDriver objWebDriver = new InternetExplorerDriver(ids, cp);
				RemoteWebDriver rwd = (RemoteWebDriver) objWebDriver;
				SessionDetails.saveSessionDetails(rwd.getSessionId().toString(), ids.getUrl(), brType);
				Environment.driver = (WebDriver) rwd;
				Environment.driver.get(Config.appURL);
			}

			// Environment.driver = new InternetExplorerDriver();
			break;
		case Safari:
			break;
		case Firefox:
			Environment.driver = new FirefoxDriver();
			Environment.driver.get(Config.appURL);
			break;
		}

		Environment.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
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

	public int updateOutput(boolean blnAllEnvironmentVariables){
		return this.xlBook.updateEnvironmentVariables(this.curTestID, "TestData", blnAllEnvironmentVariables);
	}
	public void Quit() {
		this.Environment.driver.quit();
	}
}
