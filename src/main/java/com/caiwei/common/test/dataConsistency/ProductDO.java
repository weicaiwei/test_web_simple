package com.caiwei.common.test.dataConsistency;

import lombok.Data;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-28
 */
@Data
public class ProductDO {

    private String id;
    private String name;
    private Integer newCount;
    private Integer oldCount;
}
