package com.jyadmin.system.user.service.impl;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.system.role.domain.Role;
import com.jyadmin.system.role.domain.UserRole;
import com.jyadmin.system.role.service.RoleService;
import com.jyadmin.system.role.service.UserRoleService;
import com.jyadmin.system.user.model.dto.UserDTO;
import com.jyadmin.system.user.service.UserService;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-04-12 23:19:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public User getByUserName(String userName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
    }

    @Override
    public Page<UserDTO> getPage(Page<User> page, LambdaQueryWrapper<User> wrapper) {
        return this.baseMapper.selectUserPage(page, wrapper);
    }

    @Override
    public UserDTO getUserDetailById(Long id) {
        return this.baseMapper.selectUserDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean saveWaiter(User waiter) {
        // 保存用户信息
        this.save(waiter);
        // 获取服务人员角色
        Role waiterRole = roleService.getOne(new LambdaQueryWrapper<Role>()
                .eq(Role::getStatus, GlobalConstants.SysStatus.ON.getValue())
                .eq(Role::getCode, GlobalConstants.DEFAULT_WAITER_ROLE)
        );
        // 绑定服务角色
        UserRole userRole = new UserRole().setUserId(waiter.getId()).setRoleId(waiterRole.getId());
        userRoleService.save(userRole);
        return true;
    }
}




