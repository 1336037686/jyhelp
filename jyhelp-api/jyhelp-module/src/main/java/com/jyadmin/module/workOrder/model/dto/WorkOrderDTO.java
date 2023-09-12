package com.jyadmin.module.workOrder.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.module.workOrder.domain.WorkOrder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 16:35 <br>
 * @description: WorkOrderDTO <br>
 */
@Data
@Accessors(chain = true)
public class WorkOrderDTO extends WorkOrder {

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String username;

}
