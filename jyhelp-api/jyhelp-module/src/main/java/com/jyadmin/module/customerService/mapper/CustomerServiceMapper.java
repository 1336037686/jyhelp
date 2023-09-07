package com.jyadmin.module.customerService.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.module.customerService.domain.CustomerService;
import com.jyadmin.module.customerService.model.dto.CustomerServiceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 会员服务表 Mapper <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:12:27 <br>
 * @Entity com.jyadmin.module.customerService.domain.CustomerService
 * @description: CustomerServiceMapper <br>
 */
@Mapper
public interface CustomerServiceMapper extends BaseMapper<CustomerService> {

    Page<CustomerServiceDTO> selectCustomerServicePage(Page<CustomerService> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<? extends CustomerService> wrapper);

}




