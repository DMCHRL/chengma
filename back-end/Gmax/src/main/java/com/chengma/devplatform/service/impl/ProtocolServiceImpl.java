package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.Protocol;
import com.chengma.devplatform.repository.ProtocolRepository;
import com.chengma.devplatform.service.ProtocolService;
import com.chengma.devplatform.service.dto.ProtocolDTO;
import com.chengma.devplatform.service.mapper.ProtocolMapper;
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
public class ProtocolServiceImpl implements ProtocolService {

    private final Logger log = LoggerFactory.getLogger(ProtocolServiceImpl.class);

    private final ProtocolRepository protocolRepository;

    private final ProtocolMapper protocolMapper;

    public ProtocolServiceImpl(ProtocolRepository protocolRepository, ProtocolMapper protocolMapper) {
        this.protocolRepository = protocolRepository;
        this.protocolMapper = protocolMapper;
    }

    @Override
    public ProtocolDTO save(ProtocolDTO protocolDTO) {
        if(StringUtils.isBlank(protocolDTO.getId())){
            Date now = new Date();
            protocolDTO.setCreateTime(now);
            protocolDTO.setUpdateTime(now);
            protocolDTO.setDelFlag("N");
        }else{
            Protocol protocol = protocolRepository.findOne(protocolDTO.getId());
            protocolDTO.setCreateTime(protocol.getCreateTime());
            protocolDTO.setUpdateTime(new Date());
        }

        return protocolMapper.toDto(protocolRepository.save(protocolMapper.toEntity(protocolDTO)));
    }

    @Override
    public HashMap<String, Object> checkSave(ProtocolDTO protocolDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(protocolDTO.getContext())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入内容");
            return retMap;
        }

        if(StringUtils.isBlank(protocolDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择类型");
            return retMap;
        }
        ProtocolDTO existProtocolDTO = protocolMapper.toDto(protocolRepository.findProtocolByTypeEquals(protocolDTO.getType()));
        if(null != existProtocolDTO && !existProtocolDTO.getId().equals(protocolDTO.getId())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "已存在相同的类型");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Page<ProtocolDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public List<ProtocolDTO> findList() {
        return protocolMapper.toDto(protocolRepository.findAll());
    }

    @Override
    public ProtocolDTO findOne(String id) {
        return protocolMapper.toDto(protocolRepository.findOne(id));
    }

    @Override
    public ProtocolDTO findType(String type) {
        return protocolMapper.toDto(protocolRepository.findProtocolByTypeEquals(type));
    }

    @Override
    public void delete(String id) {
        protocolRepository.delete(id);
       /* Protocol protocol = protocolRepository.findOne(id);
        protocol.setDelFlag("Y");
        protocolRepository.save(protocol);*/
    }
}
