package com.jyadmin.module.customerService.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.customerService.domain.CustomerService;
import com.jyadmin.module.customerService.model.dto.CustomerServiceDTO;
import com.jyadmin.module.customerService.model.vo.CustomerServiceCreateReqVO;
import com.jyadmin.module.customerService.model.vo.CustomerServiceQueryReqVO;
import com.jyadmin.module.customerService.model.vo.CustomerServiceUpdateReqVO;
import com.jyadmin.module.customerService.service.CustomerServiceService;
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
 * 会员服务 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:12:27 <br>
 * @description: CustomerServiceController <br>
 */
@Slf4j
@Api(value = "会员服务", tags = {"商城：会员服务"})
@RequestMapping("/api/customer-service")
@RestController
public class CustomerServiceController {

    @Resource
    private CustomerServiceService customerServiceService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增会员服务", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('customerService:create')")
    public Result<Object> doCreate(@RequestBody @Valid CustomerServiceCreateReqVO vo) {
        return ResultUtil.toResult(customerServiceService.save(BeanUtil.copyProperties(vo, CustomerService.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新会员服务", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('customerService:update')")
    public Result<Object> doUpdate(@RequestBody @Valid CustomerServiceUpdateReqVO vo) {
        CustomerService customerService = customerServiceService.getById(vo.getId());
        BeanUtil.copyProperties(vo, customerService);
        return ResultUtil.toResult(customerServiceService.updateById(customerService));
    }

    @ApiOperation(value = "删除会员服务", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('customerService:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(customerServiceService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前会员服务", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('customerService:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(customerServiceService.getById(id));
    }

    @ApiOperation(value = "分页查询会员服务", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('customerService:query')")
    public PageResult<CustomerServiceDTO> doQueryPage(CustomerServiceQueryReqVO vo) {
        return PageUtil.toPageResult(this.customerServiceService.getPage(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<CustomerService>()
                        .like(StringUtils.isNotBlank(vo.getServiceCode()), CustomerService::getServiceCode, vo.getServiceCode())
                        .eq(Objects.nonNull(vo.getUserId()), CustomerService::getUserId, vo.getUserId())
                        .eq(Objects.nonNull(vo.getServiceCategoryId()), CustomerService::getServiceCategoryId, vo.getServiceCategoryId())
                        .eq(Objects.nonNull(vo.getServiceStatus()), CustomerService::getServiceStatus, vo.getServiceStatus())
                        .apply(StringUtils.isNotBlank(vo.getUsername()), "username LIKE CONCAT('%',{0},'%')", vo.getUsername())
                        .orderByDesc(CustomerService::getCreateTime)
        ));
    }
}
