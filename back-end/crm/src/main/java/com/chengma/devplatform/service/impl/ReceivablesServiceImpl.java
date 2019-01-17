package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.Receivables;
import com.chengma.devplatform.repository.ReceivablesRepository;
import com.chengma.devplatform.service.ReceivablesService;
import com.chengma.devplatform.service.dto.PaymentImgDTO;
import com.chengma.devplatform.service.dto.ReceivablesDTO;
import com.chengma.devplatform.service.mapper.ReceivablesMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2018/10/10.
 */
@Service
@Transactional
public class ReceivablesServiceImpl implements ReceivablesService {

    private final ReceivablesRepository receivablesRepository;

    private final ReceivablesMapper receivablesMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    public ReceivablesServiceImpl(ReceivablesRepository receivablesRepository, ReceivablesMapper receivablesMapper){
        this.receivablesRepository=receivablesRepository;
        this.receivablesMapper=receivablesMapper;
    }

    @Override
    public Page<ReceivablesDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public ReceivablesDTO save(ReceivablesDTO receivablesDTO) {
        return receivablesMapper.toDto(receivablesRepository.save(receivablesMapper.toEntity(receivablesDTO)));
    }

    @Override
    public ReceivablesDTO createReceivablesDTO(ReceivablesDTO receivablesDTO) {
        //添加
        if(StringUtils.isBlank(receivablesDTO.getId())){
            Date now = new Date();
            receivablesDTO.setCreateTime(now);
            receivablesDTO.setUpdateTime(now);
            receivablesDTO.setFlag("N"); //下線
        }else{
            //修改
            Receivables receivables = receivablesRepository.findOne(receivablesDTO.getId());
            receivablesDTO.setCreateTime(receivables.getCreateTime());
            receivablesDTO.setUpdateTime(new Date());
            receivablesDTO.setFlag(receivables.getFlag());
        }

        return receivablesMapper.toDto(receivablesRepository.save(receivablesMapper.toEntity(receivablesDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateReceivablesDTO(ReceivablesDTO receivablesDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(receivablesDTO.getBank())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入銀行名稱");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesDTO.getName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入收款姓名");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesDTO.getBankNum())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入銀行卡號");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesDTO.getRemark())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入備註號");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesDTO.getCompany())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入公司名稱");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public ReceivablesDTO findOne(String id) {
        return receivablesMapper.toDto(receivablesRepository.findOne(id));
    }

    @Override
    public List<ReceivablesDTO> findAll() {
        return receivablesMapper.toDto(receivablesRepository.findAll());
    }

    @Override
    public void delete(String id) {
        receivablesRepository.delete(id);
    }

   /* @Override
    public ReceivablesDTO findByRandom() {
        List<ReceivablesDTO> list = this.findAll();
        Random random = new Random();

        if(list == null || list.size() == 0)return null;
        ReceivablesDTO  receivablesDTO = list.get(random.nextInt(list.size()));
        //計算隨機
        //TODO
        String num =System.currentTimeMillis()+"";
        receivablesDTO.setRemark(receivablesDTO.getRemark() + num.substring(num.length()-5, num.length()));
        return receivablesDTO;
    }*/

    @Override
    public ReceivablesDTO findNew() {
        ReceivablesDTO receivablesDTO = receivablesMapper.toDto(receivablesRepository.findByFlagEquals("Y"));
        if(receivablesDTO == null) return null;
        String num =System.currentTimeMillis()+"";
        receivablesDTO.setRemark(receivablesDTO.getRemark() + num.substring(num.length()-5, num.length()));
        return receivablesDTO;
    }

    @Override
    public void changeFlag(String id) {
        List<Receivables> list = receivablesRepository.findAll();
        Receivables receivables =null;
        for(Receivables p:list){
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
            receivables=p;
            receivablesRepository.save(receivables);
        }
    }
}
