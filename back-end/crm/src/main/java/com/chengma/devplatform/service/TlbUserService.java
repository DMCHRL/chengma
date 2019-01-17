package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbUserDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbUserService {

    /**
     * Save a sysComponent.
     *
     * @param tlbUserDTO the entity to save
     * @return the persisted entity
     */
    TlbUserDTO save(TlbUserDTO tlbUserDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<TlbUserDTO> pageList(HashMap<String, Object> params);

    List<TlbUserDTO> findAll(HashMap<String, Object> params);

    List<TlbUserDTO> queryComponentPage(Long formId, String visible);

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TlbUserDTO findOne(String id);

    TlbUserDTO findOneByUserId(String userId);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    /**
     *
     * @param id
     * @param params
     * @return
     */
    HashMap<String, Object> approved(String id, HashMap<String, Object> params);

    /**
     *
     * @param id
     * @return
     */
    HashMap<String, Object> checkDeleteUser(String id);

    /**
     *
     */
    TlbAccountDTO createAccount(String password);

}
