package org.gdou.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/4
 **/
@Configuration
public class ElasticsearchConfig  {

    @Value("${elasticsearch.host}")
    String host;
    @Value("${elasticsearch.port}")
    String port;
    @Value("${elasticsearch.password}")
    String password;


    @Bean
    public RestHighLevelClient restHighLevelClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(host+":"+port)
                .withBasicAuth("elastic",password)
                .withConnectTimeout(10000L)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
