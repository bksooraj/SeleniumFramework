package seleniumFramework;

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
			// TODO Auto-generated catch block
			Reporter.Report(String.format("Error occured while Querying - '%s'", strQuery), false);
			e.printStackTrace();
			return null;
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
