package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbCommissionTraceDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;

/**
 * Service Interface for managing BindBank.
 */
public interface TlbCommissionTraceService {

    /**
     * Save
     */
    TlbCommissionTraceDTO save(TlbCommissionTraceDTO tlbCommissionTraceDTO);



    /**
     *
     * @param params
     * @return
     */
    Page<TlbCommissionTraceDTO> pageList(HashMap<String, Object> params);

}
