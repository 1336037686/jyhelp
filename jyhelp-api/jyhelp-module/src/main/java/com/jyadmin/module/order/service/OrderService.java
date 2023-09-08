package com.jyadmin.module.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.module.order.domain.Order;
import com.jyadmin.module.order.model.dto.OrderDTO;

import java.util.List;

/**
 * 用户订单表 Service <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 19:59:59 <br>
 * @description: OrderService <br>
 */
public interface OrderService extends IService<Order> {

    Page<OrderDTO> getPage(Page<Object> page, LambdaQueryWrapper<Order> wrapper);

    List<OrderDTO> getList(LambdaQueryWrapper<Order> wrapper);

}
