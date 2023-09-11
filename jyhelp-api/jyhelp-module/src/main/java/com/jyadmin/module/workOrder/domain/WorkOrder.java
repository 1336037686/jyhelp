package com.jyadmin.module.workOrder.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 工单表 <br>
 * @TableName tb_work_order <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-11 19:57:27 <br>
 * @description: WorkOrder <br>
 */
@TableName(value ="tb_work_order")
@Accessors(chain = true)
@Data
public class WorkOrder extends BaseEntity implements Serializable {

    /**
     * 创建用户
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 工单类别
     */
    @TableField(value = "type")
    private String type;

    /**
     * 工单标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 工单内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 联系方式
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 联系邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 工单状态（待处理、已处理、不予解决）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 版本
     */
    @TableField(value = "version")
    @Version
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}