package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.AllotRuleRepository;
import com.chengma.devplatform.repository.FundSignalRepository;
import com.chengma.devplatform.service.AllotRuleService;
import com.chengma.devplatform.service.FundSignalService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.mapper.AllotRuleMapper;
import com.chengma.devplatform.service.mapper.FundSignalMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
@Service
@Transactional
public class AllotRuleServiceImpl implements AllotRuleService {

    private final AllotRuleRepository allotRuleRepository;

    private final AllotRuleMapper allotRuleMapper;

    @Autowired
    private PageCommon pageCommon;

    public AllotRuleServiceImpl(AllotRuleRepository allotRuleRepository, AllotRuleMapper allotRuleMapper){
        this.allotRuleRepository=allotRuleRepository;
        this.allotRuleMapper=allotRuleMapper;
    }
    
    @Override
    public Page<AllotRuleDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public AllotRuleDTO save(AllotRuleDTO allotRuleDTO) {
        return allotRuleMapper.toDto(allotRuleRepository.save(allotRuleMapper.toEntity(allotRuleDTO)));
    }

    @Override
    public AllotRuleDTO createAllotRuleDTO(AllotRuleDTO allotRuleDTO) {
        //添加
        if(StringUtils.isBlank(allotRuleDTO.getId())){
            
        }else{
            //修改
        }
        
        return allotRuleMapper.toDto(allotRuleRepository.save(allotRuleMapper.toEntity(allotRuleDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateAllotRuleDTO(AllotRuleDTO allotRuleDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public AllotRuleDTO findOne(String id) {
        return allotRuleMapper.toDto(allotRuleRepository.findOne(id));
    }

    @Override
    public List<AllotRuleDTO> findAll() {
        return allotRuleMapper.toDto(allotRuleRepository.findAll());
    }

    @Override
    public void delete(String id) {
        allotRuleRepository.delete(id);
    }

    @Override
    public List<AllotRuleDTO> findByFundSignalId(String id) {
        return allotRuleMapper.toDto(allotRuleRepository.findByFundSignalIdEquals(id));
    }

    @Override
    public void deleteByFundSignalId(String fundSignalId) {
        allotRuleRepository.deleteByFundSignalIdEquals(fundSignalId);
    }
}