package org.gdou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author: HILL
 * @date: 2020/2/3 15:48
 *
**/
@SpringBootApplication
@MapperScan("org.gdou.dao")
public class CounselApplication {
    public static void main(String[] args) {
        SpringApplication.run(CounselApplication.class, args);
    }

}
