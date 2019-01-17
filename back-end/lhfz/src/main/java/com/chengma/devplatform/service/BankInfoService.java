package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.BankInfoDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface BankInfoService {

    Page<BankInfoDTO> pageList(HashMap<String, Object> params);

    BankInfoDTO save(BankInfoDTO bankInfoDTO);

    BankInfoDTO createBankInfoDTO(BankInfoDTO bankInfoDTO);

    HashMap<String, Object> checkCreateBankInfoDTO(BankInfoDTO bankInfoDTO);

    BankInfoDTO findOne(String id);

    List<BankInfoDTO> findAll();
    
    void delete(String id);

    List<BankInfoDTO> loadMyBank();
}
