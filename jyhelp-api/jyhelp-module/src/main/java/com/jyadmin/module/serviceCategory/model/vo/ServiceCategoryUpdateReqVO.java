package com.jyadmin.module.serviceCategory.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务类别表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 08:57:22 <br>
 * @description: ServiceCategoryUpdateReqVO <br>
 */
@ApiModel("服务类别表-修改-数据模型")
@Data
public class ServiceCategoryUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", name = "name")
    @NotBlank(message = "服务名称不能为空")
    private String name;

    /**
     * 服务编号
     */
    @ApiModelProperty(value = "服务编号", name = "code")
    @NotBlank(message = "服务编号不能为空")
    private String code;

    /**
     * 服务图标
     */
    @ApiModelProperty(value = "服务图标", name = "icon")
    @NotBlank(message = "服务图标不能为空")
    private String icon;

    /**
     * 服务类型（服务时间类型、服务次数类型、物品类型等）
     */
    @ApiModelProperty(value = "服务类型（服务时间类型、服务次数类型、物品类型等）", name = "type")
    @NotBlank(message = "服务类型（服务时间类型、服务次数类型、物品类型等）不能为空")
    private String type;

    /**
     * 服务描述
     */
    @ApiModelProperty(value = "服务描述", name = "description")
    @NotBlank(message = "服务描述不能为空")
    private String description;

    /**
     * 是否启用 0=禁用 1=启用
     */
    @ApiModelProperty(value = "是否启用 0=禁用 1=启用", name = "status")
    @NotNull(message = "是否启用 0=禁用 1=启用不能为空")
    private Integer status;
}
