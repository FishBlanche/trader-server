package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import flex.data.DataService;
import flex.messaging.log.Log;

public class MySqlConnectionHelper {
	// private static String url =
	// "jdbc:mysql://s.sensehuge.com:3306/Citysee_DATA?user=admin&password=citysee&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true";
	private static MySqlConnectionHelper instance;

	private static DataSource ds;

	public static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
				connection=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", e);
		}
	}

	public static void closeStatement(Statement sm) {
		try {
			if (sm != null) {
				sm.close();
				sm = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", e);
		}
	}

	public static void closeResultSet(ResultSet st) {
		try {
			if (st != null) {
				st.close();
				st = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", e);
		}
	}

	// Code to use JDBC driver
	// If using JNDI comment out everything below here.

	/*
	 * private MySqlConnectionHelper() { try {
	 * Class.forName("com.mysql.jdbc.Driver"); url =
	 * "jdbc:mysql://s.sensehuge.com:3306/Citysee_DATA?user=admin&password=citysee&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true"
	 * ; } catch (Exception e) { e.printStackTrace(); //
	 * Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", // e); }
	 * }
	 */

	private MySqlConnectionHelper() {
		try {
			InitialContext ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:comp/env");
			ds = (DataSource) envContext.lookup("jdbc/Citysee_DATA");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {

			instance = new MySqlConnectionHelper();
		}
		try {
			 
			return ds.getConnection();

		} catch (SQLException e) {
			System.out.println("Error here.");
			e.printStackTrace();
			throw e;
		}
	}
}
