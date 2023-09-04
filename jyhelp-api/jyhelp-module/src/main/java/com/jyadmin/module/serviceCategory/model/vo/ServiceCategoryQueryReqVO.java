package com.jyadmin.module.serviceCategory.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务类别表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 08:57:22 <br>
 * @description: ServiceCategoryQueryReqVO <br>
 */
@ApiModel("服务类别表-查询-数据模型")
@Data
public class ServiceCategoryQueryReqVO extends BasePageVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    private Long id;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", name = "name")
    private String name;

    /**
     * 服务编号
     */
    @ApiModelProperty(value = "服务编号", name = "code")
    private String code;


    /**
     * 服务类型（服务时间类型、服务次数类型、物品类型等）
     */
    @ApiModelProperty(value = "服务类型（服务时间类型、服务次数类型、物品类型等）", name = "type")
    private String type;

    /**
     * 是否启用 0=禁用 1=启用
     */
    @ApiModelProperty(value = "是否启用 0=禁用 1=启用", name = "status")
    private Integer status;
}
