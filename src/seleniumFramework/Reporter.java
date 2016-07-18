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
			e.printStackTrace();
			Assert.fail(strDescription);
		}
	}
	
	
	/**
	 * 
	 * @param strDescription
	 *            Description to be written to the report
	 * @param e
	 *            Exception object which needs to be handled
	 * @return returns true if the step is Optional and false if the step is
	 *         Required
	 */
	protected static boolean handleException(String strDescription, Exception e) {
		if (ObjectFunctions.Optional) {
			Reporter.Report(strDescription, true);
			return true;
		} else {
			Reporter.Report(strDescription, false);
			e.printStackTrace();
			return false;
		}
	}

}
