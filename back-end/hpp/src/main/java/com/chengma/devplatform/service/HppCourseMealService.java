package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppCourseMealDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */
public interface HppCourseMealService {

    Page<HppCourseMealDTO> pageList(HashMap<String, Object> params);

    HppCourseMealDTO save(HppCourseMealDTO hppVideoDTO);

    HppCourseMealDTO createHppCourseMealDTO(HppCourseMealDTO hppVideoDTO);

    HashMap<String, Object> checkCreateHppCourseMealDTO(HppCourseMealDTO hppVideoDTO);

    HppCourseMealDTO findOne(String id);

    List<HppCourseMealDTO> findAll();

    /**
     * 根据课程id或考试id获取套餐
     * @param id
     * @return
     */
    HppCourseMealService findByCourseId(String id);

    void delete(String id);
}

