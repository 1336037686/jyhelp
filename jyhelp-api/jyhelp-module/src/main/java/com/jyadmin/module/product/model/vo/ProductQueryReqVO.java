package com.jyadmin.module.product.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 12:34:21 <br>
 * @description: ProductQueryReqVO <br>
 */
@ApiModel("商品表-查询-数据模型")
@Data
public class ProductQueryReqVO extends BasePageVO implements Serializable {

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", name = "name")
    private String name;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码", name = "code")
    private String code;

    /**
     * 商品类别
     */
    @ApiModelProperty(value = "商品类别", name = "productCategoryId")
    private Long productCategoryId;

    /**
     * 服务类别
     */
    @ApiModelProperty(value = "服务类别", name = "serviceCategoryId")
    private Long serviceCategoryId;

    /**
     * 是否热门 0=不热门 1=热门
     */
    @ApiModelProperty(value = "是否热门 0=不热门 1=热门", name = "hot")
    private Integer hot;

    /**
     * 上架状态 0=下架 1=上架
     */
    @ApiModelProperty(value = "上架状态 0=下架 1=上架", name = "status")
    private Integer status;
}
