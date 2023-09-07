package com.jyadmin.module.order.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 用户订单表 <br>
 * @TableName tb_order <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 19:59:59 <br>
 * @description: Order <br>
 */
@TableName(value ="tb_order")
@Accessors(chain = true)
@Data
public class Order extends BaseEntity implements Serializable {

    /**
     * 订单编号
     */
    @TableField(value = "order_code")
    private String orderCode;

    /**
     * 商品ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 购买数量
     */
    @TableField(value = "quantity")
    private Integer quantity;

    /**
     * 购买用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 所需金额（分）
     */
    @TableField(value = "total_amount")
    private Long totalAmount;

    /**
     * 实际金额（分）
     */
    @TableField(value = "final_total_amount")
    private Long finalTotalAmount;

    /**
     * 订单状态（待付款、已付款、已取消）
     */
    @TableField(value = "order_status")
    private String orderStatus;

    /**
     * 下单时间
     */
    @TableField(value = "order_time")
    private LocalDateTime orderTime;

    /**
     * 付款时间
     */
    @TableField(value = "payment_time")
    private LocalDateTime paymentTime;

    /**
     * 付款方式
     */
    @TableField(value = "peyment_method")
    private String peymentMethod;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 版本
     */
    @TableField(value = "version")
    @Version
    private Long version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}