package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.view.AccountComboListDTO;
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

    HashMap<String, Object> editPassword(HashMap<String, Object> params);
    
    void delete(String id);

    List<AccountComboListDTO> accountComboList(String account);
}
