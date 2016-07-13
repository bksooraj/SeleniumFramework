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
				System.out.println(curRow.getField(strFieldName).toString());
				return curRow.getField(strFieldName).toString();
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
