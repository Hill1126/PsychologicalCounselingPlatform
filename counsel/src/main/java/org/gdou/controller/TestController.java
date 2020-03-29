package org.gdou.controller;

import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.common.utils.RedisUtil;
import org.gdou.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/29
 **/
@Controller("/test")
public class TestController {

    @Autowired
    RedisUtil redisUtil;

    public Result redisSpedTest(){
        long start = System.currentTimeMillis();
        User user =new User();
        user.setName("许俊锋"+start);
        redisUtil.set("test",user);
        Object test = redisUtil.get("test");
        long end = System.currentTimeMillis();
        return ResultGenerator.genSuccessResult((start-end)+"ms");
    }
}
