package com.jyadmin.module.order.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户订单表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 19:59:59 <br>
 * @description: OrderQueryReqVO <br>
 */
@ApiModel("用户订单表-查询-数据模型")
@Data
public class OrderQueryReqVO extends BasePageVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    private Long id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", name = "orderCode")
    private String orderCode;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", name = "productId")
    private Long productId;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量", name = "quantity")
    private Integer quantity;

    /**
     * 购买用户ID
     */
    @ApiModelProperty(value = "购买用户ID", name = "userId")
    private Integer userId;

    /**
     * 所需金额（分）
     */
    @ApiModelProperty(value = "所需金额（分）", name = "totalAmount")
    private Long totalAmount;

    /**
     * 实际金额（分）
     */
    @ApiModelProperty(value = "实际金额（分）", name = "finalTotalAmount")
    private Long finalTotalAmount;

    /**
     * 订单状态（待付款、已付款、已取消）
     */
    @ApiModelProperty(value = "订单状态（待付款、已付款、已取消）", name = "orderStatus")
    private String orderStatus;

    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间", name = "orderTime")
    private LocalDateTime orderTime;

    /**
     * 付款时间
     */
    @ApiModelProperty(value = "付款时间", name = "paymentTime")
    private LocalDateTime paymentTime;

    /**
     * 付款方式
     */
    @ApiModelProperty(value = "付款方式", name = "peymentMethod")
    private String peymentMethod;


}
