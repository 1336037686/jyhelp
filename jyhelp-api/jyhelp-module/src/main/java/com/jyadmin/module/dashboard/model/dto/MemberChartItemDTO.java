package com.jyadmin.module.dashboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-11 13:20 <br>
 * @description: MemberChartItemDTO <br>
 */
@Data
@Accessors(chain = true)
public class MemberChartItemDTO {

    /**
     * 日期
     */
    private String date;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 新增数目
     */
    private Integer add;

}
