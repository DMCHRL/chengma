package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.HppUserDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface HppUserService {

    /**
     * Save a sysComponent.
     *
     * @param hppUserDTO the entity to save
     * @return the persisted entity
     */
    HppUserDTO save(HppUserDTO hppUserDTO);

    HashMap<String, Object> checkSaveUser(HppUserDTO hppUserDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<HppUserDTO> pageList(HashMap<String, Object> params);

    List<HppUserDTO> findAll(HashMap<String, Object> params);

    List<HppUserDTO> queryComponentPage(Long formId, String visible);

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HppUserDTO findOne(String id);

    HppUserDTO findOneByUserId(String userId);

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

    List<HppUserDTO> findByMobile(String mobile);

}
