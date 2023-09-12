package com.jyadmin.module.workOrder.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.module.order.model.dto.OrderDTO;
import com.jyadmin.module.workOrder.domain.WorkOrder;
import com.jyadmin.module.workOrder.model.dto.WorkOrderDTO;

/**
 * 工单表 Service <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 09:27:09 <br>
 * @description: WorkOrderService <br>
 */
public interface WorkOrderService extends IService<WorkOrder> {

    Page<WorkOrderDTO> getPage(Page<Object> page, LambdaQueryWrapper<WorkOrder> wrapper);

}
