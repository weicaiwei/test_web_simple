package com.caiwei.common.test.dataConsistency;

import com.caiwei.common.test.mapper.CommonConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-28
 */
@Service
@Slf4j
public class ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CommonConfigMapper commonConfigMapper;

    /**
     * 悲观锁方式
     *
     * @param buyInfoVO 购买信息
     */
    @Transactional
    public void buyProduct(BuyInfoVO buyInfoVO) {


        //用乐观锁还是悲观锁 0:悲观锁 1:乐观锁
        String sync = commonConfigMapper.getProperty("sync");
        ProductDO productDO;
        if(sync.equals("0")){
            productDO = productMapper.syncGetProduct(buyInfoVO.getProductId());
        }else {
            productDO = productMapper.getProduct(buyInfoVO.getProductId());
        }

        if (Objects.isNull(productDO)) {
            log.warn("未查询到该商品");
            return;
        }
        if (productDO.getNewCount() <= 0 || (productDO.getNewCount() - buyInfoVO.getProductCount() < 0)) {
            log.warn("商品库存不足 或者 购买数量太多");
            return;
        }
        OrderDO orderDO = new OrderDO();
        orderDO.setSerialNo(UUID.randomUUID().toString().replaceAll("-", ""));
        orderDO.setUid(buyInfoVO.getUid());
        orderDO.setProductId(buyInfoVO.getProductId());
        orderDO.setBuyCount(buyInfoVO.getProductCount());
        int addResult = orderMapper.addOrder(orderDO);
        if (addResult == 1) {
            if(sync.equals("0")){//悲观锁模式，由于库存表行已上锁，插入订单信息成功直接更新商品库存表即可
                productDO.setNewCount(productDO.getNewCount() - buyInfoVO.getProductCount());
                int updateResult = productMapper.updateProductCount(productDO);
                if (updateResult != 1) {
                    throw new RuntimeException("更新商品表库存失败，抛出异常，回滚插入订单表操作，方法退出结束事务");
                }
            }else {
                productDO.setOldCount(productDO.getNewCount());
                productDO.setNewCount(productDO.getNewCount() - buyInfoVO.getProductCount());
                for (int i = 0; true; i++) {
                    int updateResult = productMapper.updateProductCountO(productDO);
                    if (updateResult == 1) {
                        //更新数据库成功，退出循环
                        break;
                    } else {
                        if (i == 2) {
                            log.warn("3次都没有获得乐观锁，退出方法，客户重新下单");
                            throw new RuntimeException("3次都没有获得乐观锁，抛出异常，回滚插入订单表操作，方法退出结束事务");
                        }
                    }
                }
            }

        } else {
            throw new RuntimeException("订单表插入数据失败，抛出异常，回滚，方法退出结束事务");
        }


    }


}
