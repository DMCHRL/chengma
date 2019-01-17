package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbUserFeedbackDTO;
import org.springframework.data.domain.Page;

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
    TlbUserFeedbackDTO save(TlbUserFeedbackDTO tlbUserFeedbackDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<TlbUserFeedbackDTO> pageList(HashMap<String, Object> params);

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
    TlbUserFeedbackDTO findOne(String id);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

}
