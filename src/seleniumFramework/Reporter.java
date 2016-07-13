package seleniumFramework;

public class Reporter {
	public static boolean debug;
	Environment Environment;

	public static  void Log(String s) {
		if (debug) {
			System.out.println(s);
		}
	}

	public static void Report(String strDescription, boolean resStatus) {
			System.out.println(strDescription);
	}
}
