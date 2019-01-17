package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbFundApply;
import com.chengma.devplatform.repository.TlbFundApplyRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbFundApplyDTO;
import com.chengma.devplatform.service.mapper.TlbFundApplyMapper;
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
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class TlbFundApplyServiceImpl implements TlbFundApplyService {

    private final Logger log = LoggerFactory.getLogger(TlbFundApplyServiceImpl.class);

    private final TlbFundApplyRepository tlbFundApplyRepository;

    private final TlbFundApplyMapper tlbFundApplyMapper;


    @Autowired
    private UserService userService;

    @Autowired
    private TlbCommissionService tlbCommissionService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;

    @Autowired
    private MailService mailService;

    public TlbFundApplyServiceImpl(TlbFundApplyRepository tlbFundApplyRepository, TlbFundApplyMapper tlbFundApplyMapper) {
        this.tlbFundApplyRepository = tlbFundApplyRepository;
        this.tlbFundApplyMapper = tlbFundApplyMapper;
    }

    /**
     * Save a tlbFundApply.
     */
    @Override
    public TlbFundApplyDTO save(TlbFundApplyDTO tlbFundApplyDTO) {
        ResponseResult response = new ResponseResult();
        TlbFundApply tlbFundApply = tlbFundApplyMapper.toEntity(tlbFundApplyDTO);
        tlbFundApply = tlbFundApplyRepository.save(tlbFundApply);
        return tlbFundApplyMapper.toDto(tlbFundApply);
    }

    /**
     * findAll
     */
    @Override
    @Transactional(readOnly = true)
    public List<TlbFundApplyDTO> findAll(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String fundType = formParams.get("fundType") == null ? "" : formParams.get("fundType").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();

        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_tlb_fund_apply where 1 = 1 ");

        if (StringUtils.isNotBlank(account)) {
            cond.append(" and c_out_trade_no like '%" + account + "%' ");
        }
        cond.append(" order by c_out_trade_no desc ");
        String orderBy = " order by c_id desc ";
        return baseDao.findListBySql(column.toString() + cond.toString() + orderBy.toString(), TlbFundApplyDTO.class);
       /* Page<TlbFundApplyDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TlbFundApplyDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("list", page.getContent());
        response.put("page_number", page_number);
        response.put("page_size", page_size);

        return page.getContent();*/
    }

    @Override
    public Page<TlbFundApplyDTO> pageList(HashMap<String, Object> params) {

        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String fundType = formParams.get("fundType") == null ? "" : formParams.get("fundType").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String username = formParams.get("username") == null ? "" : formParams.get("username").toString();
        String approvedname = formParams.get("approvedname") == null ? "" : formParams.get("approvedname").toString();
        String bCreateAt = formParams.get("bCreateAt") == null ? "" : formParams.get("bCreateAt").toString();
        String eCreateAt = formParams.get("eCreateAt") == null ? "" : formParams.get("eCreateAt").toString();
        String bApprovedAt = formParams.get("bApprovedAt") == null ? "" : formParams.get("bApprovedAt").toString();
        String eApprovedAt = formParams.get("eApprovedAt") == null ? "" : formParams.get("eApprovedAt").toString();

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select t.*, b.first_name as username, ifnull(b2.first_name, '') as approvedname ");

        StringBuilder cond = new StringBuilder("  from t_tlb_fund_apply t join jhi_user b on t.c_user_id = b.id left join jhi_user b2 on b2.id = t.c_approved_id where 1 = 1 ");

        if (StringUtils.isNotBlank(fundType)) {
            cond.append(" and t.c_fund_type = '" + fundType + "' ");
        }

        if (StringUtils.isNotBlank(status)) {
            cond.append(" and t.c_status = '" + status + "' ");
        }

        if (StringUtils.isNotBlank(account)) {
            cond.append(" and t.c_account like '%" + account + "%' ");
        }
        if (StringUtils.isNotBlank(username)) {
            cond.append(" and b.first_name like '%" + username + "%' ");
        }
        if (StringUtils.isNotBlank(approvedname)) {
            cond.append(" and b2.first_name like '%" + approvedname + "%' ");
        }
        if (StringUtils.isNotBlank(bCreateAt)) {
            cond.append(" and TO_DAYS(t.d_create_at) >= TO_DAYS('"+bCreateAt+"') ");
        }
        if (StringUtils.isNotBlank(eCreateAt)) {
            cond.append(" and TO_DAYS(t.d_create_at) <= TO_DAYS('"+eCreateAt+"') ");
        }
        if (StringUtils.isNotBlank(bApprovedAt)) {
            cond.append(" and TO_DAYS(t.d_approved_at) >= TO_DAYS('"+bApprovedAt+"') ");
        }
        if (StringUtils.isNotBlank(eApprovedAt)) {
            cond.append(" and TO_DAYS(t.d_approved_at) <= TO_DAYS('"+eApprovedAt+"') ");
        }
        /*cond.append(" order by c_out_trade_no desc ");*/

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName="t."+ ReflectUtils.getColumnName(TlbFundApply.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TlbFundApplyDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TlbFundApplyDTO.class);
        return page;

    }

    /**
     * Get one tlbFundApply by id.
     */
    @Override
    @Transactional(readOnly = true)
    public TlbFundApplyDTO findOne(String id) {
        TlbFundApply tlbFundApply = tlbFundApplyRepository.findOne(id);
        return tlbFundApplyMapper.toDto(tlbFundApply);
    }

    @Override
    public List<TlbFundApplyDTO> findTlbFundApplyList(String account, String fundType, String status) {
        List<TlbFundApply> tlbFundApplyList = tlbFundApplyRepository.findTlbFundAppliesByAccountEqualsAndFundTypeAndStatusEquals(account, fundType, status);
        return tlbFundApplyMapper.toDto(tlbFundApplyList);
    }

    @Override
    public List<TlbFundApplyDTO> findTlbFundHistoryList(String account, String status) {
        List<TlbFundApply> tlbFundApplyList = tlbFundApplyRepository.findTlbFundAppliesByAccountEqualsAndStatus(account, status);
        return tlbFundApplyMapper.toDto(tlbFundApplyList);
    }

    /**
     * delete
     */
    @Override
    public void delete(String id) {
        tlbFundApplyRepository.delete(id);
    }

    @Override
    public TlbFundApplyDTO withdrawals(HashMap<String, Object> params) {
        String account = params.get("account") == null?null:params.get("account").toString();
        //double amount = params.get("usd") == null?0.0:Double.valueOf(params.get("usd").toString());
        double amount =  params.get("amount") == null?0.0:Double.valueOf(params.get("amount").toString());

        TlbAccountDTO tlbAccountDTO = tlbAccountService.findByAccount(account);

        TlbFundApplyDTO tlbFundApplyDTO = new TlbFundApplyDTO();
        tlbFundApplyDTO.setAccount(account);
        tlbFundApplyDTO.setCreateAt(new Date());
        tlbFundApplyDTO.setAmount(amount);
        //tlbFundApplyDTO.setCnyAmount();
        tlbFundApplyDTO.setFundType(DevplatformConstants.FUND_APPLY_OUT);
        tlbFundApplyDTO.setStatus(DevplatformConstants.FUND_APPLY_STATUS_APPLYING);
        tlbFundApplyDTO.setUserId(tlbAccountDTO.getUserId());


        return save(tlbFundApplyDTO);
    }

    @Override
    public TlbFundApplyDTO inner(HashMap<String, Object> params) {
        String account = params.get("account") == null?null:params.get("account").toString();
        double amount =  params.get("amount") == null?0.0:Double.valueOf(params.get("amount").toString());

        TlbAccountDTO tlbAccountDTO = tlbAccountService.findByAccount(account);

        TlbFundApplyDTO tlbFundApplyDTO = new TlbFundApplyDTO();
        tlbFundApplyDTO.setAccount(account);
        tlbFundApplyDTO.setCreateAt(new Date());
        tlbFundApplyDTO.setAmount(amount);
        tlbFundApplyDTO.setCnyAmount(null);
        tlbFundApplyDTO.setFundType(DevplatformConstants.FUND_APPLY_INNER);
        tlbFundApplyDTO.setStatus(DevplatformConstants.FUND_APPLY_STATUS_APPLYING);
        tlbFundApplyDTO.setUserId(tlbAccountDTO.getUserId());

        return save(tlbFundApplyDTO);
    }


    @Override
    public TlbFundApplyDTO recharge(HashMap<String, Object> params) {

        String account = params.get("account").toString();
        double amount = Double.valueOf(params.get("usd").toString());
        double cnyAmount = Double.valueOf(params.get("amount").toString());
        String transactionId = params.get("transactionId") == null ? "" : params.get("transactionId").toString();

        TlbAccountDTO tlbAccountDTO = tlbAccountService.findByAccount(account);

        TlbFundApplyDTO tlbFundApplyDTO = new TlbFundApplyDTO();
        tlbFundApplyDTO.setAccount(account);
        tlbFundApplyDTO.setCreateAt(new Date());
        tlbFundApplyDTO.setAmount(amount);
        tlbFundApplyDTO.setCnyAmount(cnyAmount);
        tlbFundApplyDTO.setTransactionId(transactionId);
        tlbFundApplyDTO.setFundType(DevplatformConstants.FUND_APPLY_IN);
        tlbFundApplyDTO.setStatus(DevplatformConstants.FUND_APPLY_STATUS_APPLYING);
        tlbFundApplyDTO.setUserId(tlbAccountDTO.getUserId());


        return save(tlbFundApplyDTO);
    }

    @Override
    public HashMap<String, Object> checkWithdrawals(HashMap<String, Object> params) {
        return tlbAccountService.checkWithdrawals(params);
    }

    @Override
    public HashMap<String, Object> checkInner(HashMap<String, Object> params) {
        return tlbCommissionService.checkInner(params);
    }

    @Override
    public TlbFundApplyDTO passInner(String id) {
        return pass(id);
    }

    @Override
    public TlbFundApplyDTO rejectInner(String id) {
        return reject(id);
    }

    @Override
    public TlbFundApplyDTO passRecharge(String id) {
        return pass(id);
    }

    @Override
    public TlbFundApplyDTO rejectRecharge(String id) {
        return reject(id);
    }

    @Override
    public TlbFundApplyDTO passWithdrawals(String id) {
        return pass(id);
    }

    @Override
    public TlbFundApplyDTO rejectWithdrawals(String id) {
        return reject(id);
    }

    @Override
    public TlbFundApplyDTO pass(String id) {
        TlbFundApplyDTO tlbFundApplyDTO = this.findOne(id);
        tlbFundApplyDTO.setStatus(DevplatformConstants.FUND_APPLY_STATUS_PASSED);
        tlbFundApplyDTO.setApprovedAt(new Date());
        tlbFundApplyDTO.setApprovedId(userService.getUserWithAuthorities().getId());

        double amount = tlbFundApplyDTO.getAmount();

        //郵箱
        TlbAccountDTO tlbAccountDTO = tlbAccountService.findByAccount(tlbFundApplyDTO.getAccount());
        tlbAccountDTO.setAccount(tlbFundApplyDTO.getAccount());
        tlbAccountDTO.setBalance(tlbFundApplyDTO.getAmount());

        if(!DevplatformConstants.FUND_APPLY_INNER.equals(tlbFundApplyDTO.getFundType())) {
            double updateAmount = DevplatformConstants.FUND_APPLY_IN.equals(tlbFundApplyDTO.getFundType()) ? amount : -amount;
            tlbAccountService.updateAccountBalance(tlbFundApplyDTO.getAccount(), updateAmount);

            if (DevplatformConstants.FUND_APPLY_IN.equals(tlbFundApplyDTO.getFundType())) {
                mailService.sendFundInMail(userService.getUserWithAuthorities(tlbAccountDTO.getUserId()), tlbAccountDTO);
            } else {
                mailService.sendFundOutMail(userService.getUserWithAuthorities(tlbAccountDTO.getUserId()), tlbAccountDTO);
            }
        }else{
            if(null != tlbCommissionService.updateCommissionBalance(tlbAccountDTO.getAccount(), amount)) {
                tlbAccountService.updateAccountBalance(tlbAccountDTO.getAccount(), amount);
                mailService.sendFundInnerMail(userService.getUserWithAuthorities(tlbAccountDTO.getUserId()), tlbAccountDTO);
            }
        }

        return this.save(tlbFundApplyDTO);
    }
    @Override
    public TlbFundApplyDTO reject(String id) {
        TlbFundApplyDTO tlbFundApplyDTO = this.findOne(id);
        tlbFundApplyDTO.setStatus(DevplatformConstants.FUND_APPLY_STATUS_REJECT);
        tlbFundApplyDTO.setApprovedAt(new Date());
        tlbFundApplyDTO.setApprovedId(userService.getUserWithAuthorities().getId());
        return this.save(tlbFundApplyDTO);
    }

}