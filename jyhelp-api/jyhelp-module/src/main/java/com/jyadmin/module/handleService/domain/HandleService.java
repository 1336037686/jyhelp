package com.jyadmin.module.handleService.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 服务执行记录表 <br>
 * @TableName tb_handle_service <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @description: HandleService <br>
 */
@TableName(value ="tb_handle_service")
@Accessors(chain = true)
@Data
public class HandleService extends BaseEntity implements Serializable {

    /**
     * 服务记录编号
     */
    @TableField(value = "handle_code")
    private String handleCode;

    /**
     * 会员服务ID customer_service id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "customer_service_id")
    private Long customerServiceId;

    /**
     * 服务时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "handle_time")
    private LocalDateTime handleTime;

    /**
     * 服务人员
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "handle_user")
    private Long handleUser;

    /**
     * 服务记录
     */
    @TableField(value = "handle_remark")
    private String handleRemark;

    /**
     * 服务附件
     */
    @TableField(value = "attachment")
    private String attachment;

    /**
     * 服务状态 （已完成、进行中、待处理）
     */
    @TableField(value = "handle_status")
    private String handleStatus;

    /**
     * 用户评分
     */
    @TableField(value = "user_score")
    private Integer userScore;

    /**
     * 用户反馈
     */
    @TableField(value = "user_feedback")
    private String userFeedback;

    /**
     * 版本
     */
    @TableField(value = "version")
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}