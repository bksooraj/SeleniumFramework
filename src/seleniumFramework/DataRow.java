package seleniumFramework;

import java.util.ArrayList;

import Fillo.Recordset;

public class DataRow {
	private Recordset curRow;

	private int iInstance = -1;

	public DataRow(Recordset curRow) {
		this.curRow = curRow;
	}

	/**
	 * Sets the instance to be taken from Value separated by pipe symbol '|'
	 * <br>
	 * Eg: Value1 - 1|Value2 - 2
	 * 
	 * @param iInstance
	 *            Instance starts with 1 and data from instance to instance has
	 *            to be separated with |
	 */
	public void setInstance(int iInstance) {
		this.iInstance = iInstance;
	}

	/**
	 * Returns the value from Test data file based on the column name. <br>
	 * - Parses the String and replaces the data between [ ] with Environment
	 * variables text and Datarow values. <br>
	 * - Returns the instance data based on setInstance value. -1 will return
	 * entire text, and remaining based on the instance value
	 * 
	 * @param strFieldName
	 *            Column name of the test data file
	 * @return Returns the text after parsing the String from corresponding
	 *         column.
	 */
	public String Value(String strFieldName) {
		System.out.println(strFieldName);

		Reporter.Log(String.format("DataRow.Value(String)[1]:Retrieving data from '%s' column of Test data file",
				strFieldName));
		try {
			ArrayList<String> arrFieldNames = curRow.getFieldNames();
			if (arrFieldNames.contains(strFieldName)) {
				String strValue = curRow.getField(strFieldName).toString();
				strValue = TextUtilities.fnParseData(strValue, this);
				Reporter.Log(
						String.format("'%s' is retrieved from '%s' column of Test data file", strValue, strFieldName));
				if (this.iInstance == -1) {
					return strValue;
				} else {
					try {
						String[] arrVals = strValue.split("\\|");
						for (String s : arrVals) {
							Reporter.Log(String.format("%s", s));
						}
						String retVal = arrVals[this.iInstance];
						Reporter.Log(String.format("'%d' instance '%s' is taken from '%s' is taken.", this.iInstance,
								retVal, strValue));
						return retVal;
					} catch (Exception e) {
						e.printStackTrace();
						return "";
					}
				}
			} else {
				Reporter.Log(strFieldName + " is not present in Test data file");
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean Exist(String strFieldName) {
		try {
			ArrayList<String> arrFieldNames = curRow.getFieldNames();
			if (arrFieldNames.contains(strFieldName)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
