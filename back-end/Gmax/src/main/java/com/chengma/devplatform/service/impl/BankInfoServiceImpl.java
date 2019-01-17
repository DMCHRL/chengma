package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.repository.BankInfoRepository;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.mapper.BankInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class BankInfoServiceImpl implements BankInfoService {

    private final Logger log = LoggerFactory.getLogger(BankInfoServiceImpl.class);

    private final BankInfoRepository bankInfoRepository;

    private final BankInfoMapper bankInfoMapper;



    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private DBService dbService;

    public BankInfoServiceImpl(BankInfoRepository bankInfoRepository, BankInfoMapper bankInfoMapper) {
        this.bankInfoRepository = bankInfoRepository;
        this.bankInfoMapper = bankInfoMapper;
    }

    @Override
    public BankInfoDTO save(BankInfoDTO bankInfoDTO) {
        BankInfo bankInfo =bankInfoMapper.toEntity(bankInfoDTO);
        BankInfo bankInfo1 =bankInfoRepository.save(bankInfo);
        return bankInfoMapper.toDto(bankInfo1);
    }

    @Override
    public BankInfoDTO findByUserId(String userId) {
        return bankInfoMapper.toDto(bankInfoRepository.findByUserId(userId));
    }

    @Override
    public Page<BankInfoDTO> pageList(HashMap<String, Object> params) {
       return null;
    }

    @Override
    public BankInfoDTO findOne(String id) {
        return bankInfoMapper.toDto(bankInfoRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        bankInfoRepository.delete(id);
    }

}
