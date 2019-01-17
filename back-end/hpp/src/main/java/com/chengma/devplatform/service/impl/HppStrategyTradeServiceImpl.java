package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppExamApply;
import com.chengma.devplatform.domain.HppStrategyData;
import com.chengma.devplatform.domain.HppStrategyTrade;
import com.chengma.devplatform.repository.HppStrategyRepository;
import com.chengma.devplatform.repository.HppStrategyTradeRepository;
import com.chengma.devplatform.service.HppStrategyDataService;
import com.chengma.devplatform.service.HppStrategyOrderService;
import com.chengma.devplatform.service.HppStrategyTradeService;
import com.chengma.devplatform.service.dto.HppStrategyDTO;
import com.chengma.devplatform.service.dto.HppStrategyDataDTO;
import com.chengma.devplatform.service.dto.HppStrategyOrderDTO;
import com.chengma.devplatform.service.dto.HppStrategyTradeDTO;
import com.chengma.devplatform.service.mapper.HppStrategyMapper;
import com.chengma.devplatform.service.mapper.HppStrategyTradeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class HppStrategyTradeServiceImpl implements HppStrategyTradeService {

    private final HppStrategyTradeRepository hppStrategyTradeRepository;

    private final HppStrategyTradeMapper hppStrategyTradeMapper;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private HppStrategyDataService hppStrategyDataService;



    public HppStrategyTradeServiceImpl(HppStrategyTradeRepository hppStrategyTradeRepository, HppStrategyTradeMapper hppStrategyTradeMapper) {
        this.hppStrategyTradeRepository = hppStrategyTradeRepository;
        this.hppStrategyTradeMapper = hppStrategyTradeMapper;
    }

    @Override
    public HppStrategyTradeDTO save(HppStrategyTradeDTO hppStrategyTradeDTO) {
        return hppStrategyTradeMapper.toDto(hppStrategyTradeRepository.save(hppStrategyTradeMapper.toEntity(hppStrategyTradeDTO)));
    }

    @Override
    public void countFundInOrOutByAccount(String account) {
        String sql = "select * from t_hpp_strategy_trade where (c_order_type='IN' or c_order_type=\"OUT\") and c_calc_sum='N' and c_account='"+account+"'";
        List<HppStrategyTradeDTO> list = baseDao.findListBySql(sql,HppStrategyTradeDTO.class);
        if(list == null || list.size()==0)return ;
        Double fundIn = 0.0;
        Double fundOut = 0.0;
        for(HppStrategyTradeDTO h :list ){
            if(h.getOrderType().equals("IN")){
                fundIn += Math.abs(h.getProfitAmount()); //
            }else{
                fundOut += Math.abs(h.getProfitAmount());
            }
            //结算
            h.setCalcSum("Y");
            hppStrategyTradeRepository.save(hppStrategyTradeMapper.toEntity(h));
        }
        HppStrategyDataDTO hppStrategyDataDTO = hppStrategyDataService.findByAccount(account);
        fundIn = new BigDecimal(fundIn).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue();
        fundOut = new BigDecimal(fundOut).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue();
        Double totalFundIn = hppStrategyDataDTO.getFundIn()==null?(0.0+fundIn):(hppStrategyDataDTO.getFundIn()+fundIn);
        Double totalFundOut= hppStrategyDataDTO.getFundOut()==null?(0.0+fundOut):(hppStrategyDataDTO.getFundOut()+fundOut);
        hppStrategyDataDTO.setFundIn(totalFundIn);
        hppStrategyDataDTO.setFundOut(totalFundOut);
        hppStrategyDataService.save(hppStrategyDataDTO);
    }

    public List<HppStrategyDataDTO> findWithTrade(){
        String sql = "SELECT * from t_hpp_strategy_data d WHERE EXISTS (select null from t_hpp_strategy_trade t where d.c_account=t.c_account)";
        List<HppStrategyDataDTO> list = baseDao.findListBySql(sql,HppStrategyDataDTO.class);
        if(list ==null || list.size() == 0)return null;
        return list;
    }

    @Override
    public void countFundInOrOut() {
        List<HppStrategyDataDTO> list = this.findWithTrade();
        if(list ==null || list.size() == 0)return;
        for(HppStrategyDataDTO hppStrategyDataDTO : list){
            this.countFundInOrOutByAccount(hppStrategyDataDTO.getAccount());
        }
    }

    @Override
    public void countLotsAndTotalByAccount(String account) {
        String sql = "select * from t_hpp_strategy_trade where (c_order_type='BUY' or c_order_type=\"SELL\") and c_calc_sum='N' and c_account='"+account+"'";
        List<HppStrategyTradeDTO> list = baseDao.findListBySql(sql,HppStrategyTradeDTO.class);
        if(list == null || list.size()==0)return ;
        Double lots = 0.0;
        Double profit = 0.0;
        for(HppStrategyTradeDTO h :list ){
            lots += h.getLots();
            profit += h.getGainAmount();
            h.setCalcSum("Y");
            hppStrategyTradeRepository.save(hppStrategyTradeMapper.toEntity(h));
        }
        HppStrategyDataDTO hppStrategyDataDTO = hppStrategyDataService.findByAccount(account);
       /* lots = new BigDecimal(lots).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue();
        profit = new BigDecimal(profit).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue();*/
        Double totalLots = hppStrategyDataDTO.getLots()==null?(0.0+lots):(hppStrategyDataDTO.getLots()+lots);
        Double totalProfit= hppStrategyDataDTO.getTotalProfit()==null?(0.0+profit):(hppStrategyDataDTO.getTotalProfit()+profit);
        hppStrategyDataDTO.setLots( new BigDecimal(totalLots).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue());
        hppStrategyDataDTO.setTotalProfit( new BigDecimal(totalProfit).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue());
        hppStrategyDataService.save(hppStrategyDataDTO);
    }

    @Override
    public void countLotsAndTotal() {
        List<HppStrategyDataDTO> list = this.findWithTrade();
        if(list ==null || list.size() == 0)return;
        for(HppStrategyDataDTO hppStrategyDataDTO : list){
            this.countLotsAndTotalByAccount(hppStrategyDataDTO.getAccount());
        }
    }

    @Override
    public void countMonthProfit() {
        List<HppStrategyDataDTO> list = this.findWithTrade();
        if(list ==null || list.size() == 0)return;
        for(HppStrategyDataDTO hppStrategyDataDTO : list){
            this.countMonthProfitByAccount(hppStrategyDataDTO.getAccount());
        }
    }

    @Override
    public void countMonthProfitByAccount(String account) {
        // "AND DATE_FORMAT(d_create_at, '%Y%m') = DATE_FORMAT(CURDATE(), '%Y%m')\n" + //月收益率
       /* String sql = "select ROUND((a.total_gain_amount/b.total_amount)*100,2) as c_gain_amount FROM\n" +
                "(\n" +
                "SELECT\n" +
                "\tsum(c_gain_amount) AS total_gain_amount \n" +
                "FROM\n" +
                "\tt_hpp_strategy_trade\n" +
                "WHERE\n" +
                "\t(\n" +
                "\t\tc_order_type = 'BUY'\n" +
                "\t\tOR c_order_type = 'SELL'\n" +
                "\t)\n" +
                "AND c_account = '"+account+"'\n" +
                ")a,\n" +
                "(SELECT\n" +
                "\tc_gain_amount AS total_amount \n" +
                "FROM\n" +
                "\tt_hpp_strategy_trade\n" +
                "WHERE\n" +
                "\t\tc_order_type = 'IN'\n" +
                " AND c_account = '"+account+"'\n" +
                " order by d_open_time asc LIMIT 0,1\n" +
                ")b\n";*/
       StringBuilder sql =new StringBuilder("SELECT round( sum(a.c_gain_amount) * 100 / ( SELECT sum(b.i_profit_amount) FROM t_hpp_strategy_trade b WHERE a.c_account = b.c_account AND b.c_order_type = 'IN' ), 2 ) as c_gain_amount ");
       sql.append("FROM t_hpp_strategy_trade a WHERE ( a.c_order_type = 'BUY' OR a.c_order_type = 'SELL' ) AND a.c_account = '"+account+"' GROUP BY a.c_account");
        List<HppStrategyTradeDTO> list = baseDao.findListBySql(sql.toString(),HppStrategyTradeDTO.class);
        if(list == null || list.size()==0)return ;
        Double monthProfit = list.get(0).getGainAmount() == null ? 0.0:list.get(0).getGainAmount() ;
        monthProfit = new BigDecimal(monthProfit).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue();
        HppStrategyDataDTO hppStrategyDataDTO = hppStrategyDataService.findByAccount(account);
        hppStrategyDataDTO.setMonthProfit(monthProfit);
        hppStrategyDataService.save(hppStrategyDataDTO);
    }

    @Override
    public List<HppStrategyTradeDTO> findTradeByAccount(String account) {
        String sql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tt_hpp_strategy_trade\n" +
                "WHERE\n" +
                "\tc_account = '"+account+"'\n" +
                "AND (\n" +
                "\tc_order_type = 'SELL'\n" +
                "\tOR c_order_type = 'BUY'\n" +
                "\tOR c_order_type = 'IN'\n" +
                "\tOR c_order_type = 'OUT'\n" +
                ")\n" +
                "ORDER BY\n" +
                "\td_create_at DESC";
        List<HppStrategyTradeDTO> list = baseDao.findListBySql(sql,HppStrategyTradeDTO.class);
        return list;
    }

    /**
     * 近三条交易记录
     * @param account
     * @return
     */
    @Override
    public List<HppStrategyTradeDTO> findTradeByAccountLimit(String account) {
        String sql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tt_hpp_strategy_trade\n" +
                "WHERE\n" +
                "\tc_account = '"+account+"'\n" +
                "AND (\n" +
                "\tc_order_type = 'SELL'\n" +
                "\tOR c_order_type = 'BUY'\n" +
                ")\n" +
                "ORDER BY\n" +
                "\td_create_at DESC\n" +
                "LIMIT 0,3";
        List<HppStrategyTradeDTO> list = baseDao.findListBySql(sql,HppStrategyTradeDTO.class);
        return list;
    }

    @Override
    public List<HppStrategyTradeDTO> findTradeByRatio(String account) {
        String sql ="SELECT\n" +
                "\tr1.c_symbol,\n" +
                "\tround((r1.count / r2.total)*100,2) AS ratio\n" +
                "FROM\n" +
                "\t\t(\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tc_symbol,\n" +
                "\t\t\t\tcount(c_symbol) AS count\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_hpp_strategy_trade \n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tc_account = '"+account+"'\n" +
                "\t\t\tAND (\n" +
                "\t\t\t\tc_order_type = 'SELL'\n" +
                "\t\t\t\tOR c_order_type = 'BUY'\n" +
                "\t\t\t)\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tc_symbol\n" +
                "\t\t) as r1,\n" +
                "\t\t(\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tcount(1) total\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_hpp_strategy_trade \n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tc_account = '"+account+"'\n" +
                "\t\t\tAND (\n" +
                "\t\t\t\tc_order_type = 'SELL'\n" +
                "\t\t\t\tOR c_order_type = 'BUY'\n" +
                "\t\t\t) \n" +
                "\t\t)as r2\n" +
                "GROUP BY\n" +
                "\tr1.c_symbol";
        List<HppStrategyTradeDTO> list = baseDao.findListBySql(sql,HppStrategyTradeDTO.class);
        return list;
    }

    @Override
    public HashMap<String,Object> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String startTime = formParams.get("startTime") == null ? "" : formParams.get("startTime").toString();
        String endTime = formParams.get("endTime") == null ? "" : formParams.get("endTime").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_hpp_strategy_trade where (c_order_type = 'BUY' or c_order_type = 'SELL')");

        if(StringUtils.isNotBlank(account)){
            cond.append(" and  c_account like '%"+account+"%' ");
        }

        if(StringUtils.isNotBlank(startTime)){
            cond.append(" and TO_DAYS(d_close_time)>=TO_DAYS('"+startTime+"') ");
        }

        if(StringUtils.isNotBlank(endTime)){
            cond.append(" and TO_DAYS(d_close_time)<=TO_DAYS('"+endTime+"') ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppStrategyTrade.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        HashMap<String, Object> response = new HashMap<>();
        Page<HppStrategyTradeDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy, page_number, page_size, HppStrategyTradeDTO.class);

        //统计总手数
        String sql = "select sum(i_lots) as total_lots "+cond.toString();
        List<HppStrategyTradeDTO> list = baseDao.findListBySql(sql,HppStrategyTradeDTO.class);
        Double totalLots = list.get(0).getTotalLots() == null ? 0.0 : list.get(0).getTotalLots();
        response.put("page", page);
        response.put("totalLots", totalLots);
        return response;
    }
}
