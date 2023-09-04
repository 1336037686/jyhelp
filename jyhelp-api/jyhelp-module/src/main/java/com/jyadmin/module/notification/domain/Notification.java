package com.jyadmin.module.notification.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 商城公告表 <br>
 * @TableName tb_notification <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:25:29 <br>
 * @description: Notification <br>
 */
@TableName(value ="tb_notification")
@Accessors(chain = true)
@Data
public class Notification extends BaseEntity implements Serializable {

    /**
     * 公告标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 公告内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 是否置顶 0=不置顶 1=置顶
     */
    @TableField(value = "pinned")
    private Integer pinned;

    /**
     * 公告状态 0=不显示 1=显示
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 版本
     */
    @TableField(value = "version")
    @Version
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}