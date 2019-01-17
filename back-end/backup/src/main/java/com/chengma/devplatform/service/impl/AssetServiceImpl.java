package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.AllotRuleRepository;
import com.chengma.devplatform.repository.AssetRepository;
import com.chengma.devplatform.service.AllotRuleService;
import com.chengma.devplatform.service.AssetService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.AssetDTO;
import com.chengma.devplatform.service.mapper.AllotRuleMapper;
import com.chengma.devplatform.service.mapper.AssetMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
@Service
@Transactional
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    private final AssetMapper assetMapper;

    @Autowired
    private PageCommon pageCommon;

    public AssetServiceImpl(AssetRepository assetRepository, AssetMapper assetMapper){
        this.assetRepository=assetRepository;
        this.assetMapper=assetMapper;
    }
    
    @Override
    public Page<AssetDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public AssetDTO save(AssetDTO assetDTO) {
        return assetMapper.toDto(assetRepository.save(assetMapper.toEntity(assetDTO)));
    }

    @Override
    public AssetDTO createAssetDTO(AssetDTO assetDTO) {
        //添加
        if(StringUtils.isBlank(assetDTO.getId())){
            
        }else{
            //修改
        }
        
        return assetMapper.toDto(assetRepository.save(assetMapper.toEntity(assetDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateAssetDTO(AssetDTO assetDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public AssetDTO findOne(String id) {
        return assetMapper.toDto(assetRepository.findOne(id));
    }

    @Override
    public List<AssetDTO> findAll() {
        return assetMapper.toDto(assetRepository.findAll());
    }

    @Override
    public void delete(String id) {
        assetRepository.delete(id);
    }

    @Override
    public AssetDTO findByUserId(String userId) {
        return assetMapper.toDto(assetRepository.findByUserIdEquals(userId));
    }

    @Override
    public AssetDTO initAsset(String userId) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setBalance(DevplatformConstants.USER_ASSET_INIT);
        assetDTO.setFundProfit(DevplatformConstants.USER_ASSET_INIT);
        assetDTO.setNetProfit(DevplatformConstants.USER_ASSET_INIT);
        assetDTO.setUsed(DevplatformConstants.USER_ASSET_INIT);
        assetDTO.setTotal(DevplatformConstants.USER_ASSET_INIT);
        assetDTO.setUserId(userId);
        assetDTO.setCreateAt(new Date());
        assetDTO.setUpdateAt(new Date());
        return save(assetDTO);
    }
}