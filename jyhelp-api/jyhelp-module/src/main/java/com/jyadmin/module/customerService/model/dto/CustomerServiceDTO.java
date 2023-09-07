package com.jyadmin.module.customerService.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.module.customerService.domain.CustomerService;
import com.jyadmin.system.user.domain.User;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-07 09:00 <br>
 * @description: CustomerServiceDTO <br>
 */
@Data
@Accessors(chain = true)
public class CustomerServiceDTO extends CustomerService {

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String nickname;

    @TableField(exist = false)
    private String serviceCategoryName;

}
