package com.jyadmin.module.handleService.controller;
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
import com.jyadmin.module.customerService.domain.CustomerService;
import com.jyadmin.module.customerService.service.CustomerServiceService;
import com.jyadmin.module.handleService.domain.HandleService;
import com.jyadmin.module.handleService.model.dto.HandleServiceDTO;
import com.jyadmin.module.handleService.model.vo.HandleServiceCreateReqVO;
import com.jyadmin.module.handleService.model.vo.HandleServiceQueryReqVO;
import com.jyadmin.module.handleService.model.vo.HandleServiceUpdateReqVO;
import com.jyadmin.module.handleService.service.HandleServiceService;
import com.jyadmin.module.serviceCategory.domain.ServiceCategory;
import com.jyadmin.module.serviceCategory.service.ServiceCategoryService;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.service.UserService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import com.jyadmin.util.SecurityUtil;
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
    @Resource
    private UserService userService;
    @Resource
    private CustomerServiceService customerServiceService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增服务执行记录", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('handleService:create')")
    public Result<Object> doCreate(@RequestBody @Valid HandleServiceCreateReqVO vo) {
        HandleService handleService = BeanUtil.copyProperties(vo, HandleService.class);
        handleService.setHandleTime(LocalDateTime.now());
        handleService.setHandleUser(SecurityUtil.getCurrentUserId());
        handleService.setUserScore(0);
        return ResultUtil.toResult(handleServiceService.save(handleService));
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
        HandleService handleService = handleServiceService.getById(id);
        User handlerUser = userService.getById(handleService.getHandleUser());
        CustomerService customerService = customerServiceService.getById(handleService.getCustomerServiceId());
        User user = userService.getById(customerService.getUserId());
        HandleServiceDTO handleServiceDTO = BeanUtil.copyProperties(handleService, HandleServiceDTO.class);
        handleServiceDTO.setHandleUserName(handlerUser.getUsername());
        handleServiceDTO.setHandleUserNickname(handlerUser.getNickname());
        handleServiceDTO.setServiceCode(customerService.getServiceCode());
        handleServiceDTO.setUsername(user.getUsername());
        handleServiceDTO.setNickname(user.getNickname());
        return Result.ok(handleServiceDTO);
    }

    @ApiOperation(value = "列表查询服务执行记录", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('handleService:list')")
    public Result<List<HandleServiceDTO>> doQueryList(HandleServiceQueryReqVO vo) {
        return Result.ok(this.handleServiceService.getList(new LambdaQueryWrapper<HandleService>()
                .like(StringUtils.isNotBlank(vo.getHandleCode()), HandleService::getHandleCode, vo.getHandleCode())
                .eq(Objects.nonNull(vo.getHandleTime()), HandleService::getHandleTime, vo.getHandleTime())
                .eq(Objects.nonNull(vo.getHandleStatus()), HandleService::getHandleStatus, vo.getHandleStatus())
                .eq(Objects.nonNull(vo.getCustomerServiceId()), HandleService::getCustomerServiceId, vo.getCustomerServiceId())
                .eq(HandleService::getDeleted, GlobalConstants.SysDeleted.EXIST.getValue())
                .apply(StringUtils.isNotBlank(vo.getServiceCode()), "service_code LIKE CONCAT('%',{0},'%')", vo.getServiceCode())
                .apply(StringUtils.isNotBlank(vo.getHandleUserName()), "handle_user_name LIKE CONCAT('%',{0},'%')", vo.getHandleUserName())
                .apply(StringUtils.isNotBlank(vo.getHandleUserNickname()), "handle_user_nickname LIKE CONCAT('%',{0},'%')", vo.getHandleUserNickname())
                .apply(StringUtils.isNotBlank(vo.getUsername()), "username LIKE CONCAT('%',{0},'%')", vo.getUsername())
                .apply(StringUtils.isNotBlank(vo.getNickname()), "nickname LIKE CONCAT('%',{0},'%')", vo.getNickname())
                .orderByDesc(HandleService::getCreateTime)
        ));
    }

    @ApiOperation(value = "分页查询服务执行记录", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('handleService:query')")
    public PageResult<HandleServiceDTO> doQueryPage(HandleServiceQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.handleServiceService.getPage(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<HandleService>()
                            .like(StringUtils.isNotBlank(vo.getHandleCode()), HandleService::getHandleCode, vo.getHandleCode())
                            .eq(Objects.nonNull(vo.getHandleTime()), HandleService::getHandleTime, vo.getHandleTime())
                            .eq(Objects.nonNull(vo.getHandleStatus()), HandleService::getHandleStatus, vo.getHandleStatus())
                            .eq(HandleService::getDeleted, GlobalConstants.SysDeleted.EXIST.getValue())
                            .apply(StringUtils.isNotBlank(vo.getServiceCode()), "service_code LIKE CONCAT('%',{0},'%')", vo.getServiceCode())
                            .apply(StringUtils.isNotBlank(vo.getHandleUserName()), "handle_user_name LIKE CONCAT('%',{0},'%')", vo.getHandleUserName())
                            .apply(StringUtils.isNotBlank(vo.getHandleUserNickname()), "handle_user_nickname LIKE CONCAT('%',{0},'%')", vo.getHandleUserNickname())
                            .apply(StringUtils.isNotBlank(vo.getUsername()), "username LIKE CONCAT('%',{0},'%')", vo.getUsername())
                            .apply(StringUtils.isNotBlank(vo.getNickname()), "nickname LIKE CONCAT('%',{0},'%')", vo.getNickname())
                            .orderByDesc(HandleService::getCreateTime)
                )
        );
    }
}
