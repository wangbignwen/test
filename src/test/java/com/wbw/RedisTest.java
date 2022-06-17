package com.wbw;

import com.wbw.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // @Autowired根据类型寻找Bean @Resource根据名称寻找Bean
    // 手动配置了RedisConfig之后，在spring上下文就存在了RedisTemplate<String,Object>类型的Bean

    // 需要开启本地redis数据库
    @Test
    public void testRedisTemplateObj() throws InterruptedException {
        User user = new User();
        user.setId(1001);
        user.setName("王炳文");
        String key = "test3";
        redisTemplate.opsForValue().set(key, user);
        System.out.println("key=" + key + ",value=" + redisTemplate.opsForValue().get(key));
        redisTemplate.opsForValue().set(key,user,3, TimeUnit.SECONDS);
        Thread.sleep(5000);
        Object o = redisTemplate.opsForValue().get(key);
        boolean bo = o==null;
        System.out.println(bo);
    }
}
