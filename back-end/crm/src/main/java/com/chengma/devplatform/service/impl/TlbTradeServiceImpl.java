package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.DateUtil;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.common.util.StringUtil;
import com.chengma.devplatform.domain.TlbTrade;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.TlbTradeRepository;
import com.chengma.devplatform.service.TlbAccountControlService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.TlbTradeService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.TlbTradeMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
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

    @Autowired
    private TlbAccountControlService tlbAccountControlService;

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
            tlbTradeDTO.setOvernight("N");
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
    public HashMap<String, Object> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String startCloseDate = formParams.get("startCloseDate") == null ? "" : formParams.get("startCloseDate").toString();
        String orderNo = formParams.get("orderNo") == null ? "" : formParams.get("orderNo").toString();
        String endCloseDate = formParams.get("endCloseDate") == null ? "" : formParams.get("endCloseDate").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        String closed = params.get("closed") == null ? "Y" : params.get("closed").toString();

        StringBuilder column = new StringBuilder(" select a.*");
        column.append(", ifnull(b.company_money, 0) company_amount, ifnull(b.proxy_money, 0) proxy_amount");

        StringBuilder cond = new StringBuilder(" from t_tlb_trade a left join t_tlb_commission_trace_group_view b on a.i_order_no = b.i_order_no ");
        cond.append( " where 1 = 1 ");
        User user = userService.getUserWithAuthorities();
        if(!(EnumRole.ADMIN.value().equals(user.getDepartment()) || EnumRole.SERVICE.value().equals(user.getDepartment()))) {
            cond.append(" and exists(select null from ( ");
            cond.append(userService.accountTableStr());
            cond.append(")b where a.c_account = b.c_account and b.top_id = '" + user.getId() + "')");
        }
        cond.append(" and a.c_closed = '" + closed + "'");
        if (StringUtils.isNotBlank(account)) {
            cond.append(" and a.c_account like '%" + account + "%' ");
        }

        if (StringUtils.isNotBlank(orderNo)) {
            cond.append(" and a.i_order_no = " + orderNo);
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
        HashMap<String, Object> response = new HashMap<>();

        Page<TlbTradeDTO> page= pageCommon.execPage(column.toString(), cond.toString(), orderBy.toString(), page_number, page_size, TlbTradeDTO.class);

        StringBuilder sumLots = new StringBuilder("select sum(a.i_lots) i_lots, sum(a.c_gain_amount) c_gain_amount  " + cond.toString());
        List<TlbTradeDTO> list=baseDao.findListBySql(sumLots.toString(),TlbTradeDTO.class);
        TlbTradeDTO tlbTradeDTO=null;
        if(list!=null && list.size()>0){
            tlbTradeDTO=list.get(0);
        }
        if(tlbTradeDTO != null){
            response.put("sumLots", tlbTradeDTO.getLots());
            response.put("sumAmount", tlbTradeDTO.getGainAmount());
        }

        response.put("page", page);
       /* response.put("list", page.getContent());
        response.put("page_number", page_number);
        response.put("page_size", page_size);*/
        return response;
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

        if("".equals(tlbTradeDTO.getAccount())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入帳戶信息");
            return retMap;
        }
        User currentUser=userService.getUserWithAuthorities();
        TlbAccountDTO tlbAccountDTO=tlbAccountService.findByAccount(tlbTradeDTO.getAccount());

        if(null == tlbAccountDTO){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入有效套利寶帳戶");
            return retMap;
        }

        //非本人賬號
       /* if(!currentUser.getId().equals(tlbAccountDTO.getUserId())){
            TlbAccountControlDTO tlbAccountControlDTO=tlbAccountControlService.findByUserIdEqualsAndAccountEqualsAndTradeEquals(currentUser.getId(),tlbTradeDTO.getAccount(),DevplatformConstants.ACCOUNT_CONTROL_TRADE_YES);
            if(tlbAccountControlDTO==null){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "登錄為觀摩賬號，沒有交易權限");
                return retMap;
            }
        }*/

        if(tlbTradeDTO.getLots() < DevplatformConstants.MIN_LOTS){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入最小手數" + DevplatformConstants.MIN_LOTS);
            return retMap;
        }


        double lots = new BigDecimal(tlbTradeDTO.getLots()).setScale(DevplatformConstants.LOTS_KEEP_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
        if(lots != tlbTradeDTO.getLots()){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入最小手數" + DevplatformConstants.MIN_LOTS + "的整數倍");
            return retMap;
        }


        if(tlbTradeDTO.getLots() >  DevplatformConstants.MAX_PER_LOTS){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "最大單筆"+ DevplatformConstants.MAX_PER_LOTS +"手");
            return retMap;
        }

        if(DateUtil.isTranDate(DateUtil.format(new Date()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "非交易時段");
            return retMap;
        }


        if(!"Y".equals(tlbAccountDTO.getEnableTrade())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "該帳戶不允許交易");
            return retMap;
        }

       /* List<TlbTradeDTO>  tlbTradeDTOList = tlbTradeMapper.toDto(tlbTradeRepository.findTlbTradesByAccountEqualsAndOrderedAndClosed(tlbTradeDTO.getAccount(), "Y", "N"));
        double orderLots = tlbTradeDTOList.stream().mapToDouble(TlbTradeDTO::getLots).sum();
        if(orderLots + tlbTradeDTO.getLots() > DevplatformConstants.MAX_SUM_LOTS){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "纍計持倉超過"+DevplatformConstants.MAX_SUM_LOTS+"手");
            return retMap;
        }*/

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

        if("XAUUSD".equals(tlbTradeDTO.getSymbol())){
            Calendar cal = Calendar.getInstance();
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            if(DevplatformConstants.XAUUSD_LIMIT_TRADE_START_TIME < hours && hours < DevplatformConstants.XAUUSD_LIMIT_TRADE_END_TIME){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "凌晨时段暫停新交易訂單");
                return retMap;
            }
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

    @Override
    public TlbDataDTO getTlbData() {
       User currentUser= userService.getUserWithAuthorities();
        if(currentUser == null || !EnumRole.ADMIN.value().equals(currentUser.getDepartment())){
            return null;
        }
        StringBuilder column =new StringBuilder("select sum(total_count) total_trade_count, sum(total_lots) total_lots, sum(total_gain) total_gain\n" +
                ", round(sum(a.total_commission), 2) total_commission\n" +
                ", sum(a.total_account)total_account\n" +
                ", sum(a.total_success_count) total_success_count\n" +
                ", round(sum(total_success_count) / sum(a.total_count) * 100, 2)  total_success_rate\n" +
                ", round(sum(total_count) / sum(a.total_account), 2) avg_trade_count\n" +
                ", round(sum(total_lots) / sum(a.total_account), 2) avg_lots\n" +
                ", round(sum(total_gain) / sum(a.total_account), 2) avg_gain\n" +
                ", sum(a.total_in) total_in\n" +
                ", sum(a.total_out) total_out\n" +
                "from (\n" +
                "select count(1) total_count, \n" +
                "sum(i_lots) total_lots, \n" +
                "sum(c_gain_amount) total_gain, \n" +
                "sum(CASE WHEN a.c_gain = 'Y' then 1 else 0 end) total_success_count,\n" +
                "0 as total_account, \n" +
                "0 as total_commission,\n" +
                "0 total_in, 0 total_out\n" +
                "from t_tlb_trade a \n" +
                "WHERE a.c_closed = 'Y' AND\n" +
                "exists (select null from  t_tlb_account b where a.c_account = b.c_account and b.c_group = 'TXA3')\n" +
                "union all \n" +
                "select 0 as total_count, 0 total_lots, 0 total_gain, 0 total_success_count, count(1) as total_account, 0 as total_commission,\n" +
                "0 total_in, 0 total_out\n" +
                "from  t_tlb_account b where b.c_group = 'TXA3'\n" +
                "union all \n" +
                "select 0 as total_count, 0 total_lots, 0 total_gain, 0 total_success_count, 0 total_account, sum(i_total) as total_commission, \n" +
                "0 total_in, 0 total_out\n" +
                "from t_tlb_commission\n" +
                "union all \n" +
                "select 0 as total_count, 0 total_lots, 0 total_gain, 0 total_success_count, 0 total_account, 0 as total_commission, \n" +
                "sum(case when a.c_fund_type = 'IN' THEN a.i_amount else 0 end ) total_in, \n" +
                "sum(case when a.c_fund_type = 'OUT' THEN a.i_amount else 0 end ) total_out\n" +
                "from t_tlb_fund_apply as a\n" +
                "where a.c_status = 'PASSED'\n" +
                "and (a.c_account like 'system-%' or exists (select null from  t_tlb_account b where a.c_account = b.c_account and b.c_group = 'TXA3'))\n" +
                ") a;");

        List<TlbDataDTO> list = baseDao.findListBySql(column.toString(), TlbDataDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TradeDataDTO> getTlbTradeProfit(HashMap<String, Object> params) {
        StringBuilder column = new StringBuilder("select a.account, a.date, case when t.i_fund_in=0 or t.i_fund_in is null then 0\n" +
                "\telse ROUND((sum(b.gain_money)/t.i_fund_in)*100,4) end AS gain_money \n" +
                "from \n" +
                "tlb_trade_profit_view a , tlb_trade_profit_view b,t_tlb_account t \n" +
                "where a.account = b.account and a.account=t.c_account and a.date >= b.date\n");
        String account = params.get("account") == null ? "" : params.get("account").toString();
        String month = params.get("month") == null ? "" :params.get("month").toString();
        if(StringUtils.isNotBlank(account)){
            column.append(" and a.account = '" + account + "'");
        }
        if(StringUtils.isNotBlank(month)){
            column.append(" and DATE_FORMAT(a.date, '%Y-%m') = '" + month + "'");
        }
        column.append(" group by a.account, a.date");

        List<TradeDataDTO> list = baseDao.findListBySql(column.toString(), TradeDataDTO.class);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<TradeDataDTO> getTlbTradeSymbol(String account) {
        StringBuilder column = new StringBuilder("select c_account, c_symbol, sum(i_lots) as lots\n" +
                "from t_tlb_trade where 1 = 1");
        column.append(" and c_account = '" + account + "'");
        column.append(" group by c_account, c_symbol");

        List<TradeDataDTO> list = baseDao.findListBySql(column.toString(), TradeDataDTO.class);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public TradeDataDTO getTlbTradeWin(String account) {
        String sql="SELECT\n" +
                "\ta.c_account,\n" +
                "\tround(\n" +
                "\t\tifnull(\n" +
                "\t\t\tsum(a.success_count) / sum(a.total_count),\n" +
                "\t\t\t0\n" +
                "\t\t) * 100,\n" +
                "\t\t2\n" +
                "\t) AS success_rate\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tc_account,\n" +
                "\t\t\tcount(1) AS success_count,\n" +
                "\t\t\t0 AS total_count\n" +
                "\t\tFROM\n" +
                "\t\t\tt_tlb_trade a\n" +
                "\t\tWHERE\n" +
                "\t\t\ta.c_closed = 'Y'\n" +
                "\t\tAND c_gain = 'Y'\n" +
                "\t\tGROUP BY\n" +
                "\t\t\tc_account\n" +
                "\t\tUNION ALL\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tc_account,\n" +
                "\t\t\t\t0 AS success_count,\n" +
                "\t\t\t\tcount(1) AS total_count\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_tlb_trade a\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\ta.c_closed = 'Y'\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tc_account\n" +
                "\t) a\n" +
                "where a.c_account='"+account+"'";

        return baseDao.findListBySql(sql,TradeDataDTO.class).get(0);
    }
}
