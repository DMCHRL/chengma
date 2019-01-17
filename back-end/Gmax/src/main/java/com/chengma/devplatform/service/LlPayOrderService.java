package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.LlPayOrderDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface LlPayOrderService {

    Page<LlPayOrderDTO> pageList(HashMap<String, Object> params);

    LlPayOrderDTO save(LlPayOrderDTO llPayOrderDTO);

    HashMap<String,Object> createLlPayOrderDTO(LlPayOrderDTO llPayOrderDTO);

    HashMap<String, Object> checkCreateLlPayOrderDTO(LlPayOrderDTO llPayOrderDTO);

    LlPayOrderDTO findOne(String id);

    List<LlPayOrderDTO> findAll();

    void delete(String id);

    void updateTranInfo(String noOrder);
}
