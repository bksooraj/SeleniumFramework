package seleniumFramework;

import static seleniumFramework.Reporter.handleException;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ObjectFunctions {
	protected Controls controlObj;
	SeleniumEngine sEngine;
	protected static boolean Optional = false;

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
		sEngine.Environment.controlObj = controlObj;

		sEngine.WinOperations = new WinObjectsFunctions(sEngine);
		// sEngine.Optional = new Optional(sEngine);
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
		Reporter.Log(String.format("ObjectFunctions.SetText(String,String):Set '%s' in '%s' object", strValue,
				strObjectName));
		strValue = TextUtilities.fnParseData(strValue, sEngine.DataRow);
		Reporter.Log(String.format("Set '%s' in '%s' object", strValue, strObjectName));
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
		String strVal = sEngine.DataRow.Value(strObjectName);
		Reporter.Log(String.format("ObjectFunctions.SetText(String):Set '%s' into '%s' object", strVal, strObjectName));
		SetText(strObjectName, strVal);
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
		if (strValue.isEmpty())
			return true;

		if (objReqElement == null) {
			Reporter.Report(String.format("'%s' object is null, unable to set '%s'", strObjectName, strValue), false);
			return true;
		} else {
			Reporter.Log(objReqElement.getTagName().concat(" tag name for given element."));
		}
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
		String strEscapedValue = TextUtilities.fnEscapeForXpath(strValue);
		WebElement optElement;
		try {
			Thread.sleep(500);

			optElement = objReqElement
					.findElement(By.xpath(String.format("./option/./text()[contains(.,%s)]/..", strEscapedValue)));
			if (optElement.getText().replace("\n\r", "").trim().equalsIgnoreCase(strValue)) {
				optElement.click();
				Reporter.Log("DDL-Selection1");
				return true;
			} else {
				throw new Exception(String.format("'%s' not found in given object.", strValue));
			}
		} catch (Exception e) {
			try {
				optElement = objReqElement
						.findElement(By.xpath(String.format(".//Option[text()=%s]", strEscapedValue)));
				optElement.click();
				Reporter.Log("DDL-Selection2");
				return true;
			} catch (Exception e2) {
				try {
					optElement = objReqElement.findElement(By.xpath(String.format(".//Option[.=%s]", strEscapedValue)));
					optElement.click();
					Reporter.Log("DDL-Selection3");
					return true;
				} catch (Exception e3) {
					try {
						optElement = objReqElement.findElement(By.xpath(String.format(".//Option[%s]", strValue)));
						optElement.click();
						Reporter.Log("DDL-Selection4");
						return true;
					} catch (Exception e4) {
						try {
							optElement = objReqElement
									.findElement(By.xpath(String.format(".//Option[@value=%s]", strEscapedValue)));
							optElement.click();
							Reporter.Log("DDL-Selection5");
						} catch (Exception e5) {

						}
						Reporter.Log("DDL-Selection5- Unable to select dropdown");
						throw e4;
					}
				}
			}
		}
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
		switch(objReqElement.getTagName().toUpperCase().trim()) {
		case "SELECT":
		case "INPUT":
			return objReqElement.getAttribute("value");
		default:
			return objReqElement.getText();
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

	public static void Wait(int iMilliSeconds) {
		try {
			Thread.sleep(iMilliSeconds);
		} catch (InterruptedException e) {
		}
	}

}
