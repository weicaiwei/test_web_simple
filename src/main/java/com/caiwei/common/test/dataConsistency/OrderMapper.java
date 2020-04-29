package com.caiwei.common.test.dataConsistency;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-28
 */
@Mapper
@Repository
public interface OrderMapper {

    @Insert(" insert into avp_order(serial_no, uid, product_id, buy_count)" +
            " values(#{serialNo}, #{uid}, #{productId}, #{buyCount})")
    int addOrder(OrderDO orderDO);

    int updateOrder(OrderDO orderDO);
}
