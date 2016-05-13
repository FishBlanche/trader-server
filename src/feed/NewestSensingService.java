package feed;

import java.util.List;

import po.SensingEntry;

/**
 * 获取所有最新的消息，客户端将通过RMI机制调用此方法以获得消息进行数据展示
 */
public class NewestSensingService {

	public List<SensingEntry> getNewestSensings() {
		/*
		List<SensingEntry> se=FeedSensing.getNewestSensing().newestSensing;
		int len=se.size();
		for(int i=0;i<len;i++)
		{
			System.out.println("senseEntry   "+se.get(i).getnodeid());
		}*/
		
		return FeedSensing.getNewestSensing().newestSensing;

	}

}