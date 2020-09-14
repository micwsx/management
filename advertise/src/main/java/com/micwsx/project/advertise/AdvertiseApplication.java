package com.micwsx.project.advertise;

import com.micwsx.project.advertise.interceptors.RequestWithNoCodeInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@SpringBootApplication
@MapperScan("com.micwsx.project.advertise.dao")
public class AdvertiseApplication {


    /**
     * 配置类添加自定义拦截器
     */
    @Configuration
    public class WebConfigurationSupport extends WebMvcConfigurationSupport {

        @Override
        protected void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new RequestWithNoCodeInterceptor())
                    .addPathPatterns("/menu/**")//拦截所有请求,排除二维码关注链接
                    .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                            "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg")
                    .excludePathPatterns("/html/**");
            super.addInterceptors(registry);
        }

        /**
         * 静态资源路径
         *
         * @param registry
         */
        @Override
        protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/static/")
                    .addResourceLocations("classpath:/templates//");
            super.addResourceHandlers(registry);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(AdvertiseApplication.class, args);
    }

}
