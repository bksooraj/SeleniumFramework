package seleniumFramework;

import static seleniumFramework.Reporter.handleException;

import java.util.concurrent.TimeUnit;

import org.apache.http.auth.InvalidCredentialsException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;

public class WinObjectsFunctions {
	private WebDriver driver;

	public WinObjectsFunctions(SeleniumEngine sEngine) {
		this.driver = sEngine.Environment.driver;
	}

	public void Accept() {
		try {
			driver.manage().timeouts().implicitlyWait(Config.iLongWait, TimeUnit.SECONDS);
			Alert alert = getAlert(driver);
			alert.accept();
			ObjectFunctions.Wait(1000);
		} catch (Exception e) {
			handleException("Alert window is not displayed to accept", e);
		} finally {
			driver.manage().timeouts().implicitlyWait(Config.iShortWait, TimeUnit.SECONDS);
		}
	}

	public void Dismiss() {
		try {
			driver.manage().timeouts().implicitlyWait(Config.iLongWait, TimeUnit.SECONDS);
			Alert alert = getAlert(driver);
			alert.dismiss();
			ObjectFunctions.Wait(1000);
		} catch (Exception e) {
			handleException("Alert window is not displayed to dismiss", e);
		} finally {
			driver.manage().timeouts().implicitlyWait(Config.iShortWait, TimeUnit.SECONDS);
		}

	}

	public String GetText() {
		try {
			return getAlert(this.driver).getText();
		} catch (Exception e) {
			handleException("Alert window is not displayed.  Text could not be retrieved.", e);
			return "";
		}
	}
	
	public void SetCredentials(String strUserName, String strPassword){
		Alert credAlert = getAlert(driver);
		Credentials cred = new UserAndPassword(strUserName, strPassword);
		credAlert.setCredentials(cred);
		
	}

	private Alert getAlert(WebDriver alertDriver) {
		for (int i = 0; i < 20; i++) {
			try {
				ObjectFunctions.Wait(2000);
				return alertDriver.switchTo().alert();
			} catch (Exception e) {
				
			}
		}
		return null;
	}

}
