package feed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Timer;

import po.EmailData;

import service.NewestSensing;
import service.NodeDBBean;
import service.PostOffice;
import utility.SenseDataFilter;
import cron.DataSourceServlet;
import dao.MySqlConnectionHelper;
import flex.messaging.MessageBroker;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.util.UUIDUtils;

/**
 * 启动线程，并在线程之初， 初始化nodeDBBean对象，即为服务端的application bean对象 初始化最新的数据列表
 * newestSensing； 线程的主要工作是保持对数据库的连接，对数据库进行轮询， 将新来的消息采用Flex的异步消息处理机制发送给客户端
 */
public class FeedSensing extends Thread {
	// ///
	private static NodeDBBean nodeDBBean;
	// ///
	// private static NodeInfo nodeInfo;
	private PreparedStatement ps;

	private static NewestSensing newestSensing;
	private int newestId = 0;
	private Connection c;
	// private Statement s;
	private ResultSet rs;
	private StringBuilder body = new StringBuilder();
	SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
  //  private int timer;
  //  private boolean alert1=true;
  //  private boolean alert2=true;
    private String timestamp;
  //  PostOffice po = new PostOffice("smtp.126.com", "indoor_sensehuge@126.com", "yitian");
	/*
	 * public static NodeInfo getNodeInfo() { return nodeInfo; }
	 */

	public static NodeDBBean getNodeDBBean() {
		if (nodeDBBean == null) {
			try {
				setNodeDBBean(new NodeDBBean(
						MySqlConnectionHelper.getConnection()));
			} catch (SQLException e) {
				System.out.println("Get connection error.");
				e.printStackTrace();
			}
		}

		return nodeDBBean;
	}

	public static NodeDBBean getNodeDBBean(boolean updated) {
		// if updated is true, then show the latest informaion.
		if (updated) {

			nodeDBBean = DataSourceServlet.getNodeDBBean();
		}

		return nodeDBBean;
	}

	private static void setNodeDBBean(NodeDBBean aNodeDBBean) {
		nodeDBBean = aNodeDBBean;
	}

	public static NewestSensing getNewestSensing() {
		return newestSensing;
	}

	private void waitMoment(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Statement s = null;
//		timer=0;
		// System.out.println("Statement s = null");
		try {
			c = MySqlConnectionHelper.getConnection();
			if (nodeDBBean == null) {
				setNodeDBBean(new NodeDBBean(c));
			}
			newestSensing = new NewestSensing();
		 
			
			Timer timer = new Timer();
			timer.schedule(new MyTask(), 1000, 60000*15);//在1秒后执行此任务,每次间隔2秒
	         System.out.println("timer.schedule");
	         
			s = c.createStatement();
			rs = s.executeQuery("SELECT max(Id) as max_id FROM SimpleSensing");
			while (rs.next()) {
				newestId = rs.getInt("max_id");
				System.out.println("max_id: " + newestId);
			}
			ps = c.prepareStatement(
					"SELECT * FROM SimpleSensing where Id > ? ORDER BY Id ASC ",
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
					ResultSet.CLOSE_CURSORS_AT_COMMIT);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MySqlConnectionHelper.closeResultSet(rs);
			MySqlConnectionHelper.closeStatement(s);
		}
		MessageBroker msgBroker = MessageBroker.getMessageBroker(null);
		String clientID = UUIDUtils.createUUID();

		while (true) {
		//	timer++;
		//	System.out.println(timer);
			try {
				/*if(timer>720&&alert1){
					 String[] recipients = {"baohong19871022@gmail.com", "chinaxqx90@126.com","dongzh86@outlook.com"};
				        EmailData email = new EmailData("indoor_sensehuge@126.com", recipients, "Alert", "We haven't received data for about 2 hours since  "+timestamp, "text");				       
				        po.sendMail(email);
				        alert1=false;
				        System.out.println(timer+"send email ok");
				}else if(timer>1440&&alert2){
					 String[] recipients = {"baohong19871022@gmail.com", "chinaxqx90@126.com", "dongzh86@outlook.com","hanhealthy@gmail.com"};
					 EmailData email = new EmailData("indoor_sensehuge@126.com", recipients, "Alert", "We haven't received data for about 4 hours since  "+timestamp, "text");
					 po.sendMail(email);
					 alert2=false;
				}*/
				ps.setInt(1, newestId);
				rs = ps.executeQuery();
				AsyncMessage msg = new AsyncMessage();
				msg.setDestination("feed");
				msg.setHeader("DSSubtopic", "LiveSensing");
				msg.setClientId(clientID);
				msg.setMessageId(UUIDUtils.createUUID());
				msg.setTimestamp(System.currentTimeMillis());
				while (msgBroker == null) {
					waitMoment(1000);
				}
				body.delete(0, body.length());
				while (rs.next()) {
			//		timer=0;
		//			alert1=true;
		//			alert2=true;
					newestId = rs.getInt("Id");
			 
					/*
					if (!SenseDataFilter.isUsable(rs)) {		
						System.out.println("SenseDataFilter.isUsable(rs) return false");
						continue;
					} else {
						newestSensing.updateNewestSensings(rs);
						timestamp=sDateFormat.format(rs.getTimestamp("TimestampArrive_TM"));
						body.append("|" + rs.getInt("Id") + ","
								+ rs.getInt("Cluster_ID") + ","
								+ rs.getInt("Moteid_ID") + ","
								+ timestamp + ","
								+ rs.getInt("temperature") + ","
								+ rs.getInt("humidity") + ","
								+ rs.getInt("light") 
								+ "," + rs.getInt("co2")
								+ "," + rs.getInt("NodeType")
								+ "," + rs.getString("path"));
						System.out.println("body"+body.toString());
					}*/
					newestSensing.updateNewestSensings(rs);
					timestamp=sDateFormat.format(rs.getTimestamp("TimestampArrive_TM"));
					body.append("|" + rs.getInt("Id") + ","
							+ rs.getInt("Cluster_ID") + ","
							+ rs.getInt("Moteid_ID") + ","
							+ timestamp + ","
							+ rs.getFloat("temperature") + ","
							+ rs.getFloat("humidity") + ","
							+ rs.getFloat("light") 
							+ "," + rs.getFloat("co2")
							+ "," + rs.getInt("NodeType")
							+ "," + rs.getString("path"));
				 
				}
				if (body.length() > 0) {
				 	msg.setBody(body.toString());
					msgBroker.routeMessageToService(msg, null);
				//	System.out.println(body.toString());
				}
				MySqlConnectionHelper.closeResultSet(rs);
			} catch (SQLException e) {
				System.out.println("query exception");
				e.printStackTrace();
			//	timer++;
				waitMoment(10000);
				try {
					MySqlConnectionHelper.closeStatement(ps);
					MySqlConnectionHelper.close(c);
					c = MySqlConnectionHelper.getConnection();
					ps = c.prepareStatement(
							"SELECT * FROM SimpleSensing where Id > ? ORDER BY Id ASC ",
							ResultSet.TYPE_FORWARD_ONLY,
							ResultSet.CONCUR_READ_ONLY,
							ResultSet.CLOSE_CURSORS_AT_COMMIT);

				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("--re  connected--");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			waitMoment(10000);

		}
	}
}
