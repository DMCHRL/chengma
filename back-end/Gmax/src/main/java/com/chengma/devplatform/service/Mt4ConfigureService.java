package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.Mt4ConfigureDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface Mt4ConfigureService {

    Page<Mt4ConfigureDTO> pageList(HashMap<String, Object> params);

    Mt4ConfigureDTO save(Mt4ConfigureDTO mt4ConfigureDTO);

    Mt4ConfigureDTO createMt4ConfigureDTO(Mt4ConfigureDTO mt4ConfigureDTO);

    HashMap<String, Object> checkCreateMt4ConfigureDTO(Mt4ConfigureDTO mt4ConfigureDTO);

    Mt4ConfigureDTO findOne();

    List<Mt4ConfigureDTO> findAll();

    void delete(String id);
}
