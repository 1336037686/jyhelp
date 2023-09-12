package com.jyadmin.module.blog.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 博客文章表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 17:20:12 <br>
 * @description: BlogQueryReqVO <br>
 */
@ApiModel("博客文章表-查询-数据模型")
@Data
public class BlogQueryReqVO extends BasePageVO implements Serializable {

    /**
     * 文章名称
     */
    @ApiModelProperty(value = "文章名称", name = "title")
    private String title;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", name = "category")
    private Long category;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", name = "tag")
    private Long tag;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容", name = "content")
    private String content;

    /**
     * 状态（草稿、发布）
     */
    @ApiModelProperty(value = "状态（草稿、发布）", name = "status")
    private String status;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者", name = "author")
    private String author;
}
