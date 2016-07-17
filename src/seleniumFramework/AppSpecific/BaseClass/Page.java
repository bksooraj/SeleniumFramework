package seleniumFramework.AppSpecific.BaseClass;

import seleniumFramework.DataRow;
import seleniumFramework.ObjectFunctions;
import seleniumFramework.SeleniumEngine;

public class Page {
	public SeleniumEngine sEngine;
	public ObjectFunctions Operations;
	public DataRow DataRow;
	public Page(SeleniumEngine sEngine) {
		this.sEngine = sEngine;
		this.Operations = sEngine.Operations;
		this.DataRow = sEngine.DataRow;
		// TODO Auto-generated constructor stub
	}
}
