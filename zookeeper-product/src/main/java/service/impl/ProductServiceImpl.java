package service.impl;

import mapper.OrderMapper;
import mapper.ProductMapper;
import model.Order;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ProductService;

import java.util.UUID;

/**
 * @author YuMengMeng
 * @date 2022/10/8
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 减少库存
     *
     * @param id
     * @return
     */
    @Override
    public void reduceStock(Integer id) throws Exception {
        // 1.获取商品库存
        Product product = productMapper.getProduct(id);
        if (product.getStock() > 0) {
            // 2.减库存
            if (productMapper.reduceStock(id) == 1) {
                Order order = new Order(UUID.randomUUID().toString(), id, 101);
                orderMapper.insert(order);
            } else {
                throw new RuntimeException("减库存失败");
            }
        } else {
            throw new RuntimeException("已抢光");
        }
    }
}
