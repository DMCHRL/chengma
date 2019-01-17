package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.ExpectRiskProfitDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface ExpectRiskProfitService {

    Page<ExpectRiskProfitDTO> pageList(HashMap<String, Object> params);

    ExpectRiskProfitDTO save(ExpectRiskProfitDTO hppVideoDTO);

    ExpectRiskProfitDTO createExpectRiskProfitDTO(ExpectRiskProfitDTO hppVideoDTO);

    HashMap<String, Object> checkCreateExpectRiskProfitDTO(ExpectRiskProfitDTO hppVideoDTO);

    ExpectRiskProfitDTO findOne(String id);

    List<ExpectRiskProfitDTO> findAll();

    List<ExpectRiskProfitDTO> findByFundSignalId(String id);

    void delete(String id);

    void deleteByFundSignalId(String fundSignalId);
}


