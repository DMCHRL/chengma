package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbAccountTrade;
import com.chengma.devplatform.repository.TlbAccountRepository;
import com.chengma.devplatform.repository.TlbAccountTradeRepository;
import com.chengma.devplatform.service.TlbAccountDataService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.TlbAccountTradeService;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbAccountDataDTO;
import com.chengma.devplatform.service.dto.TlbAccountTradeDTO;
import com.chengma.devplatform.service.mapper.TlbAccountMapper;
import com.chengma.devplatform.service.mapper.TlbAccountTradeMapper;
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
public class TlbAccountTradeServiceImpl implements TlbAccountTradeService {

    private final TlbAccountTradeRepository tlbAccountTradeRepository;

    private final TlbAccountTradeMapper tlbAccountTradeMapper;

    @Autowired
    private PageCommon pageCommon;

    public TlbAccountTradeServiceImpl(TlbAccountTradeRepository tlbAccountTradeRepository, TlbAccountTradeMapper tlbAccountTradeMapper){
        this.tlbAccountTradeRepository=tlbAccountTradeRepository;
        this.tlbAccountTradeMapper=tlbAccountTradeMapper;
    }

    @Override
    public void countFundInOrOutByAccount(String account) {

    }

    @Override
    public void countFundInOrOut() {

    }

    @Override
    public void countLotsAndTotalByAccount(String account) {

    }

    @Override
    public void countLotsAndTotal() {

    }

    @Override
    public void countMonthProfit() {

    }

    @Override
    public void countMonthProfitByAccount(String account) {

    }

    @Override
    public TlbAccountTradeDTO save(TlbAccountTradeDTO tlbAccountTradeDTO) {
        return null;
    }

    @Override
    public List<TlbAccountTradeDTO> findTradeByAccount(String account) {
        return null;
    }

    @Override
    public List<TlbAccountTradeDTO> findTradeByAccountLimit(String account) {
        return null;
    }

    @Override
    public List<TlbAccountTradeDTO> findTradeByRatio(String account) {
        return null;
    }

    @Override
    public HashMap<String, Object> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" select * ");
        StringBuilder cond = new StringBuilder(" from t_tlb_account_trade ");

        Page<TlbAccountTradeDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, TlbAccountTradeDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        response.put("page",page);
        return response;
    }
}