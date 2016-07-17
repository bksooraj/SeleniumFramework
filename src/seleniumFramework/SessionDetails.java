package seleniumFramework;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.Properties;

public class SessionDetails {
	public static final String SESSION_NAME = "SessionName";
	public static final String HOST_NAME = "HostName";
	public static final String BROWSER_TYPE = "BrowserType";

	public static boolean saveSessionDetails(String strSessionID, URL hostName, BrowserType browserType) {
		// TODO Auto-generated constructor stub
		clearSessionsFile();
		Properties prop = new Properties();
		prop.put(SESSION_NAME, strSessionID);
		prop.put(HOST_NAME, hostName.toString());
		prop.put(BROWSER_TYPE, browserType.toString());
		try {
			FileWriter fw = new FileWriter(Config.SessionDetailsPath);
			prop.store(fw, "Latest");
			Reporter.Log(String.format("%s, %s, %s stored in Session details file successfully", strSessionID, hostName,
					browserType.toString()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.Log(String.format("problem storing %s, %s, %s in Session details file successfully", strSessionID, hostName,
					browserType.toString()));
			return false;
		}
	}

	public static Properties getSessionDetails() {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(Config.SessionDetailsPath));
			return prop;
		} catch (Exception e) {
			return null;
		}
	}

	public static void clearSessionsFile() {
		try {
			new File(Config.SessionDetailsPath).delete();
		} catch (Exception e) {
		}
	}
}
