package com.jyadmin.module.productCategory.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品类别表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-03 11:17:29 <br>
 * @description: ProductCategoryCreateReqVO <br>
 */
@ApiModel("商品类别表-新增-数据模型")
@Data
public class ProductCategoryCreateReqVO implements Serializable {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", name = "name")
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", name = "code")
    @NotBlank(message = "编号不能为空")
    private String code;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", name = "icon")
    @NotBlank(message = "图标不能为空")
    private String icon;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", name = "description")
    @NotBlank(message = "描述不能为空")
    private String description;

    /**
     * 是否启用 0=不展示 1=展示
     */
    @ApiModelProperty(value = "是否启用 0=不展示 1=展示", name = "status")
    @NotNull(message = "是否启用 0=不展示 1=展示不能为空")
    private Integer status;
}
