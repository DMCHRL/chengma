package com.chengma.devplatform.schedule;

/**
 * Created by Administrator on 2017/9/5/0005.
 */

import com.chengma.devplatform.common.util.EnvUtil;
import com.chengma.devplatform.common.util.JSONUtils;
import com.chengma.devplatform.service.TlbCommissionService;
import com.chengma.devplatform.service.TlbMtPriceService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.impl.MT4Service;
import com.chengma.devplatform.websocket.MyWebSocket;
import com.mt4.api.MT4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 定时任务配置类
 * http://blog.csdn.net/catoop/
 */
@Configuration
@EnableScheduling // 启用定时任务
@ComponentScan({"com.chengma.devplatform.service"})
public class SchedulingConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Autowired
    TlbCommissionService tlbCommissionService;


    @Autowired
    private TlbMtPriceService mtPriceService;


    @Scheduled(cron = "0 0/2 * * * ?") // 每2分钟执行一次
    public void checkUserExpire() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.checkUserExpire.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //校验失效用户的方法
            userService.checkUserExpire();
        }
    }

    @Scheduled(cron = "${scheduled.updateCommission.cron}") // 每2分钟执行一次
    public void updateCommission() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.updateCommission.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //更新佣金
            tlbCommissionService.updateCommission();
            //mt4Service.sendSymbolInfo("EURUSD");
        }
    }

    @Scheduled(cron = "${scheduled.sendSymbolInfo.cron}") // 每2分钟执行一次
    public void sendSymbolInfo() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.sendSymbolInfo.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //更新佣金
            try {
                MyWebSocket.sendInfo(JSONUtils.obj2json(mtPriceService.findAll()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
