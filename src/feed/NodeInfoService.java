package feed;

import java.util.List;

import po.NodeInformationBean;

/**
 * 获取所有nodeInfo的信息，客户端将通过RMI机制调用此方法以获得消息进行数据展示
 */
public class NodeInfoService {

	/*
	 * public List<NodeEntry> getNodeList() {
	 * 
	 * return FeedSensing.getNodeInfo().nodes; }
	 */
	public List<NodeInformationBean> getNodeList() {
      //  System.out.println("getNodeList"+FeedSensing.getNodeDBBean(true).getNodeInfoList().size());
		return FeedSensing.getNodeDBBean(true).getNodeInfoList();
	}
}
