package com.jyadmin.module.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 博客文章表 <br>
 * @TableName tb_blog <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 17:20:12 <br>
 * @description: Blog <br>
 */
@TableName(value ="tb_blog")
@Accessors(chain = true)
@Data
public class Blog extends BaseEntity implements Serializable {

    /**
     * 文章名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 摘要
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 类别
     */
    @TableField(value = "category")
    private Long category;

    /**
     * 标签
     */
    @TableField(value = "tag")
    private Long tag;

    /**
     * 封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 状态（草稿、发布）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}