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
 * @description: HandleServiceCreateReqVO <br>
 */
@ApiModel("服务执行记录表-新增-数据模型")
@Data
public class HandleServiceCreateReqVO implements Serializable {

    /**
     * 服务记录编号
     */
    @ApiModelProperty(value = "服务记录编号", name = "handleCode")
    @NotBlank(message = "服务记录编号不能为空")
    private String handleCode;

    /**
     * 会员服务ID
     */
    @ApiModelProperty(value = "会员服务ID", name = "customerServiceId")
    @NotNull(message = "会员服务ID不能为空")
    private Long customerServiceId;

    /**
     * 服务记录
     */
    @ApiModelProperty(value = "服务记录", name = "handleRemark")
    private String handleRemark;

    /**
     * 服务附件
     */
    @ApiModelProperty(value = "服务附件", name = "attachment")
    private String attachment;

    /**
     * 服务状态 （已完成、进行中、待处理）
     */
    @ApiModelProperty(value = "服务状态", name = "handleStatus")
    @NotBlank(message = "服务状态不能为空")
    private String handleStatus;

    /**
     * 用户反馈
     */
    @ApiModelProperty(value = "用户反馈", name = "userFeedback")
    private String userFeedback;
}
