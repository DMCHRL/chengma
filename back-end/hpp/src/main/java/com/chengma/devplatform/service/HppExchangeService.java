package com.chengma.devplatform.service;


import com.chengma.devplatform.domain.HppExchange;
import com.chengma.devplatform.service.dto.HppExchangeDTO;
import com.chengma.devplatform.service.dto.HppIntegralViewDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface HppExchangeService {

    HppExchangeDTO findByMobileIdAndVideoId(String mobileNum, String videoId);

    /**
     *
     * @param params
     * @return
     */
    Page<HppExchangeDTO> pageList(HashMap<String, Object> params);

    HppExchangeDTO save(HppExchangeDTO hppExchangeDTO);

    HppExchangeDTO findOne(String id);

    void delete(String id);

    /**
     * 检查积分兑换
     * @param params
     * @return
     */
    public HashMap<String, Object> checkExchange(HashMap<String, Object> params);

    /**
     * 积分兑换(废弃了)
     * @param params
     * @return
     */
     HppExchangeDTO exchange(HashMap<String, Object> params);

    /**
     * 兑换明细
     * @return
     */
    List<HppIntegralViewDTO> findList();
}
