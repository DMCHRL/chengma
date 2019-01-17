package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing HppHppMobileUserValidate.
 *
 * @author administrator
 */
public interface HppMobileUserService {


    /**
     * 保存
     *
     * @param hppMobileUserDTO 短信验证码信息
     * @return HppMobileUserDTO
     */
    HppMobileUserDTO save(HppMobileUserDTO hppMobileUserDTO);

    /**
     * 修改
     * @param hppMobileUserDTO
     * @return HppMobileUserDTO
     */
    HppMobileUserDTO edit(HppMobileUserDTO hppMobileUserDTO);

    HashMap<String, Object> checkHppMobileUserDTO(HppMobileUserDTO hppMobileUserDTO);

    /**
     * 根据手机号查询
     *
     * @param moblieNum 参数：{mobileNum:""}
     * @return MobileDTO
     */
    HppMobileUserDTO findByMobile(String moblieNum);

    HppMobileUserDTO findOne(String id);

    /**
     *
     * @param params
     * @return
     */
    Page<HppMobileUserDTO> pageList(HashMap<String, Object> params);

    List<HppMobileUserDTO> findAll();

    HppMobileUserDTO findByRecommendation(String recommendation);


    /**
     * 设置个推Id
     * @param params
     */
    HashMap<String, Object> setCid(HashMap<String, Object> params);

    /**
     * 标记为跟单用户
     * @param mobile
     */
    void followFlagY(String mobile);

    /**
     * 标记为不跟单用户
     * @param mobile
     */
    void followFlagN(String mobile);

    /**
     * 标记为消费用户
     * @param mobile
     */
    void buyFlagY(String mobile);

    /**
     *标记是否开户
     * @param mobile
     */
    void openFlag(String mobile);

    List<HppMobileUserDTO> findWithFollowFlag(); //跟单用户

    List<HppMobileUserDTO> findWithBuyFlag();    //消费用户

    Page<HppMobileUserDTO> findByRecommendationEd(HashMap<String,Object> params);    //推荐用户
}
