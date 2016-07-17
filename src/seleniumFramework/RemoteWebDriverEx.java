package seleniumFramework;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteWebDriverEx extends RemoteWebDriver {
	public RemoteWebDriverEx(URL url, Capabilities cp) {
		super(url, cp);
		
	}

	public void setOldSession(String strSessionID) {
		super.setSessionId(strSessionID);
	}
}
