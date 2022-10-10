package mapper;

import model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YuMengMeng
 * @date 2022/10/8
 */

@Mapper
public interface OrderMapper {
    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    @Insert("insert into `order`(id, pid, userid) values (#{id}, #{pid}, #{userid})")
    int insert(Order order);
}
