package feed;

import java.sql.SQLException;
import java.util.List;

import dao.MySqlConnectionHelper;
import po.CameraInformationBean;
import po.NodeInformationBean;
import service.CameraDBBean;
import service.NodeDBBean;

public class GettingCameraService {
	private  CameraDBBean cameraDBBean;
	
	public List<CameraInformationBean> getCameras() {
      //  System.out.println("getCameras");
        if (cameraDBBean == null) {
			try {
				cameraDBBean=new CameraDBBean(
						MySqlConnectionHelper.getConnection());
			} catch (SQLException e) {
				System.out.println("Get connection error.");
				e.printStackTrace();
			}
		}
		return cameraDBBean.getCameraInfoListBean();
	}
}
