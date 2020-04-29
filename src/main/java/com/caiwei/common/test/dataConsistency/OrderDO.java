package com.caiwei.common.test.dataConsistency;

import lombok.Data;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-28
 */
@Data
public class OrderDO {

    private String serialNo;
    private String uid;
    private String productId;
    private Integer buyCount;
}
