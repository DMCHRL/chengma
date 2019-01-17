package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.CourseMealDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */
public interface CourseMealService {

    Page<CourseMealDTO> pageList(HashMap<String, Object> params);

    CourseMealDTO save(CourseMealDTO hppVideoDTO);

    CourseMealDTO createCourseMealDTO(CourseMealDTO hppVideoDTO);

    HashMap<String, Object> checkCreateCourseMealDTO(CourseMealDTO hppVideoDTO);

    CourseMealDTO findOne(String id);

    List<CourseMealDTO> findAll();

    void delete(String id);

    /**
     * 根据课程id查询套餐
     */
    List<CourseMealDTO> findByCourseId(String courseId);

    /**
     * 根据课程套餐id查询套餐
     */
    CourseMealDTO findByCourseMealId(String courseMealId);

    /**
     * 根据课程套餐id删除关联
     * @param courseMealId
     */
    void deleteByCourseMealId(String courseMealId);
}

