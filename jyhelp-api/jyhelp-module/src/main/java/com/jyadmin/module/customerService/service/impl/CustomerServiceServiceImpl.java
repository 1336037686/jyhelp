package com.jyadmin.module.customerService.service.impl;
import java.time.LocalDateTime;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.module.customerService.domain.CustomerService;
import com.jyadmin.module.customerService.mapper.CustomerServiceMapper;
import com.jyadmin.module.customerService.model.dto.CustomerServiceDTO;
import com.jyadmin.module.customerService.service.CustomerServiceService;
import com.jyadmin.module.serviceCategory.domain.ServiceCategory;
import com.jyadmin.module.serviceCategory.service.ServiceCategoryService;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 会员服务表 ServiceImpl <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:12:27 <br>
 * @description: CustomerServiceServiceImpl <br>
 */
@Service
public class CustomerServiceServiceImpl extends ServiceImpl<CustomerServiceMapper, CustomerService> implements CustomerServiceService {

    @Resource
    private UserService userService;
    @Resource
    private ServiceCategoryService serviceCategoryService;

    @Override
    public Page<CustomerServiceDTO> getPage(Page<CustomerService> page, LambdaQueryWrapper<CustomerService> wrapper) {
        return this.baseMapper.selectCustomerServicePage(page, wrapper);
    }

    @Override
    public CustomerServiceDTO getById(Serializable id) {
        CustomerService customerService = super.getById(id);
        CustomerServiceDTO customerServiceDTO = new CustomerServiceDTO();
        BeanUtil.copyProperties(customerService, customerServiceDTO);

        User user = userService.getById(customerService.getUserId());
        ServiceCategory serviceCategory = serviceCategoryService.getById(customerService.getServiceCategoryId());
        customerServiceDTO.setUsername(user.getUsername());
        customerServiceDTO.setNickname(user.getNickname());
        customerServiceDTO.setServiceCategoryName(serviceCategory.getName());
        return customerServiceDTO;
    }
}




