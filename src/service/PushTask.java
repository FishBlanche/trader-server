package service;

import java.io.InputStream;
import java.util.TimerTask;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Payload;

public class PushTask extends TimerTask {
	private static IApnsService apnsService;
	public static int flag=0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		flag++;
       IApnsService service = getApnsService();
		
		Payload payload = new Payload();
		payload.setAlert("How are you?");
		payload.setBadge(1);
		payload.setSound("bubu.wav");
		payload.addParam("uid", 123456);
		payload.addParam("type", 12);
		apnsService.getFeedbacks();
		
		System.out.println("run()");
	 
		 if(flag==300)
		 {
			 for(int j=0;j<apnsService.getFeedbacks().size();j++)
				{
					 if(ApnsService.tokenList.contains(apnsService.getFeedbacks().get(j)))
					 {
						 ApnsService.tokenList.remove(apnsService.getFeedbacks().get(j));
						 System.out.println("remove token"+apnsService.getFeedbacks().get(j));
					 }
				} 
		 }
		
		
		for(int i=0;i<ApnsService.tokenList.size();i++)
		{
			
			System.out.println("sendNotification"+i);
			service.sendNotification(ApnsService.tokenList.get(i), payload);
		}
		
	}
	private static IApnsService getApnsService() {
		if (apnsService == null) {
	 		ApnsConfig config = new ApnsConfig();
			InputStream is = ApnsService.class.getClassLoader().getResourceAsStream("certResource/runpush.p12");
			config.setKeyStore(is);
			config.setDevEnv(true);
			
			config.setPassword("greenorbs");
		//	config.setPoolSize(3);
			// 假如需要在同个java进程里给不同APP发送通知，那就需要设置为不同的name
//			config.setName("welove1");
			apnsService = ApnsServiceImpl.createInstance(config);
		}
		return apnsService;
	}
}
