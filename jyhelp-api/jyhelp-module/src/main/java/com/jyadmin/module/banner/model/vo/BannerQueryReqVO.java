package com.jyadmin.module.banner.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Banner设置 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:56:21 <br>
 * @description: BannerQueryReqVO <br>
 */
@ApiModel("Banner设置-查询-数据模型")
@Data
public class BannerQueryReqVO extends BasePageVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    private Long id;

    /**
     * banner图片
     */
    @ApiModelProperty(value = "banner图片", name = "bannerImg")
    private String bannerImg;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", name = "sort")
    private Integer sort;

    /**
     * 版本
     */
    @ApiModelProperty(value = "版本", name = "version")
    private Long version;
}
