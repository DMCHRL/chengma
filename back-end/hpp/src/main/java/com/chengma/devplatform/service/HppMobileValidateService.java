package com.chengma.devplatform.service;


import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.dto.HppMobileValidateDTO;

import java.util.HashMap;

/**
 * Service Interface for managing HppMobileValidate.
 *
 * @author administrator
 */
public interface HppMobileValidateService {

    /**
     * 保存
     *
     * @param hppMobileValidateDTO 短信验证码信息
     * @return HppMobileValidateDTO
     */
    HppMobileValidateDTO save(HppMobileValidateDTO hppMobileValidateDTO);

    /**
     * 根据手机号查询
     *
     * @param params 参数：{mobileNum:""}
     * @return HppMobileValidateDTO
     */
    HppMobileValidateDTO findByMobile(HashMap<String, Object> params);

    /**
     * 验证随机码
     *
     * @param params 参数：{mobileNum:"",validateCode:""}
     * @return ResponseResult
     */
    HashMap<String, Object> verification(HashMap<String, Object> params);

    /**
     * 发送验证码
     *
     * @param params 参数：{mobileNum:""}
     * @return ResponseResult
     */
    HashMap<String, Object> sendCode(HashMap<String, Object> params);

    /**
     * 发送通知（示例）
     *
     * @param params 参数：{mobileNum:""}
     * @return ResponseResult
     */
    HashMap<String, Object> notice(HashMap<String, Object> params);

}
