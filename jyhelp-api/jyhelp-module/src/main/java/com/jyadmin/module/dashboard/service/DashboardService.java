package com.jyadmin.module.dashboard.service;

import com.jyadmin.module.dashboard.model.dto.*;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-09 12:22 <br>
 * @description: DashboardService <br>
 */
public interface DashboardService {

    List<PanelItemDTO> getPanelGroup();

    List<OrderChartItemDTO> getOrderChartData();

    List<MemberChartItemDTO> getMemberChart();

    List<ServiceChartItemDTO> getServiceChart();

    List<ServiceCategoryChartItemDTO> getServiceCategoryChart();
}
