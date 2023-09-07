package com.jyadmin.module.customerService.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.module.customerService.domain.CustomerService;
import com.jyadmin.module.customerService.model.dto.CustomerServiceDTO;
import com.jyadmin.system.user.domain.User;

import java.util.List;

/**
 * 会员服务表 Service <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:12:27 <br>
 * @description: CustomerServiceService <br>
 */
public interface CustomerServiceService extends IService<CustomerService> {

    Page<CustomerServiceDTO> getPage(Page<CustomerService> result, LambdaQueryWrapper<CustomerService> wrapper);

    List<CustomerServiceDTO> getList(LambdaQueryWrapper<CustomerService> wrapper);

}
