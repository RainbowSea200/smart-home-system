package com.yaosheng.assistant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcResourceConfig implements WebMvcConfigurer {

    // 配置静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. 映射项目内静态资源（classpath）
        registry.addResourceHandler("/static/**")  // 前端访问前缀（如：/static/xxx.png）
                .addResourceLocations("classpath:/static/")  // 后端实际存储路径
                .setCachePeriod(3600);  // 可选，设置缓存时间（秒）

        // 2. 映射项目外本地磁盘资源（重点！前后端分离常用，避免图片被打包进JAR包）
        String localImagePath = "file:D:/ProgramData/IdeaPrograms/CourseDesign/smart-home-system/simple-assistant/src/main/resources/static/image";  // 本地绝对路径（注意：必须以 file: 开头）
        registry.addResourceHandler("/image/**")  // 前端访问前缀（如：/images/xxx.png）
                .addResourceLocations(localImagePath)
                .setCachePeriod(3600);
    }
}
