package com.jyadmin.module.workOrder.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工单表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 09:27:09 <br>
 * @description: WorkOrderCreateReqVO <br>
 */
@ApiModel("工单表-新增-数据模型")
@Data
public class WorkOrderCreateReqVO implements Serializable {

    /**
     * 创建用户
     */
    @ApiModelProperty(value = "创建用户", name = "userId")
    @NotNull(message = "创建用户不能为空")
    private Long userId;

    /**
     * 工单类别
     */
    @ApiModelProperty(value = "工单类别", name = "type")
    @NotBlank(message = "工单类别不能为空")
    private String type;

    /**
     * 工单标题
     */
    @ApiModelProperty(value = "工单标题", name = "title")
    @NotBlank(message = "工单标题不能为空")
    private String title;

    /**
     * 工单内容
     */
    @ApiModelProperty(value = "工单内容", name = "content")
    @NotBlank(message = "工单内容不能为空")
    private String content;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式", name = "phone")
    @NotBlank(message = "联系方式不能为空")
    private String phone;

    /**
     * 联系邮箱
     */
    @ApiModelProperty(value = "联系邮箱", name = "email")
    @NotBlank(message = "联系邮箱不能为空")
    private String email;

    /**
     * 工单状态
     */
    @ApiModelProperty(value = "工单状态", name = "status")
    private String status;

    /**
     * 处理备注
     */
    @ApiModelProperty(value = "处理备注", name = "handleRemark")
    private String handleRemark;
}
