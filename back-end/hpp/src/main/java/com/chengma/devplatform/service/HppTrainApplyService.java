package com.chengma.devplatform.service;


import com.chengma.devplatform.domain.HppTrainApply;
import com.chengma.devplatform.service.dto.HppTrainApplyDTO;
import com.chengma.devplatform.service.dto.HppTrainDTO;
import com.sun.net.httpserver.HttpServer;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface HppTrainApplyService {

    /**
     *
     * @param params
     * @return
     */
    Page<HppTrainApplyDTO> pageList(HashMap<String, Object> params);

    HppTrainApplyDTO save(HppTrainApplyDTO hppTrainApplyDTO);

    HashMap<String,Object> createHppTrainApplyDTO(HttpServletRequest request , HppTrainApplyDTO hppTrainApplyDTO);

    HppTrainApplyDTO findOne(String id);

    /*HppTrainApplyDTO findByPhoneAndCourseMealId(String phone,String courseMealId); //废弃*/

    HppTrainApplyDTO findByOutTradeNo(String outTradeNo);

    void delete(String id);

    public HashMap<String, Object> checkHppTrainApply(HppTrainApplyDTO hppTrainApplyDTO);

    /**
     * 根据培训id返回报名人数
     * @param courseMealId
     * @return
     */
    Long countByCourseMealId(String courseMealId);

    void deleteByOutTradeNo(String outTradeNo);


}
