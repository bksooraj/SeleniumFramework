package seleniumFramework.Utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import seleniumFramework.Controls;
import static seleniumFramework.Utilities.Environment.*;
public class ObjectFunctions {
	
	

	private void getControls() throws Exception {
			
	}

	public boolean SetText(String strObjectName, String strValue) {
		try {
			this.getControls();
			WebElement objReqElement = controlObj.getControl(strObjectName);
			Reporter.Report(strValue + " is being entered in " + strObjectName);
			objReqElement.sendKeys(strValue);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean SetText(WebElement objReqElement, String strValue) {
		try {
			objReqElement.sendKeys(strValue);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean SelectByVisibleText(String strObjectName, String strValue) {
		try {
			this.getControls();
			WebElement objReqElement = controlObj.getControl(strObjectName);
			Select objSelect = (Select) objReqElement;
			objSelect.selectByVisibleText(strValue);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean SelectByVisibleText(WebElement objReqElement, String strValue) {
		try {
			Select objSelect = (Select) objReqElement;
			objSelect.selectByVisibleText(strValue);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean Click(String strObjectName) {
		try {
			this.getControls();
			WebElement objReqElement = controlObj.getControl(strObjectName);
			objReqElement.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	

	public static boolean Click(WebElement objReqElement) {
		try {
			objReqElement.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	public String getText(String strObjectName) {
		try {
			this.getControls();
			WebElement objReqElement = controlObj.getControl(strObjectName);
			return objReqElement.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static boolean SelectRadio(java.util.List<WebElement> objReqElements, String strValue) {
		try {
			boolean blnFound = false;
			for (int i = 0; i < objReqElements.size(); i++) {
				if (objReqElements.get(i).getAttribute("value") == strValue) {
					objReqElements.get(i).click();
					blnFound = true;
					break;
				}
			}
			if (blnFound == true) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

}
