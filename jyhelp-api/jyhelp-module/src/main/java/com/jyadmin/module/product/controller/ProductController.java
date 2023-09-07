package com.jyadmin.module.product.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.order.domain.Order;
import com.jyadmin.module.product.domain.Product;
import com.jyadmin.module.product.model.vo.ProductCreateReqVO;
import com.jyadmin.module.product.model.vo.ProductQueryReqVO;
import com.jyadmin.module.product.model.vo.ProductUpdateReqVO;
import com.jyadmin.module.product.service.ProductService;
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
 * 商品表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 12:34:21 <br>
 * @description: ProductController <br>
 */
@Slf4j
@Api(value = "商品管理", tags = {"商城：商品管理"})
@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Resource
    private ProductService productService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增商品表", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('product:create')")
    public Result<Object> doCreate(@RequestBody @Valid ProductCreateReqVO vo) {
        return ResultUtil.toResult(productService.save(BeanUtil.copyProperties(vo, Product.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新商品表", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('product:update')")
    public Result<Object> doUpdate(@RequestBody @Valid ProductUpdateReqVO vo) {
        Product product = productService.getById(vo.getId());
        BeanUtil.copyProperties(vo, product);
        return ResultUtil.toResult(productService.updateById(product));
    }

    @ApiOperation(value = "删除商品表", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('product:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(productService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前商品表", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('product:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(productService.getById(id));
    }

    @ApiOperation(value = "分页查询商品表", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('product:query')")
    public PageResult<Product> doQueryPage(ProductQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.productService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Product>()
                            .like(StringUtils.isNotBlank(vo.getName()), Product::getName, vo.getName())
                            .like(StringUtils.isNotBlank(vo.getCode()), Product::getCode, vo.getCode())
                            .eq(Objects.nonNull(vo.getProductCategoryId()), Product::getProductCategoryId, vo.getProductCategoryId())
                            .eq(Objects.nonNull(vo.getServiceCategoryId()), Product::getServiceCategoryId, vo.getServiceCategoryId())
                            .eq(Objects.nonNull(vo.getHot()), Product::getHot, vo.getHot())
                            .eq(Objects.nonNull(vo.getStatus()), Product::getStatus, vo.getStatus())
                            .orderByDesc(Product::getCreateTime)
                )
        );
    }
}
