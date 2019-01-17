package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.domain.Mt4Configure;
import com.chengma.devplatform.repository.BindBankRepository;
import com.chengma.devplatform.repository.Mt4ConfigureRepository;
import com.chengma.devplatform.service.BindBankService;
import com.chengma.devplatform.service.Mt4ConfigureService;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.dto.Mt4ConfigureDTO;
import com.chengma.devplatform.service.mapper.BindBankMapper;
import com.chengma.devplatform.service.mapper.Mt4ConfigureMapper;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.MapperConfig;
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
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class Mt4ConfigureServiceImpl implements Mt4ConfigureService {

    private final Mt4ConfigureRepository mt4ConfigureRepository;

    private final Mt4ConfigureMapper mt4ConfigureMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private MT4Service mt4Service;

    public Mt4ConfigureServiceImpl(Mt4ConfigureRepository mt4ConfigureRepository, Mt4ConfigureMapper mt4ConfigureMapper){
        this.mt4ConfigureRepository=mt4ConfigureRepository;
        this.mt4ConfigureMapper=mt4ConfigureMapper;
    }

    @Override
    public Page<Mt4ConfigureDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" ");
        StringBuilder cond = new StringBuilder(" ");

        Page<Mt4ConfigureDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, Mt4ConfigureDTO.class);
        return page;
    }

    @Override
    public Mt4ConfigureDTO save(Mt4ConfigureDTO mt4ConfigureDTO) {
        return mt4ConfigureMapper.toDto(mt4ConfigureRepository.save(mt4ConfigureMapper.toEntity(mt4ConfigureDTO)));
    }

    @Override
    public Mt4ConfigureDTO createMt4ConfigureDTO(Mt4ConfigureDTO mt4ConfigureDTO) {
        //添加
        if(StringUtils.isBlank(mt4ConfigureDTO.getId())){
            mt4ConfigureDTO.setCreateAt(new Date());
        }else{
            //修改
            Mt4Configure mt4Configure = mt4ConfigureRepository.findOne(mt4ConfigureDTO.getId());
            mt4ConfigureDTO.setCreateAt(mt4Configure.getCreateAt());
            mt4ConfigureDTO.setUpdateAt(new Date());
        }
        return mt4ConfigureMapper.toDto(mt4ConfigureRepository.save(mt4ConfigureMapper.toEntity(mt4ConfigureDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateMt4ConfigureDTO(Mt4ConfigureDTO mt4ConfigureDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(mt4ConfigureDTO.getServer())){
            retMap.put("msg","服务器不能为空");
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            return retMap;
        }

        if(mt4ConfigureDTO.getUid() == null){
            retMap.put("msg","账户不能为空");
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            return retMap;
        }

        if(StringUtils.isBlank(mt4ConfigureDTO.getPassword())){
            retMap.put("msg","密码不能为空");
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            return retMap;
        }

        if(mt4Service.login(mt4ConfigureDTO) !=0){
            retMap.put("msg","连接失败");
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Mt4ConfigureDTO findOne() {
        return mt4ConfigureMapper.toDto(mt4ConfigureRepository.findOne(DevplatformConstants.MT4_CONFIGURE));
    }

    @Override
    public List<Mt4ConfigureDTO> findAll() {
        return mt4ConfigureMapper.toDto(mt4ConfigureRepository.findAll());
    }

    @Override
    public void delete(String id) {
        mt4ConfigureRepository.delete(id);
    }
}