package com.jyadmin.module.dashboard.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.jyadmin.module.dashboard.mapper.DashboardMapper;
import com.jyadmin.module.dashboard.model.dto.PanelItemDTO;
import com.jyadmin.module.dashboard.service.DashboardService;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-09 12:22 <br>
 * @description: DashboardServiceImpl <br>
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private DashboardMapper dashboardMapper;


    @Override
    public List<PanelItemDTO> getPanelGroup() {
        List<PanelItemDTO> res = Lists.newArrayList();
        String thisDayStart = DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd") + " 00:00:00";
        String thisDayEnd = DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd") + " 23:59:59";
        String thisMonthStart = DateUtil.format(DateUtil.beginOfMonth(new Date()).toLocalDateTime(), "yyyy-MM-dd") + " 00:00:00";
        String thisMonthEnd = DateUtil.format(DateUtil.endOfMonth(new Date()).toLocalDateTime(), "yyyy-MM-dd") + " 23:59:59";

        // 注册会员
        Integer totalMembers = dashboardMapper.selectMemberCount(null, null);
        Integer thisDayMembers = dashboardMapper.selectMemberCount(thisDayStart, thisDayEnd);
        PanelItemDTO people = new PanelItemDTO().setName("注册会员").setCode("people")
                .setValue(String.valueOf(totalMembers)).setSubValue(String.valueOf(thisDayMembers))
                .setSubValueState(thisDayMembers).setSubValueRemark("今日新增会员数");
        res.add(people);

        // 今日订单
        Integer totalOrderToday = dashboardMapper.selectOrderCount(thisDayStart, thisDayEnd);
        PanelItemDTO orderToday = new PanelItemDTO().setName("今日订单").setCode("orderToday")
                .setValue(String.valueOf(totalOrderToday)).setSubValue(null)
                .setSubValueState(null).setSubValueRemark(null);
        res.add(orderToday);

        // 本月订单
        Integer totalOrderMonth = dashboardMapper.selectOrderCount(thisMonthStart, thisMonthEnd);
        PanelItemDTO orderMonth = new PanelItemDTO().setName("本月订单").setCode("orderMonth")
                .setValue(String.valueOf(totalOrderMonth)).setSubValue(null)
                .setSubValueState(null).setSubValueRemark(null);
        res.add(orderMonth);

        // 总计营收
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Double totalMoneyAll = dashboardMapper.selectTotalMoney(null, null);
        Double totalMoneyToday = dashboardMapper.selectTotalMoney(thisDayStart, thisDayEnd);
        PanelItemDTO totalMoney = new PanelItemDTO().setName("总计营收").setCode("totalMoney")
                .setValue(decimalFormat.format(totalMoneyAll)).setSubValue(decimalFormat.format(totalMoneyToday))
                .setSubValueState(totalMoneyToday > 0 ? 1 : (totalMoneyToday == 0 ? 0 : -1)).setSubValueRemark("今日营收");
        res.add(totalMoney);

        // 上架商品
        Integer totalProductsAll = dashboardMapper.selectMemberCount(null, null);
        Integer totalProductsToday = dashboardMapper.selectMemberCount(thisDayStart, thisDayEnd);
        PanelItemDTO products = new PanelItemDTO().setName("上架商品").setCode("products")
                .setValue(String.valueOf(totalProductsAll)).setSubValue(String.valueOf(totalProductsToday))
                .setSubValueState(totalProductsToday).setSubValueRemark("今日新上架商品");
        res.add(products);

        // 累计服务
        Integer totalServiceAll = dashboardMapper.selectService(null, null);
        Integer totalServiceToday = dashboardMapper.selectService(thisDayStart, thisDayEnd);
        PanelItemDTO services = new PanelItemDTO().setName("累计服务").setCode("services")
                .setValue(String.valueOf(totalServiceAll)).setSubValue(String.valueOf(totalServiceToday))
                .setSubValueState(totalServiceToday).setSubValueRemark("今日服务");
        res.add(services);

        return res;
    }

}
