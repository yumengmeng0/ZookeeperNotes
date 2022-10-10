import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author YuMengMeng
 * @date 2022/10/7
 */

public class TestZookeeper {
    /**
     * zookeeper集群的ip和端口
     */
    private String connectString = "47.98.138.215:2181" +
            ",81.68.215.86:2181" +
            ",139.9.81.145:2181";
    /**
     * sessionTimeout 超时时间
     */
    private int sessionTimeout = 60 * 1000;

    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("得到监听反馈，再进行业务处理代码");
                System.out.println(watchedEvent.getType());
            }
        });
    }


    /**
     * 创建节点
     *
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void CreateNode() throws InterruptedException, KeeperException {
        /**
         * String path,
         * byte[] data,
         * List<ACL> acl,
         * CreateMode createMode
         */
        String s = zkClient.create("/my1", "ymm".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("已创建节点：" + s);
    }

    /**
     * 获取节点数据

     */
    @Test
    public void getNodeData() throws InterruptedException, KeeperException {
        byte[] data = zkClient.getData("/my", false, new Stat());
        String s = new String(data);
        System.out.println("/my节点数据：" + s);
    }

    /**
     * 修改节点上的数据
     */
    @Test
    public void updateDate() throws InterruptedException, KeeperException {
        Stat stat = zkClient.setData("/my", "ymm2".getBytes(), 0);
        // 4294967327,4294967353,1665129687370,1665131204393,1,0,0,0,4,0,4294967327
        System.out.println(stat);
    }

    /**
     * 删除节点
     */
    @Test
    public void deleteData() throws InterruptedException, KeeperException {
        zkClient.delete("/my", 1);
        System.out.println("删除成功");
    }

    /**
     * 获取子节点
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/china", false);
        for (String child : children) {
            System.out.println(child);
        }
    }

    /**
     * 监听根节点
     */
    @Test
    public void watchRoot() throws InterruptedException, KeeperException, IOException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        System.in.read();
    }

    @Test
    public void exists() throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists("/my", false);
        if (stat == null){
            System.out.println("节点不存在");
        }else {
            System.out.println("节点存在");
        }
    }
}
