package com.jyadmin.module.order.controller;
import java.time.LocalDateTime;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.notification.domain.Notification;
import com.jyadmin.module.order.domain.Order;
import com.jyadmin.module.order.model.dto.OrderDTO;
import com.jyadmin.module.order.model.vo.OrderCreateReqVO;
import com.jyadmin.module.order.model.vo.OrderQueryReqVO;
import com.jyadmin.module.order.model.vo.OrderUpdateReqVO;
import com.jyadmin.module.order.service.OrderService;
import com.jyadmin.module.product.domain.Product;
import com.jyadmin.module.product.service.ProductService;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.service.UserService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用户订单 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 19:59:59 <br>
 * @description: OrderController <br>
 */
@Slf4j
@Api(value = "用户订单", tags = {"商城：用户订单"})
@RequestMapping("/api/order")
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Resource
    private ProductService productService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增用户订单", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('order:create')")
    public Result<Object> doCreate(@RequestBody @Valid OrderCreateReqVO vo) {
        return ResultUtil.toResult(orderService.save(BeanUtil.copyProperties(vo, Order.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新用户订单", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('order:update')")
    public Result<Object> doUpdate(@RequestBody @Valid OrderUpdateReqVO vo) {
        Order order = orderService.getById(vo.getId());
        BeanUtil.copyProperties(vo, order);
        return ResultUtil.toResult(orderService.updateById(order));
    }

    @ApiOperation(value = "删除用户订单", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('order:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(orderService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前用户订单", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('order:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        Order order = orderService.getById(id);
        OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
        Product product = productService.getById(order.getProductId());
        User user = userService.getById(order.getUserId());
        orderDTO.setProductName(product.getName());
        orderDTO.setUsername(user.getUsername());
        orderDTO.setNickname(user.getNickname());
        return Result.ok(orderDTO);
    }

    @ApiOperation(value = "根据用户服务ID获取所属用户订单", notes = "")
    @GetMapping("/query-customer-service/{customerServiceId}")
    @PreAuthorize("@jy.check('order:query-customer-service')")
    public Result<List<OrderDTO>> doQueryByCustomerServiceId(@PathVariable("customerServiceId") String customerServiceId) {
        if (StringUtils.isBlank(customerServiceId)) return Result.fail();
        return Result.ok(this.orderService.getList(new LambdaQueryWrapper<Order>()
                        .eq(Order::getDeleted, GlobalConstants.SysDeleted.EXIST.getValue())
                        .exists("SELECT 1 FROM tr_customer_service_order tcso " +
                                "WHERE tcso.deleted = '0' and tcso.customer_service_id = {0} " +
                                "and tcso.order_id = id", Long.parseLong(customerServiceId))
                        .orderByDesc(Order::getCreateTime)
        ));
    }

    @ApiOperation(value = "分页查询用户订单", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('order:query')")
    public PageResult<OrderDTO> doQueryPage(OrderQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.orderService.getPage(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Order>()
                            .eq(Objects.nonNull(vo.getId()), Order::getId, vo.getId())
                            .like(StringUtils.isNotBlank(vo.getOrderCode()), Order::getOrderCode, vo.getOrderCode())
                            .eq(Objects.nonNull(vo.getProductId()), Order::getProductId, vo.getProductId())
                            .eq(Objects.nonNull(vo.getUserId()), Order::getUserId, vo.getUserId())
                            .eq(Objects.nonNull(vo.getOrderStatus()), Order::getOrderStatus, vo.getOrderStatus())
                            .eq(Objects.nonNull(vo.getOrderTime()), Order::getOrderTime, vo.getOrderTime())
                            .eq(Objects.nonNull(vo.getPaymentTime()), Order::getPaymentTime, vo.getPaymentTime())
                            .eq(Objects.nonNull(vo.getPeymentMethod()), Order::getPeymentMethod, vo.getPeymentMethod())
                            .eq(Order::getDeleted, GlobalConstants.SysDeleted.EXIST.getValue())
                            .orderByDesc(Order::getCreateTime)
                )
        );
    }
}
