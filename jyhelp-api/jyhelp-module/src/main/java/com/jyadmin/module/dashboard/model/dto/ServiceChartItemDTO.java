package com.jyadmin.module.dashboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-11 16:49 <br>
 * @description: ServiceChartItemDTO <br>
 */
@Data
@Accessors(chain = true)
public class ServiceChartItemDTO {

    /**
     * 当前日期
     */
    private String date;

    /**
     * 当日服务数
     */
    private Integer count;

}
