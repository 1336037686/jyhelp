package com.jyadmin.module.notification.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商城公告表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:25:29 <br>
 * @description: NotificationQueryReqVO <br>
 */
@ApiModel("商城公告表-查询-数据模型")
@Data
public class NotificationQueryReqVO extends BasePageVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    private Long id;

    /**
     * 公告标题
     */
    @ApiModelProperty(value = "公告标题", name = "title")
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
    private Integer pinned;

    /**
     * 公告状态 0=不显示 1=显示
     */
    @ApiModelProperty(value = "公告状态 0=不显示 1=显示", name = "status")
    private Integer status;
}
