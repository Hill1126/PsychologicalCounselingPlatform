package org.gdou.config;

import org.gdou.common.interceptor.SystemLogInterceptor;
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

    @Bean
    public HandlerInterceptor getSystemLogInterceptor(){
        return new SystemLogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {"/","/static/**","/templates/**","/index.html"};
        registry.addInterceptor(getSystemLogInterceptor()).addPathPatterns("/**").excludePathPatterns(exclude);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

}
