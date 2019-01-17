package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.AllotRuleRepository;
import com.chengma.devplatform.repository.ExpectRiskProfitRepository;
import com.chengma.devplatform.service.ExpectRiskProfitService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.ExpectRiskProfitDTO;
import com.chengma.devplatform.service.mapper.AllotRuleMapper;
import com.chengma.devplatform.service.mapper.ExpectRiskProfitMapper;
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
public class ExpectRiskProfitServiceImpl implements ExpectRiskProfitService {

    private final ExpectRiskProfitRepository expectRiskProfitRepository;

    private final ExpectRiskProfitMapper expectRiskProfitMapper;

    @Autowired
    private PageCommon pageCommon;

    public ExpectRiskProfitServiceImpl(ExpectRiskProfitRepository expectRiskProfitRepository, ExpectRiskProfitMapper expectRiskProfitMapper){
        this.expectRiskProfitRepository=expectRiskProfitRepository;
        this.expectRiskProfitMapper=expectRiskProfitMapper;
    }

    @Override
    public Page<ExpectRiskProfitDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public ExpectRiskProfitDTO save(ExpectRiskProfitDTO expectRiskProfitDTO) {
        return expectRiskProfitMapper.toDto(expectRiskProfitRepository.save(expectRiskProfitMapper.toEntity(expectRiskProfitDTO)));
    }

    @Override
    public ExpectRiskProfitDTO createExpectRiskProfitDTO(ExpectRiskProfitDTO expectRiskProfitDTO) {
        //添加
        if(StringUtils.isBlank(expectRiskProfitDTO.getId())){

        }else{
            //修改
        }

        return expectRiskProfitMapper.toDto(expectRiskProfitRepository.save(expectRiskProfitMapper.toEntity(expectRiskProfitDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateExpectRiskProfitDTO(ExpectRiskProfitDTO expectRiskProfitDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public ExpectRiskProfitDTO findOne(String id) {
        return expectRiskProfitMapper.toDto(expectRiskProfitRepository.findOne(id));
    }

    @Override
    public List<ExpectRiskProfitDTO> findAll() {
        return expectRiskProfitMapper.toDto(expectRiskProfitRepository.findAll());
    }

    @Override
    public void delete(String id) {
        expectRiskProfitRepository.delete(id);
    }

    @Override
    public List<ExpectRiskProfitDTO> findByFundSignalId(String id) {
        return expectRiskProfitMapper.toDto(expectRiskProfitRepository.findByFundSignalIdEquals(id));
    }

    @Override
    public void deleteByFundSignalId(String fundSignalId) {
        expectRiskProfitRepository.deleteByFundSignalIdEquals(fundSignalId);
    }
}