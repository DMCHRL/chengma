package com.chengma.devplatform.common.util;

import com.alibaba.fastjson.JSONObject;
import com.chengma.devplatform.domain.SysRest;
import com.ucpaas.restDemo.client.JsonReqClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Random;

/**
 * 短信工具类
 */
@Component
public class SysRestUtil {

    @Autowired
    private SysRest sysRest;
    private static SysRestUtil sysRestUtil;

    @PostConstruct
    public void init() {
        sysRestUtil = this;
        sysRestUtil.sysRest = this.sysRest;
    }

    /**
     * 发送信息
     *
     * @param phone 手机号
     * @param param 对应短信模板参数，参数用英文逗号隔开
     * @param type  根据不同的type调用不同的短信模板
     * @return 返回000000则表示发送成功, 105112表示参数数量不匹配, 105110表示模版id错误或为空, 其他代码请查阅云之讯官网文档
     */
    public static String sendMessage(String phone, String param, String type) {
        String accountSid = sysRestUtil.sysRest.getAccountSid();
        String token = sysRestUtil.sysRest.getToken();
        String appId = sysRestUtil.sysRest.getAppId();
        Map<String, String> templates = sysRestUtil.sysRest.getTemplates();
        int paramsCount = 0;

        String id_count = templates.get(type);
        if (null == id_count) {
            return "105110";
        }
        String[] id_counts = id_count.split(",");
        if (id_counts.length != 2) {
            return "105110";
        }
        String templateId = id_counts[0];
        paramsCount = "".equals(id_counts[1]) ? 0 : Integer.valueOf(id_counts[1]);
        if (!checkParams(paramsCount, param)) {
            return "105112";
        }

        String result = new JsonReqClient().templateSMS(accountSid, token, appId, templateId, phone, param);
        JSONObject json = JSONObject.parseObject(result);
        JSONObject resp = json.getJSONObject("resp");
        String respCode = resp.getString("respCode");
        System.out.println("Response content is: " + result);
        return respCode;
    }

    /**
     * 生成随机码
     *
     * @return 返回4位随机码
     */
    public static String getCode() {
        char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int codeCount = 4;//验证码个数
        //定义随机数类
        Random r = new Random();
        //定义存储验证码的类
        StringBuilder builderCode = new StringBuilder();
        for (int i = 0; i < codeCount; i++) {
            char c = codeSequence[r.nextInt(codeSequence.length)];
            builderCode.append(c);
        }
        return builderCode.toString();
    }

    /**
     * 检查参数
     *
     * @param paramsCount 参数数量
     * @param param       发送内容（对应短信模版的参数）
     * @return true表示检查通过，false表示参数有误
     */
    private static boolean checkParams(int paramsCount, String param) {
        if (null != param && paramsCount != param.split(",").length) {
            return false;
        }
        if (null == param && paramsCount != 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取失效时间（单位：分钟）
     *
     * @return 获取yml配置文件里的值并返回（默认10）
     */
    public static Integer getExpiryTime() {
        return sysRestUtil.sysRest.getExpiryTime() == null ? 10 : sysRestUtil.sysRest.getExpiryTime();
    }

}
