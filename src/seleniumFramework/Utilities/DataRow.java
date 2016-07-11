package seleniumFramework.Utilities;
import static seleniumFramework.Utilities.Environment.*;
import java.util.ArrayList;

public class DataRow {
	public static String Value(String strFieldName) {
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
	
	public static boolean Exist(String strFieldName){
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
