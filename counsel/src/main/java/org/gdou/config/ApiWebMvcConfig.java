package org.gdou.config;

import org.gdou.common.interceptor.ArticleSearchInterceptor;
import org.gdou.common.interceptor.LoginCheckInterceptor;
import org.gdou.common.interceptor.SystemLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
    @Bean
    public HandlerInterceptor getLoginCheckInterceptor(){return new LoginCheckInterceptor(); }
    @Bean
    public HandlerInterceptor getArticleController(){return new ArticleSearchInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //日志记录过滤器
        String[] exclude = {"/","/static/**","/templates/**","/index.html"};
        String [] include = {"/user/**","/admin/**","/counsel/**","/paper/**"};
        //登录检查过滤器
        registry.addInterceptor(getLoginCheckInterceptor()).addPathPatterns(include).excludePathPatterns(exclude)
                .excludePathPatterns("/user/login").excludePathPatterns("/user/register").excludePathPatterns("/image/**")
                .excludePathPatterns("/article/**");
        //添加文章搜索过滤器
        registry.addInterceptor(getArticleController()).addPathPatterns("/article/search");



    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/static/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .exposedHeaders("Access-Control-Allow-Origin")
                .exposedHeaders("Content-Type")
                .exposedHeaders( "X-Requested-With")
                .exposedHeaders("accept")
                .exposedHeaders("Origin")
                .exposedHeaders( "Access-Control-Request-Method")
                .exposedHeaders("Access-Control-Request-Headers")
                .maxAge(1000L*60*60);

    }
}
