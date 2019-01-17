package com.chengma.devplatform.service;

import com.chengma.devplatform.domain.UsdtOrder;
import com.chengma.devplatform.service.dto.UsdtOrderDTO;

import java.util.HashMap;

/**
 * Service Interface for managing BindBank.
 */
public interface UsdtOrderService {

    HashMap<String,Object> createUsdtOrderDTO(UsdtOrderDTO usdtOrderDTO);

    HashMap<String,Object> checkUsdtOrderDTO(UsdtOrderDTO usdtOrderDTO);

    void notifyUrl(HashMap<String, Object> params);

    UsdtOrder findByOrderId(String orderId);

}
