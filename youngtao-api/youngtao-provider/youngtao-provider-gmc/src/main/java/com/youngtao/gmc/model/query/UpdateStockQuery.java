package com.youngtao.gmc.model.query;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2020/12/13
 */
@Data
public class UpdateStockQuery {

    private String skuId;

    private Integer num;
}
