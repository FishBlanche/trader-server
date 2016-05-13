package utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import cron.DataSourceServlet;
import po.NodeInformationBean;
import po.NodeTypeBean;
import po.SensingEntry;
import po.SensorTypeBean;
import service.NodeDBBean;

/**
 * 主要包含对历史数据的过滤，以及对限定条件的信息的获取，为工具类
 */
public class SenseDataFilter {

	/**
	 * 修改结果集中超出温度范围的传感器温度，将其置为无效
	 * 
	 * @param rs
	 */
	public static void senseDataFiltering(List<SensingEntry> rs) {

		for (int i = 0; i < rs.size(); i++) {
			if (rs.get(i).getTemperature() < 0
					|| rs.get(i).getTemperature() > 50) {
				rs.get(i).setTemperature(-1);
			}
		}
	}

	/**
	 * 根据节点ID获取对应该节点的所有的传感器
	 * 
	 * @param Moteid_ID
	 * @return
	 */
	public static List<String> sensorListFilter(String Moteid_ID) {
		List<String> list = new ArrayList<String>();

		NodeInformationBean nodeInfoBean = DataSourceServlet.getNodeDBBean()
				.findNodeInfoBean(Moteid_ID);
		if (nodeInfoBean != null) {
		//	System.out.println("nodeInfoBean != null");
			NodeTypeBean nodeTypeBean = DataSourceServlet.getNodeDBBean()
					.findNodeTypeBean(nodeInfoBean.getNodeType());
			if (nodeTypeBean != null) {
			//	System.out.println("nodeTypeBean != null"+nodeTypeBean
				//		.getSensorTypeListBean().size());
				for (SensorTypeBean tempBean : nodeTypeBean
						.getSensorTypeListBean()) {

					list.add(tempBean.getSensorName());
				}
			}
		}
		return list;
	}

	/*
	 * If type is equaled to "low", then call the getRangeLow() method. vice
	 * versa.
	 */
	public static float getRange(String sensorName, String type) {
		NodeDBBean nodeDB = DataSourceServlet.getNodeDBBean();
		SensorTypeBean sensorType = nodeDB.findSensorTypeBean(sensorName);

		if (sensorType != null) {

			String range = "";
			/*
			 * System.out.println("sensorName : " + sensorName + ", low = " +
			 * sensorType.getRangeLow() + ", high = " +
			 * sensorType.getRangeHigh() + ".");
			 */
			if ("low".equals(type)) {

				range = BaseUtil.StringSafe(sensorType.getRangeLow());
			} else if ("high".equals(type)) {

				range = BaseUtil.StringSafe(sensorType.getRangeHigh());
			}
			if (range.equals("")) {

				return 0;
			} else {

				return Float.valueOf(range);
			}
		}

		return 0;
	}

	/**
	 * 判断结果集中的数据是否有效，若无效，则返回false， vice versa.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static boolean isUsable(ResultSet rs) throws SQLException {
		if (rs != null) {

			List<String> list = SenseDataFilter.sensorListFilter(rs
					.getString("Moteid_ID"));
			/*
			 * System.out.println("-----MyTest-------");
			 * System.out.println(list);
			 * System.out.println("--------------------");
			 */
			float low_range = 0, high_range = 0;
			//System.out.println("list "+list.size());
			for (String sensorName : list) {
			// 	System.out.println("sensorName  "+sensorName);
				low_range = SenseDataFilter.getRange(sensorName, "low");
				high_range = SenseDataFilter.getRange(sensorName, "high");
			// 	System.out.println("sensorName  "+sensorName+","+low_range+","+high_range);
				if(!sensorName.equals("temperature")&&!sensorName.equals("humidity")&&!sensorName.equals("light"))
				{
					 sensorName="co2";
				}
				if (!BaseUtil.isQualified(rs.getFloat(sensorName), low_range,
						high_range)) {
				//	System.out.println("sensorName  "+sensorName);
					return false;
				}
				
			}
			return true;
		}
		return false;
	}
}
