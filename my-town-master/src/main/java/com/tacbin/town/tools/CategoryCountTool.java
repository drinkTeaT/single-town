package com.tacbin.town.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-16 15:16
 **/
@Component
public class CategoryCountTool {
    @Autowired
    private RedisTemplate redisTemplate;

    // 自增可用的商品
    public void addEnablePrdNumber(String key) {
        String count = (String) redisTemplate.opsForValue().get(key);
        if (count == null) {
            count = "1";
        } else {
            int num = Integer.parseInt(count);
            num++;
            count = "" + num;
        }
        redisTemplate.opsForValue().set(key, count);
    }

    // 自减可用商品数
    public void removeEnablePrdNumber(String key) {
        String count = (String) redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(count)) {
            int num = Integer.parseInt(count);
            num--;
            count = num + "";
            redisTemplate.opsForValue().set(key, count);
        }
    }
}
