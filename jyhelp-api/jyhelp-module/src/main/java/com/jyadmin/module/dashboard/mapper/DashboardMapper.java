package com.jyadmin.module.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyadmin.module.dashboard.model.dto.MemberChartItemDTO;
import com.jyadmin.module.dashboard.model.dto.OrderChartItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-09 12:50 <br>
 * @description: DashboardMapper <br>
 */
@Mapper
public interface DashboardMapper extends BaseMapper {

    /**
     * 统计某个时间段内的注册会员总数，为空的话默认统计全部
     */
    Integer selectMemberCount(String startTime, String endTime);

    /**
     * 统计某个时间段内的订单总数，为空的话默认统计全部
     */
    Integer selectOrderCount(String startTime, String endTime);

    /**
     * 统计某个时间段内的营收总数，为空的话默认统计全部
     */
    Double selectTotalMoney(String startTime, String endTime);

    /**
     * 统计某个时间段内的上架商品总数，为空的话默认统计全部
     */
    Integer selectProduct(String startTime, String endTime);

    /**
     * 统计某个时间段内的提供服务总数，为空的话默认统计全部
     */
    Integer selectService(String startTime, String endTime);

    /**
     * 查询当月每日订单数
     */
    List<OrderChartItemDTO> selectDailyOrderForCurrentMonth();

    /**
     * 查询本周会员注册数
     */
    List<MemberChartItemDTO> selectDailyMemberForCurrentWeek();
}
