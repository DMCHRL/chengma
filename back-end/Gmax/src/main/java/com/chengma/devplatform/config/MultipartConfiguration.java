package com.chengma.devplatform.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/9/7.
 */
@Configuration
public class MultipartConfiguration {

    /*@Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(500*1024*1024);//上传文件大小 50M 50*1024*1024
        return resolver;
    }*/
}
