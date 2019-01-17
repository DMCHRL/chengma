package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppIntegralDetailDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/10/12.
 */
public interface HppIntegralDetailService {

    Page<HppIntegralDetailDTO> pageList(HashMap<String, Object> params);

    HppIntegralDetailDTO save(HppIntegralDetailDTO hppVideoDTO);

    HppIntegralDetailDTO createHppIntegralDetailDTO(HppIntegralDetailDTO hppVideoDTO);

    HashMap<String, Object> checkCreateHppIntegralDetailDTO(HppIntegralDetailDTO hppVideoDTO);

    HppIntegralDetailDTO findOne(String id);

    List<HppIntegralDetailDTO> findAll();


    List<HppIntegralDetailDTO> findByCommunityOrFriend(String type,String mobile);

    HppIntegralDetailDTO findByFriend(String uuid);
    
    void delete(String id);

    List<HppIntegralDetailDTO> findByMobile(String mobile);
}

