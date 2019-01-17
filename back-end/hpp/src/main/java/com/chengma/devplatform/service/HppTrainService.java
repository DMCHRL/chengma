package com.chengma.devplatform.service;


import com.chengma.devplatform.domain.HppTrain;
import com.chengma.devplatform.service.dto.HppTrainDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface HppTrainService {

    /**
     *
     * @param params
     * @return
     */
    Page<HppTrainDTO> pageList(HashMap<String, Object> params);

    HppTrainDTO save(HppTrainDTO hppTrainDTO);

    HppTrainDTO findOne(String id);

    void delete(String id);

    HppTrainDTO findByName(String name,String userId);

    public HashMap<String, Object> checkHppTrain(HppTrainDTO hppTrainDTO);

    /**
     * 报名人数
     * @param trainId
     * @return
     */
    public Long applyCount(String trainId);

    /**
     * act on app
     * @return
     */
    List<HppTrainDTO> findAll();

}
