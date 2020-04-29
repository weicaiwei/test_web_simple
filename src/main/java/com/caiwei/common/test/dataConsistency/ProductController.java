package com.caiwei.common.test.dataConsistency;

import com.caiwei.common.test.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 用户商品操作入口
 *
 * @auther caiwei
 * @date 2020-04-28
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("buy")
    public Result buyP(@RequestBody @Validated BuyInfoVO buyInfoVO) {

        productService.buyProduct(buyInfoVO);
        return Result.success();
    }

}
