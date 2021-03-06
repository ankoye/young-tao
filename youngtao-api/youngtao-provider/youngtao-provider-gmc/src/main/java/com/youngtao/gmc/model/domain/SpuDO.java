package com.youngtao.gmc.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youngtao.core.lang.JsonList;
import com.youngtao.core.lang.JsonMap;
import com.youngtao.web.typehandler.JsonListTypeHandler;
import com.youngtao.web.typehandler.JsonMapTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品SPU实体类
 *
 * @author ankoye@qq.com
 * @since 2020/11/21
 */
@Data
@TableName("gmc_spu")
public class SpuDO implements Serializable {

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String spuId;

    /**
     * 品牌+型号
     */
    private String spu;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 店名
     */
    private String shopName;

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
    @TableField(typeHandler = JsonListTypeHandler.class)
    private JsonList<String> images;

    /**
     * 商品图文说明
     */
    private String detail;

    /**
     * 商品服务
     */
    @TableField(typeHandler = JsonListTypeHandler.class)
    private JsonList<String> serve;

    /**
     * 规格参数
     */
    @TableField(typeHandler = JsonMapTypeHandler.class)
    private JsonMap<String> spec;

    /**
     * sku模板
     */
    @TableField(typeHandler = JsonMapTypeHandler.class)
    private JsonMap<List<String>> skuTemplate;

    /**
     * 邮费
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
     * 排序
     */
    private Integer seq;

    /**
     *  是否可销售
     */
    private Boolean isMarketable;

    /**
     * 0-审核中，1-审核成功，2-审核失败
     */
    private Integer status;

    @TableField("is_deleted")
    private Boolean deleted;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}