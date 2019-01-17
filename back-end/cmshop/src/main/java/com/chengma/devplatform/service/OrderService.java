package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.OrderDTO;
import com.chengma.devplatform.service.dto.UsdtOrderDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface OrderService {

    Page<OrderDTO> pageList(HashMap<String, Object> params);

    OrderDTO save(OrderDTO llPayOrderDTO);

    HashMap<String,Object> createOrderDTO(OrderDTO llPayOrderDTO);

    HashMap<String,Object> createUsdtOrderDTO(UsdtOrderDTO usdtOrderDTO);

    HashMap<String, Object> checkCreateOrderDTO(OrderDTO llPayOrderDTO);

    OrderDTO findOne(String id);

    List<OrderDTO> loadMyOrder();

    List<OrderDTO> findAll();

    void delete(String id);

    void updateTranInfo(String noOrder);
}
