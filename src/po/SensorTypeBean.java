package po;

import java.io.Serializable;

/**
 * 对应数据库中sensorType，包含了每个节点的信息id,名字,单位,范围下限以及上限.
 */
public class SensorTypeBean implements Serializable {
	private static final long serialVersionUID = -5858659167381015128L;
	private String Id;
	private String sensorName;
	private String unit;
	private String rangeLow;
	private String rangeHigh;

	public SensorTypeBean(String aSensorName, String aUnit, String aRange_low,
			String aRange_high) {

		sensorName = aSensorName;
		unit = aUnit;
		rangeLow = aRange_low;
		rangeHigh = aRange_high;
	}

	public SensorTypeBean(String id, String aSensorName, String aUnit,
			String aRange_low, String aRange_high) {

		this(aSensorName, aUnit, aRange_low, aRange_high);
		this.Id = id;
	}

	public String toString() {
		return "sensorName = " + sensorName + "\n";
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRangeLow() {
		return rangeLow;
	}

	public void setRangeLow(String rangeLow) {
		this.rangeLow = rangeLow;
	}

	public String getRangeHigh() {
		return rangeHigh;
	}

	public void setRangeHigh(String rangeHigh) {
		this.rangeHigh = rangeHigh;
	}
}
