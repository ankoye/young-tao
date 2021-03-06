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

/**
 * 商品SKU实体类
 *
 * @author ankoye@qq.com
 * @since 2020/11/21
 */
@Data
@TableName("gmc_sku")
public class SkuDO implements Serializable {
    private static final long serialVersionUID = 227889639103151085L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String skuId;

    /**
     * spuId
     */
    private String spuId;

    /**
     * 商品规格
     */
    @TableField(typeHandler = JsonMapTypeHandler.class)
    private JsonMap<String> sku;

    /**
     * 商品图片
     */
    @TableField(typeHandler = JsonListTypeHandler.class)
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

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}