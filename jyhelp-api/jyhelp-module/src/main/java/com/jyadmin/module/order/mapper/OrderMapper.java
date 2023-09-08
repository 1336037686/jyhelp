package com.jyadmin.module.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.module.order.domain.Order;
import com.jyadmin.module.order.model.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户订单表 Mapper <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 19:59:59 <br>
 * @Entity com.jyadmin.module.order.domain.Order
 * @description: OrderMapper <br>
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Page<OrderDTO> selectOrderPage(Page<Object> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Order> wrapper);

    List<OrderDTO> selectOrderList(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> wrapper);

}




