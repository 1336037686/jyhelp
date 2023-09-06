package com.jyadmin.module.handleService.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务执行记录表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @description: HandleServiceQueryReqVO <br>
 */
@ApiModel("服务执行记录表-查询-数据模型")
@Data
public class HandleServiceQueryReqVO extends BasePageVO implements Serializable {

    /**
     * 服务记录编号
     */
    @ApiModelProperty(value = "服务记录编号", name = "handleCode")
    private String handleCode;

    /**
     * 会员服务ID customer_service id
     */
    @ApiModelProperty(value = "会员服务ID customer_service id", name = "customerServiceId")
    private Long customerServiceId;

    /**
     * 服务时间
     */
    @ApiModelProperty(value = "服务时间", name = "handleTime")
    private LocalDateTime handleTime;

    /**
     * 服务人员
     */
    @ApiModelProperty(value = "服务人员", name = "handleUser")
    private String handleUser;

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
    @ApiModelProperty(value = "服务状态 （已完成、进行中、待处理）", name = "handleStatus")
    private String handleStatus;

    /**
     * 用户反馈
     */
    @ApiModelProperty(value = "用户反馈", name = "userFeedback")
    private String userFeedback;
}
