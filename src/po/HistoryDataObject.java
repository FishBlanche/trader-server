package po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryDataObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2231748836793088607L;
	public String datestr;
	public List<SensingEntry> historyentrylist = new ArrayList<SensingEntry>();
}
