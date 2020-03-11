package org.gdou.counsel.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/11
 **/
@SpringBootTest
public class UserTest {

    @Value("${user.interceptor}")
    private boolean startInterceptor;

    @Test
    public void fun(){
        Assert.assertNotNull(startInterceptor);
    }


}
