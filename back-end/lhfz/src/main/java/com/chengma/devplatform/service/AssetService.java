package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.AssetDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface AssetService {

    Page<AssetDTO> pageList(HashMap<String, Object> params);

    AssetDTO save(AssetDTO assetDTO);

    AssetDTO createAssetDTO(AssetDTO assetDTO);

    HashMap<String, Object> checkCreateAssetDTO(AssetDTO assetDTO);

    AssetDTO findOne(String id);

    AssetDTO findByUserId(String userId);

    List<AssetDTO> findAll();

    /*
    初始化
     */
    AssetDTO initAsset(String userId);

    HashMap<String, Object>  updateAsset(String userId,Double fund,String type);

    void delete(String id);
}



