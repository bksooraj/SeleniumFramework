package seleniumFramework.AppSpecific;
import static seleniumFramework.Utilities.Environment.ObjectFunction;

import seleniumFramework.Utilities.DataRow;


public class AppSpecific {
	
	static public class Gmail {
		static public  void fnLogin(){
			System.out.println("Login");
	
		}
	}
	
	static public class GmailHome {
		
		static public void fnSearch() {
			ObjectFunction.SetText("txtSearch", DataRow.Value("SearchText"));
		}
	}
	
	
	
}



