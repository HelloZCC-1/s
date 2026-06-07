package com.hmdp.config;

import com.hmdp.utils.LoginInterceptor;
import com.hmdp.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;


import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new LoginInterceptor(stringRedisTemplate))
                .excludePathPatterns(
                        "/shop/**",
                        "/voucher/**",
                        "/shop-type/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login"
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 把 nginx-1.0 文件夹里的所有静态资源全部映射上，一劳永逸
        String path = System.getProperty("user.dir") + "/nginx-1.0/";
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + path);
        // 如果你嫌上面太粗暴，也可以只加这几行精确映射（效果一样）
        // registry.addResourceHandler("/types/**").addResourceLocations("file:" + path + "types/");
        // registry.addResourceHandler("/imgs/**").addResourceLocations("file:" + path + "imgs/");
        // registry.addResourceHandler("/shop/**").addResourceLocations("file:" + path + "shop/");
        // registry.addResourceHandler("/blogs/**").addResourceLocations("file:" + path + "blogs/");
    }
}
