package com.jyadmin.module.workOrder.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工单表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-11 19:57:27 <br>
 * @description: WorkOrderQueryReqVO <br>
 */
@ApiModel("工单表-查询-数据模型")
@Data
public class WorkOrderQueryReqVO extends BasePageVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    private Long id;

    /**
     * 创建用户
     */
    @ApiModelProperty(value = "创建用户", name = "userId")
    private Long userId;

    /**
     * 工单类别
     */
    @ApiModelProperty(value = "工单类别", name = "type")
    private String type;

    /**
     * 工单标题
     */
    @ApiModelProperty(value = "工单标题", name = "title")
    private String title;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式", name = "phone")
    private String phone;

    /**
     * 联系邮箱
     */
    @ApiModelProperty(value = "联系邮箱", name = "email")
    private String email;

    /**
     * 工单状态
     */
    @ApiModelProperty(value = "工单状态", name = "status")
    private String status;
}
