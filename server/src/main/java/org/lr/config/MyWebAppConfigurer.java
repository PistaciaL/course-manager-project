package org.lr.config;

import org.lr.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {


    /**
     * Interceptor注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login","/**/login/**","/**/register/**", "/**/verifyCode/**","/**/avatar/**");
    }

    //自定义interceptor后,由于请求经过的先后顺序,请求先进入interceptor中导致跨域配置失效.
    //因此使用CorsConfig过滤器解决跨域问题
//    /**
//     * 跨域问题处理
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                //是否发送Cookie
//                .allowCredentials(true)
//                //放行哪些原始域
//                .allowedOrigins("*")
//                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
//                .allowedHeaders("*")
//                .exposedHeaders("*");
//    }
}
