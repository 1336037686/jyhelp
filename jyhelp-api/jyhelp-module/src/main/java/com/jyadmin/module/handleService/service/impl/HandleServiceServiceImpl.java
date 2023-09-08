package com.jyadmin.module.handleService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.module.handleService.domain.HandleService;
import com.jyadmin.module.handleService.mapper.HandleServiceMapper;
import com.jyadmin.module.handleService.model.dto.HandleServiceDTO;
import com.jyadmin.module.handleService.service.HandleServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务执行记录表 ServiceImpl <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @description: HandleServiceServiceImpl <br>
 */
@Service
public class HandleServiceServiceImpl extends ServiceImpl<HandleServiceMapper, HandleService> implements HandleServiceService {

    @Override
    public Page<HandleServiceDTO> getPage(Page<HandleService> page, LambdaQueryWrapper<HandleService> wrapper) {
        return this.baseMapper.selectHandleServicePage(page, wrapper);
    }

    @Override
    public List<HandleServiceDTO> getList(LambdaQueryWrapper<HandleService> wrapper) {
        return this.baseMapper.selectHandleServiceList(wrapper);
    }
}




