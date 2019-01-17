package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.HppServerDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface HppServerService {

    Page<HppServerDTO> pageList(HashMap<String, Object> params);

    HppServerDTO save(HppServerDTO hppVideoDTO);

    HppServerDTO createHppServerDTO(HppServerDTO hppVideoDTO);

    HashMap<String, Object> checkCreateHppServerDTO(HppServerDTO hppVideoDTO);

    HppServerDTO findOne(String id);

    List<HppServerDTO> findAll();

    void delete(String id);
}

