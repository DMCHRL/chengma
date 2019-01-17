package com.chengma.devplatform.config;

/**
 * Created by Administrator on 2017/10/11/0011.
 */
import com.chengma.devplatform.common.util.EnvUtil;
import com.mt4.ApiMT4Kit;
import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by pangkunkun on 2017/9/3.
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        initApiConfig(env);
    }

    private void initApiConfig(Environment env){

        ConnectorAPI mt4 = new MT4();

		String mt4Ip = EnvUtil.getProperty("mt4.ip");
        String mt4Port = EnvUtil.getProperty("mt4.port");
        int login = Integer.valueOf(EnvUtil.getProperty("mt4.login"));
        String password = EnvUtil.getProperty("mt4.password");

        mt4.connect(mt4Ip + ":" + mt4Port);//服务器地址

        mt4.login(login, password);//登录密码

        ApiMT4Kit.putMT4Config(mt4);
    }

}
