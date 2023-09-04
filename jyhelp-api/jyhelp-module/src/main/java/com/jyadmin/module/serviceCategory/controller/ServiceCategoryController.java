package com.jyadmin.module.serviceCategory.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.serviceCategory.domain.ServiceCategory;
import com.jyadmin.module.serviceCategory.model.vo.ServiceCategoryCreateReqVO;
import com.jyadmin.module.serviceCategory.model.vo.ServiceCategoryQueryReqVO;
import com.jyadmin.module.serviceCategory.model.vo.ServiceCategoryUpdateReqVO;
import com.jyadmin.module.serviceCategory.service.ServiceCategoryService;
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
 * 服务类别表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 08:57:22 <br>
 * @description: ServiceCategoryController <br>
 */
@Slf4j
@Api(value = "服务类别", tags = {"商城：服务类别"})
@RequestMapping("/api/service-category")
@RestController
public class ServiceCategoryController {

    @Resource
    private ServiceCategoryService serviceCategoryService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增服务类别", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('serviceCategory:create')")
    public Result<Object> doCreate(@RequestBody @Valid ServiceCategoryCreateReqVO vo) {
        return ResultUtil.toResult(serviceCategoryService.save(BeanUtil.copyProperties(vo, ServiceCategory.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新服务类别", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('serviceCategory:update')")
    public Result<Object> doUpdate(@RequestBody @Valid ServiceCategoryUpdateReqVO vo) {
        ServiceCategory serviceCategory = serviceCategoryService.getById(vo.getId());
        BeanUtil.copyProperties(vo, serviceCategory);
        return ResultUtil.toResult(serviceCategoryService.updateById(serviceCategory));
    }

    @ApiOperation(value = "删除服务类别", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('serviceCategory:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(serviceCategoryService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前服务类别", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('serviceCategory:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(serviceCategoryService.getById(id));
    }

    @ApiOperation(value = "列表查询服务类别", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('serviceCategory:list')")
    public Result<List<ServiceCategory>> doQueryList(ServiceCategoryQueryReqVO vo) {
        return Result.ok(this.serviceCategoryService.list(
                        new LambdaQueryWrapper<ServiceCategory>()
                                .eq(Objects.nonNull(vo.getId()), ServiceCategory::getId, vo.getId())
                                .like(StringUtils.isNotBlank(vo.getName()), ServiceCategory::getName, vo.getName())
                                .like(Objects.nonNull(vo.getCode()), ServiceCategory::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getType()), ServiceCategory::getType, vo.getType())
                                .eq(Objects.nonNull(vo.getStatus()), ServiceCategory::getStatus, vo.getStatus())
        ));
    }

    @ApiOperation(value = "分页查询服务类别", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('serviceCategory:query')")
    public PageResult<ServiceCategory> doQueryPage(ServiceCategoryQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.serviceCategoryService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<ServiceCategory>()
                            .eq(Objects.nonNull(vo.getId()), ServiceCategory::getId, vo.getId())
                            .like(StringUtils.isNotBlank(vo.getName()), ServiceCategory::getName, vo.getName())
                            .like(Objects.nonNull(vo.getCode()), ServiceCategory::getCode, vo.getCode())
                            .eq(Objects.nonNull(vo.getType()), ServiceCategory::getType, vo.getType())
                            .eq(Objects.nonNull(vo.getStatus()), ServiceCategory::getStatus, vo.getStatus())
                )
        );
    }
}
