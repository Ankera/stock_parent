package com.ankers.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TestRedisCache {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testCache() {
        String sessionId="myNameType";
        String checkCode="778899";
        //一般给key加业务主键：CK:12345678->7788 好处：keys CK*
        redisTemplate.opsForValue().set("CK:"+sessionId,checkCode);
        //获取
        String code = (String) redisTemplate.opsForValue().get("CK:" + sessionId);
        System.out.println(code);
    }
}
