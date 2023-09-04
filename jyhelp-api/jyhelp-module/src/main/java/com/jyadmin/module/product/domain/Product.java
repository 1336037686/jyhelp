package com.jyadmin.module.product.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 商品表 <br>
 * @TableName tb_product <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 12:34:21 <br>
 * @description: Product <br>
 */
@TableName(value ="tb_product")
@Accessors(chain = true)
@Data
public class Product extends BaseEntity implements Serializable {

    /**
     * 商品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 商品编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 商品类别
     */
    @TableField(value = "product_category_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productCategoryId;

    /**
     * 服务类别
     */
    @TableField(value = "service_category_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceCategoryId;

    /**
     * 商品图片
     */
    @TableField(value = "product_img")
    private String productImg;

    /**
     * 商品描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 原始价格（单位分）
     */
    @TableField(value = "initial_price")
    private Long initialPrice;

    /**
     * 商品库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 商品单位
     */
    @TableField(value = "unit")
    private String unit;

    /**
     * 商品折扣
     */
    @TableField(value = "discount")
    private BigDecimal discount;

    /**
     * 当前价格（单位分）
     */
    @TableField(value = "current_price")
    private Long currentPrice;

    /**
     * 是否热门 0=不热门 1=热门
     */
    @TableField(value = "hot")
    private Integer hot;

    /**
     * 上架状态 0=下架 1=上架
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 版本
     */
    @TableField(value = "version")
    @Version
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}