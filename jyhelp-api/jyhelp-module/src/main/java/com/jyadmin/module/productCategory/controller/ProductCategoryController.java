package com.jyadmin.module.productCategory.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.module.productCategory.domain.ProductCategory;
import com.jyadmin.module.productCategory.model.vo.ProductCategoryCreateReqVO;
import com.jyadmin.module.productCategory.model.vo.ProductCategoryQueryReqVO;
import com.jyadmin.module.productCategory.model.vo.ProductCategoryUpdateReqVO;
import com.jyadmin.module.productCategory.service.ProductCategoryService;
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
 * 商品类别表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-03 11:17:29 <br>
 * @description: ProductCategoryController <br>
 */
@Slf4j
@Api(value = "商品类别表", tags = {"系统：商品类别表"})
@RequestMapping("/api/product-category")
@RestController
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;


    @RateLimit @Idempotent
    @ApiOperation(value = "新增商品类别表", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('productCategory:create')")
    public Result<Object> doCreate(@RequestBody @Valid ProductCategoryCreateReqVO vo) {
        return ResultUtil.toResult(productCategoryService.save(BeanUtil.copyProperties(vo, ProductCategory.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新商品类别表", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('productCategory:update')")
    public Result<Object> doUpdate(@RequestBody @Valid ProductCategoryUpdateReqVO vo) {
        ProductCategory productCategory = productCategoryService.getById(vo.getId());
        BeanUtil.copyProperties(vo, productCategory);
        return ResultUtil.toResult(productCategoryService.updateById(productCategory));
    }

    @ApiOperation(value = "删除商品类别表", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('productCategory:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(productCategoryService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前商品类别表", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('productCategory:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(productCategoryService.getById(id));
    }

    @ApiOperation(value = "分页查询商品类别表", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('productCategory:query')")
    public PageResult<ProductCategory> doQueryPage(ProductCategoryQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.productCategoryService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<ProductCategory>()
                            .like(StringUtils.isNotBlank(vo.getName()), ProductCategory::getName, vo.getName())
                            .like(StringUtils.isNotBlank(vo.getCode()), ProductCategory::getCode, vo.getCode())
                            .eq(Objects.nonNull(vo.getStatus()), ProductCategory::getStatus, vo.getStatus())
                )
        );
    }
}
