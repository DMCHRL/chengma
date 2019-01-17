package com.chengma.devplatform.service;


import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.domain.HppIntegralDetail;
import com.chengma.devplatform.service.dto.HppIntegralDTO;
import com.chengma.devplatform.service.dto.HppIntegralDetailDTO;
import com.chengma.devplatform.service.dto.HppIntegralViewDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface HppIntegralService {

    /**
     *
     * @param params
     * @return
     */
    Page<HppIntegralDTO> pageList(HashMap<String, Object> params);

    HppIntegralDTO save(HppIntegralDTO hppIntegralDTO);

    HppIntegralDTO findOne(String id);

    HppIntegralDTO findByMobileNum(String mobileNum);

    /**
     * 积分兑换列表
     * @return
     */
    List<HppIntegralViewDTO> findList();

    void delete(String id);

    void addIntegral(String mobile,Double total);

    void reduceIntegral(String mobile,Double total);

    void shareToCommunity(String mobile);

    void shareToFriend(String mobile);

    void login(String mobile);

    void setIntegral(HashMap<String,Object> params);

    List<HppIntegralDetailDTO> findDetailByMobile(String mobile);

}
