package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.FundRecord;
import com.chengma.devplatform.domain.FundSignal;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.AllotRuleRepository;
import com.chengma.devplatform.repository.FundRecordRepository;
import com.chengma.devplatform.service.AllotRuleService;
import com.chengma.devplatform.service.FundRecordService;
import com.chengma.devplatform.service.FundSignalService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.FundRecordDTO;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.mapper.AllotRuleMapper;
import com.chengma.devplatform.service.mapper.FundRecordMapper;
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
public class FundRecordServiceImpl implements FundRecordService {

    private final FundRecordRepository fundRecordRepository;

    private final FundRecordMapper fundRecordMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private FundSignalService fundSignalService;


    public FundRecordServiceImpl(FundRecordRepository fundRecordRepository, FundRecordMapper fundRecordMapper){
        this.fundRecordRepository=fundRecordRepository;
        this.fundRecordMapper=fundRecordMapper;
    }
    
    @Override
    public Page<FundRecordDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public FundRecordDTO save(FundRecordDTO fundRecordDTO) {
        return fundRecordMapper.toDto(fundRecordRepository.save(fundRecordMapper.toEntity(fundRecordDTO)));
    }

    @Override
    public FundRecordDTO createFundRecordDTO(FundRecordDTO fundRecordDTO) {
        //添加
        if(StringUtils.isBlank(fundRecordDTO.getId())){
            User user = userService.getUserWithAuthorities();
            fundRecordDTO.setCreateAt(new Date());
            fundRecordDTO.setUserId(user.getId());
            fundRecordDTO.setUserName(user.getFirstName());
        }else{
            //修改
        }
        
        return fundRecordMapper.toDto(fundRecordRepository.save(fundRecordMapper.toEntity(fundRecordDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateFundRecordDTO(FundRecordDTO fundRecordDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(fundRecordDTO.getFundSignalId() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择信号");
            return  retMap;
        }
        FundSignalDTO fundSignal = fundSignalService.findOne(fundRecordDTO.getFundSignalId());

        if(fundRecordDTO.getMoney() == null || fundRecordDTO.getMoney() < fundSignal.getMinFund()){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入有效的金额");
            return  retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public FundRecordDTO findOne(String id) {
        return fundRecordMapper.toDto(fundRecordRepository.findOne(id));
    }

    @Override
    public List<FundRecordDTO> findAll() {
        return fundRecordMapper.toDto(fundRecordRepository.findAll());
    }

    @Override
    public void delete(String id) {
        fundRecordRepository.delete(id);
    }

    @Override
    public Double sumFundByFundSignalId(String id) {
        String sql = "select ifnull(sum(r.i_money),0) as total from t_fund_record r where r.c_fund_signal_id='"+id+"'";
        return baseDao.findListBySql(sql,FundRecordDTO.class).get(0).getTotal();
    }

    @Override
    public List<FundRecordDTO> findMyFund(String userId) {
        return fundRecordMapper.toDto(fundRecordRepository.findByUserIdEquals(userId));
    }

    @Override
    public HashMap<String, Object> findListByFundSignalId(String fundSignalId) {
        HashMap<String, Object>  response = new HashMap<>();

        List<FundRecordDTO> fundRecordDTOList = fundRecordMapper.toDto(fundRecordRepository.findByFundSignalIdEquals(fundSignalId));
        String sql ="select ifnull(sum(i_money),0) as total,ifnull(count(*),0) as num from t_fund_record where c_fund_signal_id='"+fundSignalId+"'";

        FundRecordDTO fundRecordDTO = baseDao.findListBySql(sql,FundRecordDTO.class).get(0);
        response.put("list",fundRecordDTOList);
        response.put("total",fundRecordDTO.getTotal());
        response.put("num",fundRecordDTO.getNum());
        return response;
    }
}