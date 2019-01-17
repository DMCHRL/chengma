package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbAccountControlDTO;

import java.util.List;

/**
 * Service Interface for managing BindBank.
 */
public interface TlbAccountControlService {

    /**
     * Save
     */
    TlbAccountControlDTO save(TlbAccountControlDTO tlbAccountControlDTO);


    /**
     * Get
     */
    List<TlbAccountControlDTO> findByUserId(String userId);

    /**
     * findByCode
     */

    /**
     * delete
     */
    void delete(String id);


}
