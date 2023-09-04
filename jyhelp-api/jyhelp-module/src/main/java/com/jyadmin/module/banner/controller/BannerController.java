package com.jyadmin.module.banner.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.banner.domain.Banner;
import com.jyadmin.module.banner.model.vo.BannerCreateReqVO;
import com.jyadmin.module.banner.model.vo.BannerQueryReqVO;
import com.jyadmin.module.banner.model.vo.BannerUpdateReqVO;
import com.jyadmin.module.banner.service.BannerService;
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
 * Banner设置 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:56:21 <br>
 * @description: BannerController <br>
 */
@Slf4j
@Api(value = "Banner设置", tags = {"商城：Banner设置"})
@RequestMapping("/api/banner")
@RestController
public class BannerController {

    @Resource
    private BannerService bannerService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增Banner设置", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('banner:create')")
    public Result<Object> doCreate(@RequestBody @Valid BannerCreateReqVO vo) {
        return ResultUtil.toResult(bannerService.save(BeanUtil.copyProperties(vo, Banner.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新Banner设置", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('banner:update')")
    public Result<Object> doUpdate(@RequestBody @Valid BannerUpdateReqVO vo) {
        Banner banner = bannerService.getById(vo.getId());
        BeanUtil.copyProperties(vo, banner);
        return ResultUtil.toResult(bannerService.updateById(banner));
    }

    @ApiOperation(value = "删除Banner设置", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('banner:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(bannerService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前Banner设置", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('banner:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(bannerService.getById(id));
    }

    @ApiOperation(value = "分页查询Banner设置", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('banner:query')")
    public PageResult<Banner> doQueryPage(BannerQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.bannerService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Banner>()
                            .eq(Objects.nonNull(vo.getId()), Banner::getId, vo.getId())
                            .eq(Objects.nonNull(vo.getBannerImg()), Banner::getBannerImg, vo.getBannerImg())
                            .eq(Objects.nonNull(vo.getSort()), Banner::getSort, vo.getSort())
                            .eq(Objects.nonNull(vo.getVersion()), Banner::getVersion, vo.getVersion())
                )
        );
    }
}
