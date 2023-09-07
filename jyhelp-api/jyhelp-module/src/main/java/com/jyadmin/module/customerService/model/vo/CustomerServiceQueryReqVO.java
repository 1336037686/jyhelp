package com.jyadmin.module.customerService.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员服务表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:12:27 <br>
 * @description: CustomerServiceQueryReqVO <br>
 */
@ApiModel("会员服务表-查询-数据模型")
@Data
public class CustomerServiceQueryReqVO extends BasePageVO implements Serializable {

    /**
     * 服务项目编号
     */
    @ApiModelProperty(value = "服务项目编号", name = "serviceCode")
    private String serviceCode;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", name = "userId")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", name = "username")
    private String username;

    /**
     * 服务类别 tb_service_category ID
     */
    @ApiModelProperty(value = "服务类别 tb_service_category ID", name = "serviceCategoryId")
    private Long serviceCategoryId;

    /**
     * 服务状态 （服务中、已结束）
     */
    @ApiModelProperty(value = "服务状态 （服务中、已结束）", name = "serviceStatus")
    private String serviceStatus;
}
