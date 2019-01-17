package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.WxOrderDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface WxOrderService {

    Page<WxOrderDTO> pageList(HashMap<String, Object> params);

    WxOrderDTO save(WxOrderDTO hppVideoDTO);

    WxOrderDTO createWxOrderDTO(WxOrderDTO hppVideoDTO);

    HashMap<String, Object> checkCreateWxOrderDTO(WxOrderDTO hppVideoDTO);

    WxOrderDTO findOne(String id);

    List<WxOrderDTO> findAll();

    void delete(String id);

    //根据bodyParentId 累计报名人数 与近30天报名人数
    WxOrderDTO countNum(String bodyParentId);
}

