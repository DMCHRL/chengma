package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.dto.FundSignalDataDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface FundSignalDataService {

    Page<FundSignalDataDTO> pageList(HashMap<String, Object> params);

    FundSignalDataDTO save(FundSignalDataDTO hppVideoDTO);

    FundSignalDataDTO createFundSignalDataDTO(FundSignalDataDTO hppVideoDTO);

    HashMap<String, Object> checkCreateFundSignalDataDTO(FundSignalDataDTO hppVideoDTO);

    FundSignalDataDTO findOne(String id);

    FundSignalDataDTO findByFundSignal(String id);

    List<FundSignalDataDTO> findAll();
    
    void delete(String id);
}

