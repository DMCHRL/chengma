package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppExamDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */
public interface HppExamService {

    Page<HppExamDTO> pageList(HashMap<String, Object> params);

    HppExamDTO save(HppExamDTO hppExamDTO);

    HppExamDTO createHppExamDTO(HppExamDTO hppExamDTO);

    HashMap<String, Object> checkCreateHppExamDTO(HppExamDTO hppExamDTO);

    HppExamDTO findOne(String id);

    HppExamDTO findOneDetailed(String id);

    List<HppExamDTO> findAll();

    void delete(String id);

    HppExamDTO findByCourseMealId(String courseMealId);

    /**
     * 根据套餐id删除套餐
     * @param courseMealId
     */
    void deleteCourseMealById(String courseMealId);
}

