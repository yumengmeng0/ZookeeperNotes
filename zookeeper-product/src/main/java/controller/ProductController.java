package controller;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ProductService;

/**
 * 控制层
 *
 * @author YuMengMeng
 * @date 2022/10/8
 */

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * zookeeper集群的ip和端口
     */
    private static String connectString = "47.98.138.215:2181,81.68.215.86:2181,139.9.81.145:2181";

    @GetMapping("/product/reduce")
    @ResponseBody
    public Object reduce(Integer id) throws Exception {

        // 1.创建curator客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
        // 2. 加锁
        InterProcessMutex lock = new InterProcessMutex(client, "/product_" + id);
        try {
            lock.acquire();
            productService.reduceStock(id);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw e;
            }
        } finally {
            // 3. 释放锁
            lock.release();
        }
        return "ok";
    }
}
