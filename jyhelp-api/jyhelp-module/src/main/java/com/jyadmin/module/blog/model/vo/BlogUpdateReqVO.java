package com.jyadmin.module.blog.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 博客文章表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 17:20:12 <br>
 * @description: BlogUpdateReqVO <br>
 */
@ApiModel("博客文章表-修改-数据模型")
@Data
public class BlogUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 文章名称
     */
    @ApiModelProperty(value = "文章名称", name = "title")
    @NotBlank(message = "文章名称不能为空")
    private String title;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要", name = "intro")
    @NotBlank(message = "摘要不能为空")
    private String intro;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", name = "category")
    @NotNull(message = "类别不能为空")
    private Long category;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", name = "tag")
    @NotNull(message = "标签不能为空")
    private Long tag;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面", name = "cover")
    @NotBlank(message = "封面不能为空")
    private String cover;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容", name = "content")
    @NotBlank(message = "文章内容不能为空")
    private String content;

    /**
     * 状态（草稿、发布）
     */
    @ApiModelProperty(value = "状态（草稿、发布）", name = "status")
    @NotNull(message = "状态（草稿、发布）不能为空")
    private Integer status;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者", name = "author")
    @NotBlank(message = "作者不能为空")
    private String author;
}
