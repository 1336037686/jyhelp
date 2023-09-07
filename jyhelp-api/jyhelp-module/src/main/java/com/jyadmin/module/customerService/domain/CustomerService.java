package com.jyadmin.module.customerService.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 会员服务表 <br>
 * @TableName tb_customer_service <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:12:27 <br>
 * @description: CustomerService <br>
 */
@TableName(value ="tb_customer_service")
@Accessors(chain = true)
@Data
public class CustomerService extends BaseEntity implements Serializable {

    /**
     * 服务项目编号
     */
    @TableField(value = "service_code")
    private String serviceCode;

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 服务类别 tb_service_category ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "service_category_id")
    private Long serviceCategoryId;

    /**
     * 剩余服务库存
     */
    @TableField(value = "service_stock")
    private String serviceStock;

    /**
     * 服务状态 （服务中、已结束）
     */
    @TableField(value = "service_status")
    private String serviceStatus;

    /**
     * 版本
     */
    @TableField(value = "version")
    @Version
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}