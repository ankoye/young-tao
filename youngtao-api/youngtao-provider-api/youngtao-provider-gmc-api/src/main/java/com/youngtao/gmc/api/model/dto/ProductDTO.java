package com.youngtao.gmc.api.model.dto;

import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Data
public class ProductDTO implements Serializable {

    private String spuId;

    /**
     * 品牌+型号
     */
    private String spu;

    /**
     * 商品标题
     */
//    private String title;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     * 一级分类
     */
    private String category1Id;

    /**
     * 二级分类
     */
    private String category2Id;

    /**
     * 三级分类
     */
    private String category3Id;

    /**
     * 商品展示
     */
    private JsonList<String> images;

    /**
     * 商品详情说明
     */
    private String detail;

    /**
     * 商品服务
     */
    private JsonList<String> serve;

    /**
     * 规格参数
     */
    private JsonMap<String> spec;

    /**
     * sku模板
     */
    private JsonMap<List<String>> skuTemplate;

    /**
     * 是否包邮
     */
    private BigDecimal postage;

    /**
     * 销售量
     */
    private Integer saleNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     *  是否可销售
     */
    private Boolean isMarketable;

    /**
     * 0-审核中，1-审核成功，2-审核失败
     */
    private Integer status;

    /**
     * sku
     */
    private List<Sku> skuList;

    @Data
    public static class Sku {

        private String skuId;

        /**
         * spuId
         */
        private String spuId;

        /**
         * 商品规格
         */
        private JsonMap<String> sku;

        /**
         * 商品图片
         */
        private JsonList<String> images;

        /**
         * 单价
         */
        private BigDecimal price;

        /**
         * 原价
         */
        private BigDecimal oldPrice;

        /**
         * 库存数量
         */
        private Integer num;

        /**
         * 剩余库存
         */
        private Integer residue;

        /**
         * 库存预警数量
         */
        private Integer alertNum;

        /**
         * 销量
         */
        private Integer saleNum;

        /**
         * 冻结库存
         */
        private Integer freezeNum;

        /**
         * 默认显示
         */
        private Boolean defaultShow;
    }
}
