package po;

import java.io.Serializable;
import utility.*;

/*
 * This class defines the full meanings of Node Information.			
 */
public class NodeInformationBean implements Serializable {
	static final long serialVersionUID = 103844514947365256L;
	private String moteid_ID;
	private String nodeType;
	private String location_X;
	private String location_Y;
	private String Latitude;
	private String longitude;
	private String remarks;

	public NodeInformationBean(String aMoteid_ID, String aLocation_X,
			String aLocation_Y, String aLatitude, String aLongitude,
			String aRemarks) {
		this.moteid_ID = aMoteid_ID;
		this.Latitude = aLatitude;
		this.location_X = aLocation_X;
		this.remarks = aRemarks;
		this.location_Y = aLocation_Y;
		this.longitude = aLongitude;
	}

	public NodeInformationBean(String nodeType, String aMoteid_ID,
			String aLocation_X, String aLocation_Y, String aLatitude,
			String aLongitude, String aRemarks) {
		this(aMoteid_ID, aLocation_X, aLocation_Y, aLatitude, aLongitude,
				aRemarks);
		this.nodeType = nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeType() {
		return this.nodeType;
	}

	public boolean isEquals(String nodeType) {

		if (BaseUtil.StringSafe(this.nodeType).equals(nodeType)) {

			return true;
		}
		return false;
	}

	public String toString() {
		return "Moteid_id = " + this.moteid_ID + "||" + nodeType + "\n";
	}

	public String getMoteid_ID() {
		return moteid_ID;
	}

	public void setMoteid_ID(String moteid_ID) {
		this.moteid_ID = moteid_ID;
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

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
