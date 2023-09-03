package com.jyadmin.module.productCategory.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品类别表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-03 11:17:29 <br>
 * @description: ProductCategoryQueryReqVO <br>
 */
@ApiModel("商品类别表-查询-数据模型")
@Data
public class ProductCategoryQueryReqVO extends BasePageVO implements Serializable {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", name = "name")
    private String name;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    /**
     * 是否启用 0=不展示 1=展示
     */
    @ApiModelProperty(value = "是否启用 0=不展示 1=展示", name = "status")
    private Integer status;
}
