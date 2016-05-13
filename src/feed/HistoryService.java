package feed;

import java.util.List;

import po.HistoryDataObject;
import po.SensingEntry;

public class HistoryService {
	
	public List<HistoryDataObject> getHistorySensings() {
		return MyTask.hdoList;

	}
}
