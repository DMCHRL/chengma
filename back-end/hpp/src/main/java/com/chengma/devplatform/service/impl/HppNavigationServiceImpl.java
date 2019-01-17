package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.HppNavigation;
import com.chengma.devplatform.repository.HppNavigationRepository;
import com.chengma.devplatform.service.HppNavigationService;
import com.chengma.devplatform.service.dto.HppNavigationDTO;
import com.chengma.devplatform.service.mapper.HppNavigationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/26.
 */
@Service
@Transactional
public class HppNavigationServiceImpl implements HppNavigationService {

    private final HppNavigationRepository hppNavigationRepository;

    private final HppNavigationMapper hppNavigationMapper;

    @Autowired
    private PageCommon pageCommon;

    public HppNavigationServiceImpl(HppNavigationRepository hppNavigationRepository, HppNavigationMapper hppNavigationMapper){
        this.hppNavigationRepository=hppNavigationRepository;
        this.hppNavigationMapper=hppNavigationMapper;
    }

    @Override
    public Page<HppNavigationDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public HppNavigationDTO save(HppNavigationDTO hppNavigationDTO) {
        return hppNavigationMapper.toDto(hppNavigationRepository.save(hppNavigationMapper.toEntity(hppNavigationDTO)));
    }

    @Override
    public HppNavigationDTO createHppNavigationDTO(HppNavigationDTO hppNavigationDTO) {
        //添加
        if(StringUtils.isBlank(hppNavigationDTO.getId())){

        }else{
            //修改
        }

        return hppNavigationMapper.toDto(hppNavigationRepository.save(hppNavigationMapper.toEntity(hppNavigationDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateHppNavigationDTO(HppNavigationDTO hppNavigationDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppNavigationDTO findOne(String id) {
        return hppNavigationMapper.toDto(hppNavigationRepository.findOne(id));
    }

    @Override
    public List<HppNavigationDTO> findAll() {
        return hppNavigationMapper.toDto(hppNavigationRepository.findAll());
    }

    @Override
    public void delete(String id) {
        hppNavigationRepository.delete(id);
    }

    @Override
    public void countFlag(String flag) {
        HppNavigation hppNavigation = hppNavigationRepository.findByFlagEquals(flag);
        if(hppNavigation == null) return;
        hppNavigation.setNum(hppNavigation.getNum()+1);
        hppNavigation.setDay(hppNavigation.getDay()+1);
        hppNavigationRepository.save(hppNavigation);
    }

    @Override
    public void cleanDay() {
        for(HppNavigation hppNavigation : hppNavigationRepository.findAll()){
            hppNavigation.setDay(0);
            hppNavigationRepository.save(hppNavigation);
        }
    }
}
