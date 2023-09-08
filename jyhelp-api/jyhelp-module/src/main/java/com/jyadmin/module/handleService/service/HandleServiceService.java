package com.jyadmin.module.handleService.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.module.handleService.domain.HandleService;
import com.jyadmin.module.handleService.model.dto.HandleServiceDTO;

import java.util.List;

/**
 * 服务执行记录表 Service <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @description: HandleServiceService <br>
 */
public interface HandleServiceService extends IService<HandleService> {

    Page<HandleServiceDTO> getPage(Page<HandleService> page, LambdaQueryWrapper<HandleService> wrapper);

    List<HandleServiceDTO> getList(LambdaQueryWrapper<HandleService> wrapper);
}
