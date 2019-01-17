package com.chengma.devplatform.schedule;

/**
 * Created by Administrator on 2017/9/5/0005.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务配置类
 * http://blog.csdn.net/catoop/
 */
@Configuration
@EnableScheduling // 启用定时任务
@ComponentScan({"com.chengma.devplatform.service"})
public class SchedulingConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());


  /*  @Scheduled(cron = "0 0/2 * * * ?") // 每2分钟执行一次
    public void countFundInOrOut() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.countFundInOrOut.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //结算出入金
            hppStrategyTradeService.countFundInOrOut();
        }
    }*/


}
