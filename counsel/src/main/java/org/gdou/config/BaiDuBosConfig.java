package org.gdou.config;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/9
 **/
@Configuration
public class BaiDuBosConfig {

    public static final String ACCESS_KEY_ID = "dd71e9aeb6e8433b86a10a998cf8e8ac";
    public static final String SECRET_ACCESS_KEY  = "d15f962503344d368ea68e010ee76f6c";
    public static final String GZ_ENDPOINT = "gz.bcebos.com";

    @Bean
    public BosClient getBosClient(){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(GZ_ENDPOINT);
        config.setEnableHttpAsyncPut(true);
        return new BosClient(config);

    }

}
