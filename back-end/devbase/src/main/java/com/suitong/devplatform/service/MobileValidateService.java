package com.suitong.devplatform.service;


import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.dto.MobileValidateDTO;

import java.util.HashMap;

/**
 * Service Interface for managing MobileValidate.
 */
public interface MobileValidateService {

    /**
     * 保存
     */
    MobileValidateDTO save(MobileValidateDTO mobileValidateDTO);

    /**
     * 根据手机号查询
     */
    MobileValidateDTO findByMobile(HashMap<String,Object> params);

    /**
     * 验证随机码
     */
    ResponseResult verification(HashMap<String,Object> params);

    /**
     * 发送验证码
     */
    ResponseResult sendCode(HashMap<String,Object> params);

    /**
     * 发送通知
     */
    HashMap<String,Object> notice(HashMap<String,Object> params);

}
