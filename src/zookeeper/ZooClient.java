package zookeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.KeeperException;
public class ZooClient {
  public   void testZoo()
     {
    	 BaseZookeeper baseZookeeper = new BaseZookeeper();
    	    
    	    String host = "192.168.0.204:2181,192.168.0.231:2181";
    	    
    	    // 连接zookeeper
    	    try {
				baseZookeeper.connectZookeeper(host);
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				  System.out.println("==========IOException e5");
				e5.printStackTrace();
			} catch (InterruptedException e5) {
				// TODO Auto-generated catch block
				System.out.println("===========InterruptedException e5");
				e5.printStackTrace();
			}
    	    System.out.println("--------connect zookeeper ok-----------");
    	    
    	    // 创建节点
    	    byte [] data = {1, 2, 3, 4, 5};
    	    String result = null;
			try {
				result = baseZookeeper.createNode("/test", data);
			} catch (KeeperException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} catch (InterruptedException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
    	    System.out.println(result);
    	    System.out.println("--------create node ok-----------");
    	    
    	    // 获取某路径下所有节点
    	    List<String> children=null;
			try {
				children = baseZookeeper.getChildren("/test");
			} catch (KeeperException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
    	    for (String child : children)
    	    {
    	      System.out.println(child);
    	    }
    	    System.out.println("--------get children ok-----------");
    	    
    	    // 获取节点数据
    	    byte[] nodeData = null;
			try {
				nodeData = baseZookeeper.getData("/test");
			} catch (KeeperException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    	    System.out.println(Arrays.toString(nodeData));
    	    System.out.println("--------get node data ok-----------");
    	    
    	    // 更新节点数据
    	    data = "test data".getBytes();
    	    try {
				baseZookeeper.setData("/test", data, -1);
			} catch (KeeperException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	    System.out.println("--------set node data ok-----------");
    	    
    	    try {
				nodeData = baseZookeeper.getData("/test");
			} catch (KeeperException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	    System.out.println(Arrays.toString(nodeData));
    	    System.out.println("--------get node new data ok-----------");
    	    /*
    	    try {
				baseZookeeper.closeConnect();
			} catch (InterruptedException e) {
			 
				e.printStackTrace();
			}
    	   System.out.println("--------close zookeeper ok-----------");*/
    	  }
    
}
