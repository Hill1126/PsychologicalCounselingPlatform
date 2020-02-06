package org.gdou.config;


import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * druid配置类
 * @author HILL
 * @version V1.0
 * @date 2020/2/6
 **/
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean statViewServlet() {
        // 创建servlet注册实体
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
                "/druid/*");
        // 设置ip白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 设置ip黑名单,如果allow与deny共同存在时,deny优先于allow
        servletRegistrationBean.addInitParameter("deny", "192.168.0.1");
        // 设置控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "hill");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        // 是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }


}
