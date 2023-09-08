package com.jyadmin.module.order.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.module.order.domain.Order;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-08 11:21 <br>
 * @description: OrderDTO <br>
 */
@Data
@Accessors(chain = true)
public class OrderDTO extends Order {

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String productName;

    /**
     * 购买用户-账号
     */
    @TableField(exist = false)
    private String username;

    /**
     * 购买用户-昵称
     */
    @TableField(exist = false)
    private String nickname;

}
