package com.youngtao.gsc.common.constant;

import lombok.Getter;

/**
 * 专门存放Redis的各种Key，统一起来方便管理和维护
 * 每个Key需要写清楚注释，哪个流程的，有什么作用，里面的占位符是什么含义
 *
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public enum RedisKey {
    /**
     * 秒杀商品空间
     * key: 秒杀时间段(yyyyMMddHH)
     */
    SKU_SPACE("youngtao:gsc:sku:space:%s"),
    /**
     * key: skuId
     */
    SKU_KEY("youngtao:gsc:sku:info:%s"),
    /**
     * 扣减库存
     * key: skuId
     */
    SKU_COUNT_KEY("youngtao:gsc:sku:count:%s")
    ;

    @Getter
    private final String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String format(Object ...arg) {
        return String.format(key, arg);
    }

}