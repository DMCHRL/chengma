package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppLiveDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */
public interface HppLiveService {

    Page<HppLiveDTO> pageList(HashMap<String, Object> params);

    HppLiveDTO save(HppLiveDTO hppVideoDTO);

    HppLiveDTO createHppLiveDTO(HppLiveDTO hppVideoDTO);

    HashMap<String, Object> checkCreateHppLiveDTO(HppLiveDTO hppVideoDTO);

    HppLiveDTO findOne(String id);

    List<HppLiveDTO> findAll();

    void delete(String id);
}

