package mapper;

import model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 商品操作类
 *
 * @author YuMengMeng
 * @date 2022/10/8
 */
@Mapper
public interface ProductMapper {

    /**
     * 查询商品
     *
     * @param id
     * @return
     */
    @Select("select * from product where id = #{id}")
    Product getProduct(@Param("id") Integer id);

    /**
     * 减少库存
     *
     * @param id
     * @return
     */
    @Update("update product set stock = stock - 1 where id = #{id}")
    int reduceStock(@Param("id") Integer id);
}
