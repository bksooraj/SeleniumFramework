package seleniumFramework;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import static seleniumFramework.Reporter.*;

public class WinObjectsFunctions {
	private SeleniumEngine sEngine;
	private WebDriver driver;

	public WinObjectsFunctions(SeleniumEngine sEngine) {
		this.sEngine = sEngine;
		this.driver = sEngine.Environment.driver;
	}

	public void Accept() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			handleException("Alert window is not displayed to accept", e);
		}
	}

	public void Dismiss() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			handleException("Alert window is not displayed to dismiss", e);
		}

	}

	public String GetText() {
		try {
			return this.driver.switchTo().alert().getText();
		} catch (Exception e) {
			handleException("Alert window is not displayed.  Text could not be retrieved.", e);
			return "";
		}
	}

}
