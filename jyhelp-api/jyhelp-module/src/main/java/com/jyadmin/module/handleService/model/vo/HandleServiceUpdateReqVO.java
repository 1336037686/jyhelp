package com.jyadmin.module.handleService.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务执行记录表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @description: HandleServiceUpdateReqVO <br>
 */
@ApiModel("服务执行记录表-修改-数据模型")
@Data
public class HandleServiceUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 服务记录编号
     */
    @ApiModelProperty(value = "服务记录编号", name = "handleCode")
    @NotBlank(message = "服务记录编号不能为空")
    private String handleCode;

    /**
     * 会员服务ID customer_service id
     */
    @ApiModelProperty(value = "会员服务ID customer_service id", name = "customerServiceId")
    @NotNull(message = "会员服务ID customer_service id不能为空")
    private Long customerServiceId;

    /**
     * 服务时间
     */
    @ApiModelProperty(value = "服务时间", name = "handleTime")
    @NotNull(message = "服务时间不能为空")
    private LocalDateTime handleTime;

    /**
     * 服务人员
     */
    @ApiModelProperty(value = "服务人员", name = "handleUser")
    @NotBlank(message = "服务人员不能为空")
    private String handleUser;

    /**
     * 服务记录
     */
    @ApiModelProperty(value = "服务记录", name = "handleRemark")
    @NotBlank(message = "服务记录不能为空")
    private String handleRemark;

    /**
     * 服务附件
     */
    @ApiModelProperty(value = "服务附件", name = "attachment")
    @NotBlank(message = "服务附件不能为空")
    private String attachment;

    /**
     * 服务状态 （已完成、进行中、待处理）
     */
    @ApiModelProperty(value = "服务状态 （已完成、进行中、待处理）", name = "handleStatus")
    @NotBlank(message = "服务状态 （已完成、进行中、待处理）不能为空")
    private String handleStatus;

    /**
     * 用户反馈
     */
    @ApiModelProperty(value = "用户反馈", name = "userFeedback")
    @NotBlank(message = "用户反馈不能为空")
    private String userFeedback;
}
