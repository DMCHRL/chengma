package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.AdvertisementDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface AdvertisementService {

    AdvertisementDTO save(AdvertisementDTO advertisementDTO);

    void delete(String id);

    Page<AdvertisementDTO> pageList(HashMap<String, Object> params);

    HashMap<String,Object> checkAdvertisementDTO(AdvertisementDTO advertisementDTO);

    AdvertisementDTO findOne(String id);

    List<AdvertisementDTO> loadByType(String type);

}
