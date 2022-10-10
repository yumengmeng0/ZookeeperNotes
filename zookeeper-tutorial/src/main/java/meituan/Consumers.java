package meituan;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 消费者
 *
 * @author YuMengMeng
 * @date 2022/10/7
 */

public class Consumers {

    /**
     * zookeeper集群的ip和端口
     */
    private String connectString = "47.98.138.215:2181" +
            ",81.68.215.86:2181" +
            ",139.9.81.145:2181";
    private int sessionTimeout = 60000;
    private ZooKeeper zkCli;

    /**
     * 创建客户端，连接到zookeeper
     *
     * @throws IOException
     */
    public void connect() throws Exception {
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 再次获取商家列表
                try {
                    getShopList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void getShopList() throws Exception {
        // 1.获取服务器子节点信息，并对父节点进行监听
        List<String> shops = zkCli.getChildren("/meituan", true);

        // 2.声明存储服务器信息的集合
        ArrayList<String> shopList = new ArrayList<>();
        for (String shop : shops) {
            byte[] data = zkCli.getData("/meituan/" + shop, false, new Stat());
            shopList.add(new String(data));
        }

        System.out.print("正在营业的商家:");
        for (String s : shopList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void business() throws Exception {
        System.out.println("用户正在浏览商家");
        System.in.read();
    }

    public static void main(String[] args) throws Exception {
        // 1.获取zookeeper连接
        Consumers consumers = new Consumers();
        consumers.connect();
        // 2.获取商家列表
        consumers.getShopList();
        // 3.业务处理
        consumers.business();
    }
}
