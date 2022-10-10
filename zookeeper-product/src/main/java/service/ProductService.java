package service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author YuMengMeng
 * @date 2022/10/8
 */
public interface ProductService {

    /**
     * 减少库存
     *
     * @param id
     * @throws Exception
     */
    void reduceStock(@Param("id") Integer id) throws Exception;
}
