package com.jyadmin.module.notification.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.notification.domain.Notification;
import com.jyadmin.module.notification.model.vo.NotificationCreateReqVO;
import com.jyadmin.module.notification.model.vo.NotificationQueryReqVO;
import com.jyadmin.module.notification.model.vo.NotificationUpdateReqVO;
import com.jyadmin.module.notification.service.NotificationService;
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
 * 商城公告 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-04 22:25:29 <br>
 * @description: NotificationController <br>
 */
@Slf4j
@Api(value = "商城公告", tags = {"商城：商城公告"})
@RequestMapping("/api/notification")
@RestController
public class NotificationController {

    @Resource
    private NotificationService NotificationService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增商城公告", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('Notification:create')")
    public Result<Object> doCreate(@RequestBody @Valid NotificationCreateReqVO vo) {
        return ResultUtil.toResult(NotificationService.save(BeanUtil.copyProperties(vo, Notification.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新商城公告", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('Notification:update')")
    public Result<Object> doUpdate(@RequestBody @Valid NotificationUpdateReqVO vo) {
        Notification Notification = NotificationService.getById(vo.getId());
        BeanUtil.copyProperties(vo, Notification);
        return ResultUtil.toResult(NotificationService.updateById(Notification));
    }

    @ApiOperation(value = "删除商城公告", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('Notification:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(NotificationService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前商城公告", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('Notification:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(NotificationService.getById(id));
    }

    @ApiOperation(value = "分页查询商城公告", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('Notification:query')")
    public PageResult<Notification> doQueryPage(NotificationQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.NotificationService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Notification>()
                            .eq(Objects.nonNull(vo.getId()), Notification::getId, vo.getId())
                            .eq(Objects.nonNull(vo.getTitle()), Notification::getTitle, vo.getTitle())
                            .eq(Objects.nonNull(vo.getPinned()), Notification::getPinned, vo.getPinned())
                            .eq(Objects.nonNull(vo.getStatus()), Notification::getStatus, vo.getStatus())
                )
        );
    }
}
