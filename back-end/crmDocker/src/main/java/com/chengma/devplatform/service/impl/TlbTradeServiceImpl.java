package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.DateUtil;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbTrade;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.TlbTradeRepository;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.TlbTradeService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbTradeDTO;
import com.chengma.devplatform.service.mapper.TlbTradeMapper;
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
public class TlbTradeServiceImpl implements TlbTradeService {

    private final Logger log = LoggerFactory.getLogger(TlbTradeServiceImpl.class);

    private final TlbTradeRepository tlbTradeRepository;

    private final TlbTradeMapper tlbTradeMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private TlbAccountService tlbAccountService;

    public TlbTradeServiceImpl(TlbTradeRepository tlbTradeRepository, TlbTradeMapper tlbTradeMapper) {
        this.tlbTradeRepository = tlbTradeRepository;
        this.tlbTradeMapper = tlbTradeMapper;
    }

    /**
     * Save a tlbTrade.
     */
    @Override
    public TlbTradeDTO save(TlbTradeDTO tlbTradeDTO) {
        //配置默认值
        if(null == tlbTradeDTO.getId()) {
            tlbTradeDTO.setCalcSum("N");
            tlbTradeDTO.setOrdered("N");
            tlbTradeDTO.setClosed("N");
            tlbTradeDTO.setGain("N");
            tlbTradeDTO.setCreateAt(new Date());
            tlbAccountService.updateAccountMargin(tlbTradeDTO.getAccount(), tlbTradeDTO.getLots());
        }
        TlbTrade tlbTrade = tlbTradeMapper.toEntity(tlbTradeDTO);
        tlbTrade = tlbTradeRepository.save(tlbTrade);
        //更新可用资金
        return tlbTradeMapper.toDto(tlbTrade);
    }

    /**
     * findAll
     */
    @Override
    @Transactional(readOnly = true)
    public List<TlbTradeDTO> findAll(HashMap<String, Object> params) {
      /*  int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String outTradeNo = formParams.get("outTradeNo") == null ? "" : formParams.get("outTradeNo").toString();

        StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        if (StringUtils.isNotBlank(outTradeNo)) {
            cond.append(" and c_out_trade_no like '%" + outTradeNo + "%' ");
        }
        cond.append(" order by c_out_trade_no desc ");
        String orderBy = " order by c_out_trade_no desc ";
        Page<TlbTradeDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TlbTradeDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("list", page.getContent());
        response.put("page_number", page_number);
        response.put("page_size", page_size);
*/
        return null;
    }

    @Override
    public Page<TlbTradeDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String startCloseDate = formParams.get("startCloseDate") == null ? "" : formParams.get("startCloseDate").toString();
        String endCloseDate = formParams.get("endCloseDate") == null ? "" : formParams.get("endCloseDate").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select a.*");

        StringBuilder cond = new StringBuilder(" from t_tlb_trade a where 1 = 1 ");
        User user = userService.getUserWithAuthorities();
        if(!EnumRole.ADMIN.value().equals(user.getDepartment())) {
            cond.append(" and exists(select null from ( ");
            cond.append(userService.accountTableStr());
            cond.append(")b where a.c_account = b.c_account and b.top_id = '" + user.getId() + "')");
        }
        if(!EnumRole.COMPANY.value().equals(user.getDepartment()) || !EnumRole.PROXY.value().equals(user.getDepartment())){
            cond.append(" and a.c_closed = 'Y' ");
        }

        if (StringUtils.isNotBlank(account)) {
            cond.append(" and a.c_account like '%" + account + "%' ");
        }

        if (StringUtils.isNotBlank(startCloseDate)) {
            cond.append(" and TO_DAYS(a.d_close_time) >= TO_DAYS('" + startCloseDate + "') ");
        }
        if (StringUtils.isNotBlank(endCloseDate)) {
            cond.append(" and TO_DAYS(a.d_close_time) <= TO_DAYS('" + endCloseDate + "') ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName="a."+ReflectUtils.getColumnName(TlbTrade.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<TlbTradeDTO> page= pageCommon.execPage(column.toString(), cond.toString(), orderBy.toString(), page_number, page_size, TlbTradeDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("list", page.getContent());
        response.put("page_number", page_number);
        response.put("page_size", page_size);
        return page;
    }

    /**
     * Get one tlbTrade by id.
     */
    @Override
    @Transactional(readOnly = true)
    public TlbTradeDTO findOne(String id) {
        TlbTrade tlbTrade = tlbTradeRepository.findOne(id);
        return tlbTradeMapper.toDto(tlbTrade);
    }

    /**
     * findByCode
     */
    @Override
    @Transactional(readOnly = true)
    public TlbTradeDTO findByCode(TlbTradeDTO tlbTradeDTO) {
    /*    StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        cond.append(" and c_out_trade_no = '" + tlbTradeDTO.getOutTradeNo() + "' ");

        List<TlbTradeDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TlbTradeDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;*/
        return null;
    }

    @Override
    public TlbTradeDTO findOneByParam(HashMap<String, Object> params) {
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

        List<TlbTradeDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TlbTradeDTO.class);
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
        tlbTradeRepository.delete(id);
    }

    @Override
    public HashMap<String, Object> checkLotsTrade(TlbTradeDTO tlbTradeDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(tlbTradeDTO.getLots() < 0.1){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入最小手數0.1");
            return retMap;
        }

        if(tlbTradeDTO.getLots() > 5){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "最大單筆5手");
            return retMap;
        }

        if(DateUtil.isTranDate(DateUtil.format(new Date()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "非交易時段");
            return retMap;
        }

        if("".equals(tlbTradeDTO.getAccount())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入帳戶信息");
            return retMap;
        }
        TlbAccountDTO tlbAccountDTO = tlbAccountService.findByAccount(tlbTradeDTO.getAccount());

        if(null == tlbAccountDTO){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入有效套利寶帳戶");
            return retMap;
        }
        if(!"Y".equals(tlbAccountDTO.getEnableTrade())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "該帳戶不允許交易");
            return retMap;
        }

        List<TlbTradeDTO>  tlbTradeDTOList = tlbTradeMapper.toDto(tlbTradeRepository.findTlbTradesByAccountEqualsAndOrderedAndClosed(tlbTradeDTO.getAccount(), "Y", "N"));
        double orderLots = tlbTradeDTOList.stream().mapToDouble(TlbTradeDTO::getLots).sum();
        if(orderLots + tlbTradeDTO.getLots() > 10){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "纍計持倉超過10手");
            return retMap;
        }

        if("".equals(tlbTradeDTO.getOrderType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入交易類型");
            return retMap;
        }
        if("".equals(tlbTradeDTO.getSymbol())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入有效交易類型");
            return retMap;
        }
        double amount = - (tlbTradeDTO.getLots() * DevplatformConstants.PER_LOT_MONEY);
        if(!tlbAccountService.checkAccountAmount(tlbTradeDTO.getAccount(), amount)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "可用保證金不足");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);

        return retMap;
    }

    @Override
    public List<TlbTradeDTO> openTlbTrads(String account) {
        return tlbTradeMapper.toDto(tlbTradeRepository.findTlbTradesByAccountEqualsAndOrderedAndClosed(account, "Y", "N"));
    }

    @Override
    public List<TlbTradeDTO> closedTlbTrads(String account) {
        return tlbTradeMapper.toDto(tlbTradeRepository.findTlbTradesByAccountEqualsAndOrderedAndClosed(account, "Y", "Y"));
    }

    public boolean checkMargin(String account, double lots){
        boolean result = false;

        return result;
    }

	@Override
	public List<TlbTradeDTO> findTlbTradesWithClosePosition() {
		
		return tlbTradeMapper.toDto(tlbTradeRepository.findTlbTradesWithClosePosition());
	}
}
