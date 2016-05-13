package zookeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class BaseZookeeper implements Watcher
{

  public ZooKeeper zooKeeper;

  private static final int SESSION_TIME_OUT = 2000;

  private CountDownLatch countDownLatch = new CountDownLatch(1);
  byte[] nodeData = null;
  /**
   * 连接zookeeper
   * 
   * @param host
   * @throws IOException
   * @throws InterruptedException
   */
  public void connectZookeeper(String host) throws IOException,
      InterruptedException
  {
    zooKeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
    countDownLatch.await();
    System.out.println("zookeeper connect ok");

  }

  /**
   * 实现watcher的接口方法，当连接zookeeper成功后，zookeeper会通过此方法通知watcher<br>
   * 此处为如果接受到连接成功的event，则countDown，让当前线程继续其他事情。
   */
  @Override
  public void process(WatchedEvent event)
  {
    if (event.getState() == KeeperState.SyncConnected)
    {
      System.out.println("watcher received event");
      countDownLatch.countDown();
    }
    System.out.println("回调watcher实例： 路径" + event.getPath() + " 类型："

    + event.getType());
    try {
    	nodeData=this.zooKeeper.getData(event.getPath(), true, null);
	} catch (KeeperException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String res = new String(nodeData);
    System.out.println("+++++++++"+res);
  }

  /**
   * 根据路径创建节点，并且设置节点数据
   * @param path
   * @param data
   * @return
   * @throws KeeperException
   * @throws InterruptedException
   */
  public String createNode(String path, byte[] data) throws KeeperException,
      InterruptedException
  {
	  if(this.zooKeeper.exists(path, true)==null)
	  {
    return this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE,
        CreateMode.PERSISTENT);
	  }
	  return "false";
  }

  /**
   * 根据路径获取所有孩子节点
   * 
   * @param path
   * @return
   * @throws KeeperException
   * @throws InterruptedException
   */
  public List<String> getChildren(String path) throws KeeperException,
      InterruptedException
  {
    return this.zooKeeper.getChildren(path, true);
  }

  public Stat setData(String path, byte [] data, int version) throws KeeperException, InterruptedException
  {
    return this.zooKeeper.setData(path, data, version);
  }
  
  /**
   * 根据路径获取节点数据
   * 
   * @param path
   * @return
   * @throws KeeperException
   * @throws InterruptedException
   */
  public byte[] getData(String path) throws KeeperException,
      InterruptedException
  {
    return this.zooKeeper.getData(path, false, null);
  }

  /**
   * 删除节点
   * 
   * @param path
   * @param version
   * @throws InterruptedException
   * @throws KeeperException
   */
  public void deletNode(String path, int version)
      throws InterruptedException, KeeperException
  {
    this.zooKeeper.delete(path, version);
  }

  /**
   * 关闭zookeeper连接
   * 
   * @throws InterruptedException
   */
  public void closeConnect() throws InterruptedException
  {
    if (null != zooKeeper)
    {
      zooKeeper.close();
    }
  }

}