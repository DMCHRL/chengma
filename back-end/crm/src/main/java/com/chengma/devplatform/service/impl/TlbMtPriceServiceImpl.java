package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbMtPrice;
import com.chengma.devplatform.repository.TlbMtPriceRepository;
import com.chengma.devplatform.service.TlbMtPriceService;
import com.chengma.devplatform.service.dto.TlbMtPriceDTO;
import com.chengma.devplatform.service.mapper.TlbMtPriceMapper;
import org.apache.commons.lang.StringUtils;
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
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class TlbMtPriceServiceImpl implements TlbMtPriceService {

    private final Logger log = LoggerFactory.getLogger(TlbMtPriceServiceImpl.class);

    private final TlbMtPriceRepository tlbMtPriceRepository;

    private final TlbMtPriceMapper tlbMtPriceMapper;



    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private DBService dbService;

    public TlbMtPriceServiceImpl(TlbMtPriceRepository tlbMtPriceRepository, TlbMtPriceMapper tlbMtPriceMapper) {
        this.tlbMtPriceRepository = tlbMtPriceRepository;
        this.tlbMtPriceMapper = tlbMtPriceMapper;
    }

    @Override
    public TlbMtPriceDTO save(TlbMtPriceDTO tlbMtPriceDTO) {
        tlbMtPriceDTO.setTime(new Date());
        TlbMtPrice tlbMtPrice =tlbMtPriceMapper.toEntity(tlbMtPriceDTO);
        tlbMtPriceRepository.save(tlbMtPrice);
        return tlbMtPriceMapper.toDto(tlbMtPrice);
    }

    @Override
    public Page<TlbMtPriceDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String bTime = formParams.get("bTime") == null ? "" : formParams.get("bTime").toString();
        String eTime = formParams.get("eTime") == null ? "" : formParams.get("eTime").toString();
        String symbol = formParams.get("symbol") == null ? "" : formParams.get("symbol").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_tlb_mt_price ");

        if (StringUtils.isNotBlank(symbol)) {
            cond.append(" and c_symbol = '"+symbol+"' ");
        }
        if (StringUtils.isNotBlank(bTime)) {
            cond.append(" and TO_DAYS(c_time) >= TO_DAYS('"+bTime+"') ");
        }
        if (StringUtils.isNotBlank(eTime)) {
            cond.append(" and TO_DAYS(c_time) <= TO_DAYS('"+eTime+"') ");
        }

        String orderBy="";
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(TlbMtPrice.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TlbMtPriceDTO> page= pageCommon.execPage(column.toString(), cond.toString().replaceFirst("and","where"),orderBy, page_number, page_size, TlbMtPriceDTO.class);
        return page;
    }

    @Override
    public TlbMtPriceDTO findOne(String id) {
        return tlbMtPriceMapper.toDto(tlbMtPriceRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        tlbMtPriceRepository.delete(id);
    }

    @Override
    public TlbMtPriceDTO findBySymbol(String symbol) {
        return tlbMtPriceMapper.toDto(tlbMtPriceRepository.findTlbMtPriceBySymbolEquals(symbol));
    }

    @Override
    public List<TlbMtPriceDTO> findAll() {
        return tlbMtPriceMapper.toDto(tlbMtPriceRepository.findAll());
    }

}
