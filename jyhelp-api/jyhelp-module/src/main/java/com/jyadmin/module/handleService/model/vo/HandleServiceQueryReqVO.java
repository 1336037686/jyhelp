package com.jyadmin.module.handleService.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务执行记录表 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-06 10:46:43 <br>
 * @description: HandleServiceQueryReqVO <br>
 */
@ApiModel("服务执行记录表-查询-数据模型")
@Data
public class HandleServiceQueryReqVO extends BasePageVO implements Serializable {

    /**
     * 服务记录编号
     */
    @ApiModelProperty(value = "服务记录编号", name = "handleCode")
    private String handleCode;

    /**
     * 用户服务编码
     */
    @ApiModelProperty(value = "用户服务编码", name = "serviceCode")
    private String serviceCode;

    /**
     * 服务时间
     */
    @ApiModelProperty(value = "服务时间", name = "handleTime")
    private LocalDateTime handleTime;

    /**
     * 服务人员账号
     */
    @ApiModelProperty(value = "服务人员账号", name = "handleUserName")
    private String handleUserName;

    /**
     * 服务人员名称
     */
    @ApiModelProperty(value = "服务人员名称", name = "handleUserNickname")
    private String handleUserNickname;


    /**
     * 服务状态
     */
    @ApiModelProperty(value = "服务状态", name = "handleStatus")
    private String handleStatus;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", name = "username")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "nickname")
    private String nickname;

}
