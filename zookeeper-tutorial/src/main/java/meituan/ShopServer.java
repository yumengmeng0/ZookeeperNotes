package meituan;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * 商家服务类
 *
 * @author YuMengMeng
 * @date 2022/10/7
 */

public class ShopServer {

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
    public void connect() throws IOException {
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
    }

    /**
     * 注册到zookeeper
     */
    public void register(String shopName) throws Exception {
        // EPHEMERAL_SEQUENTIAL 临时有序的节点（营业）
        // 自动编号，断开节点自动删除（关门）
        String s = zkCli.create("/meituan/shop", shopName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(s + "开始营业");

    }

    /**
     *
     */
    public void business() throws Exception{
        System.out.println("正在营业");
        System.in.read();
    }

    public static void main(String[] args) throws Exception {
        // 1.我要开饭店
        ShopServer shopServer = new ShopServer();
        // 2.连接zookeeper集群
        shopServer.connect();

        // 3.将服务节点注册到zookeeper
        shopServer.register("胜利商店");

        // 4.业务逻辑处理
        shopServer.business();
    }
}
