package com.jyadmin.module.workOrder.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.module.workOrder.domain.WorkOrder;
import com.jyadmin.module.workOrder.model.dto.WorkOrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 工单表 Mapper <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 09:27:09 <br>
 * @Entity com.jyadmin.module.workOrder.domain.WorkOrder
 * @description: WorkOrderMapper <br>
 */
@Mapper
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

    Page<WorkOrderDTO> selectWorkOrderPage(Page<Object> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<WorkOrder> wrapper);

}




