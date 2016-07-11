package seleniumFramework.Utilities;

public class Reporter {
	public static void Log(String s) {
		if (Environment.debug) {
			System.out.println(s);
		}
	}

	public static void Report(String strDescription) {
			System.out.println(strDescription);
	}
}
