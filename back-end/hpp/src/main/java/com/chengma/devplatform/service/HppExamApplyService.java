package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.HppExamApplyDTO;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface HppExamApplyService {

    /**
     *
     * @param params
     * @return
     */
    Page<HppExamApplyDTO> pageList(HashMap<String, Object> params);

    HppExamApplyDTO save(HppExamApplyDTO hppExamApplyDTO);

    HashMap<String,Object> createHppExamApplyDTO(HttpServletRequest request , HppExamApplyDTO hppExamApplyDTO);

    HppExamApplyDTO findOne(String id);

    HppExamApplyDTO findByOutTradeNo(String outTradeNo);

    void delete(String id);

    void deleteByOutTradeNo(String outTradeNo);

    public HashMap<String, Object> checkHppExamApply(HppExamApplyDTO hppExamApplyDTO);

    /**
     * 根据培训id返回报名人数
     * @param courseMealId
     * @return
     */
    Long countByCourseMealId(String courseMealId);


}
