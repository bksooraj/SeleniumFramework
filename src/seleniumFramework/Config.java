package seleniumFramework;

public class Config {
	public  String ControlsFilePath = "Framework/TestData/Controls.xlsx";
	public  String TestDataFilePath = "Framework/TestData/TestData.xlsx";
	public  BrowserType browserName = BrowserType.Firefox;
	protected static long iShortWait = 10;
	protected static long iLongWait = 40;
	protected String DriversPath = "Framework/dependencies/";
	protected  String appURL = "";
	
	public String getAppURL() {
		return appURL;
	}
}
