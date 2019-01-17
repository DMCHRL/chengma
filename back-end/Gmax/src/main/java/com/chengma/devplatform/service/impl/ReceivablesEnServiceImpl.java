package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.ReceivablesEn;
import com.chengma.devplatform.repository.ReceivablesEnRepository;
import com.chengma.devplatform.service.ReceivablesEnService;
import com.chengma.devplatform.service.dto.ReceivablesEnDTO;
import com.chengma.devplatform.service.mapper.ReceivablesEnMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/10/10.
 */
@Service
@Transactional
public class ReceivablesEnServiceImpl implements ReceivablesEnService {

    private final ReceivablesEnRepository receivablesEnRepository;

    private final ReceivablesEnMapper receivablesEnMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    public ReceivablesEnServiceImpl(ReceivablesEnRepository receivablesEnRepository, ReceivablesEnMapper receivablesEnMapper){
        this.receivablesEnRepository=receivablesEnRepository;
        this.receivablesEnMapper=receivablesEnMapper;
    }

    @Override
    public Page<ReceivablesEnDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public ReceivablesEnDTO save(ReceivablesEnDTO receivablesEnDTO) {
        return receivablesEnMapper.toDto(receivablesEnRepository.save(receivablesEnMapper.toEntity(receivablesEnDTO)));
    }

    @Override
    public ReceivablesEnDTO createReceivablesEnDTO(ReceivablesEnDTO receivablesEnDTO) {
        //添加
        if(StringUtils.isBlank(receivablesEnDTO.getId())){
            Date now = new Date();
            receivablesEnDTO.setCreateTime(now);
            receivablesEnDTO.setUpdateTime(now);
            receivablesEnDTO.setFlag("N"); //下線
        }else{
            //修改
            ReceivablesEn receivablesEn = receivablesEnRepository.findOne(receivablesEnDTO.getId());
            receivablesEnDTO.setCreateTime(receivablesEn.getCreateTime());
            receivablesEnDTO.setUpdateTime(new Date());
            receivablesEnDTO.setFlag(receivablesEn.getFlag());
        }

        return receivablesEnMapper.toDto(receivablesEnRepository.save(receivablesEnMapper.toEntity(receivablesEnDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateReceivablesEnDTO(ReceivablesEnDTO receivablesEnDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(receivablesEnDTO.getUserName()) ){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入客戶名稱");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesEnDTO.getAccountType()) || StringUtils.isBlank(receivablesEnDTO.getAccountTypeEn())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入賬號類別");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesEnDTO.getBankName()) || StringUtils.isBlank(receivablesEnDTO.getBankNameEn())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入銀行名稱");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesEnDTO.getAddress()) || StringUtils.isBlank(receivablesEnDTO.getAddressEn())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入銀行地址");
            return retMap;
        }
        
        if(StringUtils.isBlank(receivablesEnDTO.getBankCode())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入編碼");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesEnDTO.getSwiftCode())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入匯款編碼");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesEnDTO.getCompany())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入公司名稱");
            return retMap;
        }

        if(StringUtils.isBlank(receivablesEnDTO.getAccount())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入銀行卡號");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public ReceivablesEnDTO findOne(String id) {
        return receivablesEnMapper.toDto(receivablesEnRepository.findOne(id));
    }

    @Override
    public List<ReceivablesEnDTO> findAll() {
        return receivablesEnMapper.toDto(receivablesEnRepository.findAll());
    }

    @Override
    public void delete(String id) {
        receivablesEnRepository.delete(id);
    }

   /* @Override
    public ReceivablesEnDTO findByRandom() {
        List<ReceivablesEnDTO> list = this.findAll();
        Random random = new Random();

        if(list == null || list.size() == 0)return null;
        ReceivablesEnDTO  receivablesEnDTO = list.get(random.nextInt(list.size()));
        //計算隨機
        //TODO
        String num =System.currentTimeMillis()+"";
        receivablesEnDTO.setRemark(receivablesEnDTO.getRemark() + num.substring(num.length()-5, num.length()));
        return receivablesEnDTO;
    }*/

    @Override
    public ReceivablesEnDTO findNew() {
        ReceivablesEnDTO receivablesEnDTO = receivablesEnMapper.toDto(receivablesEnRepository.findByFlagEquals("Y"));
        if(receivablesEnDTO == null) return null;
        String num =System.currentTimeMillis()+"";
        receivablesEnDTO.setSwiftCode(receivablesEnDTO.getSwiftCode() + num.substring(num.length()-5, num.length()));
        return receivablesEnDTO;
    }

    @Override
    public void changeFlag(String id) {
        List<ReceivablesEn> list = receivablesEnRepository.findAll();
        ReceivablesEn receivablesEn =null;
        for(ReceivablesEn p:list){
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
            receivablesEn=p;
            receivablesEnRepository.save(receivablesEn);
        }
    }
}
