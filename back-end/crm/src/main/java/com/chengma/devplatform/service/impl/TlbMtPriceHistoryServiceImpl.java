package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.TlbMtPriceHistoryRepository;
import com.chengma.devplatform.service.TlbMtPriceHistoryService;
import com.chengma.devplatform.service.dto.TlbMtPriceHistoryDTO;
import com.chengma.devplatform.service.mapper.TlbMtPriceHistoryMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class TlbMtPriceHistoryServiceImpl implements TlbMtPriceHistoryService {

    private final Logger log = LoggerFactory.getLogger(TlbMtPriceHistoryServiceImpl.class);

    private final TlbMtPriceHistoryRepository tlbMtPriceHistoryRepository;

    private final TlbMtPriceHistoryMapper tlbMtPriceHistoryMapper;


    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private DBService dbService;

    public TlbMtPriceHistoryServiceImpl(TlbMtPriceHistoryRepository tlbMtPriceHistoryRepository, TlbMtPriceHistoryMapper tlbMtPriceHistoryMapper) {
        this.tlbMtPriceHistoryRepository = tlbMtPriceHistoryRepository;
        this.tlbMtPriceHistoryMapper = tlbMtPriceHistoryMapper;
    }

    @Override
    public List<TlbMtPriceHistoryDTO> findListByType(HashMap<String,Object> params) {
        String type = params.get("type") == null ? "XAUUSD" : (String)params.get("type");
        String startTime = params.get("startTime") == null ? "" : (String)params.get("startTime");
        String endTime = params.get("endTime") == null ? "" : (String)params.get("endTime");
        String cond ;
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            startTime= startTime.substring(0,10)+" "+startTime.substring(11,startTime.length());
            endTime= endTime.substring(0,10)+" "+endTime.substring(11,endTime.length());
            cond = "AND c_time >= STR_TO_DATE(\n" +
                    "\t'"+startTime+"',\n" +
                    "\t'%Y-%m-%d %H:%i:%s'\n" +
                    ")\n" +
                    "AND c_time <= STR_TO_DATE(\n" +
                    "\t'"+endTime+"',\n" +
                    "\t'%Y-%m-%d %H:%i:%s'\n" +
                    ")";
        }else{
            cond = "and c_time>DATE_SUB(NOW(), INTERVAL 60 MINUTE)";
        }
        String sql = "select c_time,c_symbol,i_spread from t_tlb_mt_price_history where c_symbol='"+type+"' "+cond+" ORDER BY c_time desc";
        return baseDao.findListBySql(sql,TlbMtPriceHistoryDTO.class);
    }
}
