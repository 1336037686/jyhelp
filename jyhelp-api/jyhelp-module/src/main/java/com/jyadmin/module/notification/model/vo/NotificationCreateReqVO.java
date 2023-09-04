package com.jyadmin.module.notification.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商城公告表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:25:29 <br>
 * @description: NotificationCreateReqVO <br>
 */
@ApiModel("商城公告表-新增-数据模型")
@Data
public class NotificationCreateReqVO implements Serializable {

    /**
     * 公告标题
     */
    @ApiModelProperty(value = "公告标题", name = "title")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    /**
     * 公告内容
     */
    @ApiModelProperty(value = "公告内容", name = "content")
    private String content;

    /**
     * 是否置顶 0=不置顶 1=置顶
     */
    @ApiModelProperty(value = "是否置顶 0=不置顶 1=置顶", name = "pinned")
    @NotNull(message = "是否置顶 0=不置顶 1=置顶不能为空")
    private Integer pinned;

    /**
     * 公告状态 0=不显示 1=显示
     */
    @ApiModelProperty(value = "公告状态 0=不显示 1=显示", name = "status")
    @NotNull(message = "公告状态 0=不显示 1=显示不能为空")
    private Integer status;
}
