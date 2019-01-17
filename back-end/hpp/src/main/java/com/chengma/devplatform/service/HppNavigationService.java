package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppNavigationDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/26.
 */
public interface HppNavigationService {

    Page<HppNavigationDTO> pageList(HashMap<String, Object> params);

    HppNavigationDTO save(HppNavigationDTO hppVideoDTO);

    HppNavigationDTO createHppNavigationDTO(HppNavigationDTO hppVideoDTO);

    HashMap<String, Object> checkCreateHppNavigationDTO(HppNavigationDTO hppVideoDTO);

    HppNavigationDTO findOne(String id);

    List<HppNavigationDTO> findAll();

    void delete(String id);

    void countFlag(String flag);

    /**
     * 清空当天点击量
     */
    void cleanDay();
}

