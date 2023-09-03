package com.jyadmin.module.productCategory.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 商品类别表 <br>
 * @TableName tb_product_category <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-03 11:17:29 <br>
 * @description: ProductCategory <br>
 */
@TableName(value ="tb_product_category")
@Accessors(chain = true)
@Data
public class ProductCategory extends BaseEntity implements Serializable {

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否启用 0=不展示 1=展示
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