package org.gdou.counsel.baidu;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
public class BosTest {

    BosClient client;

    @Test
    public void fun() throws FileNotFoundException {
        var askey = "dd71e9aeb6e8433b86a10a998cf8e8ac";
        var asSert = "d15f962503344d368ea68e010ee76f6c";
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(askey, asSert));
        config.setEndpoint("gz.bcebos.com");
        client = new BosClient(config);

        File file = ResourceUtils.getFile("classpath:static/image/default_avatar.jpg");
        InputStream inputStream = new FileInputStream(file);
        PutObjectResponse putObjectResponse = client.putObject("avatar-img", "test-img", file);
        System.out.println(putObjectResponse.getETag());
    }
}
