package seleniumFramework;

import org.junit.Assert;

public class Reporter {
	public static boolean debug;
	Environment Environment;

	public static void Log(String s) {
		if (debug) {
			System.out.println(s);
		}
	}

	public static void Report(String strDescription, boolean resStatus) {
		if (resStatus) {
			System.out.println(strDescription);
		} else {
			Assert.fail(strDescription);
		}
	}

	protected static void Report(String strDescription, boolean resStatus, Exception e) {
		if (resStatus) {
			System.out.println(strDescription);
		} else {
			Assert.fail(strDescription);
		}
	}
}
