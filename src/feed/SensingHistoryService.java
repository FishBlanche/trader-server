package feed;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import po.SensingEntry;
import utility.SenseDataFilter;
import dao.MySqlConnectionHelper;
import flex.data.DataService;
import flex.messaging.log.Log;
/**
 * 获取有效时间内所有的历史数据，客户端将通过RMI机制调用此方法以获得消息进行数据展示
 */
public class SensingHistoryService {
	
	private SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");  

	/**
	 * 通过Moteid_ID获取该节点所有历史数据
	 * 
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public List<SensingEntry> getMoteHistoryByMoteID(java.util.Map para)
			throws Exception {
		java.util.Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		int MoteID = Integer.parseInt(para.get("MoteID").toString());
		int starty = Integer.parseInt(para.get("starty").toString());
		int startm = Integer.parseInt(para.get("startm").toString());
		int startd = Integer.parseInt(para.get("startd").toString());
		int endy = Integer.parseInt(para.get("endy").toString());
		int endm = Integer.parseInt(para.get("endm").toString());
		int endd = Integer.parseInt(para.get("endd").toString());

		// Date start = new Date(para.get("start"));
		// start.setTime(.fasttime);
		@SuppressWarnings("deprecation")
		Date start = new Date(starty - 1900, startm - 1, startd);
		@SuppressWarnings("deprecation")
		Date end = new Date(endy - 1900, endm - 1, endd);

		List<SensingEntry> list = new ArrayList<SensingEntry>();

		Connection c = null;

		try {
			c = MySqlConnectionHelper.getConnection();
			Statement s = c.createStatement();
			/*
			 * ResultSet rs =
			 * s.executeQuery("SELECT * FROM SimpleSensing where Moteid_ID="+
			 * MoteID + " and TimestampArrive_TM>"+start+
			 * " and TimestampArrive_TM<"+end +" ORDER BY Id");
			 */
			String sql = "SELECT * FROM SimpleSensing where Moteid_ID='"
					+ MoteID + "' and TimestampArrive_TM> '" + start
					+ "' and TimestampArrive_TM< '" + end
					+ "' ORDER BY TimestampArrive_TM desc";
			ResultSet rs = s.executeQuery(sql);
			// System.out.println(sql);
			while (rs.next()) {
				SensingEntry senseEntry = this.getSensingEntryInstance(rs);

				if (senseEntry == null) {

					continue;
				}

				list.add(senseEntry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", e);
		} finally {
			MySqlConnectionHelper.close(c);
		}
		return list;
	}

	/**
	 * 通过Moteid_ID获取该节点在一定时间内的所有历史数据
	 * 
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public List<SensingEntry> getFixedNumberMoteHistoryByMoteID(
			java.util.Map para) throws Exception {
		java.util.Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		int MoteID = Integer.parseInt(para.get("MoteID").toString());

		int days = Integer.parseInt(para.get("days").toString());
		List<SensingEntry> list = new ArrayList<SensingEntry>();
		Connection c = null;

		try {
			c = MySqlConnectionHelper.getConnection();
			Statement s = c.createStatement();
			/*
			 * ResultSet rs =
			 * s.executeQuery("SELECT * FROM SimpleSensing where Moteid_ID="+
			 * MoteID + " and TimestampArrive_TM>"+start+
			 * " and TimestampArrive_TM<"+end +" ORDER BY Id");
			 */
			String sql = "SELECT * FROM SimpleSensing where Moteid_ID='"
					+ MoteID
					+ "' "
					+ "and TimestampArrive_TM >TIMESTAMP(DATE_SUB(NOW(), INTERVAL "
					+ days + " day))";
			ResultSet rs = s.executeQuery(sql);
		//	 System.out.println(sql);
			while (rs.next()) {
				SensingEntry senseEntry = this.getSensingEntryInstance(rs);

				if (senseEntry == null) {
					
					continue;
				}

				list.add(senseEntry);
			}
		//	 System.out.println("list.size()"+list.size());
		} catch (SQLException e) {
			e.printStackTrace();
			Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", e);

		} finally {
			MySqlConnectionHelper.close(c);
		}
		return list;
	}

	/**
	 * 通过Moteid_ID获取该节点在一定时间内已经经过过滤的所有历史数据
	 * 
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public List<SensingEntry> getFixedNumberMoteHistoryByMoteIDdesc(
			java.util.Map para) throws Exception {
		java.util.Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		int MoteID = Integer.parseInt(para.get("MoteID").toString());

		int days = Integer.parseInt(para.get("days").toString());
		List<SensingEntry> list = new ArrayList<SensingEntry>();
		Connection c = null;
		try {
			c = MySqlConnectionHelper.getConnection();
			Statement s = c.createStatement();
			/*
			 * ResultSet rs =
			 * s.executeQuery("SELECT * FROM SimpleSensing where Moteid_ID="+
			 * MoteID + " and TimestampArrive_TM>"+start+
			 * " and TimestampArrive_TM<"+end +" ORDER BY Id");
			 */
			String sql = "SELECT * FROM SimpleSensing where Moteid_ID='"
					+ MoteID
					+ "' "
					+ "and TimestampArrive_TM >TIMESTAMP(DATE_SUB(NOW(), INTERVAL "
					+ days + " day)) ORDER BY TimestampArrive_TM desc";
			ResultSet rs = s.executeQuery(sql);
	//		 System.out.println(sql);
			while (rs.next()) {
				SensingEntry senseEntry = this.getSensingEntryInstance(rs);

				if (senseEntry == null) {

					continue;
				}
				list.add(senseEntry);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			Log.getLogger(DataService.LOG_CATEGORY).error("Got exception: ", e);
		} finally {
			MySqlConnectionHelper.close(c);
		}
		return list;
	}

	/**
	 * 对数据集进行过滤，如果历史数据中不符合指定要求的，就不进行获取
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private SensingEntry getSensingEntryInstance(ResultSet rs) throws Exception {
		SensingEntry senseEntry = null;

		if (rs != null) {

			if (!SenseDataFilter.isUsable(rs)) {
				// System.out.println("senseEntry == null");
				return senseEntry;
			}

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