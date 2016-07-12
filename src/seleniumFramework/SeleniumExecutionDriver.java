package seleniumFramework;

import static seleniumFramework.Utilities.Environment.driver;

import javax.swing.JOptionPane;

import seleniumFramework.AppSpecific.Freecharge.FreechargeHome;
import seleniumFramework.Enumerators.BrowserType;
import seleniumFramework.Utilities.Environment;

//@RunWith(Parameterized.class)
public class SeleniumExecutionDriver {

	public SeleniumExecutionDriver(String curParameter) {
		// TODO Auto-generated constructor stub
		Environment.currentRecord = curParameter;
	}
	
//	@Before
	public void initialize() {
		Initializer.initialize(BrowserType.Firefox, "http://freecharge.in");
	}
	
	
//	@After
	public void quit() {
		driver.quit();
	}
	
	//@Parameters
	public static String[] getParams(){
		return new String[] {"Freecharge1", "Freecharge2"};
	}

	//@Test
	public void CheckLogin() {
		FreechargeHome.fnLogin();
		FreechargeHome.fnValidateBalance();
		FreechargeHome.fnLogout();
	//	String myInput = JOptionPane.showInputDialog("Enter your name");
		//System.out.println(myInput);
		
	//OpenSSO openSSO = new OpenSSO(driver);openSSO.fnSearch();

	// openSSO.fnLogin();
	}

	public static void main(String[] args) {
		int myInput = JOptionPane.showConfirmDialog(null, "ShowMessage");
		System.out.println(myInput);

/*
		JFrame frame = new JFrame("JFrame Example");

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		JLabel label = new JLabel("This is a label!");

		JButton button = new JButton();
		button.setText("Press me");

		panel.add(label);
		panel.add(button);

		frame.add(panel);
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	*/	/*
		 * Workbook xlBook = null; try { xlBook = new XSSFWorkbook(
		 * "E:\\Sooraj\\Sooraj\\Me\\Notes\\Eclipse SeleniumWorkSpace\\SeleniumFramework\\TestData\\Controls.xlsx"
		 * ); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } Sheet xlSheet = xlBook.getSheet("Controls");
		 * System.out.println(xlSheet.getRow(1).getCell(1));
		 */

	}

}
/*
 * Environment.Add("Name", "Sooraj"); Environment.Add("Company", "Tech Mahindra"
 * ); Environment.Add("Name", "SoorajKumar"); Environment.Add("Signature",
 * "I am [Name], and I am[NotPresent] working at [Company]");
 * System.out.println(TextUtilities.fnParseData(
 * "This is Signature '[Signature]'"));
 */
// Properties p = new Properties();

/**
 * Java Reflections - Get Methods and Invoke the methods
 */
/*
 * Class ab = Environment.class; try { Method method = ab.getMethod("Add", new
 * Class[]{String.class, String.class}); method.invoke(ab, new Object[]{"XYZ",
 * "123"});
 * 
 * method = ab.getMethod("Value", new Class[]{String.class}); Object retVal =
 * method.invoke(ab, new Object[]{"XYZ"});
 * System.out.println(retVal.toString());
 * 
 * 
 * } catch (Exception e) { System.out.println("Exception occured");
 * e.printStackTrace(); }
 */

/*
 * String mySentence = "t[es]t[This is [my] own mes]sage"; String regEx =
 * "\\[(.*?)\\]"; Pattern p = Pattern.compile(regEx); String strToBeParsed;
 * Matcher m; do { //while(Pattern.matches(regEx, mySentence).=) // {
 * System.out.println("Inside first While."); p = Pattern.compile(regEx); m=
 * p.matcher(mySentence); while(m.find()) { strToBeParsed = m.group(1);
 * if(strToBeParsed.contains("[")){ strToBeParsed =
 * strToBeParsed.substring(strToBeParsed.indexOf("[")+1); } else { strToBeParsed
 * = m.group(1); } strToBeParsed = "[" + strToBeParsed + "]"; mySentence =
 * mySentence.replace(strToBeParsed, fnParse(strToBeParsed));
 * 
 * } m= p.matcher(mySentence); } while (m.find()); // }
 * System.out.println(mySentence);
 * 
 * 
 * }
 * 
 * public static String fnParse(String strText){ String retText =
 * strText.substring(1, strText.length()-1); System.out.println("Replacing"+
 * strText + " with " + retText); return retText;
 */
