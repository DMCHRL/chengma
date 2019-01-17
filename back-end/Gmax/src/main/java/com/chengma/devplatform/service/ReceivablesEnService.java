package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.ReceivablesEnDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dministrator on 2018/10/10.
 */
public interface ReceivablesEnService {

    Page<ReceivablesEnDTO> pageList(HashMap<String, Object> params);

    ReceivablesEnDTO save(ReceivablesEnDTO hppVideoDTO);

    ReceivablesEnDTO createReceivablesEnDTO(ReceivablesEnDTO receivablesEnDTO);

    HashMap<String, Object> checkCreateReceivablesEnDTO(ReceivablesEnDTO receivablesEnDTO);

    ReceivablesEnDTO findOne(String id);

    //ReceivablesEnDTO findByRandom();

    ReceivablesEnDTO findNew();

    List<ReceivablesEnDTO> findAll();

    //改變狀態
    void changeFlag(String id);

    void delete(String id);
}

