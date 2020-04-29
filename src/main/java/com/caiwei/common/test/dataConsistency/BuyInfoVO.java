package com.caiwei.common.test.dataConsistency;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-28
 */
@Data
public class BuyInfoVO {

    @NotBlank
    private String uid;
    @NotBlank
    private String productId;
    @NotNull
    private Integer productCount;
}
