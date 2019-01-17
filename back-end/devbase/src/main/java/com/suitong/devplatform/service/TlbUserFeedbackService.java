package com.suitong.devplatform.service;

import com.suitong.devplatform.service.dto.TlbUserDTO;
import com.suitong.devplatform.service.dto.TlbUserFeedbackDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbUserFeedbackService {

    /**
     * Save a sysComponent.
     *
     * @param tlbUserFeedbackDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(TlbUserFeedbackDTO tlbUserFeedbackDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TlbUserFeedbackDTO> pageList(Pageable pageable);

    /**
     *
     * @param params
     * @return
     */
    List<TlbUserFeedbackDTO> findAll(HashMap<String, Object> params);
    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TlbUserFeedbackDTO findOne(Long id);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
