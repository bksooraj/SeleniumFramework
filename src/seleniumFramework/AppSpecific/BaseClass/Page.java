package seleniumFramework.AppSpecific.BaseClass;

import seleniumFramework.DataRow;
import seleniumFramework.ObjectFunctions;
import seleniumFramework.SeleniumEngine;
import seleniumFramework.WinObjectsFunctions;

public class Page {
	public SeleniumEngine sEngine;
	public ObjectFunctions Operations;
	public DataRow DataRow;
	public WinObjectsFunctions WinOperations;
	public Page(SeleniumEngine sEngine) {
		this.sEngine = sEngine;
		this.Operations = sEngine.Operations;
		this.DataRow = sEngine.DataRow;
		this.WinOperations = sEngine.WinOperations;
		// TODO Auto-generated constructor stub
	}
}
