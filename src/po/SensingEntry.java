package po;

import java.io.Serializable;
import java.util.Date;

/**
 * 这个实际上是对视图SimpleSensing的映射
 */
public class SensingEntry implements Serializable {

	static final long serialVersionUID = 103844514947365244L;
	private int Id;
	private int Cluster_id;
	private int nodeid;
	private Date TimestampArrive_TM;
	private float temperature;
	private float humidity;
	private float light;
	private float co2;
	private int type;

	public float getCo2() {
		return co2;
	}

	public void setCo2(float co2) {
		this.co2 = co2;
	}

	public SensingEntry(int id, int cluster_id, int moteid_ID,
			Date timestampArrive_TM, float temperature, float humidity,
			float light, float co2,int nodeType) {
		super();
		Id = id;
		Cluster_id = cluster_id;
		nodeid = moteid_ID;
		TimestampArrive_TM = timestampArrive_TM;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.co2 = co2;
		type = nodeType;
	}

	public String toString() {

		return "Moteid_ID = " + nodeid + ", " + "TimestampArrive_TM = "
				+ this.TimestampArrive_TM + ", " + "temperature = "
				+ this.temperature + ", " + "humidity = " + this.humidity
				+ ", " + "light = " + this.light + ", " + "co2 = " + this.co2+ ", " + "NodeType = " + this.type
				+ ".";
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getCluster_id() {
		return Cluster_id;
	}

	public void setCluster_id(int cluster_id) {
		Cluster_id = cluster_id;
	}

	public int getnodeid() {
		return nodeid;
	}

	public void setnodeid(int moteid_ID) {
		nodeid = moteid_ID;
	}

	public Date getTimestampArrive_TM() {
		return TimestampArrive_TM;
	}

	public void setTimestampArrive_TM(Date timestampArrive_TM) {
		TimestampArrive_TM = timestampArrive_TM;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getLight() {
		return light;
	}

	public void setLight(float light) {
		this.light = light;
	}

	public SensingEntry() {

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}