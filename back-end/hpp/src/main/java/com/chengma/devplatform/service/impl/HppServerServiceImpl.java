package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.repository.BankInfoRepository;
import com.chengma.devplatform.repository.HppServerRepository;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.HppServerService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.HppServerDTO;
import com.chengma.devplatform.service.mapper.BankInfoMapper;
import com.chengma.devplatform.service.mapper.HppServerMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class HppServerServiceImpl implements HppServerService {

    private final HppServerRepository hppServerRepository;

    private final HppServerMapper hppServerMapper;

    @Autowired
    private PageCommon pageCommon;

    public HppServerServiceImpl(HppServerRepository hppServerRepository, HppServerMapper hppServerMapper){
        this.hppServerRepository=hppServerRepository;
        this.hppServerMapper=hppServerMapper;
    }

    @Override
    public Page<HppServerDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public HppServerDTO save(HppServerDTO hppServerDTO) {
        return hppServerMapper.toDto(hppServerRepository.save(hppServerMapper.toEntity(hppServerDTO)));
    }

    @Override
    public HppServerDTO createHppServerDTO(HppServerDTO hppServerDTO) {
        //添加
       /* if(StringUtils.isBlank(hppServerDTO.getId())){
        }else{
            //修改
        }*/
        hppServerDTO.setUpdateTime(new Date());
        return hppServerMapper.toDto(hppServerRepository.save(hppServerMapper.toEntity(hppServerDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateHppServerDTO(HppServerDTO hppServerDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        if(StringUtils.isBlank(hppServerDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入类型");
            return retMap;
        }

        if(StringUtils.isBlank(hppServerDTO.getText())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入内容");
            return retMap;
        }

        if(StringUtils.isBlank(hppServerDTO.getLabel())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入内容");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppServerDTO findOne(String id) {
        return hppServerMapper.toDto(hppServerRepository.findOne(id));
    }

    @Override
    public List<HppServerDTO> findAll() {
        return hppServerMapper.toDto(hppServerRepository.findAll());
    }

    @Override
    public void delete(String id) {
        hppServerRepository.delete(id);
    }
}