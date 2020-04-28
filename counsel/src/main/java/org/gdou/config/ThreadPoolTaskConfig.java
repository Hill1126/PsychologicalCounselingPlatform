package org.gdou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/9
 **/
@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {

    @Bean("msgInsertExecutor")
    public ThreadPoolTaskExecutor MsgInsertExecutor(){
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(30);
        executor.setKeepAliveSeconds(20);
        executor.setThreadNamePrefix("msgInsert-Thread-");
        //拒绝策略为交付给原线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
