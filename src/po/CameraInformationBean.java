package po;

import java.io.Serializable;

public class CameraInformationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3167868678030410326L;
 
	private String location_X;
	private String location_Y;
    private String ip;
    
    public CameraInformationBean(String aLocation_X,
			String aLocation_Y,
			String ipAddress) {
		this.location_X = aLocation_X;
		this.location_Y = aLocation_Y;
		this.ip=ipAddress;
	}
    
	public String getLocation_X() {
		return location_X;
	}
	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}
	public String getLocation_Y() {
		return location_Y;
	}
	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
