package com.jyadmin.module.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 12:34:21 <br>
 * @description: ProductUpdateReqVO <br>
 */
@ApiModel("商品表-修改-数据模型")
@Data
public class ProductUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", name = "name")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码", name = "code")
    @NotBlank(message = "商品编码不能为空")
    private String code;

    /**
     * 商品类别
     */
    @ApiModelProperty(value = "商品类别", name = "productCategoryId")
    @NotNull(message = "商品类别不能为空")
    private Long productCategoryId;

    /**
     * 服务类别
     */
    @ApiModelProperty(value = "服务类别", name = "serviceCategoryId")
    @NotNull(message = "服务类别不能为空")
    private Long serviceCategoryId;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片", name = "productImg")
    @NotBlank(message = "商品图片不能为空")
    private String productImg;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述", name = "description")
    private String description;

    /**
     * 原始价格（单位分）
     */
    @ApiModelProperty(value = "原始价格（单位分）", name = "initialPrice")
    @NotNull(message = "原始价格（单位分）不能为空")
    private Long initialPrice;

    /**
     * 商品库存
     */
    @ApiModelProperty(value = "商品库存", name = "stock")
    @NotNull(message = "商品库存不能为空")
    private Integer stock;

    /**
     * 商品单位
     */
    @ApiModelProperty(value = "商品单位", name = "unit")
    @NotBlank(message = "商品单位不能为空")
    private String unit;

    /**
     * 商品折扣
     */
    @ApiModelProperty(value = "商品折扣", name = "discount")
    @NotNull(message = "商品折扣不能为空")
    private BigDecimal discount;

    /**
     * 当前价格（单位分）
     */
    @ApiModelProperty(value = "当前价格（单位分）", name = "currentPrice")
    @NotNull(message = "当前价格（单位分）不能为空")
    private Long currentPrice;

    /**
     * 是否热门 0=不热门 1=热门
     */
    @ApiModelProperty(value = "是否热门 0=不热门 1=热门", name = "hot")
    @NotNull(message = "是否热门 0=不热门 1=热门不能为空")
    private Integer hot;

    /**
     * 上架状态 0=下架 1=上架
     */
    @ApiModelProperty(value = "上架状态 0=下架 1=上架", name = "status")
    @NotNull(message = "上架状态 0=下架 1=上架不能为空")
    private Integer status;
}
