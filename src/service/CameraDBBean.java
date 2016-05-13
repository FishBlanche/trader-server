package service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import po.CameraInformationBean;
import po.NodeInformationBean;
import po.NodeTypeBean;
import po.SensorTypeBean;
import utility.BaseUtil;


public class CameraDBBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8676529141625533767L;

	private List<CameraInformationBean> CameraInfoListBean;
	
	public CameraDBBean() {
		 
	}

	public CameraDBBean(Connection connection) {
		CameraInfoListBean = new ArrayList<CameraInformationBean>();
		initCameraInfoListParam(connection);
	}
	 
 
	private void initCameraInfoListParam(Connection conn) {
		 
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			String sql = "select * from CameraInformation";
			ResultSet rs = null;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String  location_X = "", location_Y = "", ipAddress = "";
				location_X = BaseUtil
						.StringSafe(rs.getString("Location_X"));
				location_Y = BaseUtil
						.StringSafe(rs.getString("Location_Y"));
				ipAddress=BaseUtil
						.StringSafe(rs.getString("ip"));
				CameraInformationBean cameraInfoBean = new CameraInformationBean(location_X, location_Y,
						ipAddress);
				CameraInfoListBean.add(cameraInfoBean);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeStatement(stmt);
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

	public List<CameraInformationBean> getCameraInfoListBean() {
		return CameraInfoListBean;
	}

	public void setCameraInfoListBean(List<CameraInformationBean> cameraInfoListBean) {
		CameraInfoListBean = cameraInfoListBean;
	}
}
