package com.jyadmin.module.order.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户订单表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 19:59:59 <br>
 * @description: OrderUpdateReqVO <br>
 */
@ApiModel("用户订单表-修改-数据模型")
@Data
public class OrderUpdateReqVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", name = "orderCode")
    @NotBlank(message = "订单编号不能为空")
    private String orderCode;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", name = "productId")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量", name = "quantity")
    @NotNull(message = "购买数量不能为空")
    private Integer quantity;

    /**
     * 购买用户ID
     */
    @ApiModelProperty(value = "购买用户ID", name = "userId")
    @NotNull(message = "购买用户ID不能为空")
    private Integer userId;

    /**
     * 所需金额（分）
     */
    @ApiModelProperty(value = "所需金额（分）", name = "totalAmount")
    @NotNull(message = "所需金额（分）不能为空")
    private Long totalAmount;

    /**
     * 实际金额（分）
     */
    @ApiModelProperty(value = "实际金额（分）", name = "finalTotalAmount")
    @NotNull(message = "实际金额（分）不能为空")
    private Long finalTotalAmount;

    /**
     * 订单状态（待付款、已付款、已取消）
     */
    @ApiModelProperty(value = "订单状态（待付款、已付款、已取消）", name = "orderStatus")
    @NotBlank(message = "订单状态（待付款、已付款、已取消）不能为空")
    private String orderStatus;

    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间", name = "orderTime")
    @NotNull(message = "下单时间不能为空")
    private LocalDateTime orderTime;

    /**
     * 付款时间
     */
    @ApiModelProperty(value = "付款时间", name = "paymentTime")
    @NotNull(message = "付款时间不能为空")
    private LocalDateTime paymentTime;

    /**
     * 付款方式
     */
    @ApiModelProperty(value = "付款方式", name = "peymentMethod")
    @NotBlank(message = "付款方式不能为空")
    private String peymentMethod;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;
}
