package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.ReceivablesDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dministrator on 2018/10/10.
 */
public interface ReceivablesService {

    Page<ReceivablesDTO> pageList(HashMap<String, Object> params);

    ReceivablesDTO save(ReceivablesDTO hppVideoDTO);

    ReceivablesDTO createReceivablesDTO(ReceivablesDTO hppVideoDTO);

    HashMap<String, Object> checkCreateReceivablesDTO(ReceivablesDTO hppVideoDTO);

    ReceivablesDTO findOne(String id);

    //ReceivablesDTO findByRandom();

    ReceivablesDTO findNew();

    List<ReceivablesDTO> findAll();

    //改變狀態
    void changeFlag(String id);

    void delete(String id);
}

