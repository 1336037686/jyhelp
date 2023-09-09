package com.jyadmin.module.dashboard.controller;

import com.jyadmin.domain.Result;
import com.jyadmin.module.dashboard.model.dto.PanelItemDTO;
import com.jyadmin.module.dashboard.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-09 12:21 <br>
 * @description: DashboardController <br>
 */
@Slf4j
@Api(value = "首页统计", tags = {"商城：首页统计"})
@RequestMapping("/api/dashboard")
@RestController
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    /**
     * 获取首页面板统计数据
     */
    @ApiOperation(value = "获取首页面板统计数据", notes = "")
    @GetMapping("/query/panel-group")
    public Result<List<PanelItemDTO>> doQueryPanelGroup() {
        List<PanelItemDTO> list = dashboardService.getPanelGroup();
        return Result.ok(list);
    }

}