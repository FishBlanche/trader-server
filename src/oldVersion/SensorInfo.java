package oldVersion;

public class SensorInfo {
	static final long serialVersionUID = 103844514947365256L;

	public int getMoteid_ID() {
		return Moteid_ID;
	}

	public void setMoteid_ID(int moteid_ID) {
		Moteid_ID = moteid_ID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(int location_X) {
		Location_X = location_X;
	}

	public int getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(int location_Y) {
		Location_Y = location_Y;
	}

	public float getLatitude() {
		return Latitude;
	}

	public void setLatitude(float latitude) {
		Latitude = latitude;
	}

	public float getlongitude() {
		return longitude;
	}

	public void setlongitude(float longitude) {
		this.longitude = longitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SensorInfo(int id, int userid, int moteid_ID, int type,
			int location_X, int location_Y, float latitude, float longitude,
			String remarks) {
		this.Moteid_ID = moteid_ID;
		this.type = type;
		this.Location_X = location_X;
		this.Location_Y = location_Y;
		this.Latitude = latitude;
		this.longitude = longitude;

	}

	public SensorInfo() {
	}

	private int Moteid_ID;
	private int type;
	private int Location_X;
	private int Location_Y;
	private float Latitude;
	private float longitude;

}
