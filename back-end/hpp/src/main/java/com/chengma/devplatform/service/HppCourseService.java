package com.chengma.devplatform.service;

import com.chengma.devplatform.domain.HppCourseMeal;
import com.chengma.devplatform.service.dto.HppCourseDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */
public interface HppCourseService {

    Page<HppCourseDTO> pageList(HashMap<String, Object> params);

    HppCourseDTO save(HppCourseDTO hppCourseDTO);

    HppCourseDTO createHppCourseDTO(HppCourseDTO hppCourseDTO);

    HashMap<String, Object> checkCreateHppCourseDTO(HppCourseDTO hppCourseDTO);

    HppCourseDTO findOne(String id);

    HppCourseDTO findOneDetailed(String id);

    List<HppCourseDTO> findAll();

    void delete(String id);

    HppCourseDTO findByCourseMealId(String courseMealId);

    /**
     * 根据套餐id删除套餐
     * @param courseMealId
     */
    void deleteCourseMealById(String courseMealId);
}

