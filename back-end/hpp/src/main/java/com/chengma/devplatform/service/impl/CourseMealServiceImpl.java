package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.CourseMealRepository;
import com.chengma.devplatform.service.CourseMealService;
import com.chengma.devplatform.service.dto.CourseMealDTO;
import com.chengma.devplatform.service.mapper.CourseMealMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */
@Service
@Transactional
public class CourseMealServiceImpl implements CourseMealService {

    private final CourseMealRepository courseMealRepository;

    private final CourseMealMapper courseMealMapper;

    @Autowired
    private PageCommon pageCommon;

    public CourseMealServiceImpl(CourseMealRepository courseMealRepository, CourseMealMapper courseMealMapper){
        this.courseMealRepository=courseMealRepository;
        this.courseMealMapper=courseMealMapper;
    }

    @Override
    public Page<CourseMealDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public CourseMealDTO save(CourseMealDTO courseMealDTO) {
        return courseMealMapper.toDto(courseMealRepository.save(courseMealMapper.toEntity(courseMealDTO)));
    }

    @Override
    public CourseMealDTO createCourseMealDTO(CourseMealDTO courseMealDTO) {
        //添加
        if(StringUtils.isBlank(courseMealDTO.getId())){

        }else{
            //修改

        }

        return courseMealMapper.toDto(courseMealRepository.save(courseMealMapper.toEntity(courseMealDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateCourseMealDTO(CourseMealDTO courseMealDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();


        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public CourseMealDTO findOne(String id) {
        return courseMealMapper.toDto(courseMealRepository.findOne(id));
    }

    @Override
    public List<CourseMealDTO> findAll() {
        return courseMealMapper.toDto(courseMealRepository.findAll());
    }


    @Override
    public List<CourseMealDTO> findByCourseId(String courseId) {
       return courseMealMapper.toDto(courseMealRepository.findByCourseIdEquals(courseId));
    }

    @Override
    public CourseMealDTO findByCourseMealId(String courseMealId) {
        return courseMealMapper.toDto(courseMealRepository.findByCourseMealIdEquals(courseMealId));
    }

    @Override
    public void delete(String id) {
        courseMealRepository.delete(id);
    }

    @Override
    public void deleteByCourseMealId(String courseMealId) {
        courseMealRepository.deleteByCourseMealIdEquals(courseMealId);
    }
}