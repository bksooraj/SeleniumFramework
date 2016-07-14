package seleniumFramework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ObjectFunctions {
	protected Controls controlObj;
	SeleniumEngine sEngine;
	private static boolean Optional = false;

	public void setOptional(boolean blnOptional) {
		if (blnOptional) {
			sEngine.Environment.driver.manage().timeouts().implicitlyWait(Config.iShortWait, TimeUnit.SECONDS);
			Optional = true;
		} else {
			Optional = false;
			sEngine.Environment.driver.manage().timeouts().implicitlyWait(Config.iLongWait, TimeUnit.SECONDS);
		}
	}

	/**
	 * 
	 * @param sEngine
	 *            SeleniumEngine instance has to be passed to this constructor
	 * @throws Exception
	 *             If the Controls file is not present in
	 *             Framework/TestData/Controls.xls it throws exception <br>
	 *             If the Controls excel file is different than the above path
	 *             then assign the correct path to <br>
	 *             Config.ControlsFilePath variable
	 */
	public ObjectFunctions(SeleniumEngine sEngine) throws Exception {
		this.sEngine = sEngine;
		controlObj = new Controls(sEngine);
	}

	/**
	 * 
	 * @param strObjectName
	 *            Logical name of the object from Controls sheet
	 * @param strValue
	 *            Value to be entered in the given Object
	 * @return Returns <i>true</i> if the operation is successful and
	 *         <i>false</i> if the operation is failed.
	 */
	public boolean SetText(String strObjectName, String strValue) {
		WebElement objReqElement = this.controlObj.getControl(strObjectName);
		strValue = TextUtilities.fnParseData(strValue, sEngine.DataRow);
		return SetText(objReqElement, strObjectName, strValue);
	}

	/**
	 * Sets value taken from Corresponding column of the Test data sheet. To
	 * pickup the correct value to set, The LogicalName of the Control sheet
	 * object should match with Column name in Testdata sheet
	 * 
	 * @param strObjectName
	 *            Logical name of the object from Controls sheet
	 * @return Returns <i>true</i> if the operation is successful and
	 *         <i>false</i> if the operation is failed.
	 */
	public boolean SetText(String strObjectName) {
		SetText(strObjectName, sEngine.DataRow.Value(strObjectName));
		return true;
	}

	public boolean SetText(String... strObjectNames) {
		for (String strObjectName : strObjectNames) {
			SetText(strObjectName, sEngine.DataRow.Value(strObjectName));
		}
		return true;
	}

	/**
	 * 
	 * @param objReqElement
	 *            Web element object in which the text has to be set
	 * @param strObjectName
	 *            Logical name of the object, Just for reporting purpose
	 * @param strValue
	 *            Value to be entered in the UI Object
	 * @return Returns <i>true</i> if the operation is successful and
	 *         <i>false</i> if the operation is failed.
	 */
	public static boolean SetText(WebElement objReqElement, String strObjectName, String strValue) {
		if(strValue.isEmpty())
			return true;
		if (objReqElement.getTagName().contains("select")) {
			try {
				SelectByVisibleText(objReqElement, strValue);
				Reporter.Report(String.format("'%s' is selected in '%s'", strValue, strObjectName), true);
				return true;
			} catch (Exception e) {
				return handleException(
						String.format("Error occured while selecting '%s' in '%s'", strValue, strObjectName), e);
			}
		} else {
			try {
				objReqElement.sendKeys(strValue);
				Reporter.Report(String.format("'%s' is being entered in '%s'", strValue, strObjectName), true);
				return true;
			} catch (Exception e) {
				return handleException(
						String.format("Error occured while entering '%s' in object '%s'", strValue, strObjectName), e);
			}
		}
	}

	private static boolean SelectByVisibleText(WebElement objReqElement, String strValue) {
		Select objSelect = (Select) objReqElement;
		objSelect.selectByVisibleText(strValue);
		return true;
	}

	public boolean Click(String strObjectName) {
		WebElement objReqElement = controlObj.getControl(strObjectName);
		return Click(objReqElement, strObjectName);
	}

	/**
	 * Clicks the given WebElement object and returns <i>true</i> if successful
	 * and <i>false</i> if failed.
	 * 
	 * @param objReqElement
	 *            WebElement to be clicked has to be passed
	 * @param strObjectName
	 *            Logical name of the object, Just for reporting purpose
	 * @return Returns <i>true</i> if the operation is successful and
	 *         <i>false</i> if the operation is failed.
	 */
	public static boolean Click(WebElement objReqElement, String strObjectName) {
		try {
			objReqElement.click();
			Reporter.Report(String.format("Clicked '%s'", strObjectName), true);
			return true;
		} catch (Exception e) {
			return handleException(String.format("Error occured while clicking '%s'", strObjectName), e);
		}
	}

	public boolean ClickLink(String strLinkText) {
		// WebElement objReqElement =
		// this.sEngine.Environment.driver.findElement(By.linkText(strLinkText));
		WebElement objReqElement = (new WebDriverWait(this.sEngine.Environment.driver, 30))
				.until(ExpectedConditions.elementToBeClickable(By.linkText(strLinkText)));
		Click(objReqElement, strLinkText);
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

	/**
	 * 
	 * @param strDescription
	 *            Description to be written to the report
	 * @param e
	 *            Exception object which needs to be handled
	 * @return returns true if the step is Optional and false if the step is
	 *         Required
	 */
	private static boolean handleException(String strDescription, Exception e) {
		if (Optional) {
			Reporter.Report(strDescription, true);
			return true;
		} else {
			Reporter.Report(strDescription, false);
			return false;
		}
	}
}
