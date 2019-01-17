package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.MobileUserDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface MobileUserService {

    Page<MobileUserDTO> pageList(HashMap<String, Object> params);

    MobileUserDTO save(MobileUserDTO hppVideoDTO);

    MobileUserDTO createMobileUserDTO(MobileUserDTO hppVideoDTO);

    HashMap<String, Object> checkCreateMobileUserDTO(MobileUserDTO hppVideoDTO);

    MobileUserDTO findOne(String id);

    MobileUserDTO findByMobile(String mobile);

    MobileUserDTO initMobileUser(MobileUserDTO mobileUserDTO);

    HashMap<String, Object> updateUserInfo(MobileUserDTO mobileUserDTO);

    List<MobileUserDTO> findAll();

    MobileUserDTO findByUserId(String userId);

    void delete(String id);
}

