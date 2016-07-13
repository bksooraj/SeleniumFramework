package seleniumFramework.AppSpecific.BaseClass;

import seleniumFramework.ObjectFunctions;
import seleniumFramework.SeleniumEngine;

public class Page {
	public SeleniumEngine sEngine;
	public ObjectFunctions Operations;
	public Page(SeleniumEngine sEngine) {
		this.Operations = sEngine.Operations;
		// TODO Auto-generated constructor stub
	}
}
