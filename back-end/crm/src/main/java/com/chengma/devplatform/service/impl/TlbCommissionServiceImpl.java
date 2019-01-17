package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbCommission;
import com.chengma.devplatform.repository.TlbCommissionRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.TlbCommissionMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class TlbCommissionServiceImpl implements TlbCommissionService {

    private final Logger log = LoggerFactory.getLogger(TlbCommissionServiceImpl.class);

    private final TlbCommissionRepository tlbCommissionRepository;
    private final TlbCommissionMapper tlbCommissionMapper;


    @Autowired
    private TlbTradeService tlbTradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TlbFundApplyService tlbFundApplyService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private TlbCommissionTraceService tlbCommissionTraceService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private MT4Service mt4Service;

    public TlbCommissionServiceImpl(TlbCommissionRepository tlbCommissionRepository, TlbCommissionMapper tlbCommissionMapper) {
        this.tlbCommissionRepository = tlbCommissionRepository;
        this.tlbCommissionMapper = tlbCommissionMapper;
    }

    /**
     * Save a tlbCommission.
     */
    @Override
    public TlbCommissionDTO save(TlbCommissionDTO tlbCommissionDTO) {
        HashMap<String, Object> response = new HashMap<>();
        /*if(tlbCommissionDTO.getId() == null ){
            tlbCommissionDTO.setEnableTrade("Y");
        }*/
        TlbCommission tlbCommission = tlbCommissionMapper.toEntity(tlbCommissionDTO);
        tlbCommission = tlbCommissionRepository.save(tlbCommission);
        return tlbCommissionMapper.toDto(tlbCommission);
    }

    /**
     * findAll
     */
    @Override
    @Transactional(readOnly = true)
    public List<TlbCommissionDTO> findAll(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String outCommissionNo = formParams.get("outCommissionNo") == null ? "" : formParams.get("outCommissionNo").toString();

        StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        if (StringUtils.isNotBlank(outCommissionNo)) {
            cond.append(" and c_out_trade_no like '%" + outCommissionNo + "%' ");
        }
        cond.append(" order by c_out_trade_no desc ");
        String orderBy = " order by c_out_trade_no desc ";
        Page<TlbCommissionDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TlbCommissionDTO.class);
        HashMap<String, Object> response = new HashMap<>();


        return null;
    }


    @Override
    public Page<TlbCommissionDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String login = formParams.get("login") == null ? "" : formParams.get("login").toString();
        String firstName = formParams.get("firstName") == null ? "" : formParams.get("firstName").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();


        StringBuilder column = new StringBuilder(" SELECT t.*, u.`login`, u.`first_name`, u.department ");

        StringBuilder cond = new StringBuilder(" FROM t_tlb_commission t,jhi_user u WHERE t.`c_user_id`=u.`id` ");

        if (StringUtils.isNotBlank(login)) {
            cond.append(" AND u.`login` LIKE '%"+login+"%' ");
        }
        if (StringUtils.isNotBlank(firstName)) {
            cond.append(" AND u.`first_name` LIKE '%"+firstName+"%' ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(TlbCommission.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TlbCommissionDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TlbCommissionDTO.class);
        return page;
    }


    /**
     * Get one tlbCommission by id.
     */
    @Override
    @Transactional(readOnly = true)
    public TlbCommissionDTO findOne(String id) {
        TlbCommission tlbCommission = tlbCommissionRepository.findOne(id);
        return tlbCommissionMapper.toDto(tlbCommission);
    }

    /**
     * findByCode
     */
    @Override
    @Transactional(readOnly = true)
    public TlbCommissionDTO findByCode(TlbCommissionDTO tlbCommissionDTO) {
    /*    StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        cond.append(" and c_out_trade_no = '" + tlbCommissionDTO.getOutCommissionNo() + "' ");

        List<TlbCommissionDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TlbCommissionDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;*/
        return null;
    }

    @Override
    public TlbCommissionDTO findByCommission(String account) {
        TlbAccountDTO tlbAccountDTO=tlbAccountService.findByAccount(account);
        TlbCommission tlbCommission = null;
        tlbCommission = tlbCommissionRepository.findByUserId(tlbAccountDTO.getUserId());
        return tlbCommissionMapper.toDto(tlbCommission);
    }

    @Override
    public TlbCommissionDTO updateCommissionMargin(String account, double lots) {

        double amount = lots * DevplatformConstants.PER_LOT_MONEY;

        TlbCommission tlbCommission = null;
        tlbCommission = tlbCommissionRepository.save(tlbCommission);
        return tlbCommissionMapper.toDto(tlbCommission);
    }

    @Override
    public TlbCommissionDTO updateCommissionBalance(String account, double amount) {
        TlbCommission tlbCommission = null;
        TlbAccountDTO tlbAccountDTO=tlbAccountService.findByAccount(account);
        tlbCommission = tlbCommissionRepository.findByUserId(tlbAccountDTO.getUserId());
        if(tlbCommission==null){
            return null;
        }
        double newBanlance =  tlbCommission.getBalance() - amount;
        newBanlance = new BigDecimal(newBanlance).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
        if(newBanlance<0){
            return null;
        }else{
            tlbCommission.setBalance(newBanlance);
            tlbCommission.setWithdraw(tlbCommission.getWithdraw()+amount);
        }
        tlbCommission = tlbCommissionRepository.save(tlbCommission);
        return tlbCommissionMapper.toDto(tlbCommission);
    }

    @Override
    public boolean checkCommissionAmount(String account, double amount) {
        TlbAccountDTO tlbAccountDTO = tlbAccountService.findByAccount(account);
        TlbCommissionDTO tlbCommission = tlbCommissionMapper.toDto(tlbCommissionRepository.findByUserId(tlbAccountDTO.getUserId()));

        //已经申请的出金
        List<TlbFundApplyDTO> list = tlbFundApplyService.findTlbFundApplyList(account, DevplatformConstants.FUND_APPLY_INNER, DevplatformConstants.FUND_APPLY_STATUS_APPLYING);
        double fundApplyAmount = 0L;
        if(!list.isEmpty()){
            fundApplyAmount =  list.stream().collect(Collectors.summingDouble(TlbFundApplyDTO::getAmount));
        }

        if(null == tlbCommission.getBalance()){
            return false;
        }
        if(tlbCommission.getBalance() - fundApplyAmount + amount < 0){
            return false;
        }
        return true;
    }

    @Override
    public HashMap<String, Object> checkInner(HashMap<String, Object> params) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(null == params.get("account") || "".equals(params.get("account"))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入套利宝帐号");
            return retMap;
        }
        String account = params.get("account").toString();
        if(null == (tlbAccountService.findByAccount(account))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入有效套利宝帐号");
            return retMap;
        }
        if(null == params.get("amount") || 0 == Double.valueOf(params.get("amount").toString())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入有效金额");
            return retMap;
        }
        //验证可用金
        double amount = Double.valueOf(params.get("amount").toString());
        if(!checkCommissionAmount(account, -amount)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "除已申请内转资金后，佣金余额不够用");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);

        return retMap;
    }

    @Override
    public TlbCommissionDTO withdrawals(HashMap<String, Object> params) {
        String account = params.get("account").toString();
        double amount = Double.valueOf(params.get("amount").toString());
        return updateCommissionBalance(account, -amount);
    }

    @Override
    public TlbCommissionDTO recharge(HashMap<String, Object> params) {
        String account = params.get("account").toString();
        double amount = Double.valueOf(params.get("amount").toString());
        return updateCommissionBalance(account, amount);
    }

    @Override
    public TlbCommissionDTO findOneByParam(HashMap<String, Object> params) {
        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_bind_bank where 1 = 1 ");

        if(null != params.get("banksn")){
            cond.append(" and c_banksn = '" + params.get("banksn") + "' ");
        }
        if(null != params.get("idcardsn")){
            cond.append(" and c_idcardsn = '" + params.get("idcardsn") + "' ");
        }
        if(null != params.get("mobile")){
            cond.append(" and c_mobile = '" + params.get("mobile") + "' ");
        }

        List<TlbCommissionDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TlbCommissionDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * delete
     */
    @Override
    public void delete(String id) {
        tlbCommissionRepository.delete(id);
    }

    @Override
    public TlbCommissionDTO findByUserId(String userId) {
        // TODO Auto-generated method stub
        TlbCommission tlbCommission=tlbCommissionRepository.findByUserId(userId);
        return tlbCommissionMapper.toDto(tlbCommission);
    }

    @Override
    public TlbCommissionDTO setConfiguration(TlbCommissionDTO tlbCommissiondto) {
        TlbCommission result=tlbCommissionRepository.findByUserId(tlbCommissiondto.getUserId());
        TlbCommissionDTO  resultDTO=tlbCommissionMapper.toDto(result);
        if(resultDTO==null){
            //首次设置
            resultDTO=new TlbCommissionDTO();
            resultDTO.setUserId(tlbCommissiondto.getUserId());
            resultDTO.setTotal(0.0);
            resultDTO.setBalance(0.0);
            resultDTO.setWithdraw(0.0);
            resultDTO.setCreateAt(new Date());
        }
        resultDTO.setLotAmount(tlbCommissiondto.getLotAmount());
        resultDTO.setLotPercent(tlbCommissiondto.getLotPercent());
        if(tlbCommissiondto.getLotAmount()==null){
            resultDTO.setLotAmount(0.0);
        }
        if(tlbCommissiondto.getLotPercent()==null){
            resultDTO.setLotPercent(0.0);
        }
        TlbCommission tlbCommission = tlbCommissionMapper.toEntity(resultDTO);
        result=tlbCommissionRepository.save(tlbCommission);
        return tlbCommissionMapper.toDto(tlbCommission);
    }


    public TlbCommissionDTO getConfiguration(String userId){
        TlbCommission result=tlbCommissionRepository.findByUserId(userId);
        TlbCommissionDTO  resultDTO=tlbCommissionMapper.toDto(result);
        return resultDTO;
    }
    @Override
    public void updateTotal(String accountId, double lots,Long orderNo) {
        TlbAccountDTO tlbAccountDTO=tlbAccountService.findByAccount(accountId);
        TlbCommission tlbCommission=null;
        TlbCommissionDTO tlbCommissiondto = null;
        TlbCommissionDTO pTlbCommissiondto = null;
        if(tlbAccountDTO!=null&&tlbAccountDTO.getUserId()!=null){
            UserDTO userDTO = userService.findOne(tlbAccountDTO.getUserId());
            if(userDTO==null)return;
            //公司自己客户
            if(EnumRole.COMPANY.value().equals(userDTO.getDepartment())){
                tlbCommission = tlbCommissionRepository.findByUserId(tlbAccountDTO.getUserId());
                tlbCommissiondto = tlbCommissionMapper.toDto(tlbCommission);
                if(tlbCommissiondto == null) return ;
                double money =  lots * tlbCommissiondto.getLotAmount();
                money = new BigDecimal(money).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
                double total = tlbCommissiondto.getTotal() + lots * tlbCommissiondto.getLotAmount();
                total = new BigDecimal(total).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
                double balance = tlbCommissiondto.getBalance()+ lots * tlbCommissiondto.getLotAmount();
                balance = new BigDecimal(balance).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
                tlbCommissiondto.setTotal(total);
                tlbCommissiondto.setBalance(balance);
                tlbCommission = tlbCommissionMapper.toEntity(tlbCommissiondto);
                tlbCommissionRepository.save(tlbCommission);

                //记录当前交易
                new BigDecimal(total).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
                createCommissionTrace(tlbCommissiondto.getId(),orderNo,money,tlbCommissiondto.getLotAmount());

                //代理自己客户
            }else if(EnumRole.PROXY.value().equals(userDTO.getDepartment())){
                //找到公司佣金配置
                pTlbCommissiondto = tlbCommissionMapper.toDto(tlbCommissionRepository.findByUserId(userDTO.getParentId()));
                if (pTlbCommissiondto == null) return;
                //找到自己佣金配置
                tlbCommissiondto = tlbCommissionMapper.toDto(tlbCommissionRepository.findByUserId(userDTO.getId()));
                //计算公司代理佣金
                calcCompanyProxy(pTlbCommissiondto, tlbCommissiondto, lots,orderNo);
                //普通客户
            }else if(EnumRole.USER.value().equals(userDTO.getDepartment())){
                //找到代理
                UserDTO parentUserDTO = userService.findOne(userDTO.getParentId());
                UserDTO proxyUserDTO = null;
                UserDTO companyUserDTO = null;
                TlbCommissionDTO companyCommissiondto = null;
                TlbCommissionDTO proxyCommissiondto = null;
                //如果代理不为null , 找到公司
                if( parentUserDTO != null ) {
                    //挂在代理下面
                    if (EnumRole.PROXY.value().equals(parentUserDTO.getDepartment())) {
                        companyUserDTO = userService.findOne(parentUserDTO.getParentId());

                        if (companyUserDTO == null) return;
                        companyCommissiondto = tlbCommissionMapper.toDto(tlbCommissionRepository.findByUserId(companyUserDTO.getId()));

                        if (companyCommissiondto == null) return;
                        proxyCommissiondto = tlbCommissionMapper.toDto(tlbCommissionRepository.findByUserId(parentUserDTO.getId()));

                        calcCompanyProxy(companyCommissiondto, proxyCommissiondto, lots,orderNo);
                        //挂在公司下面
                    } else if (EnumRole.COMPANY.value().equals(parentUserDTO.getDepartment())) {
                        {
                            companyCommissiondto = tlbCommissionMapper.toDto(tlbCommissionRepository.findByUserId(parentUserDTO.getId()));
                            if(companyCommissiondto == null) return;
                            calcCompanyProxy(companyCommissiondto, proxyCommissiondto, lots,orderNo);
                        }
                    }

                }


            }
        }
    }

    public void updateCommission(){
        List<TlbTradeDTO> tibTradeListDTO=tlbTradeService.findTlbTradesWithClosePosition();
        if(tibTradeListDTO==null)return;
        for(TlbTradeDTO tDTO:tibTradeListDTO){
            updateTotal(tDTO.getAccount(),tDTO.getLots(),tDTO.getOrderNo());
            tDTO.setCalcSum("Y");
            tlbTradeService.save(tDTO);
        }
    }

    private void calcCompanyProxy(TlbCommissionDTO companyCommissionDTO, TlbCommissionDTO proxyCommissionDTO, double lots,Long orderNo){
        if(proxyCommissionDTO != null) {
            double proxyAmount = lots * proxyCommissionDTO.getLotAmount();
            double companyAmount =  lots * (companyCommissionDTO.getLotAmount() - proxyCommissionDTO.getLotAmount());
            //更新代理佣金
            double total = proxyCommissionDTO.getTotal() + proxyAmount;
            total = new BigDecimal(total).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            double balance = proxyCommissionDTO.getBalance()+proxyAmount;
            balance = new BigDecimal(balance).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();

            proxyCommissionDTO.setTotal(total);
            proxyCommissionDTO.setBalance(balance);
            tlbCommissionRepository.save(tlbCommissionMapper.toEntity(proxyCommissionDTO));

            //记录当前交易
            proxyAmount = new BigDecimal(proxyAmount).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            createCommissionTrace(proxyCommissionDTO.getId(),orderNo,proxyAmount,proxyCommissionDTO.getLotAmount());

            //更新公司佣金
            total = companyCommissionDTO.getTotal() + companyAmount;
            total = new BigDecimal(total).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            balance = companyCommissionDTO.getBalance() + companyAmount;
            balance = new BigDecimal(balance).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            companyCommissionDTO.setTotal(total);
            companyCommissionDTO.setBalance(balance);
            tlbCommissionRepository.save(tlbCommissionMapper.toEntity(companyCommissionDTO));

            //记录当前交易
            companyAmount = new BigDecimal(companyAmount).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            createCommissionTrace(companyCommissionDTO.getId(),orderNo,companyAmount,companyCommissionDTO.getLotAmount());
        }else{
            //更新公司佣金
            double companyAmount =  lots * companyCommissionDTO.getLotAmount() ;
            double total = companyCommissionDTO.getTotal() + companyAmount;
            total = new BigDecimal(total).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            double balance = companyCommissionDTO.getBalance()+companyAmount;
            balance = new BigDecimal(balance).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            companyCommissionDTO.setTotal(total);
            companyCommissionDTO.setBalance(balance);
            tlbCommissionRepository.save(tlbCommissionMapper.toEntity(companyCommissionDTO));
            //记录当前交易
            companyAmount = new BigDecimal(companyAmount).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE, BigDecimal.ROUND_HALF_UP).doubleValue();
            createCommissionTrace(companyCommissionDTO.getId(),orderNo,companyAmount,companyCommissionDTO.getLotAmount() );
        }
    }

    private void createCommissionTrace(String commissionId,Long orderNo,Double Money,Double lotAmount){
        TlbCommissionTraceDTO tlbCommissionTraceDTO=new TlbCommissionTraceDTO();
        tlbCommissionTraceDTO.setCommissionId(commissionId);
        tlbCommissionTraceDTO.setOrderNo(orderNo);
        tlbCommissionTraceDTO.setMoney(Money);
        tlbCommissionTraceDTO.setLotAmount(lotAmount);
        tlbCommissionTraceDTO.setCreateAt(new Date());
        tlbCommissionTraceService.save(tlbCommissionTraceDTO);
    }
}
