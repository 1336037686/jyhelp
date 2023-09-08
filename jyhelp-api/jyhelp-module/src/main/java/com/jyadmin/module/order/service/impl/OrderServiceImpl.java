package com.jyadmin.module.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.module.order.domain.Order;
import com.jyadmin.module.order.mapper.OrderMapper;
import com.jyadmin.module.order.model.dto.OrderDTO;
import com.jyadmin.module.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户订单表 ServiceImpl <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 19:59:59 <br>
 * @description: OrderServiceImpl <br>
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Page<OrderDTO> getPage(Page<Object> page, LambdaQueryWrapper<Order> wrapper) {
        return this.baseMapper.selectOrderPage(page, wrapper);
    }

    @Override
    public List<OrderDTO> getList(LambdaQueryWrapper<Order> wrapper) {
        return this.baseMapper.selectOrderList(wrapper);
    }
}




