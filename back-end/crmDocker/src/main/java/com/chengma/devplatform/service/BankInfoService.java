package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.BankInfoDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;

/**
 * Service Interface for managing SysComponent.
 */
public interface BankInfoService {

    /**
     * Save a sysComponent.
     *
     * @param bankInfoDTO the entity to save
     * @return the persisted entity
     */
    BankInfoDTO save(BankInfoDTO bankInfoDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<BankInfoDTO> pageList(HashMap<String, Object> params);


    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BankInfoDTO findOne(String id);

    BankInfoDTO findByUserId(String userId);


    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);



}
