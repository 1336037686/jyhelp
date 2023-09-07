package com.jyadmin.module.handleService.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.customerService.domain.CustomerService;
import com.jyadmin.module.handleService.domain.HandleService;
import com.jyadmin.module.handleService.model.vo.HandleServiceCreateReqVO;
import com.jyadmin.module.handleService.model.vo.HandleServiceQueryReqVO;
import com.jyadmin.module.handleService.model.vo.HandleServiceUpdateReqVO;
import com.jyadmin.module.handleService.service.HandleServiceService;
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
import java.util.Objects;
import java.util.Set;

/**
 * 服务执行记录 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @description: HandleServiceController <br>
 */
@Slf4j
@Api(value = "服务执行记录", tags = {"商城：服务执行记录"})
@RequestMapping("/api/handle-service")
@RestController
public class HandleServiceController {

    @Resource
    private HandleServiceService handleServiceService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增服务执行记录", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('handleService:create')")
    public Result<Object> doCreate(@RequestBody @Valid HandleServiceCreateReqVO vo) {
        return ResultUtil.toResult(handleServiceService.save(BeanUtil.copyProperties(vo, HandleService.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新服务执行记录", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('handleService:update')")
    public Result<Object> doUpdate(@RequestBody @Valid HandleServiceUpdateReqVO vo) {
        HandleService handleService = handleServiceService.getById(vo.getId());
        BeanUtil.copyProperties(vo, handleService);
        return ResultUtil.toResult(handleServiceService.updateById(handleService));
    }

    @ApiOperation(value = "删除服务执行记录", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('handleService:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(handleServiceService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前服务执行记录", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('handleService:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(handleServiceService.getById(id));
    }

    @ApiOperation(value = "分页查询服务执行记录", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('handleService:query')")
    public PageResult<HandleService> doQueryPage(HandleServiceQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.handleServiceService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<HandleService>()
                            .like(StringUtils.isNotBlank(vo.getHandleCode()), HandleService::getHandleCode, vo.getHandleCode())
                            .eq(Objects.nonNull(vo.getCustomerServiceId()), HandleService::getCustomerServiceId, vo.getCustomerServiceId())
                            .eq(Objects.nonNull(vo.getHandleTime()), HandleService::getHandleTime, vo.getHandleTime())
                            .eq(Objects.nonNull(vo.getHandleUser()), HandleService::getHandleUser, vo.getHandleUser())
                            .eq(Objects.nonNull(vo.getHandleRemark()), HandleService::getHandleRemark, vo.getHandleRemark())
                            .eq(Objects.nonNull(vo.getAttachment()), HandleService::getAttachment, vo.getAttachment())
                            .eq(Objects.nonNull(vo.getHandleStatus()), HandleService::getHandleStatus, vo.getHandleStatus())
                            .eq(Objects.nonNull(vo.getUserFeedback()), HandleService::getUserFeedback, vo.getUserFeedback())
                            .orderByDesc(HandleService::getCreateTime)
                )
        );
    }
}
