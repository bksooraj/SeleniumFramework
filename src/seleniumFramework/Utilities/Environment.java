package seleniumFramework.Utilities;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;

import Fillo.Recordset;
import seleniumFramework.Controls;

public class Environment {
	
	private static Hashtable<String, String> environmentDictionary = new Hashtable<>();
	
	public static String Value(String strKey){
		if(environmentDictionary.containsKey(strKey)){
			return environmentDictionary.get(strKey).toString();
		} else {
			return "[".concat(strKey).concat("]");
		}
	}
	
	public static void Add(String strKey, String strValue){
		environmentDictionary.put(strKey, strValue);
	}
	
	public static boolean Exist(String strKey) {
		return environmentDictionary.contains(strKey);
	}
	
		
	public static String currentRecord;
	public static boolean debug;
	public static String appName;
	public static Recordset curRow;
	public static ObjectFunctions ObjectFunction;
	public static Controls controlObj;
	
	public static WebDriver driver;
//	public static WebDriver driver;
}
