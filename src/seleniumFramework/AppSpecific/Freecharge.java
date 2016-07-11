package seleniumFramework.AppSpecific;
import static seleniumFramework.Utilities.Environment.*;

import seleniumFramework.Utilities.DataRow;
import seleniumFramework.Utilities.Reporter;
public class Freecharge {
	public static class FreechargeHome {
		public static void fnLogin() {
			ObjectFunction.Click("btnLoginRegister");
			ObjectFunction.SetText("txtUsername", DataRow.Value("Username"));
			ObjectFunction.SetText("txtPassword", DataRow.Value("Password"));
			ObjectFunction.Click("btnLogin");
		}
		
		
		public static void fnValidateBalance() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Reporter.Report(ObjectFunction.getText("lblBalance"));
		}
		
		
		public static void fnLogout() {
			ObjectFunction.Click("imgUser");
			ObjectFunction.Click("lnkLogout");
		}
	}
}
