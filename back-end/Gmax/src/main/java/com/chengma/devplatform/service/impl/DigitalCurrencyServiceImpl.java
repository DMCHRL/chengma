package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.DigitalCurrency;
import com.chengma.devplatform.repository.DigitalCurrencyRepository;
import com.chengma.devplatform.service.DigitalCurrencyService;
import com.chengma.devplatform.service.dto.DigitalCurrencyDTO;
import com.chengma.devplatform.service.mapper.DigitalCurrencyMapper;
import org.apache.commons.lang3.StringUtils;
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
public class DigitalCurrencyServiceImpl implements DigitalCurrencyService {

    private final DigitalCurrencyRepository digitalCurrencyRepository;

    private final DigitalCurrencyMapper digitalCurrencyMapper;

    @Autowired
    private PageCommon pageCommon;

    public DigitalCurrencyServiceImpl(DigitalCurrencyRepository digitalCurrencyRepository, DigitalCurrencyMapper digitalCurrencyMapper){
        this.digitalCurrencyRepository=digitalCurrencyRepository;
        this.digitalCurrencyMapper=digitalCurrencyMapper;
    }

    @Override
    public Page<DigitalCurrencyDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" ");
        StringBuilder cond = new StringBuilder(" ");

        Page<DigitalCurrencyDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, DigitalCurrencyDTO.class);
        return page;
    }

    @Override
    public DigitalCurrencyDTO save(DigitalCurrencyDTO digitalCurrencyDTO) {
        return digitalCurrencyMapper.toDto(digitalCurrencyRepository.save(digitalCurrencyMapper.toEntity(digitalCurrencyDTO)));
    }

    @Override
    public DigitalCurrencyDTO createDigitalCurrencyDTO(DigitalCurrencyDTO digitalCurrencyDTO) {
        //添加
        if(StringUtils.isBlank(digitalCurrencyDTO.getId())){
            Date now = new Date();
            digitalCurrencyDTO.setCreateAt(now);
            digitalCurrencyDTO.setUpdateAt(now);
            digitalCurrencyDTO.setFlag("N"); //下線
        }else{
            //修改
            DigitalCurrency digitalCurrency = digitalCurrencyRepository.findOne(digitalCurrencyDTO.getId());
            digitalCurrencyDTO.setCreateAt(digitalCurrency.getCreateAt());
            digitalCurrencyDTO.setUpdateAt(new Date());
            digitalCurrencyDTO.setFlag(digitalCurrency.getFlag());
        }
        return digitalCurrencyMapper.toDto(digitalCurrencyRepository.save(digitalCurrencyMapper.toEntity(digitalCurrencyDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateDigitalCurrencyDTO(DigitalCurrencyDTO digitalCurrencyDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public DigitalCurrencyDTO findOne(String id) {
        return digitalCurrencyMapper.toDto(digitalCurrencyRepository.findOne(id));
    }

    @Override
    public List<DigitalCurrencyDTO> findAll() {
        return digitalCurrencyMapper.toDto(digitalCurrencyRepository.findAll());
    }

    @Override
    public void delete(String id) {
        digitalCurrencyRepository.delete(id);
    }

    @Override
    public void changeFlag(String id) {
        List<DigitalCurrency> list = digitalCurrencyRepository.findAll();
        DigitalCurrency digitalCurrency =null;
        for(DigitalCurrency p:list){
            if(p.getId().equals(id)){
                String flag=p.getFlag();
                if(flag.equals("N")) {
                    p.setFlag("Y");
                }else{
                    p.setFlag("N");
                }
            }else{
                p.setFlag("N");
            }
            digitalCurrency=p;
            digitalCurrencyRepository.save(digitalCurrency);
        }
    }

    @Override
    public DigitalCurrencyDTO findNew() {
        return digitalCurrencyMapper.toDto(digitalCurrencyRepository.findByFlagEquals("Y"));
    }
}
