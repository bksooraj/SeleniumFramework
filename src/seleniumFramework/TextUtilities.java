package seleniumFramework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtilities {
	public static String fnParseData(String strText, DataRow dataRow) {
		String mySentence = strText;
		String regEx = "\\[(.*?)\\]";
		Pattern p = Pattern.compile(regEx);
		String strToBeParsed;
		Matcher m;
		int counter = 0;
		do {
			counter++;
			p = Pattern.compile(regEx);
			m = p.matcher(mySentence);
			while (m.find()) {
				strToBeParsed = m.group(1);
				if (strToBeParsed.contains("[")) {
					strToBeParsed = strToBeParsed.substring(strToBeParsed.indexOf("[") + 1);
				} else {
					strToBeParsed = m.group(1);
				}
				strToBeParsed = "[" + strToBeParsed + "]";
				mySentence = mySentence.replace(strToBeParsed, fnParse(strToBeParsed, dataRow));

			}
			m = p.matcher(mySentence);
		} while (m.find() && counter < 10);

		return mySentence;

	}

	private static String fnParse(String strText, DataRow dataRow) {
		String retText = strText.substring(1, strText.length() - 1);

		if (Environment.Exist(retText)) {
			retText = Environment.Value(retText);
		} else if (dataRow.Exist(retText)) {
			retText = dataRow.Value(retText);
		} else if (retText.startsWith("RANDOM")) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String stDate = dateFormat.format(new Date());
			StringBuilder sBuilder = new StringBuilder();
			if (retText.toLowerCase().contains("string")) {
				for (char s : stDate.toCharArray()) {
					sBuilder.append((char) (Character.getNumericValue(s) + 65));
				}
				retText = sBuilder.toString();
			} else {
				retText = stDate.toString();
			}
		} else {
			retText = strText;
		}
		return retText;
	}

	public static String fnEscapeForXpath(String strText) {
		String strEscapeText = strText;
		if (strEscapeText.contains("'")) {
			StringTokenizer st = new StringTokenizer(strEscapeText, "'");
			StringBuilder strEscapedText = new StringBuilder("concat(");
			while (st.hasMoreTokens()) {
				strEscapedText.append("'").append(st.nextToken()).append("',").append("\"'\"").append(",");
			}
			String sEscapedText = strEscapedText.substring(0, strEscapedText.length() - 5).concat(")");
			return sEscapedText;
		} else if (strEscapeText.contains("\"")) {
			StringTokenizer st = new StringTokenizer(strEscapeText, "\"");
			StringBuilder strEscapedText = new StringBuilder("concat('");
			while (st.hasMoreTokens()) {
				strEscapedText.append("'").append(st.nextToken()).append("',").append("'\"'").append(",");
			}
			String sEscapedText = strEscapedText.substring(0, strEscapedText.length() - 5).concat(")");
			return sEscapedText;
		} else {
			return "'".concat(strEscapeText).concat("'");
		}
	}

}
