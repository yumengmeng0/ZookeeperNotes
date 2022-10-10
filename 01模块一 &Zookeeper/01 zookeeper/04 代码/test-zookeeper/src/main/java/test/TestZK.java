package test;

import io.netty.channel.ChannelId;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @BelongsProject: test-zookeeper
 * @Author: GuoAn.Sun
 * @CreateTime: 2020-07-22 14:33
 * @Description: zookeeperAPI
 */
public class TestZK {

    // zookeeper集群的ip和端口
    private String connectString = "192.168.204.141:2181,192.168.204.142:2181,192.168.204.143:2181";
    /*
    session超时的时间: 时间不宜设置太小。因为zookeeper和加载集群环境会因为性能等原因而延迟略高，
    如果时间太少，还没有创建好客户端，就开始操作节点。会报错的。
    （心急吃不了热豆腐）
     */
    private int sessionTimeout = 60 * 1000;

    //zookeeper客户端对象
    private ZooKeeper zkClient;

    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("得到监听反馈，再进行的业务处理代码！");
                System.out.println(watchedEvent.getType());
            }
        });
    }

    //创建节点
    @Test
    public void createNode() throws Exception {
        String str = zkClient.create("/lagou", "laosun".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("已创建节点: " + str);
    }

    //获取节点上的值
    @Test
    public void getNodeData() throws Exception {
        byte[] bytes = zkClient.getData("/lagou", false, new Stat());
        String str = new String(bytes);
        System.out.println("/lagou节点的数据：" + str);
    }

    //修改节点上的数据
    @Test
    public void updateData() throws Exception {
        Stat stat = zkClient.setData("/lagou", "laosunA".getBytes(), 0);
        System.out.println(stat);
    }

    //删除节点
    @Test
    public void delete() throws Exception {
        zkClient.delete("/lagou", 1);
        System.out.println("删除成功！");
    }

    //获取子节点
    @Test
    public void getChildren() throws Exception {
        List<String> list = zkClient.getChildren("/china", false);
        for (String child : list) {
            System.out.println(child);
        }
    }

    //监听根节点下面的变化
    @Test
    public void watchNode() throws Exception{
        List<String> list = zkClient.getChildren("/", true);
        for (String s : list)
            System.out.println(s);

        // 让线程无限的等待下去
        System.in.read();
    }

    @Test
    public void exists()throws Exception{
        Stat stat = zkClient.exists("/lagou", false);
        if(stat==null)
            System.out.println("不存在");
        else
            System.out.println("存在");
    }
}
