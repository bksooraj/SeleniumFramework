package seleniumFramework;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import Exception.FilloException;
import Fillo.Connection;
import Fillo.Fillo;
import Fillo.Recordset;

public class ExcelUtils {
	private Connection con;
	private Fillo xlCon;
	private Recordset objRecordSet;

	/**
	 * <b>open</b> Opens Connection to Excel file
	 * 
	 * @param strFileName
	 *            - File path should be provided
	 * @return Returns <i>true</i> if the connection is successful<br>
	 *         Returns <i>false</i> if the connection is failed.
	 */
	public boolean open(String strFileName) {
		try {
			xlCon = new Fillo();
			con = xlCon.getConnection(strFileName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <b>query</b> Query is executed against connection and Fillo.Recordset is
	 * returned.
	 * 
	 * @param strQuery
	 *            - Sql query should be passed
	 * @return Fillo.Recordset is returned on successful execution of the query.
	 */
	public Recordset query(String strQuery) {
		try {
			Reporter.Log("Querying - " + strQuery);
			objRecordSet = con.executeQuery(strQuery);
			return objRecordSet;
		} catch (FilloException e) {
			Reporter.Report(String.format("Error occured while Querying - '%s'", strQuery), false);
			e.printStackTrace();
			return null;
		}
	}

	public int insert(String strQuery) {
		try {
			Reporter.Log("inserting - " + strQuery);
			return con.executeUpdate(strQuery);
		} catch (FilloException e) {
			e.printStackTrace();
			Reporter.Report(String.format("Error occured while inserting - '%s'", strQuery), false);
			return 0;
		}
	}

	public int updateEnvironmentVariables(String strTestID, String strSheetName, Boolean blnRequireAll) {
		Hashtable<String, String> environmentData = Environment.getHashTable();
		Enumeration<String> arrKeys = environmentData.keys();
		StringBuilder strUpdateQuery = new StringBuilder();
		StringBuilder strUnlistedColumns = new StringBuilder();
		try {
			ArrayList<String> arrFieldNames = this.objRecordSet.getFieldNames();
			String strElem = null;
			do {
				try {
					strElem = null;
					strElem = arrKeys.nextElement();
				} catch (Exception e1) {
					break;
				}
				if (arrFieldNames.contains(strElem)) {
					strUpdateQuery.append(strElem).append("='").append(environmentData.get(strElem)).append("',");
				} else {
					if(blnRequireAll)
					strUnlistedColumns.append(strElem).append("=").append(environmentData.get(strElem)).append(",");
				}
			} while (strElem != null);

			StringBuilder strFinalQuery = new StringBuilder();
			if (strUpdateQuery.length() > 0) {
				strFinalQuery.append(strUpdateQuery.substring(0, strUpdateQuery.length() - 1));
				Reporter.Log("Columns-" + strFinalQuery.toString());
			}
			if (strUnlistedColumns.length() > 0) {
				if (strFinalQuery.length() > 0) {
					strFinalQuery.append(",");
				} 
				strFinalQuery.append(" Description='");
				
				strFinalQuery.append(strUnlistedColumns.substring(0, strUnlistedColumns.length() - 2)).append("'");
			}
			Reporter.Log(strFinalQuery.toString());
			return con.executeUpdate(String.format("update %s set %s where TestID='%s'", strSheetName,
					strFinalQuery.toString(), strTestID));
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Closes the associated connection
	 */
	public void close() {
		try {
			con.close();
			con = null;
		} catch (Exception e) {

		}
	}

}
