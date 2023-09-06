package com.jyadmin.module.customerService.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 会员服务表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:12:27 <br>
 * @description: CustomerServiceUpdateReqVO <br>
 */
@ApiModel("会员服务表-修改-数据模型")
@Data
public class CustomerServiceUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 服务项目编号
     */
    @ApiModelProperty(value = "服务项目编号", name = "serviceCode")
    @NotBlank(message = "服务项目编号不能为空")
    private String serviceCode;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", name = "userId")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 服务类别 tb_service_category ID
     */
    @ApiModelProperty(value = "服务类别 tb_service_category ID", name = "serviceCategoryId")
    @NotNull(message = "服务类别 tb_service_category ID不能为空")
    private Long serviceCategoryId;

    /**
     * 剩余服务库存
     */
    @ApiModelProperty(value = "剩余服务库存", name = "serviceStock")
    @NotBlank(message = "剩余服务库存不能为空")
    private String serviceStock;

    /**
     * 服务状态 （服务中、已结束）
     */
    @ApiModelProperty(value = "服务状态 （服务中、已结束）", name = "serviceStatus")
    @NotBlank(message = "服务状态 （服务中、已结束）不能为空")
    private String serviceStatus;
}
