package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.BankInfoRepository;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.mapper.BankInfoMapper;
import org.apache.commons.lang3.StringUtils;
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
public class BankInfoServiceImpl implements BankInfoService {

    private final BankInfoRepository bankInfoRepository;

    private final BankInfoMapper bankInfoMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    public BankInfoServiceImpl(BankInfoRepository bankInfoRepository, BankInfoMapper bankInfoMapper){
        this.bankInfoRepository=bankInfoRepository;
        this.bankInfoMapper=bankInfoMapper;
    }

    @Override
    public Page<BankInfoDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" ");
        StringBuilder cond = new StringBuilder(" ");

        Page<BankInfoDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, BankInfoDTO.class);
        return page;
    }

    @Override
    public BankInfoDTO save(BankInfoDTO bankInfoDTO) {
        return bankInfoMapper.toDto(bankInfoRepository.save(bankInfoMapper.toEntity(bankInfoDTO)));
    }

    @Override
    public BankInfoDTO createBankInfoDTO(BankInfoDTO bankInfoDTO) {
        //添加
        if(StringUtils.isBlank(bankInfoDTO.getId())){
            User user = userService.getUserWithAuthorities();
            bankInfoDTO.setUserId(user.getId());
            Date now = new Date();
            bankInfoDTO.setCreateAt(now);
            bankInfoDTO.setUpdateAt(now);
        }else{
            //修改
        }

        return bankInfoMapper.toDto(bankInfoRepository.save(bankInfoMapper.toEntity(bankInfoDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateBankInfoDTO(BankInfoDTO bankInfoDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        User user = userService.getUserWithAuthorities();
        if(null == user || user.getId() ==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录");
            return  retMap;
        }

        if(StringUtils.isBlank(bankInfoDTO.getBank())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "开户银行不能为空");
            return  retMap;
        }

        if(StringUtils.isBlank(bankInfoDTO.getSubBank())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "银行分行不能为空");
            return  retMap;
        }

        if(StringUtils.isBlank(bankInfoDTO.getBankNum())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "银行卡号不能为空");
            return  retMap;
        }

        if(StringUtils.isBlank(bankInfoDTO.getLocation())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "银行所在地不能为空");
            return  retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public BankInfoDTO findOne(String id) {
        return bankInfoMapper.toDto(bankInfoRepository.findOne(id));
    }

    @Override
    public List<BankInfoDTO> findAll() {
        return bankInfoMapper.toDto(bankInfoRepository.findAll());
    }

    @Override
    public void delete(String id) {
        bankInfoRepository.delete(id);
    }

    @Override
    public List<BankInfoDTO> loadMyBank() {
        return bankInfoMapper.toDto(bankInfoRepository.findByUserId(userService.getUserWithAuthorities().getId()));
    }
}