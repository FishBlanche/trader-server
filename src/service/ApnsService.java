package service;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import po.HistoryDataObject;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;

import feed.MyTask;

public class ApnsService {
	

	 public static	Timer timer ;
	 public static	List<String> tokenList=new ArrayList<String>();
	
	
	public void setDeviceToken(String token){
		
		
		 if(!tokenList.contains(token))
		 {
			 tokenList.add(token);
			 System.out.println("token"+token);
		 }
		
		 
		if(timer==null)
		{
			timer = new Timer();
			timer.schedule(new PushTask(), 1000, 1000*60);//在1秒后执行此任务,每次间隔2秒
			System.out.println("newTimer()");
		}
	}
}
