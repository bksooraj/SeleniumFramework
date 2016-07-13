package seleniumFramework;

public class Config {
	public  String ControlsFilePath = "TestData\\Controls.xlsx";
	public  String TestDataFilePath = "TestData\\TestData.xlsx";
	public  BrowserType browserName = BrowserType.Firefox;
	protected  String appURL = "";
	
	public String getAppURL() {
		return appURL;
	}
}
