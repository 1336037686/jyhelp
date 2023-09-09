package com.jyadmin.module.dashboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统计页面项目面板数据模型
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-09 12:35 <br>
 * @description: PanelItemDTO <br>
 */
@Data
@Accessors(chain = true)
public class PanelItemDTO {

    /**
     * 项名称
     */
    private String name;

    /**
     * 项编码
     */
    private String code;

    /**
     * 值
     */
    private String value;

    /**
     * 子项值
     */
    private String subValue;

    /**
     * 子项值状态
     */
    private Integer subValueState;

    /**
     * 子项目备注
     */
    private String subValueRemark;

}
