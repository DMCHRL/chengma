package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.HppAdvertisementDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface HppAdvertisementService {

    HppAdvertisementDTO save(HppAdvertisementDTO hppAdvertisementDTO);

    void delete(String id);

    Page<HppAdvertisementDTO> pageList(HashMap<String, Object> params);

    HashMap<String,Object> checkHppAdvertisementDTO(HppAdvertisementDTO hppAdvertisementDTO);

    HppAdvertisementDTO findOne(String id);

    List<HppAdvertisementDTO> loadByHome();

    List<HppAdvertisementDTO> loadByVideo();

    List<HppAdvertisementDTO> loadBySinks();

    List<HppAdvertisementDTO> loadByStrategy();

    List<HppAdvertisementDTO> loadByLive();
}
