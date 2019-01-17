package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.domain.UserCid;
import com.chengma.devplatform.repository.BindBankRepository;
import com.chengma.devplatform.repository.UserCidRepository;
import com.chengma.devplatform.repository.UserRepository;
import com.chengma.devplatform.service.UserCidService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.dto.UserCidDTO;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.service.mapper.BindBankMapper;
import com.chengma.devplatform.service.mapper.UserCidMapper;
import com.chengma.devplatform.service.mapper.UserMapper;
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
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class UserCidServiceImpl implements UserCidService {

    private final UserCidRepository userCidRepository;

    private final UserCidMapper userCidMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PageCommon pageCommon;

    public UserCidServiceImpl(UserCidRepository userCidRepository, UserCidMapper userCidMapper){
        this.userCidRepository=userCidRepository;
        this.userCidMapper=userCidMapper;
    }


    @Override
    public UserCidDTO save(UserCidDTO userCidDTO) {
        User user = userService.getUserWithAuthorities();
        UserCid userCid = userCidRepository.findByUserIdEquals(user.getId());
        if(userCid != null){
            userCidDTO.setId(userCid.getId());
            userCidDTO.setUserId(userCid.getUserId());
            userCidDTO.setMobile(userCid.getMobile());
            userCidDTO.setUpdateTime(new Date());
        }else{
            userCidDTO.setUserId(user.getId());
            userCidDTO.setMobile(user.getMobile());
            userCidDTO.setUpdateTime(new Date());
        }
        return userCidMapper.toDto(userCidRepository.save(userCidMapper.toEntity(userCidDTO)));
    }

    @Override
    public HashMap<String, Object> checkSave(UserCidDTO userCidDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        if(StringUtils.isBlank(userCidDTO.getCid())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "cid不能為空");
            return retMap;
        }
        User user = userService.getUserWithAuthorities();
        if(user == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請登錄...");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public List<UserCidDTO> findAll() {
        return userCidMapper.toDto(userCidRepository.findAll());
    }


}