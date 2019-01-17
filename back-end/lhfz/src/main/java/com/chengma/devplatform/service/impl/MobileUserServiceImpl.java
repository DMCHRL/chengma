package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.MobileUser;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.MobileUserRepository;
import com.chengma.devplatform.service.MobileUserService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.dto.MobileUserDTO;
import com.chengma.devplatform.service.mapper.MobileUserMapper;
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
public class MobileUserServiceImpl implements MobileUserService {

    private final MobileUserRepository mobileUserRepository;

    private final MobileUserMapper mobileUserMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    public MobileUserServiceImpl(MobileUserRepository mobileUserRepository, MobileUserMapper mobileUserMapper){
        this.mobileUserRepository=mobileUserRepository;
        this.mobileUserMapper=mobileUserMapper;
    }

    @Override
    public Page<MobileUserDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" select m.*,u.department ");
        StringBuilder cond = new StringBuilder(" from t_lhfz_mobile_user m,jhi_user u where m.c_user_id = u.id ");

        Page<MobileUserDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, MobileUserDTO.class);
        return page;
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
            MobileUser mobileUser = mobileUserRepository.findOne(mobileUserDTO.getId());
            mobileUserDTO.setUpdateAt(new Date());
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

    @Override
    public MobileUserDTO initMobileUser(MobileUserDTO mobileUserDTO) {
        Date now=new Date();
        mobileUserDTO.setMobile(mobileUserDTO.getMobile());
        mobileUserDTO.setCreateAt(now);
        mobileUserDTO.setUpdateAt(now);
        mobileUserDTO.setHeadImg(DevplatformConstants.DEFAULT_HEAD_IMG);

        return  save(mobileUserDTO);
    }

    @Override
    public  HashMap<String, Object> updateUserInfo(MobileUserDTO mobileUserDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();


        User user = userService.getUserWithAuthorities();
        MobileUser mobileUser = mobileUserRepository.findByUserIdEquals(user.getId());
        if(null == mobileUser) return null;
        mobileUser.setUpdateAt(new Date());
        if(StringUtils.isNotBlank(mobileUserDTO.getPassword())){
            //首次设置密码
            if(StringUtils.isBlank(mobileUser.getPassword()) || mobileUser.getPassword().equals(mobileUserDTO.getOldPassword()) ){
                mobileUser.setPassword(mobileUserDTO.getPassword());
            }else{
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "原密码错误");
                return  retMap;
            }
        }

        if(StringUtils.isNotBlank(mobileUserDTO.getEmail())){
            mobileUser.setEmail(mobileUserDTO.getEmail());
        }

        if(StringUtils.isNotBlank(mobileUserDTO.getCid())){
            mobileUser.setCid(mobileUserDTO.getCid());
        }

        if(StringUtils.isNotBlank(mobileUserDTO.getHeadImg())){
            mobileUser.setHeadImg(mobileUserDTO.getHeadImg());
        }

        if(StringUtils.isNotBlank(mobileUserDTO.getName())){
            mobileUser.setName(mobileUserDTO.getName());
            user.setFirstName(mobileUserDTO.getName());
            userService.save(user);
        }

        if(DevplatformConstants.MOBILE_USER_UNAUTHORIZED.equals(mobileUser.getStatus()) && StringUtils.isNotBlank(mobileUserDTO.getIdPositive()) && StringUtils.isNotBlank(mobileUserDTO.getIdNegative())){
            mobileUser.setStatus(DevplatformConstants.MOBILE_USER_APPLYING);
            mobileUser.setIdPositive(mobileUserDTO.getIdPositive());
            mobileUser.setIdNegative(mobileUserDTO.getIdNegative());
        }
        mobileUserRepository.save(mobileUser);
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public MobileUserDTO findByUserId(String userId) {
        return mobileUserMapper.toDto(mobileUserRepository.findByUserIdEquals(userId));
    }
}
