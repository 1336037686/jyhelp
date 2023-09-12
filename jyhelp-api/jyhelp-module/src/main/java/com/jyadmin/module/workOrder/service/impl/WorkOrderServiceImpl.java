package com.jyadmin.module.workOrder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.module.workOrder.domain.WorkOrder;
import com.jyadmin.module.workOrder.mapper.WorkOrderMapper;
import com.jyadmin.module.workOrder.model.dto.WorkOrderDTO;
import com.jyadmin.module.workOrder.service.WorkOrderService;
import org.springframework.stereotype.Service;

/**
 * 工单表 ServiceImpl <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 09:27:09 <br>
 * @description: WorkOrderServiceImpl <br>
 */
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    @Override
    public Page<WorkOrderDTO> getPage(Page<Object> page, LambdaQueryWrapper<WorkOrder> wrapper) {
        return this.baseMapper.selectWorkOrderPage(page, wrapper);
    }

}




