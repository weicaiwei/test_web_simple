package com.caiwei.common.test.dataConsistency;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-28
 */
@Mapper
@Repository
public interface ProductMapper {

    int addProduct(ProductDO productDO);

    //正常更新
    @Update(" update avp_product set count = #{newCount} where id=#{id}")
    int updateProductCount(ProductDO productDO);

    //带乐观锁的更新
    @Update(" update avp_product set count = #{newCount} where id=#{id} and count =#{oldCount}")
    int updateProductCountO(ProductDO productDO);

    @Select(" select id, name, count newCount from avp_product where id = #{id} for update")
    ProductDO syncGetProduct(String productId);

    @Select(" select id, name, count newCount from avp_product where id = #{id}")
    ProductDO getProduct(String productId);
}
