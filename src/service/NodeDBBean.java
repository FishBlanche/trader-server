package service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import po.NodeInformationBean;
import po.NodeTypeBean;
import po.SensorTypeBean;
import utility.BaseUtil;

/**
 * 这个是application级别的缓存对象，所有的数据是缓存在此中的
 * 维护了，所有的nodeTypeBean,sensorTypeBean,以及nodeInformationBean
 */
public class NodeDBBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<NodeTypeBean> nodeTypeListBean;
	private List<SensorTypeBean> sensorTypeListBean;
	private List<NodeInformationBean> nodeInfoListBean;

	public NodeDBBean() {
		nodeTypeListBean = new ArrayList<NodeTypeBean>();
		sensorTypeListBean = new ArrayList<SensorTypeBean>();
		nodeInfoListBean = new ArrayList<NodeInformationBean>();
	}

	public NodeDBBean(Connection connection) {
		initParam(connection);
	}

	public void deleteNodeType(NodeTypeBean nodeTypeBean) {

		deleteNodeType(nodeTypeBean.getNodeType());
	}

	public void deleteNodeType(String nodeType) {

		NodeTypeBean nodeTypeBean = null;
		for (NodeTypeBean tempBean : nodeTypeListBean) {

			if (BaseUtil.StringSafe(tempBean.getNodeType()).equals(nodeType)) {

				nodeTypeBean = tempBean;
				break;
			}
		}

		for (NodeInformationBean tempBean : nodeInfoListBean) {

			if (tempBean.isEquals(nodeType)) {

				nodeInfoListBean.remove(tempBean);
				break;
			}
		}

		nodeTypeListBean.remove(nodeTypeBean);
	}

	public void deleteSensorType(String sensorName) {

		SensorTypeBean sensorTypeBean = null;

		sensorTypeBean = findSensorTypeBean(sensorName);
		if (sensorTypeBean != null) {

			sensorTypeListBean.remove(sensorTypeBean);
			for (NodeTypeBean nodeTypeBean : nodeTypeListBean) {

				nodeTypeBean.deleteSensorType(sensorTypeBean);
			}
		}
	}

	public void deleteNodeInfo(String moteid_ID) {

		for (NodeInformationBean tempBean : nodeInfoListBean) {

			if (BaseUtil.StringSafe(tempBean.getMoteid_ID()).equals(moteid_ID)) {

				nodeInfoListBean.remove(tempBean);
				return;
			}
		}
	}

	public void addNodeInfo(NodeInformationBean nodeInfoBean) {
		if (nodeInfoListBean.contains(nodeInfoBean)) {

			return;
		} else {

			nodeInfoListBean.add(nodeInfoBean);
		}
	}

	/*
	 * public List<String> addNodeType(List<Map<String, String>> dataMapList) {
	 * 
	 * StringTokenizer tokenizer = null; List<String> errorList = new
	 * ArrayList<String>(); MultiMap dataClassifiedMap = new MultiHashMap();
	 * 
	 * for (Map<String, String> tempMap : dataMapList) {
	 * 
	 * String nodeType = tempMap.get("nodeType"); String remarks =
	 * tempMap.get("remarks"); List<String> values = new ArrayList<String>();
	 * tokenizer = new StringTokenizer(tempMap.get("sensors"), ","); while
	 * (tokenizer.hasMoreElements()) {
	 * 
	 * values.add(tokenizer.nextToken()); }
	 * 
	 * if (!isContainSensorList(values)) {
	 * 
	 * errorList.add("Line " + tempMap.get("lineNum") +
	 * ": Unrecognized sensors involved!"); } else if
	 * (isContainNodeTypeList(nodeType)) {
	 * 
	 * errorList.add("Line " + tempMap.get("lineNum") + ": NodeType = " +
	 * nodeType + " has existed!"); } else {
	 * 
	 * addNodeType(new NodeTypeBean(nodeType, remarks), values); } }
	 * 
	 * return errorList; }
	 */
	public boolean isContainNodeTypeList(String nodeType) {

		if (this.findNodeTypeBean(nodeType) != null) {

			return true;
		}

		return false;
	}

	public boolean isContainMoteidList(String moteid) {

		for (NodeInformationBean tempBean : nodeInfoListBean) {

			if (BaseUtil.StringSafe(moteid).equals(tempBean.getMoteid_ID())) {

				return true;
			}
		}

		return false;
	}

	public boolean isContainSensorList(String value) {

		if (findSensorTypeBean(value) != null) {

			return true;
		}

		return false;
	}

	public boolean isContainSensorList(List<String> values) {

		for (String value : values) {

			if (!this.getSensorNameList().contains(value)) {

				return false;
			}
		}

		return true;
	}

	public void addNodeType(NodeTypeBean nodeTypeBean,
			List<String> sensorNameValues) {
		List<SensorTypeBean> sensorTypeListTempBean = new ArrayList<SensorTypeBean>();

		for (String name : sensorNameValues) {

			SensorTypeBean tempBean = findSensorTypeBean(name);
			if (tempBean != null) {

				sensorTypeListTempBean.add(tempBean);
			}
		}
		if (nodeTypeBean != null) {

			nodeTypeBean.setSensorTypeListBean(sensorTypeListTempBean);
		}
		nodeTypeListBean.add(nodeTypeBean);
	}

	public void addSensorType(SensorTypeBean sensorType) {
		if (sensorType != null) {

			sensorTypeListBean.add(sensorType);
		}
	}

	public boolean isEmpty() {
		if (this.nodeInfoListBean.size() == 0
				&& this.nodeTypeListBean.size() == 0
				&& this.sensorTypeListBean.size() == 0) {

			return true;
		}

		return false;
	}

	public List<NodeInformationBean> getNodeInfoList() {
		return nodeInfoListBean;
	}

	public List<NodeTypeBean> getNodeTypeList() {
		return nodeTypeListBean;
	}

	public void setNodeTypeList(List<NodeTypeBean> list) {
		this.nodeTypeListBean = list;
	}

	public void setSensorTypeList(List<SensorTypeBean> list) {
		this.sensorTypeListBean = list;
	}

	public void setNodeInfoList(List<NodeInformationBean> list) {
		this.nodeInfoListBean = list;
	}

	public List<SensorTypeBean> getSensorTypeList() {
		return sensorTypeListBean;
	}

	public List<String> getSensorNameList() {

		List<String> nameList = new ArrayList<String>();

		for (SensorTypeBean bean : this.getSensorTypeList()) {

			nameList.add(bean.getSensorName());
		}

		return nameList;
	}

	public NodeTypeBean findNodeTypeBean(String nodeType) {

		for (NodeTypeBean tempBean : nodeTypeListBean) {

			if (BaseUtil.StringSafe(tempBean.getNodeType()).equals(nodeType)) {

				return tempBean;
			}
		}

		return null;
	}

	/**
	 * 获取相关此sensor的所有节点名，按照node node node格式排列字符串
	 * 
	 * @param sensorName
	 * @return
	 */
	public String relatedSensor_Node(String sensorName) {

		List<String> list = relatedSensor_Node("", sensorName);

		String nodeString = "";

		for (String nodeName : list) {

			nodeString += nodeName + " ";
		}

		return nodeString;
	}

	/**
	 * 获取相关此sensor的所有节点，存入列表中
	 * 
	 * @param sensorName
	 * @return
	 */
	private List<String> relatedSensor_Node(String inner, String sensorName) {

		ArrayList<String> list = new ArrayList<String>();

		for (NodeTypeBean nodeTypeBean : this.nodeTypeListBean) {

			if (nodeTypeBean.isContainSensorType(sensorName)) {

				list.add(nodeTypeBean.getNodeType());
			}
		}

		return list;
	}

	/**
	 * 获取对应节点类型的所有node
	 * 
	 * @param nodeType
	 * @return
	 */
	public List<String> relatedNode_Sensor(String nodeType) {

		ArrayList<String> list = new ArrayList<String>();
		NodeTypeBean nodeTypeBean = this.findNodeTypeBean(nodeType);

		if (nodeTypeBean == null) {

			return null;
		}

		for (SensorTypeBean tempBean : nodeTypeBean.getSensorTypeListBean()) {

			list.add(tempBean.getSensorName());
		}

		return list;
	}

	public NodeInformationBean findNodeInfoBean(String moteid_ID) {

		for (NodeInformationBean tempBean : nodeInfoListBean) {

			if (BaseUtil.StringSafe(tempBean.getMoteid_ID()).equals(moteid_ID)) {

				return tempBean;
			}
		}

		return null;
	}

	public void updateNodeInfo(NodeInformationBean nodeInfoBean) {

		for (NodeInformationBean tempBean : nodeInfoListBean) {

			if (BaseUtil.StringSafe(tempBean.getMoteid_ID()).equals(
					nodeInfoBean.getMoteid_ID())) {

				tempBean.setMoteid_ID(nodeInfoBean.getMoteid_ID());
				tempBean.setLongitude(nodeInfoBean.getLongitude());
				tempBean.setLatitude(nodeInfoBean.getLatitude());
				tempBean.setLocation_X(nodeInfoBean.getLocation_X());
				tempBean.setLocation_Y(nodeInfoBean.getLocation_Y());
				tempBean.setNodeType(nodeInfoBean.getNodeType());
				tempBean.setRemarks(nodeInfoBean.getRemarks());
			}
		}
	}

	public void updateSensorType(String sensorName,
			SensorTypeBean sensorTypeBean) {
		String sensorIndex = BaseUtil.StringSafe(sensorName);

		for (SensorTypeBean tempBean : sensorTypeListBean) {

			if (sensorIndex.equals(tempBean.getSensorName())) {

				tempBean.setSensorName(sensorTypeBean.getSensorName());
				tempBean.setUnit(sensorTypeBean.getUnit());
				tempBean.setRangeLow(sensorTypeBean.getRangeLow());
				tempBean.setRangeHigh(sensorTypeBean.getRangeHigh());
			}
		}
		for (NodeTypeBean tempBean : nodeTypeListBean) {

			for (SensorTypeBean sensorTempBean : tempBean
					.getSensorTypeListBean()) {
				if (sensorIndex.equals(sensorTempBean.getSensorName())) {

					sensorTempBean
							.setSensorName(sensorTypeBean.getSensorName());
					sensorTempBean.setUnit(sensorTypeBean.getUnit());
					sensorTempBean.setRangeLow(sensorTypeBean.getRangeLow());
					sensorTempBean.setRangeHigh(sensorTypeBean.getRangeHigh());
				}
			}
		}
	}

	public SensorTypeBean findSensorTypeBean(String name) {

		for (SensorTypeBean tempBean : sensorTypeListBean) {

			if (BaseUtil.StringSafe(tempBean.getSensorName()).equals(name)) {

				return tempBean;
			}
		}

		return null;
	}

	public String toString() {
		String result = "{";

		for (NodeTypeBean tempBean : nodeTypeListBean) {

			result += tempBean.toString();
		}

		result += "}, {";

		for (SensorTypeBean tempBean : sensorTypeListBean) {

			result += tempBean.toString();
		}

		result += "}, {";

		for (NodeInformationBean tempBean : nodeInfoListBean) {

			result += tempBean.toString();
		}

		return result;
	}

	private void initParam(Connection connection) {
		List<NodeTypeBean> nodeTypeListBean = null;
		List<SensorTypeBean> sensorTypeListBean = null;
		List<NodeInformationBean> nodeInfoListBean = null;

		initParam(nodeTypeListBean, sensorTypeListBean, nodeInfoListBean,
				connection);
	}

	private void initParam(List<NodeTypeBean> nodeTypeListBean,
			List<SensorTypeBean> sensorTypeListBean,
			List<NodeInformationBean> nodeInfoListBean, Connection connection) {
		Statement stmt = null;

		try {
			stmt = connection.createStatement();

			initNodeTypeListParam(nodeTypeListBean, stmt, connection);
			initSensorTypeListParam(sensorTypeListBean, stmt, connection);
			initNodeInfoListParam(nodeInfoListBean, stmt, connection);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeStatement(stmt);
		}
	}

	private void initNodeTypeListParam(List<NodeTypeBean> nodeTypeListBean,
			Statement stmt, Connection conn) {
		if (nodeTypeListBean == null) {

			nodeTypeListBean = new ArrayList<NodeTypeBean>();
		}

		String sql = "select * from NodeType";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			NodeTypeBean nodeTypeBean = null;

			while (rs.next()) {

				String id = BaseUtil.StringSafe(rs.getString("id"));
				String nodeType = BaseUtil.StringSafe(rs.getString("nodeType"));
				String remark = BaseUtil.StringSafe(rs.getString("remark"));

				nodeTypeBean = new NodeTypeBean();
				nodeTypeBean.setId(id);
				nodeTypeBean.setNodeType(nodeType);
				nodeTypeBean.setRemark(remark);
				nodeTypeListBean.add(nodeTypeBean);
			}
			for (NodeTypeBean tempBean : nodeTypeListBean) {

				sql = "select ST.id, ST.sensorName, ST.unit, ST.range_low, ST.range_high "
						+ "from Node_sensorType NS, SensorType ST "
						+ "where NS.nodeType = "
						+ tempBean.getNodeType()
						+ " and ST.sensorName = NS.sensorName";
				rs = stmt.executeQuery(sql);
				List<SensorTypeBean> sensorTypeListTempBean = new ArrayList<SensorTypeBean>();
				while (rs.next()) {

					String id = BaseUtil.StringSafe(rs.getString("id"));
					String sensorName = BaseUtil.StringSafe(rs
							.getString("sensorName"));
					String unit = BaseUtil.StringSafe(rs.getString("unit"));
					String range_low = BaseUtil.StringSafe(rs
							.getString("range_low"));
					String range_high = BaseUtil.StringSafe(rs
							.getString("range_high"));
					sensorTypeListTempBean.add(new SensorTypeBean(id,
							sensorName, unit, range_low, range_high));
				}
				tempBean.setSensorTypeListBean(sensorTypeListTempBean);
			}
			this.setNodeTypeList(nodeTypeListBean);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeResultSet(rs);
		}
	}

	private void initSensorTypeListParam(
			List<SensorTypeBean> sensorTypeListBean, Statement stmt,
			Connection conn) {

		String sql = "select * from SensorType ST";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (sensorTypeListBean == null) {

				sensorTypeListBean = new ArrayList<SensorTypeBean>();
			}
			while (rs.next()) {
				String id = BaseUtil.StringSafe(rs.getString("id"));
				String sensorName = BaseUtil.StringSafe(rs
						.getString("sensorName"));
				String unit = BaseUtil.StringSafe(rs.getString("unit"));
				String range_low = BaseUtil.StringSafe(rs
						.getString("range_low"));
				String range_high = BaseUtil.StringSafe(rs
						.getString("range_high"));
				sensorTypeListBean.add(new SensorTypeBean(id, sensorName, unit,
						range_low, range_high));
			}
			this.setSensorTypeList(sensorTypeListBean);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeResultSet(rs);
		}
	}

	private void initNodeInfoListParam(
			List<NodeInformationBean> nodeInfoListBean, Statement stmt,
			Connection conn) {

		String sql = "select Moteid_ID from NodeInformation";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			List<String> moteidList = new ArrayList<String>();

			while (rs.next()) {

				String moteid_ID = BaseUtil.StringSafe(rs
						.getString("moteid_ID"));
				moteidList.add(moteid_ID);
			}
			if (nodeInfoListBean == null) {

				nodeInfoListBean = new ArrayList<NodeInformationBean>();
			}

			for (String moteid : moteidList) {

				String moteid_ID = "", remarks = "", location_X = "", location_Y = "", latitude = "", longitude = "";
				String nodeType = "";

				sql = "select * from NodeInformation where Moteid_ID = "
						+ moteid;
				rs = stmt.executeQuery(sql);

				if (rs.next()) {

					moteid_ID = BaseUtil.StringSafe(rs.getString("moteid_ID"));
					location_X = BaseUtil
							.StringSafe(rs.getString("location_X"));
					location_Y = BaseUtil
							.StringSafe(rs.getString("location_Y"));
					latitude = BaseUtil.StringSafe(rs.getString("Latitude"));
					longitude = BaseUtil.StringSafe(rs.getString("longitude"));
					remarks = BaseUtil.StringSafe(rs.getString("remarks"));
					nodeType = BaseUtil.StringSafe(rs.getString("nodeType"));

					NodeInformationBean nodeInfoBean = new NodeInformationBean(
							nodeType, moteid_ID, location_X, location_Y,
							latitude, longitude, remarks);
					nodeInfoListBean.add(nodeInfoBean);
				}
			}

			this.setNodeInfoList(nodeInfoListBean);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeResultSet(rs);
		}
	}

	protected void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	protected void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	protected void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
