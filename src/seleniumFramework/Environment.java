package seleniumFramework;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;

public class Environment {

	private static Hashtable<String, String> environmentDictionary = new Hashtable<>();

	protected static Hashtable<String, String>  getHashTable() {
		return environmentDictionary;
	}
	
 	public static String Value(String strKey) {
		if (environmentDictionary.containsKey(strKey)) {
			return environmentDictionary.get(strKey).toString();
		} else {
			return "[".concat(strKey).concat("]");
		}
	}

	public static void Add(String strKey, String strValue) {
		environmentDictionary.put(strKey, strValue);
	}

	public static boolean Exist(String strKey) {
		return environmentDictionary.contains(strKey);
	}

	public String currentRecord;
	public String appName;

	protected ObjectFunctions ObjectFunction;
	protected Controls controlObj;
	public WebDriver driver;
	// public static WebDriver driver;
}
