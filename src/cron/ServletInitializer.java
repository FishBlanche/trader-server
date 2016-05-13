package cron;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.util.GetClassLoader;

import com.dbay.apns4j.model.MyTestClass;

import feed.FeedSensing;
import zookeeper.ZooClient;
public class ServletInitializer extends HttpServlet {
	/**
	 * 这个在服务器开启时就启动
	 */
	private static final long serialVersionUID = 7861033593851152881L;
	Timer timer;
	//ZooClient zc=new ZooClient();
	
	public void init() throws ServletException {
		// / Automatically java script can run here
		System.out.println("************");
		System.out.println("***Cron Servlet Initialized successfully ***..");
		System.out.println("***********");
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		 
		timer = new Timer();
		timer.schedule(new RemindTask(), 20 * 1000);
	 
		  
		
		//ServletInitializer.class.getClass().getClassLoader().getResource("");
	//	System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
	//	System.out.println(getServletContext().getRealPath("/"));
	    System.out.println(new File(System.getProperty("catalina.home")));
		//System.out.println(System.getProperty("user.dir"));
	//    zc.testZoo();
	}

	class RemindTask extends TimerTask {
		public void run() {
			System.out.println("Time's up!");
			timer.cancel(); // Terminate the timer thread
			try {
				// Feed feed = new Feed();
				FeedSensing fs = new FeedSensing();
				// feed.start();
				fs.start();
				System.out.println("Feed Started");
				
				
				 
			} catch (Exception e) {
				System.out.println("A problem occured while starting the feed: "
								+ e.getMessage());
			}
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	 

}