package seleniumFramework;
import java.util.ArrayList;

import Fillo.Recordset;

public class DataRow {
	private Recordset curRow;
	public DataRow (Recordset curRow) {
		this.curRow = curRow;
	}
	public String Value(String strFieldName) {
		try {
			ArrayList<String> arrFieldNames = curRow.getFieldNames();
			if (arrFieldNames.contains(strFieldName)) {
				String strValue = curRow.getField(strFieldName).toString();
				strValue = TextUtilities.fnParseData(strValue,this);
				return strValue;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public boolean Exist(String strFieldName){
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
