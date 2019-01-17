package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbMtPriceDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbMtPriceService {

    /**
     * Save a sysComponent.
     *
     * @param tlbMtPriceDTO the entity to save
     * @return the persisted entity
     */
    TlbMtPriceDTO save(TlbMtPriceDTO tlbMtPriceDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<TlbMtPriceDTO> pageList(HashMap<String, Object> params);


    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TlbMtPriceDTO findOne(String id);


    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);


    TlbMtPriceDTO findBySymbol(String symbol);

    List<TlbMtPriceDTO> findAll();


}
