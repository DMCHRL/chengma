package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.AssetDetailRepository;
import com.chengma.devplatform.repository.AssetRepository;
import com.chengma.devplatform.service.AssetDetailService;
import com.chengma.devplatform.service.AssetService;
import com.chengma.devplatform.service.dto.AssetDTO;
import com.chengma.devplatform.service.dto.AssetDetailDTO;
import com.chengma.devplatform.service.mapper.AssetDetailMapper;
import com.chengma.devplatform.service.mapper.AssetMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
@Service
@Transactional
public class AssetDetailServiceImpl implements AssetDetailService {

    private final AssetDetailRepository assetDetailRepository;

    private final AssetDetailMapper assetDetailMapper;

    @Autowired
    private PageCommon pageCommon;

    public AssetDetailServiceImpl(AssetDetailRepository assetDetailRepository, AssetDetailMapper assetDetailMapper){
        this.assetDetailRepository=assetDetailRepository;
        this.assetDetailMapper=assetDetailMapper;
    }
    
    @Override
    public Page<AssetDetailDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public AssetDetailDTO save(AssetDetailDTO assetDetailDTO) {
        return assetDetailMapper.toDto(assetDetailRepository.save(assetDetailMapper.toEntity(assetDetailDTO)));
    }

    @Override
    public AssetDetailDTO createAssetDetailDTO(AssetDetailDTO assetDetailDTO) {
        //添加
        if(StringUtils.isBlank(assetDetailDTO.getId())){
            
        }else{
            //修改
        }
        
        return assetDetailMapper.toDto(assetDetailRepository.save(assetDetailMapper.toEntity(assetDetailDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateAssetDetailDTO(AssetDetailDTO assetDetailDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public AssetDetailDTO findOne(String id) {
        return assetDetailMapper.toDto(assetDetailRepository.findOne(id));
    }

    @Override
    public List<AssetDetailDTO> findAll() {
        return assetDetailMapper.toDto(assetDetailRepository.findAll());
    }

    @Override
    public void delete(String id) {
        assetDetailRepository.delete(id);
    }
}