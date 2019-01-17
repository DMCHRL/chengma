package com.chengma.devplatform.schedule;

/**
 * Created by Administrator on 2017/9/5/0005.
 */

import com.chengma.devplatform.common.util.EnvUtil;
import com.chengma.devplatform.common.util.JSONUtils;
import com.chengma.devplatform.domain.HppNavigation;
import com.chengma.devplatform.domain.HppStrategy;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.websocket.MyWebSocket;
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
@ComponentScan({"com.chengma.devplatform.service"})
public class SchedulingConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Autowired
    private HppStrategyTradeService hppStrategyTradeService;

    @Autowired
    private HppStrategyBalHistoryService hppStrategyBalHistoryService;

    @Autowired
    private HppNavigationService hppNavigationService;


    @Autowired
    private TlbMtPriceService mtPriceService;


    @Scheduled(cron = "0 0/2 * * * ?") // 每2分钟执行一次
    public void countFundInOrOut() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.countFundInOrOut.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //结算出入金
            hppStrategyTradeService.countFundInOrOut();
        }
    }

    @Scheduled(cron = "0 0/2 * * * ?") // 每2分钟执行一次
    public void countLotsAndTotal() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.countLotsAndTotal.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //结算总手数和总收益
            hppStrategyTradeService.countLotsAndTotal();
        }
    }

    @Scheduled(cron = "0 0/2 * * * ?") // 每2分钟执行一次
    public void countMonthProfit() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.countMonthProfit.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //结算月收益  改为累计收益率
            hppStrategyTradeService.countMonthProfit();
        }
    }

    @Scheduled(cron = "0 0/2 * * * ?") // 每2分钟执行一次
    public void countHistoryFundBack() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.countHistoryFundBack.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //计算历史最大回撤率
            hppStrategyBalHistoryService.countHistoryFundBack();
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // 每一小时
    public void cleanDay() {
        //获取yml配置的文件里对应的属性参数
        boolean flag = "true".equals(EnvUtil.getProperty("scheduled.cleanDay.enabled"));
        //如果yml里配的是true则执行校验方法
        if (flag) {
            //清除当前点击率
            hppNavigationService.cleanDay();
        }
    }

}
