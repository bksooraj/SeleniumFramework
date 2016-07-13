package seleniumFramework;

import static seleniumFramework.TextUtilities.fnParseData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Fillo.Connection;
import Fillo.Recordset;

/**
 * Controls Class will provide methods to get Controls from Controls sheet and
 * form WebElements.
 * 
 * @author SoorajKumarM
 * @version 1.0
 */
class Controls {
	private WebDriver driver;
	private Connection con;
	private ExcelUtils xlBook;
	private SeleniumEngine sEngine;

	public Controls(SeleniumEngine sEngine) throws Exception {
		this.sEngine = sEngine;
		this.driver = this.sEngine.Environment.driver;
		xlBook = new ExcelUtils();
		if (!xlBook.open(sEngine.Config.ControlsFilePath)) {
			throw new Exception("Unable to get Connection to ".concat(sEngine.Config.ControlsFilePath));
		}
	}

	/**
	 * 
	 * Gets the hierarchy of the Object name required This will take value from
	 * Controls sheet of Controls Excel and searches given logical name using
	 * LogicalName column. The objects in Controls sheet should be unique. If an
	 * object needs Parent Object the Parent object name should be specified in
	 * Child Object's ParentObject column.
	 * 
	 * @author SoorajKumarM
	 * @param controlLogicalName
	 *            - Logical name of the Test Object
	 * @return Returns the Hierarchy of the Object from Child to Parent object
	 */
	private String getHierarchy(String controlLogicalName) {
		String strHierarchy = "";
		Recordset objRecordSet;
		try {
			objRecordSet = xlBook.query("Select * from Controls where LogicalName='" + controlLogicalName + "'");
			if (objRecordSet.next()) {
				Reporter.Log("After Query " + controlLogicalName);
				strHierarchy = controlLogicalName;
				if (!objRecordSet.getField(ControlPropertyName.ParentObject.toString()).isEmpty()) {
					strHierarchy = strHierarchy + "," + this.getHierarchy(
							(objRecordSet.getField(ControlPropertyName.ParentObject.toString()).toString()));
				}
				return strHierarchy;
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	public WebElement getControl(String controlLogicalName) {
		String objectHierarchy = this.getHierarchy(controlLogicalName);
		String[] strObjects = objectHierarchy.split(",");
		WebElement curReqObject = null;
		Reporter.Log("FunctionName: getControl, Hierarchy:" + objectHierarchy);
		Reporter.Log("FunctionName: getControl, HierarchyLevels:" + strObjects.length);
		for (int i = strObjects.length - 1; i >= 0; i--) {
			curReqObject = getTestObject(strObjects[i], curReqObject);
		}
		return curReqObject;
	}

	private WebElement getTestObject(String strTestObjectName, WebElement objParent) {
		DataRow dataRow = sEngine.DataRow;
		By reqIdentification = null;
		Recordset objRecordSet;
		WebElement reqObject = null;
		try {
			objRecordSet = xlBook.query("Select * from Controls where LogicalName='" + strTestObjectName + "'");
			if (objRecordSet.next()) {
				if (!objRecordSet.getField(ControlPropertyName.Id.toString()).isEmpty()) {
					reqIdentification = By.id(fnParseData(objRecordSet.getField(ControlPropertyName.Id.toString()),dataRow));
					Reporter.Log("Function:getTestObject, Identification:" + reqIdentification.toString());
				}
				if (!objRecordSet.getField(ControlPropertyName.Name.toString()).isEmpty()) {
					reqIdentification = By
							.name(fnParseData(objRecordSet.getField(ControlPropertyName.Name.toString()),dataRow));
					Reporter.Log("Function:getTestObject, Identification:" + reqIdentification.toString());
				}
				if (!objRecordSet.getField(ControlPropertyName.TagName.toString()).isEmpty()) {
					reqIdentification = By.tagName(objRecordSet.getField(ControlPropertyName.TagName.toString()));
					Reporter.Log("Function:getTestObject, Identification:" + reqIdentification.toString());
				}
				if (!objRecordSet.getField(ControlPropertyName.LinkText.toString()).isEmpty()) {
					reqIdentification = By
							.linkText(fnParseData(objRecordSet.getField(ControlPropertyName.LinkText.toString()),dataRow));
					Reporter.Log("Function:getTestObject, Identification:" + reqIdentification.toString());
				}
				if (!objRecordSet.getField(ControlPropertyName.ClassName.toString()).isEmpty()) {
					reqIdentification = By.className(objRecordSet.getField(ControlPropertyName.ClassName.toString()));
					Reporter.Log("Function:getTestObject, Identification:" + reqIdentification.toString());
				}
				if (!objRecordSet.getField(ControlPropertyName.PartialLinkText.toString()).isEmpty()) {
					reqIdentification = By.partialLinkText(
							fnParseData(objRecordSet.getField(ControlPropertyName.PartialLinkText.toString()),dataRow));
					Reporter.Log("Function:getTestObject, Identification:" + reqIdentification.toString());
				}
				if (!objRecordSet.getField(ControlPropertyName.XPath.toString()).isEmpty()) {
					reqIdentification = By
							.xpath(fnParseData(objRecordSet.getField(ControlPropertyName.XPath.toString()),dataRow));
					Reporter.Log("Function:getTestObject, Identification:" + reqIdentification.toString());
				}
				Reporter.Log(reqIdentification.toString());

				if (objParent == null) {
					reqObject = this.driver.findElement(reqIdentification);
					Reporter.Log(strTestObjectName + " is being fetched from driver");
				} else {
					reqObject = objParent.findElement(reqIdentification);
					Reporter.Log(strTestObjectName + " is being fetched from " + objParent.toString());
				}
				if (objRecordSet.getField(ControlPropertyName.ControlType.toString()).isEmpty()) {
					Reporter.Log("Function:getTestObject, returning Object");
					return reqObject;
				} else {
					switch (objRecordSet.getField(ControlPropertyName.ControlType.toString()).toUpperCase()) {
					case "TEXTBOX":
					case "BUTTON":
					case "LINK":
					case "LISTBOX":
						break;
					case "FRAME":
						this.driver.switchTo().frame(reqObject);
					default:
					}
				}
			}
			return reqObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void finalize() {
		con.close();
	}
}
