package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.AllotRuleRepository;
import com.chengma.devplatform.repository.FundRecordRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.AllotRuleMapper;
import com.chengma.devplatform.service.mapper.FundRecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private MobileUserService mobileUserService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetDetailService assetDetailService;

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
        Double fund = fundRecordDTO.getMoney();
        FundSignalDTO fundSignalDTO = fundSignalService.findOne(fundRecordDTO.getFundSignalId());

        String userId = userService.getUserWithAuthorities().getId();
        MobileUserDTO mobileUserDTO = mobileUserService.findByUserId(userId);
        if(null == mobileUserDTO){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录...");
            return  retMap;
        }

        if(fundRecordDTO.getFundSignalId() == null ||fundSignalDTO == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择信号");
            return  retMap;
        }

        if(!DevplatformConstants.MOBILE_USER_AUTHORIZED.equals(mobileUserDTO.getStatus())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "入伙前请先完成认证");
            return  retMap;
        }

        if(mobileUserDTO.getLevel() < fundSignalDTO.getMinLevel()){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "合伙段位低于要求段位");
            return  retMap;
        }

        if(fund == null || fund < fundSignalDTO.getMinFund()){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "金额低于最小募集金额");
            return  retMap;
        }

        //更新资金
        retMap = assetService.updateAsset(userId,-fund,DevplatformConstants.USER_ASSET_FUND_JOIN);
        if(!ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            return retMap;
        }

        //资金明细
        AssetDetailDTO assetDetailDTO = new AssetDetailDTO();
        assetDetailDTO.setType(DevplatformConstants.USER_ASSET_FUND_JOIN);
        assetDetailDTO.setMoney(-fund);
        assetDetailService.createAssetDetailDTO(assetDetailDTO);


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