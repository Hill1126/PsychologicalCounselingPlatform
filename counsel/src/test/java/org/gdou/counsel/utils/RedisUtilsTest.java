package org.gdou.counsel.utils;

import org.gdou.common.utils.RedisUtil;
import org.gdou.model.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/29
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RedisUtilsTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void fun(){
        redisUtil.set("test","xujunfeg");
        Object o = redisUtil.get("test");
        System.out.println(o);
    }

    @Test
    public void fun2(){
        User user = new User();
        user.setName("许俊锋");
        redisUtil.set("user",user);
        var user1 = (User)redisUtil.get("user");
        System.out.println(user1.getName());

    }

}
