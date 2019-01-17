package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.AssetDTO;
import com.chengma.devplatform.service.dto.AssetDetailDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface AssetDetailService {

    Page<AssetDetailDTO> pageList(HashMap<String, Object> params);

    AssetDetailDTO save(AssetDetailDTO hppVideoDTO);

    AssetDetailDTO createAssetDetailDTO(AssetDetailDTO hppVideoDTO);

    HashMap<String, Object> checkCreateAssetDetailDTO(AssetDetailDTO hppVideoDTO);

    AssetDetailDTO findOne(String id);

    List<AssetDetailDTO> findAll();
    
    void delete(String id);
}


