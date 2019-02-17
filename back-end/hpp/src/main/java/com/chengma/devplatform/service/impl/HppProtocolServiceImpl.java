package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppProtocol;
import com.chengma.devplatform.repository.HppNewsRepository;
import com.chengma.devplatform.repository.HppProtocolRepository;
import com.chengma.devplatform.service.HppProtocolService;
import com.chengma.devplatform.service.dto.HppProtocolDTO;
import com.chengma.devplatform.service.mapper.HppNewsMapper;
import com.chengma.devplatform.service.mapper.HppProtocolMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/12.
 */
@Service
@Transactional
public class HppProtocolServiceImpl implements HppProtocolService {

    private final Logger log = LoggerFactory.getLogger(HppProtocolServiceImpl.class);

    private final HppProtocolRepository hppProtocolRepository;

    private final HppProtocolMapper hppProtocolMapper;

    public HppProtocolServiceImpl(HppProtocolRepository hppProtocolRepository, HppProtocolMapper hppProtocolMapper) {
        this.hppProtocolRepository = hppProtocolRepository;
        this.hppProtocolMapper = hppProtocolMapper;
    }

    @Override
    public HppProtocolDTO save(HppProtocolDTO hppProtocolDTO) {
        if(StringUtils.isBlank(hppProtocolDTO.getId())){
            Date now = new Date();
            hppProtocolDTO.setCreateTime(now);
            hppProtocolDTO.setUpdateTime(now);
            hppProtocolDTO.setDelFlag("N");
        }else{
            HppProtocol hppProtocol = hppProtocolRepository.findOne(hppProtocolDTO.getId());
            hppProtocolDTO.setCreateTime(hppProtocol.getCreateTime());
            hppProtocolDTO.setUpdateTime(new Date());
        }

        return hppProtocolMapper.toDto(hppProtocolRepository.save(hppProtocolMapper.toEntity(hppProtocolDTO)));
    }

    @Override
    public HashMap<String, Object> checkSave(HppProtocolDTO hppProtocolDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppProtocolDTO.getContext())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入内容");
            return retMap;
        }

        if(StringUtils.isBlank(hppProtocolDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择类型");
            return retMap;
        }
        HppProtocolDTO existProtocolDTO = hppProtocolMapper.toDto(hppProtocolRepository.findHppProtocolByTypeEquals(hppProtocolDTO.getType()));
        if(null != existProtocolDTO && !existProtocolDTO.getId().equals(hppProtocolDTO.getId())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "已存在相同的类型");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Page<HppProtocolDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public List<HppProtocolDTO> findList() {
        return hppProtocolMapper.toDto(hppProtocolRepository.findAll());
    }

    @Override
    public HppProtocolDTO findOne(String id) {
        return hppProtocolMapper.toDto(hppProtocolRepository.findOne(id));
    }

    @Override
    public HppProtocolDTO findType(String type) {
        return hppProtocolMapper.toDto(hppProtocolRepository.findHppProtocolByTypeEquals(type));
    }

    @Override
    public void delete(String id) {
        hppProtocolRepository.delete(id);
        /*HppProtocol hppProtocol = hppProtocolRepository.findOne(id);
        hppProtocol.setDelFlag("Y");
        hppProtocolRepository.save(hppProtocol);*/
    }
}
