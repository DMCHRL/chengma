package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.DigitalCurrencyDTO;
import com.chengma.devplatform.service.dto.ReceivablesDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface DigitalCurrencyService {

    Page<DigitalCurrencyDTO> pageList(HashMap<String, Object> params);

    DigitalCurrencyDTO save(DigitalCurrencyDTO digitalCurrencyDTO);

    DigitalCurrencyDTO createDigitalCurrencyDTO(DigitalCurrencyDTO digitalCurrencyDTO);

    HashMap<String, Object> checkCreateDigitalCurrencyDTO(DigitalCurrencyDTO digitalCurrencyDTO);

    DigitalCurrencyDTO findOne(String id);

    List<DigitalCurrencyDTO> findAll();

    DigitalCurrencyDTO findNew();

    void delete(String id);

    void changeFlag(String id);
}
