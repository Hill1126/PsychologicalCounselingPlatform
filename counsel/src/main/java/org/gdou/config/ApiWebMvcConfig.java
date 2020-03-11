package org.gdou.config;

import org.gdou.common.interceptor.LoginCheckInterceptor;
import org.gdou.common.interceptor.SystemLogInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/5
 **/
@Configuration
public class ApiWebMvcConfig implements WebMvcConfigurer {

    @Value("${user.interceptor}")
    private boolean startInterceptor;

    @Bean
    public HandlerInterceptor getSystemLogInterceptor(){
        return new SystemLogInterceptor();
    }
    @Bean
    public HandlerInterceptor getLoginCheckInterceptor(){return new LoginCheckInterceptor(); }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //日志记录过滤器
        String[] exclude = {"/","/static/**","/templates/**","/index.html"};
        registry.addInterceptor(getSystemLogInterceptor()).addPathPatterns("/**").excludePathPatterns(exclude);
        //登录检查过滤器
        if(startInterceptor){
            registry.addInterceptor(getLoginCheckInterceptor()).addPathPatterns("/**").excludePathPatterns(exclude)
                    .excludePathPatterns("/user/login").excludePathPatterns("/user/register")
                    .excludePathPatterns("/favicon.ico");
        }

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

}
