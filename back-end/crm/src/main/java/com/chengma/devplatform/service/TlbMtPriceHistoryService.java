package com.chengma.devplatform.service;

import com.chengma.devplatform.domain.TlbMtPriceHistory;
import com.chengma.devplatform.service.dto.TlbMtPriceDTO;
import com.chengma.devplatform.service.dto.TlbMtPriceHistoryDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbMtPriceHistoryService {

    List<TlbMtPriceHistoryDTO> findListByType(HashMap<String,Object> params);

}
