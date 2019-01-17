package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.domain.HppAdvertisement;
import com.chengma.devplatform.domain.HppLive;
import com.chengma.devplatform.repository.BankInfoRepository;
import com.chengma.devplatform.repository.HppLiveRepository;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.HppLiveService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.HppAdvertisementDTO;
import com.chengma.devplatform.service.dto.HppLiveDTO;
import com.chengma.devplatform.service.mapper.BankInfoMapper;
import com.chengma.devplatform.service.mapper.HppLiveMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class HppLiveServiceImpl implements HppLiveService {

    private final HppLiveRepository hppLiveRepository;

    private final HppLiveMapper hppLiveMapper;

    @Autowired
    private PageCommon pageCommon;

    public HppLiveServiceImpl(HppLiveRepository hppLiveRepository, HppLiveMapper hppLiveMapper){
        this.hppLiveRepository=hppLiveRepository;
        this.hppLiveMapper=hppLiveMapper;
    }

    @Override
    public Page<HppLiveDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_hpp_live ");

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppLive.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppLiveDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppLiveDTO.class);
        return page;
    }

    @Override
    public HppLiveDTO save(HppLiveDTO hppLiveDTO) {
        return hppLiveMapper.toDto(hppLiveRepository.save(hppLiveMapper.toEntity(hppLiveDTO)));
    }

    @Override
    public HppLiveDTO createHppLiveDTO(HppLiveDTO hppLiveDTO) {
        //添加
        if(StringUtils.isBlank(hppLiveDTO.getId())){
            Date now = new Date();
            hppLiveDTO.setCreateTime(now);
            hppLiveDTO.setUpdateTime(now);
            if(hppLiveDTO.getSortNum() == null){
                hppLiveDTO.setSortNum(99);
            }
        }else{
            //修改
            HppLive hppLive = hppLiveRepository.findOne(hppLiveDTO.getId());
            hppLiveDTO.setCreateTime(hppLive.getCreateTime());
            hppLiveDTO.setUpdateTime(new Date());
        }

        return hppLiveMapper.toDto(hppLiveRepository.save(hppLiveMapper.toEntity(hppLiveDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateHppLiveDTO(HppLiveDTO hppLiveDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        if(StringUtils.isBlank(hppLiveDTO.getImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传图片");
            return retMap;
        }

        if(StringUtils.isBlank(hppLiveDTO.getName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入名称");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppLiveDTO findOne(String id) {
        return hppLiveMapper.toDto(hppLiveRepository.findOne(id));
    }

    @Override
    public List<HppLiveDTO> findAll() {
        return hppLiveMapper.toDto(hppLiveRepository.findAllByOrderBySortNumAsc());
    }

    @Override
    public void delete(String id) {
        hppLiveRepository.delete(id);
    }
}
