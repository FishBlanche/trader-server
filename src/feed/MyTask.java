package feed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import po.HistoryDataObject;
import po.SensingEntry;
import service.NewestSensing;
public class MyTask extends TimerTask {
	 
    public static	List<HistoryDataObject> hdoList=new ArrayList<HistoryDataObject>();
	public MyTask() {
		 
	}
	@Override
	
	public void run() {
		// TODO Auto-generated method stub
	//System.out.println("MyTask");
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	String dateString = sdf.format(date);
	//System.out.println("dateString"+dateString);
	HistoryDataObject hdo=new  HistoryDataObject();
	hdo.datestr=dateString;
	 
	for(int i=0;i<NewestSensing.newestSensing.size();i++)
	{
		SensingEntry senseCopy =new SensingEntry(NewestSensing.newestSensing.get(i).getId(),NewestSensing.newestSensing.get(i).getCluster_id(),NewestSensing.newestSensing.get(i).getnodeid(),
				NewestSensing.newestSensing.get(i).getTimestampArrive_TM(),NewestSensing.newestSensing.get(i).getTemperature(),NewestSensing.newestSensing.get(i).getHumidity(),
				NewestSensing.newestSensing.get(i).getLight(),NewestSensing.newestSensing.get(i).getCo2(),NewestSensing.newestSensing.get(i).getType());
	//	 System.out.println("senseCopy.getMoteid_ID()"+senseCopy.getnodeid());
			hdo.historyentrylist.add(senseCopy);
	}
	if(hdoList.size()<9)
	{
		hdoList.add(hdo);
	}
	else
	{
		 
		hdoList.get(0).historyentrylist.clear();
		hdoList.remove(0);
		 
		hdoList.add(hdo);
	}
//	System.out.println("hdoList.size()"+hdoList.size());
}
	 
}
