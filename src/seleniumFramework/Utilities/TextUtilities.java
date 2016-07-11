package seleniumFramework.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtilities {
	public static String fnParseData(String strText){
		String mySentence = strText;
		String regEx = "\\[(.*?)\\]";
		Pattern p = Pattern.compile(regEx);
		String strToBeParsed;
		Matcher m;
		int counter=0;
		do {
			counter++;
			p = Pattern.compile(regEx);
			m= p.matcher(mySentence);
			while(m.find()) {
				strToBeParsed = m.group(1);
				if(strToBeParsed.contains("[")){
					strToBeParsed = strToBeParsed.substring(strToBeParsed.indexOf("[")+1);
				} else {
					strToBeParsed = m.group(1);
				}
				strToBeParsed = "[" + strToBeParsed + "]";
				mySentence = mySentence.replace(strToBeParsed, fnParse(strToBeParsed));
				
			}
			m= p.matcher(mySentence);
		} while (m.find()&&counter<10);

		return mySentence;
			
		}
		
	private static String fnParse(String strText){
	String retText = strText.substring(1, strText.length()-1);
	
	if(Environment.Exist(retText)){
		retText = Environment.Value(retText);
	} else if (DataRow.Exist(retText)){
		retText = DataRow.Value(retText);
	} else {
		retText = strText;
	}
		
	Reporter.Log("Replacing"+ strText + " with " + retText);
	return retText;
}
}
