package com.suitong.devplatform.schedule;

/**
 * Created by Administrator on 2017/9/5/0005.
 */

import com.suitong.devplatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务配置类
 * http://blog.csdn.net/catoop/
 */
@Configuration
@EnableScheduling // 启用定时任务
@ComponentScan({"com.suitong.devplatform.service"})
public class SchedulingConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    //@Scheduled(cron = "${}") // 每2分钟执行一次
    /*public void checkUserExpire() {
        userService.checkUserExpire();
    }*/
}
