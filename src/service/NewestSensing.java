package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.MySqlConnectionHelper;

import po.SensingEntry;
import utility.BaseUtil;
import utility.SenseDataFilter;
import flex.data.DataService;
import flex.messaging.log.Log;

/**
 * 将最新的数据取出
 */
public class NewestSensing {
	public static List<SensingEntry> newestSensing;
	private SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");  

	public NewestSensing() {
		newestSensing = getNewestSensings();
	}

	public void updateNewestSensings(ResultSet rs) throws Exception {

		for (SensingEntry tempEntry : newestSensing) {

			if (BaseUtil.StringSafe(String.valueOf(tempEntry.getnodeid()))
					.equals(rs.getString("Moteid_ID"))) {

				tempEntry.setTimestampArrive_TM(rs.getDate("TimestampArrive_TM"));
				tempEntry.setLight(rs.getFloat("light"));
				tempEntry.setHumidity(rs.getFloat("humidity"));
				tempEntry.setTemperature(rs.getFloat("temperature"));
				tempEntry.setCo2(rs.getFloat("co2"));
				tempEntry.setType(rs.getInt("NodeType"));
			//	System.out.println(tempEntry);

				break;
			}
		}
	}
	public void deleteNewestSensings(String Moteid_ID) {

		for (SensingEntry tempEntry : newestSensing) {

			if (BaseUtil.StringSafe(String.valueOf(tempEntry.getnodeid()))
					.equals(Moteid_ID)) {
				newestSensing.remove(tempEntry);
				break;
			}
		}
	}
	public List<SensingEntry> getNewestSensings() {

		List<SensingEntry> list = new ArrayList<SensingEntry>();
		Connection c = null;
		Statement s = null;
		try {
			c = MySqlConnectionHelper.getConnection();
			s = c.createStatement();
			String sql = "SELECT * FROM newestSensings where Moteid_ID in ( SELECT Moteid_ID FROM NodeInformation ) ORDER BY Moteid_ID ";
			ResultSet rs = s.executeQuery(sql);
		//	 System.out.println("sql:"+sql);
			while (rs.next()) {

				SensingEntry senseEntry = getSensingEntryInstance(rs);
				if (senseEntry == null) {

					continue;
				}
				
				list.add(senseEntry);
			}
         // System.out.println("Sensing Entry Number:"+list.size());
		} catch (Exception e) {
			e.printStackTrace();
			Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", e);
		//	 System.out.println("Sensing Entry Number:"+list.size());
		} finally {
			MySqlConnectionHelper.closeStatement(s);
			MySqlConnectionHelper.close(c);
		}
		return list;
	}

	private SensingEntry getSensingEntryInstance(ResultSet rs) throws Exception {
		SensingEntry senseEntry = null;	
		if (rs != null) {

		/*	if (!SenseDataFilter.isUsable(rs)) {

				return senseEntry;
			}*/

			senseEntry = new SensingEntry(rs.getInt("Id"),
					rs.getInt("Cluster_ID"), rs.getInt("Moteid_ID"),
					sDateFormat.parse(sDateFormat.format(rs.getTimestamp("TimestampArrive_TM")))
									, rs.getFloat("temperature"),
					rs.getFloat("humidity"), rs.getFloat("light"),
					rs.getFloat("co2"),rs.getInt("NodeType"));
			 
		}

		return senseEntry;
	}
}