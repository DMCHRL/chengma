package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbAccountDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing BindBank.
 */
public interface TlbAccountService {

    Page<TlbAccountDTO> pageList(HashMap<String, Object> params);

    TlbAccountDTO save(TlbAccountDTO hppVideoDTO);

    TlbAccountDTO createTlbAccountDTO(TlbAccountDTO hppVideoDTO);

    HashMap<String, Object> checkCreateTlbAccountDTO(TlbAccountDTO hppVideoDTO);

    TlbAccountDTO findOne(String id);

    String genAccountNo(String group);

    TlbAccountDTO findByAccount(String account);

    List<TlbAccountDTO> findAll();
    
    void delete(String id);
}
