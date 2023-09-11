package com.jyadmin.module.dashboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-11 12:41 <br>
 * @description: OrderChartItem <br>
 */
@Data
@Accessors(chain = true)
public class OrderChartItemDTO {

    /**
     * 当前日期
     */
    private String date;

    /**
     * 当日订单数
     */
    private Integer count;

    /**
     * 当日营收
     */
    private Double amount;

}
