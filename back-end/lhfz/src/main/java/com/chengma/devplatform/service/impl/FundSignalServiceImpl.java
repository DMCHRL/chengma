package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.common.util.StringUtil;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.FundSignalRepository;
import com.chengma.devplatform.repository.MobileUserRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.dto.view.FindListFundSignalDTO;
import com.chengma.devplatform.service.dto.view.PreFundJoinDTO;
import com.chengma.devplatform.service.mapper.FundSignalMapper;
import com.chengma.devplatform.service.mapper.MobileUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2018/11/26.
 */
@Service
@Transactional
public class FundSignalServiceImpl implements FundSignalService {

    private final FundSignalRepository fundSignalRepository;

    private final FundSignalMapper fundSignalMapper;

    @Autowired
    private AllotRuleService allotRuleService;

    @Autowired
    private ExpectRiskProfitService expectRiskProfitService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private FundRecordService fundRecordService;

    @Autowired
    private FundSignalDataService fundSignalDataService;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    public FundSignalServiceImpl(FundSignalRepository fundSignalRepository, FundSignalMapper fundSignalMapper){
        this.fundSignalRepository=fundSignalRepository;
        this.fundSignalMapper=fundSignalMapper;
    }

    @Override
    public Page<FundSignalDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" select s.*, ifnull(o.login, '') AS originator_name, ifnull(m.login, '') manager_name ");
        StringBuilder cond = new StringBuilder(" from t_fund_signal s ");
        cond.append(" LEFT JOIN jhi_user o ON s.c_originator_id = o.id LEFT JOIN jhi_user m ON s.c_manager_id = m.id ");
        Page<FundSignalDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, FundSignalDTO.class);
        return page;
    }

    @Override
    public FundSignalDTO save(FundSignalDTO fundSignalDTO) {
        return fundSignalMapper.toDto(fundSignalRepository.save(fundSignalMapper.toEntity(fundSignalDTO)));
    }

    @Override
    public FundSignalDTO createFundSignalDTO(FundSignalDTO fundSignalDTO) {
        //添加
        Date now = new Date();
        if(StringUtils.isBlank(fundSignalDTO.getId())){
            fundSignalDTO.setCreateAt(now);
            fundSignalDTO.setUpdateAt(now);
            fundSignalDTO.setStartAt(now);
            fundSignalDTO.setStatus(DevplatformConstants.FUND_STATUS_APPLYING);
        }else{
            //修改
        }
        FundSignal fundSignal = fundSignalRepository.save(fundSignalMapper.toEntity(fundSignalDTO));

        //保存预期风险及收益
        for(ExpectRiskProfitDTO expectRiskProfitDTO:fundSignalDTO.getExpectRiskProfitDTOList() ){
            expectRiskProfitDTO.setFundSignalId(fundSignal.getId());
            expectRiskProfitDTO.setCreateAt(now);
            expectRiskProfitDTO.setUpdateAt(now);
            expectRiskProfitService.save(expectRiskProfitDTO);
        }

        //保存分配规则
        for(AllotRuleDTO allotRuleDTO:fundSignalDTO.getAllotRuleDTOList() ){
            allotRuleDTO.setFundSignalId(fundSignal.getId());
            allotRuleDTO.setCreateAt(now);
            allotRuleDTO.setUpdateAt(now);
            allotRuleService.save(allotRuleDTO);
        }
        return fundSignalMapper.toDto(fundSignal);
    }

    @Override
    public HashMap<String, Object> checkCreateFundSignalDTO(FundSignalDTO fundSignalDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(fundSignalDTO.getAccount())){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","账号不能为空");
            return  retMap;
        }
        if(StringUtils.isBlank(fundSignalDTO.getName())){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","名称不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getYearProfit1() == null ||fundSignalDTO.getYearProfit2() == null ){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","预期收益不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getRisk() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","合伙人最大风险不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getTargetFund() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","目标金额不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getMinFund() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","最小入伙金额不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getMinLevel() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","最小合伙人等级不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getOperationAt() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","操作时间不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getEndAt() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","结束操作时间不能为空");
            return  retMap;
        }
        if(StringUtils.isBlank(fundSignalDTO.getSuccessFlag()) ||StringUtils.isBlank(fundSignalDTO.getPartnerFlag()) ){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","请选择...");
            return  retMap;
        }
        if(StringUtils.isBlank(fundSignalDTO.getOriginatorId()) ||StringUtils.isBlank(fundSignalDTO.getManagerId()) ){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","请选择发起人或资金管理人");
            return  retMap;
        }
        if(StringUtils.isBlank(fundSignalDTO.getRemark()) ){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","产品描述");
            return  retMap;
        }
        if(StringUtils.isBlank(fundSignalDTO.getPlatform()) || StringUtils.isBlank(fundSignalDTO.getStrategy()) || StringUtils.isBlank(fundSignalDTO.getDirection()) || StringUtils.isBlank(fundSignalDTO.getRange()) ){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","投资风格信息不全");
            return  retMap;
        }
        if(fundSignalDTO.getExpectRiskProfitDTOList() == null || fundSignalDTO.getExpectRiskProfitDTOList() .size() ==0 ){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","风险及预期收益不能为空");
            return  retMap;
        }
        if(fundSignalDTO.getAllotRuleDTOList() == null || fundSignalDTO.getAllotRuleDTOList() .size() ==0 ){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","收益分配规则不能为空");
            return  retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public FundSignalDTO findOne(String id) {
        return fundSignalMapper.toDto(fundSignalRepository.findOne(id));
    }

    @Override
    public List<FundSignalDTO> findAll() {
        return fundSignalMapper.toDto(fundSignalRepository.findAll());
    }

    @Override
    public void delete(String id) {
        allotRuleService.deleteByFundSignalId(id);
        expectRiskProfitService.deleteByFundSignalId(id);
        fundSignalRepository.delete(id);
    }

    @Override
    public List<FindListFundSignalDTO> findList(HashMap<String, Object> params) {
        String orderByColumn = params.get("orderByColumn") == null ? "" : params.get("orderByColumn").toString();
        String sort = params.get("sort") == null ? "" : params.get("sort").toString();
        String status = params.get("status") == null ? "" : params.get("status").toString();
        String ids = params.get("ids") == null ? "" : params.get("ids").toString();
        StringBuffer sb = new StringBuffer("select s.c_id,s.c_name,s.i_year_profit1 as year_profit1 ,s.i_year_profit2 as year_profit2 ,i_risk risk");
        sb.append(", DATEDIFF( s.d_end_at,s.d_operation_at) as cycle_time "); //操作周期
        sb.append(",DATEDIFF( s.d_operation_at,NOW()) as remaining_time ");  //募集剩余时间
        sb.append(", (select ROUND(ifnull(sum(r.i_money),0)*100/s.i_target_fund,4) from t_fund_record r where r.c_fund_signal_id = s.c_id) as progress"); //进度
        sb.append(",(SELECT CASE WHEN s.i_target_fund - ifnull(sum(r.i_money), 0) >= 0 THEN s.i_target_fund - ifnull(sum(r.i_money), 0) ELSE 0 END FROM t_fund_record r WHERE r.c_fund_signal_id = s.c_id ) AS can_join");   //还可入伙金额
        sb.append(" from t_fund_signal s where 1=1");

        if(StringUtils.isNotBlank(ids) ){
            sb.append(" and s.c_id in ("+ids+") ");
        }

        if(StringUtils.isNotBlank(status) ){
            sb.append(" and s.c_status='"+status+" ' ");
        }


        if(StringUtils.isNotBlank(orderByColumn) && StringUtils.isNotBlank(sort)){
            orderByColumn= StringUtil.humpToLine(orderByColumn);
            sb.append(" order by "+orderByColumn+" "+sort+" ");
        }

        return baseDao.findListBySql(sb.toString(),FindListFundSignalDTO.class);
    }

    @Override
    public List<FindListFundSignalDTO> loadMyFund(HashMap<String, Object> params) {
        User user = userService.getUserWithAuthorities();
        List<FundRecordDTO> fundRecordDTOList = fundRecordService.findMyFund(user.getId());
        if(null == fundRecordDTOList || fundRecordDTOList.size() == 0){
            return null;
        }
        StringBuffer sb = new StringBuffer();
       /* HashSet<String> set = new HashSet<>();*/
        for(FundRecordDTO fundRecordDTO : fundRecordDTOList){
            sb.append(" ,'"+fundRecordDTO.getFundSignalId()+"' ");
          /*  set.add(" ,'"+fundRecordDTO.getFundSignalId()+"'");*/
        }
       /* String[] str = set.toArray(new String[set.size()]);
        for(String ids : str ){
            sb.append(ids);
        }*/
        params.put("ids",sb.toString().replaceFirst(",",""));

        return findList(params);
    }

    @Override
    public FundSignalDTO findOneDetail(String id) {
        String sql = "select *,(select ROUND(ifnull(sum(r.i_money),0)*100/s.i_target_fund,4) from t_fund_record r where r.c_fund_signal_id = s.c_id) as progress,(select count(*) from t_fund_record r where r.c_fund_signal_id = s.c_id) as num,( SELECT CASE WHEN s.i_target_fund - ifnull(sum(r.i_money), 0) >= 0 THEN s.i_target_fund - ifnull(sum(r.i_money), 0) ELSE 0 END FROM t_fund_record r WHERE r.c_fund_signal_id = s.c_id ) AS can_join from t_fund_signal s where s.c_id='"+id+"'";
        FundSignalDTO fundSignalDTO = baseDao.findListBySql(sql,FundSignalDTO.class).get(0);
        fundSignalDTO.setAllotRuleDTOList(allotRuleService.findByFundSignalId(id));
        fundSignalDTO.setExpectRiskProfitDTOList(expectRiskProfitService.findByFundSignalId(id));
        return fundSignalDTO;
    }

    @Override
    public void operation(String id) {
        if(null == fundSignalDataService.findByFundSignal(id)){
            FundSignalDataDTO fundSignalDataDTO = new FundSignalDataDTO();
            FundSignal fundSignal = fundSignalRepository.findOne(id);
            fundSignal.setStatus(DevplatformConstants.FUND_STATUS_OPERATION);
            fundSignalRepository.save(fundSignal);

            fundSignalDataDTO.setFundSignalId(id);
            fundSignalDataDTO.setOriginatorId(fundSignal.getOriginatorId());
            fundSignalDataDTO.setManagerId(fundSignal.getManagerId());
            Double total = fundRecordService.sumFundByFundSignalId(id);
            fundSignalDataDTO.setPreFund(total);
            fundSignalDataDTO.setStatus(DevplatformConstants.FUND_STATUS_OPERATION);
            fundSignalDataDTO.setProfit(total);
            Date now = new Date();
            fundSignalDataDTO.setCreateAt(now);
            fundSignalDataDTO.setUpdateAt(now);
            fundSignalDataService.save(fundSignalDataDTO);
        }
    }

    @Override
    public void finish(String id) {

    }

    @Override
    public PreFundJoinDTO preFundJoin(String id) {
        StringBuffer sb = new StringBuffer(" SELECT s.c_id, s.c_name");
        sb.append(",( SELECT CASE WHEN s.i_target_fund - ifnull(sum(r.i_money), 0) >= 0 THEN s.i_target_fund - ifnull(sum(r.i_money), 0) ELSE 0 END FROM t_fund_record r WHERE r.c_fund_signal_id = s.c_id ) AS can_join"); //剩余可入伙
        sb.append(",( SELECT ifnull(a.i_balance,0) FROM t_asset a WHERE a.c_user_id = '"+userService.getUserWithAuthorities().getId()+"' ) AS balance");  //用户余额
        sb.append(" FROM t_fund_signal s WHERE s.c_id = '"+id+"'");
        return baseDao.findListBySql(sb.toString(),PreFundJoinDTO.class).get(0);
    }
}
