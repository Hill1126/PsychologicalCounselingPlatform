package org.gdou.config;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/9
 **/
@Configuration
public class BaiDuBosConfig {

    @Value("${baidu.bos.ACCESS_KEY_ID}")
    public  String ACCESS_KEY_ID ;
    @Value("${baidu.bos.SECRET_ACCESS_KEY}")
    public  String SECRET_ACCESS_KEY ;


    public static final String GZ_ENDPOINT = "gz.bcebos.com";

    public static final String AVATAR_BUCKET_NAME = "avatar-img";
    public static final String ARTICLE_BUCKET_NAME = "article-img";

    @Bean
    public BosClient getBosClient(){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(GZ_ENDPOINT);
        config.setEnableHttpAsyncPut(true);
        return new BosClient(config);

    }

}
