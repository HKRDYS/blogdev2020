package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//webMVC配置
//放行静态资源，不进行安排拦截
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
    @Override
    protected void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("guest/index");
        registry.addViewController("/error").setViewName("guest/error");
        registry.addViewController("/toLogin").setViewName("guest/login");
        registry.addViewController("/admin/home").setViewName("admin/home");
    }

}
