package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.TlbAccountData;
import com.chengma.devplatform.repository.TlbAccountDataRepository;
import com.chengma.devplatform.repository.TlbAccountRepository;
import com.chengma.devplatform.service.TlbAccountDataService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbAccountDataDTO;
import com.chengma.devplatform.service.dto.TlbAccountTradeDTO;
import com.chengma.devplatform.service.mapper.TlbAccountDataMapper;
import com.chengma.devplatform.service.mapper.TlbAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class TlbAccountDataServiceImpl implements TlbAccountDataService {

    private final TlbAccountDataRepository tlbAccountDataRepository;

    private final TlbAccountDataMapper tlbAccountDataMapper;

    @Autowired
    private PageCommon pageCommon;

    public TlbAccountDataServiceImpl(TlbAccountDataRepository tlbAccountDataRepository, TlbAccountDataMapper tlbAccountDataMapper){
        this.tlbAccountDataRepository=tlbAccountDataRepository;
        this.tlbAccountDataMapper=tlbAccountDataMapper;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteByAccount(String account) {
        tlbAccountDataRepository.deleteByAccountEquals(account);
    }

    @Override
    public TlbAccountDataDTO findByAccount(String account) {
        return null;
    }

    @Override
    public TlbAccountDataDTO loadByAccount(String account) {
        return null;
    }

    @Override
    public TlbAccountDataDTO save(TlbAccountDataDTO tlbAccountDataDTO) {
        return tlbAccountDataMapper.toDto(tlbAccountDataRepository.save(tlbAccountDataMapper.toEntity(tlbAccountDataDTO)));
    }

    @Override
    public TlbAccountDataDTO initTlbAccountDataDTO(String account) {

        TlbAccountDataDTO tlbAccountDataDTO = new TlbAccountDataDTO();

        tlbAccountDataDTO.setAccount(account);
        tlbAccountDataDTO.setBalance(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setFundBack(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setFundIn(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setFundOut(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setLots(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setMargin(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setTopNetWorth(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setTotalProfit(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setMonthProfit(DevplatformConstants.ACCOUNT_INIT);
        tlbAccountDataDTO.setNetWorth(DevplatformConstants.ACCOUNT_INIT);
        return save(tlbAccountDataDTO);
    }

    @Override
    public Page<TlbAccountDataDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" select * ");
        StringBuilder cond = new StringBuilder(" from t_tlb_account_data ");

        Page<TlbAccountDataDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, TlbAccountDataDTO.class);
        return page;
    }

    @Override
    public TlbAccountDataDTO createTlbAccountDataDTO(TlbAccountDataDTO tlbAccountDataDTO) {
        return null;
    }

    @Override
    public HashMap<String, Object> checkCreateTlbAccountDataDTO(TlbAccountDataDTO tlbAccountDataDTO) {
        return null;
    }

    @Override
    public TlbAccountDataDTO findOne(String id) {
        return null;
    }

    @Override
    public List<TlbAccountDataDTO> findAll() {
        return null;
    }
}