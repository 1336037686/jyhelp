package com.jyadmin.module.handleService.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.module.handleService.domain.HandleService;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-07 11:30 <br>
 * @description: HandleServiceDTO <br>
 */
@Data
@Accessors(chain = true)
public class HandleServiceDTO extends HandleService {

    /**
     * 服务人员账号
     */
    @TableField(exist = false)
    private String handleUserName;

    /**
     * 服务人员名称
     */
    @TableField(exist = false)
    private String handleUserNickname;

    /**
     * 用户服务编码
     */
    @TableField(exist = false)
    private String serviceCode;

    /**
     * 用户账号
     */
    @TableField(exist = false)
    private String username;

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nickname;
}
