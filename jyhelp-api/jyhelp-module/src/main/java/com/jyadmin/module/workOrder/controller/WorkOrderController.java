package com.jyadmin.module.workOrder.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.workOrder.domain.WorkOrder;
import com.jyadmin.module.workOrder.model.dto.WorkOrderDTO;
import com.jyadmin.module.workOrder.model.vo.WorkOrderCreateReqVO;
import com.jyadmin.module.workOrder.model.vo.WorkOrderQueryReqVO;
import com.jyadmin.module.workOrder.model.vo.WorkOrderUpdateReqVO;
import com.jyadmin.module.workOrder.service.WorkOrderService;
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
import java.util.stream.Stream;

/**
 * 工单 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 09:27:09 <br>
 * @description: WorkOrderController <br>
 */
@Slf4j
@Api(value = "工单", tags = {"商城：工单"})
@RequestMapping("/api/work-order")
@RestController
public class WorkOrderController {

    @Resource
    private WorkOrderService workOrderService;
    @Resource
    private UserService userService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增工单", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('workOrder:create')")
    public Result<Object> doCreate(@RequestBody @Valid WorkOrderCreateReqVO vo) {
        return ResultUtil.toResult(workOrderService.save(BeanUtil.copyProperties(vo, WorkOrder.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新工单", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('workOrder:update')")
    public Result<Object> doUpdate(@RequestBody @Valid WorkOrderUpdateReqVO vo) {
        WorkOrder workOrder = workOrderService.getById(vo.getId());
        BeanUtil.copyProperties(vo, workOrder);
        return ResultUtil.toResult(workOrderService.updateById(workOrder));
    }

    @ApiOperation(value = "删除工单", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('workOrder:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(workOrderService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前工单", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('workOrder:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        WorkOrder workOrder = workOrderService.getById(id);
        User user = userService.getById(workOrder.getUserId());
        WorkOrderDTO workOrderDTO = BeanUtil.copyProperties(workOrder, WorkOrderDTO.class);
        workOrderDTO.setUsername(user.getUsername());
        return Result.ok(workOrderDTO);
    }

    @ApiOperation(value = "分页查询工单", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('workOrder:query')")
    public PageResult<WorkOrderDTO> doQueryPage(WorkOrderQueryReqVO vo) {
        return PageUtil.toPageResult(this.workOrderService.getPage(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<WorkOrder>()
                        .eq(Objects.nonNull(vo.getId()), WorkOrder::getId, vo.getId())
                        .eq(Objects.nonNull(vo.getType()), WorkOrder::getType, vo.getType())
                        .like(StringUtils.isNotBlank(vo.getTitle()), WorkOrder::getTitle, vo.getTitle())
                        .like(StringUtils.isNotBlank(vo.getPhone()), WorkOrder::getPhone, vo.getPhone())
                        .like(StringUtils.isNotBlank(vo.getEmail()), WorkOrder::getEmail, vo.getEmail())
                        .eq(Objects.nonNull(vo.getStatus()), WorkOrder::getStatus, vo.getStatus())
                        .eq(WorkOrder::getStatus, GlobalConstants.SysDeleted.EXIST.getValue())
                        .exists(StringUtils.isNotBlank(vo.getUsername()),
                                "SELECT 1 FROM sys_user su " +
                                "WHERE su.deleted = 0 and su.id = user_id " +
                                "and su.username LIKE CONCAT('%', {0}, '%')",
                                vo.getUsername())
                        .orderByDesc(WorkOrder::getCreateTime)
        ));
    }
}
