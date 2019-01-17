package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.HppCourseMealRepository;
import com.chengma.devplatform.service.CourseMealService;
import com.chengma.devplatform.service.HppCourseMealService;
import com.chengma.devplatform.service.dto.HppCourseMealDTO;
import com.chengma.devplatform.service.mapper.HppCourseMealMapper;
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
public class HppCourseMealServiceImpl implements HppCourseMealService {

    private final HppCourseMealRepository hppCourseMealRepository;

    private final HppCourseMealMapper hppCourseMealMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private CourseMealService courseMealService;

    public HppCourseMealServiceImpl(HppCourseMealRepository hppCourseMealRepository, HppCourseMealMapper hppCourseMealMapper){
        this.hppCourseMealRepository=hppCourseMealRepository;
        this.hppCourseMealMapper=hppCourseMealMapper;
    }

    @Override
    public Page<HppCourseMealDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public HppCourseMealDTO save(HppCourseMealDTO hppCourseMealDTO) {
        return hppCourseMealMapper.toDto(hppCourseMealRepository.save(hppCourseMealMapper.toEntity(hppCourseMealDTO)));
    }

    @Override
    public HppCourseMealDTO createHppCourseMealDTO(HppCourseMealDTO hppCourseMealDTO) {
        //添加
        if(StringUtils.isBlank(hppCourseMealDTO.getId())){

        }else{
            //修改

        }

        return hppCourseMealMapper.toDto(hppCourseMealRepository.save(hppCourseMealMapper.toEntity(hppCourseMealDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateHppCourseMealDTO(HppCourseMealDTO hppCourseMealDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        //没有check套餐
        if(StringUtils.isBlank(hppCourseMealDTO.getInclude())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入套餐内容");
            return retMap;
        }

        if(StringUtils.isBlank(hppCourseMealDTO.getName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入套餐名称");
            return retMap;
        }

        if(hppCourseMealDTO.getPrice() == null || hppCourseMealDTO.getPrice()<0){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入课程价格");
            return retMap;
        }


        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppCourseMealDTO findOne(String id) {
        return hppCourseMealMapper.toDto(hppCourseMealRepository.findOne(id));
    }

    @Override
    public List<HppCourseMealDTO> findAll() {
        return hppCourseMealMapper.toDto(hppCourseMealRepository.findAll());
    }

    @Override
    public void delete(String id) {
        hppCourseMealRepository.delete(id);
    }

    @Override
    public HppCourseMealService findByCourseId(String id) {
        return null;
    }
}