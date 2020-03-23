package org.gdou.counsel.baidu;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.PutObjectResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/8
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class BosTest {

    @Autowired
    BosClient client;

    @Test
    public void fun() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:static/image/default_avatar.jpg");
        InputStream inputStream = new FileInputStream(file);
        PutObjectResponse putObjectResponse = client.putObject("avatar-img", "test-img", file);
        Assert.assertNotNull(putObjectResponse);
    }
}
