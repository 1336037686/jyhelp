package com.jyadmin.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jyadmin.util.RsaUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-14 01:10 <br>
 * @description: RedisTest <br>
 */
@SpringBootTest
public class RedisTest {


    @Test
    public void test() throws Exception {
        System.out.println(new BigDecimal(1000d / 10000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }


}
