package com.jyadmin.module.dashboard.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-11 17:15 <br>
 * @description: ServiceCategoryChartItem <br>
 */
@Data
@Accessors(chain = true)
public class ServiceCategoryChartItemDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private Integer count;

}
