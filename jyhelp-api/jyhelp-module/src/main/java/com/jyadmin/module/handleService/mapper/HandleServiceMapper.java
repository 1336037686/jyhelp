package com.jyadmin.module.handleService.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.module.handleService.domain.HandleService;
import com.jyadmin.module.handleService.model.dto.HandleServiceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 服务执行记录表 Mapper <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @Entity com.jyadmin.module.handleService.domain.HandleService
 * @description: HandleServiceMapper <br>
 */
@Mapper
public interface HandleServiceMapper extends BaseMapper<HandleService> {

    Page<HandleServiceDTO> selectHandleServicePage(Page<HandleService> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<HandleService> wrapper);

    List<HandleServiceDTO> selectHandleServiceList(@Param(Constants.WRAPPER) LambdaQueryWrapper<HandleService> wrapper);
}




