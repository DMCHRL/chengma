package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.StringUtil;
import com.chengma.devplatform.domain.HppStrategyBalHistory;
import com.chengma.devplatform.domain.HppStrategyData;
import com.chengma.devplatform.repository.HppStrategyBalHistoryRepository;
import com.chengma.devplatform.service.HppStrategyBalHistoryService;
import com.chengma.devplatform.service.HppStrategyDataService;
import com.chengma.devplatform.service.dto.HppStrategyBalHistoryDTO;
import com.chengma.devplatform.service.dto.HppStrategyDataDTO;
import com.chengma.devplatform.service.dto.HppStrategyViewDTO;
import com.chengma.devplatform.service.mapper.HppStrategyBalHistoryMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class HppStrategyBalHistoryServiceImpl implements HppStrategyBalHistoryService {

    private final Logger log = LoggerFactory.getLogger(HppStrategyBalHistoryServiceImpl.class);

    private final HppStrategyBalHistoryRepository hppStrategyBalHistoryRepository;

    private final HppStrategyBalHistoryMapper hppStrategyBalHistoryMapper;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private HppStrategyDataService hppStrategyDataService;

    @Autowired
    private DBService dbService;

    public HppStrategyBalHistoryServiceImpl(HppStrategyBalHistoryRepository hppStrategyBalHistoryRepository, HppStrategyBalHistoryMapper hppStrategyBalHistoryMapper) {
        this.hppStrategyBalHistoryRepository = hppStrategyBalHistoryRepository;
        this.hppStrategyBalHistoryMapper = hppStrategyBalHistoryMapper;
    }

    @Override
    public HppStrategyBalHistoryDTO save(HppStrategyBalHistoryDTO hppStrategyBalHistoryDTO) {
        HppStrategyBalHistory hppStrategyBalHistory =hppStrategyBalHistoryMapper.toEntity(hppStrategyBalHistoryDTO);
        HppStrategyBalHistory hppStrategyBalHistory1 =hppStrategyBalHistoryRepository.save(hppStrategyBalHistory);
        return hppStrategyBalHistoryMapper.toDto(hppStrategyBalHistory1);
    }


    @Override
    public Page<HppStrategyBalHistoryDTO> pageList(HashMap<String, Object> params) {
       return null;
    }

    @Override
    public HppStrategyBalHistoryDTO findOne(String id) {
        return hppStrategyBalHistoryMapper.toDto(hppStrategyBalHistoryRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppStrategyBalHistoryRepository.delete(id);
    }

    /**
     * 原t_hpp_strategy_bal_history 变 t_hpp_strategy_trade_view
     */
    @Override
    public void countHistoryFundBack() {
        String accountSql = "select t1.c_account from t_hpp_strategy_trade_view t1 where EXISTS (select null from t_hpp_strategy_data t2 where t2.c_account=t1.c_account) GROUP BY t1.c_account ";
        List<HppStrategyBalHistoryDTO> accountList = baseDao.findListBySql(accountSql,HppStrategyBalHistoryDTO.class);

        for(HppStrategyBalHistoryDTO h : accountList){

            List<HppStrategyBalHistoryDTO> equeryList = this.findHistoryFundBack(h.getAccount(),DevplatformConstants.DAYS);
            if(equeryList == null ||equeryList.size()<=0)continue;
            Double fundBack = 0.0;
            for(int i =0;i<equeryList.size()-1;i++){
                HppStrategyBalHistoryDTO hppStrategyBalHistoryDTO1 = equeryList.get(i);
                HppStrategyBalHistoryDTO hppStrategyBalHistoryDTO2 = equeryList.get(i+1);
                Double outAmount = hppStrategyBalHistoryDTO2.getOutAmount(); //第二天出金

                Double equery1 = hppStrategyBalHistoryDTO1.getEquery();
                Double equery2 = hppStrategyBalHistoryDTO2.getEquery();
                if(outAmount < 0){
                    equery2 += Math.abs(outAmount);
                }
                if(equery1-equery2 > 0){
                    Double temp = (equery1-equery2)/equery1;
                    if(temp > fundBack) fundBack =temp;
                }
            }

            HppStrategyDataDTO hppStrategyDataDTO =hppStrategyDataService.findByAccount(h.getAccount());
            if(hppStrategyDataDTO == null) continue;
            fundBack = new BigDecimal(fundBack*100).setScale(DevplatformConstants.AMOUNT_KEEP_PERICE,BigDecimal.ROUND_HALF_UP).doubleValue();
            hppStrategyDataDTO.setFundBack(fundBack);
            hppStrategyDataService.save(hppStrategyDataDTO);
        }
    }

    /**
     * 资金
     * @param account
     * @param type
     * @return
     */
    @Override
    public List<HppStrategyBalHistoryDTO> findHistoryFundBack(String account,String type) {
        if(StringUtils.isBlank(type)){
            type = DevplatformConstants.DAYS;
        }
        String format="";
        if(type.equals(DevplatformConstants.DAYS)){
            format = "%Y-%m-%d";
        }else if(type.equals(DevplatformConstants.MONTHS)){
            format = "%Y-%m";
        }else{
            format = "%Y-%m-%d";
        }
        /*String countSql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tDATE_FORMAT(\n" +
                "\t\t\t\tt1.d_mt4_server_time,\n" +
                "\t\t\t\t'"+format+"'\n" +
                "\t\t\t) days,\n" +
                "\t\t\tROUND(avg(t1.i_equery),2) i_equery\n" +
                "\t\tFROM\n" +
                "\t\t\tt_hpp_strategy_bal_history t1\n" +
                "\t\tWHERE\n" +
                "\t\t\tEXISTS (\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\t\tNULL\n" +
                "\t\t\t\tFROM\n" +
                "\t\t\t\t\tt_hpp_strategy_data t2\n" +
                "\t\t\t\tWHERE\n" +
                "\t\t\t\t\tt2.c_account = t1.c_account\n" +
                "\t\t\t\tAND t1.i_equery IS NOT NULL\n" +
                "\t\t\t)\n" +
                "\t\tAND t1.c_account = "+account+"\n" +
                "\t\tGROUP BY\n" +
                "\t\t\t days\t) a\n" +
                "ORDER BY\n" +
                "\t days ASC";*/
        String countSql="select a.closeDate as days, SUM(b.c_gain_amount) as equery,a.`IN` as in_amount,a.`OUT` as out_amount\n" +
                "from t_hpp_strategy_trade_view as a, t_hpp_strategy_trade_view as b\n" +
                "where a.c_account = b.c_account\n" +
                "\t\t\tand a.closeDate >= b.closeDate\n" +
                "\t\t\tand a.c_account = '"+account+"'\n" +
                "group by a.c_account, a.closeDate";
        return  baseDao.findListBySql(countSql,HppStrategyBalHistoryDTO.class);
    }


    @Override
    public List<HppStrategyViewDTO> findGrowthRate(HashMap<String,Object> params) {
        String account = params.get("account")== null ?"":params.get("account").toString();
        String type = params.get("type")== null ?"":params.get("type").toString();
       // List<HppStrategyBalHistoryDTO> hppStrategyBalHistoryDTOList =this.findHistoryFundBack(account,type);//净值表
        List<HppStrategyViewDTO> list = this.findGrowthRateBefore(account);

        //更新余额
        if(list != null && list.size() > 0){
            Double equery = list.get(list.size()-1).getEquery();
            HppStrategyDataDTO hppStrategyDataDTO = hppStrategyDataService.findByAccount(account);
            hppStrategyDataDTO.setBalance(equery);
            hppStrategyDataService.save(hppStrategyDataDTO);
        }
        return list; //历史净值
        //List<HppStrategyBalHistoryDTO> resultList = new ArrayList<HppStrategyBalHistoryDTO>();
        //String date = hppStrategyBalHistoryDTOList.get(0).getDays();
        /*if(beforeList != null && beforeList.size()>0){
            resultList.addAll(beforeList);
        resultList.addAll(hppStrategyBalHistoryDTOList);*/
       /* for(int i = 0;i<hppStrategyBalHistoryDTOList.size()-1;i++){
            HppStrategyBalHistoryDTO h1 =hppStrategyBalHistoryDTOList.get(i);  //第一条记录
            HppStrategyBalHistoryDTO h2 = hppStrategyBalHistoryDTOList.get(i+1);//第二条记录
            Double growthRate = new BigDecimal( (h2.getEquery()-h1.getEquery())/h1.getEquery()).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
            HppStrategyBalHistoryDTO result = new HppStrategyBalHistoryDTO();
            result.setGrowthRate(growthRate*100); //转为百分比
            result.setDays(h2.getDays());
            resultList.add(result);
        }*/
        //return resultList;
    }

    public List<HppStrategyViewDTO> findGrowthRateBefore(String account){
        String sql="select a.closeDate as days, SUM(b.c_gain_amount) as equery,a.IN as in_amount, a.OUT as out_amount \n" +
                "from t_hpp_strategy_trade_view as a, t_hpp_strategy_trade_view as b\n" +
                "where a.c_account = b.c_account\n" +
                "\t\t\tand a.closeDate >= b.closeDate\n" +
                "\t\t\tand a.c_account = '"+account+"'\n" +
                //"\t\t\tand TO_DAYS(a.closeDate)<TO_DAYS('"+date+"')\n" +
                "group by a.c_account, a.closeDate";
        return baseDao.findListBySql(sql,HppStrategyViewDTO.class);
    }

    @Override
    public List<HppStrategyViewDTO> findProfitRate(HashMap<String, Object> params) {
        String account = params.get("account")== null ?"":params.get("account").toString();

       StringBuilder sql = new StringBuilder("SELECT a.closeDate AS days, round(( sum(b.c_gain_amount) / ( SELECT sum(c.IN) AS c_gain_amount FROM t_hpp_strategy_trade_view c WHERE c.c_account = a.c_account GROUP BY c_account )) * 100, 4 ) AS gain_rate");
       sql.append(" FROM t_hpp_strategy_profit_view a, t_hpp_strategy_profit_view b WHERE a.closeDate >= b.closeDate AND a.c_account = b.c_account AND a.c_account = '"+account+"' GROUP BY a.c_account, a.closeDate ORDER BY a.closeDate ASC\n");
        return baseDao.findListBySql(sql.toString(),HppStrategyViewDTO.class);
    }
}
