package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.MobileUserRepository;
import com.chengma.devplatform.service.MobileUserService;
import com.chengma.devplatform.service.dto.MobileUserDTO;
import com.chengma.devplatform.service.mapper.MobileUserMapper;
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
public class MobileUserServiceImpl implements MobileUserService {

    private final MobileUserRepository mobileUserRepository;

    private final MobileUserMapper mobileUserMapper;

    @Autowired
    private PageCommon pageCommon;

    public MobileUserServiceImpl(MobileUserRepository mobileUserRepository, MobileUserMapper mobileUserMapper){
        this.mobileUserRepository=mobileUserRepository;
        this.mobileUserMapper=mobileUserMapper;
    }

    @Override
    public Page<MobileUserDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public MobileUserDTO save(MobileUserDTO mobileUserDTO) {
        return mobileUserMapper.toDto(mobileUserRepository.save(mobileUserMapper.toEntity(mobileUserDTO)));
    }

    @Override
    public MobileUserDTO createMobileUserDTO(MobileUserDTO mobileUserDTO) {
        //添加
        if(StringUtils.isBlank(mobileUserDTO.getId())){

        }else{
            //修改
        }

        return mobileUserMapper.toDto(mobileUserRepository.save(mobileUserMapper.toEntity(mobileUserDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateMobileUserDTO(MobileUserDTO mobileUserDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public MobileUserDTO findOne(String id) {
        return mobileUserMapper.toDto(mobileUserRepository.findOne(id));
    }

    @Override
    public List<MobileUserDTO> findAll() {
        return mobileUserMapper.toDto(mobileUserRepository.findAll());
    }

    @Override
    public void delete(String id) {
        mobileUserRepository.delete(id);
    }

    @Override
    public MobileUserDTO findByMobile(String mobile) {
        return mobileUserMapper.toDto(mobileUserRepository.findByMobileEquals(mobile));
    }
}
