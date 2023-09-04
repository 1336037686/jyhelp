package com.jyadmin.module.banner.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * Banner设置 <br>
 * @TableName tb_banner <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:56:21 <br>
 * @description: Banner <br>
 */
@TableName(value ="tb_banner")
@Accessors(chain = true)
@Data
public class Banner extends BaseEntity implements Serializable {

    /**
     * banner图片
     */
    @TableField(value = "banner_img")
    private String bannerImg;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 版本
     */
    @TableField(value = "version")
    @Version
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}