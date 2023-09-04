package com.jyadmin.module.banner.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Banner设置 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:56:21 <br>
 * @description: BannerUpdateReqVO <br>
 */
@ApiModel("Banner设置-修改-数据模型")
@Data
public class BannerUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * banner图片
     */
    @ApiModelProperty(value = "banner图片", name = "bannerImg")
    @NotBlank(message = "banner图片不能为空")
    private String bannerImg;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", name = "sort")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}
