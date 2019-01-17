package com.mt4;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/7/5.
 */
public class ApiMT4Kit {
    public static final Logger logger = LoggerFactory.getLogger(ApiMT4Kit.class);

    public static final Map<String, ConnectorAPI> CFG_MAP = new ConcurrentHashMap<String, ConnectorAPI>();

    public static ConnectorAPI putMT4Config(ConnectorAPI mt4) {
       /* if (CFG_MAP.size() == 0) {
            CFG_MAP.put(DevplatformConstants.MT4_DEFAULT_KEY, mt4);
        }*/
        return CFG_MAP.put(DevplatformConstants.MT4_DEFAULT_KEY, mt4);
    }

    public static ConnectorAPI removeMT4Config() {
        return CFG_MAP.remove(DevplatformConstants.MT4_DEFAULT_KEY);
    }

    public static ConnectorAPI getMT4Config() {
        return CFG_MAP.get(DevplatformConstants.MT4_DEFAULT_KEY);
    }


}
