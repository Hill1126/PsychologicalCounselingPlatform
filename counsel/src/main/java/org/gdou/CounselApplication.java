package org.gdou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 * @author: HILL
 * @date: 2020/2/3 15:48
 *
**/
@SpringBootApplication
@EnableJpaRepositories
public class CounselApplication {

    public static void main(String[] args) {
        SpringApplication.run(CounselApplication.class, args);
    }

}
