package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.FundSignal;
import com.chengma.devplatform.repository.FundSignalDataRepository;
import com.chengma.devplatform.repository.FundSignalRepository;
import com.chengma.devplatform.service.FundSignalDataService;
import com.chengma.devplatform.service.FundSignalService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.ExpectRiskProfitDTO;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.dto.FundSignalDataDTO;
import com.chengma.devplatform.service.mapper.FundSignalDataMapper;
import com.chengma.devplatform.service.mapper.FundSignalMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
@Service
@Transactional
public class FundSignalDataServiceImpl implements FundSignalDataService {

    private final FundSignalDataRepository fundSignalDataRepository;

    private final FundSignalDataMapper fundSignalDataMapper;

    @Autowired
    private PageCommon pageCommon;

    public FundSignalDataServiceImpl(FundSignalDataRepository fundSignalDataRepository, FundSignalDataMapper fundSignalDataMapper){
        this.fundSignalDataRepository=fundSignalDataRepository;
        this.fundSignalDataMapper=fundSignalDataMapper;
    }

    @Override
    public Page<FundSignalDataDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public FundSignalDataDTO save(FundSignalDataDTO fundSignalDataDTO) {
        return fundSignalDataMapper.toDto(fundSignalDataRepository.save(fundSignalDataMapper.toEntity(fundSignalDataDTO)));
    }

    @Override
    public FundSignalDataDTO createFundSignalDataDTO(FundSignalDataDTO fundSignalDataDTO) {
        //添加
        if(StringUtils.isBlank(fundSignalDataDTO.getId())){

        }else{
            //修改
        }

        return fundSignalDataMapper.toDto(fundSignalDataRepository.save(fundSignalDataMapper.toEntity(fundSignalDataDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateFundSignalDataDTO(FundSignalDataDTO fundSignalDataDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public FundSignalDataDTO findOne(String id) {
        return fundSignalDataMapper.toDto(fundSignalDataRepository.findOne(id));
    }

    @Override
    public List<FundSignalDataDTO> findAll() {
        return fundSignalDataMapper.toDto(fundSignalDataRepository.findAll());
    }

    @Override
    public void delete(String id) {
        fundSignalDataRepository.delete(id);
    }

    @Override
    public FundSignalDataDTO findByFundSignal(String id) {
        return fundSignalDataMapper.toDto(fundSignalDataRepository.findByFundSignalIdEquals(id));
    }
}