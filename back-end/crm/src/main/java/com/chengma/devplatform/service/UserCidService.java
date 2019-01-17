package com.chengma.devplatform.service;

import com.chengma.devplatform.domain.UserCid;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.dto.UserCidDTO;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing BindBank.
 */
public interface UserCidService {

    /**
     * Save
     */
    UserCidDTO save(UserCidDTO userCidDTO);

    HashMap<String,Object> checkSave(UserCidDTO userCidDTO);

    List<UserCidDTO> findAll();

}
