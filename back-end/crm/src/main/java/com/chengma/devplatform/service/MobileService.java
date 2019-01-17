package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.MobileDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing MobileValidate.
 *
 * @author administrator
 */
public interface MobileService {

    /**
     * 保存
     *
     * @param mobileDTO 短信验证码信息
     * @return MobileDTO
     */
    MobileDTO save(MobileDTO mobileDTO);

    /**
     * 更新(desc)
     * @param mobileDTO
     * @return
     */
    public MobileDTO update(MobileDTO mobileDTO) ;

    /**
     * 根据手机号查询
     *
     * @param moblieNum 参数：{mobileNum:""}
     * @return MobileDTO
     */
    MobileDTO findByMobile(String moblieNum);


    List<MobileDTO> findMobileList(HashMap<String, Object> params);

    /**
     *
     * @param params
     * @return
     */
    Page<MobileDTO> pageList(HashMap<String, Object> params);

}
