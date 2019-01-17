package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface AllotRuleService {

    Page<AllotRuleDTO> pageList(HashMap<String, Object> params);

    AllotRuleDTO save(AllotRuleDTO hppVideoDTO);

    AllotRuleDTO createAllotRuleDTO(AllotRuleDTO hppVideoDTO);

    HashMap<String, Object> checkCreateAllotRuleDTO(AllotRuleDTO hppVideoDTO);

    AllotRuleDTO findOne(String id);

    List<AllotRuleDTO> findAll();

    List<AllotRuleDTO> findByFundSignalId(String id);

    void delete(String id);

    void deleteByFundSignalId(String fundSignalId);
}

