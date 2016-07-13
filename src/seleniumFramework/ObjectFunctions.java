package seleniumFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import seleniumFramework.TextUtilities;

public class ObjectFunctions {
	Controls controlObj;
	SeleniumEngine sEngine;
	public boolean Optional = false;

	public ObjectFunctions(SeleniumEngine sEngine) throws Exception {
		this.sEngine = sEngine;
		controlObj = new Controls(sEngine);
	}

	public boolean SetText(String strObjectName, String strValue) {
		WebElement objReqElement = this.controlObj.getControl(strObjectName);
		strValue = TextUtilities.fnParseData(strValue, sEngine.DataRow);
		try {
			objReqElement.sendKeys(strValue);
			Reporter.Report(String.format("'%s' is being entered in '%s'", strValue, strObjectName), true);
			return true;
		} catch (Exception e) {
			Reporter.Report(String.format("Error occured while entering '%s' in object '%s'", strValue, strObjectName),
					false);
			return false;
		}
	}

	public boolean SetText(String strObjectName) {
		SetText(strObjectName, sEngine.DataRow.Value(strObjectName));
		return true;
	}

	public static boolean SetText(WebElement objReqElement, String strValue) {
		objReqElement.sendKeys(strValue);
		return true;
	}

	public boolean SelectByVisibleText(String strObjectName, String strValue) {

		WebElement objReqElement = controlObj.getControl(strObjectName);
		Select objSelect = (Select) objReqElement;
		objSelect.selectByVisibleText(strValue);
		return true;
	}

	public static boolean SelectByVisibleText(WebElement objReqElement, String strValue) {

		Select objSelect = (Select) objReqElement;
		objSelect.selectByVisibleText(strValue);
		return true;

	}

	public boolean Click(String strObjectName) {
		WebElement objReqElement = controlObj.getControl(strObjectName);
		objReqElement.click();
		return true;
	}

	public static boolean Click(WebElement objReqElement) {
		objReqElement.click();
		return true;
	}
	
	public boolean ClickLink(String strLinkText) {
		WebElement objReqElement = this.sEngine.Environment.driver.findElement(By.linkText(strLinkText));
		objReqElement.click();
		return true;
	}

	public String getText(String strObjectName) {

		WebElement objReqElement = controlObj.getControl(strObjectName);
		return objReqElement.getText();

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
