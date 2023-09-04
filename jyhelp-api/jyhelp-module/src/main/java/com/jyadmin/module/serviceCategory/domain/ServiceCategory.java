package com.jyadmin.module.serviceCategory.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 服务类别表 <br>
 * @TableName tb_service_category <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 08:57:22 <br>
 * @description: ServiceCategory <br>
 */
@TableName(value ="tb_service_category")
@Accessors(chain = true)
@Data
public class ServiceCategory extends BaseEntity implements Serializable {

    /**
     * 服务名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 服务编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 服务图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 服务类型（服务时间类型、服务次数类型、物品类型等）
     */
    @TableField(value = "type")
    private String type;

    /**
     * 版本
     */
    @TableField(value = "version")
    @Version
    private Long version;

    /**
     * 服务描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否启用 0=禁用 1=启用
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}