package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbCommissionTrace;
import com.chengma.devplatform.repository.TlbCommissionTraceRepository;
import com.chengma.devplatform.service.TlbCommissionTraceService;
import com.chengma.devplatform.service.dto.TlbCommissionTraceDTO;
import com.chengma.devplatform.service.mapper.TlbCommissionTraceMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class TlbCommissionTraceServiceImpl implements TlbCommissionTraceService {

    private final Logger log = LoggerFactory.getLogger(TlbCommissionTraceServiceImpl.class);

    private final TlbCommissionTraceRepository tlbCommissionTraceRepository;
    private final TlbCommissionTraceMapper tlbCommissionTraceMapper;



    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private MT4Service mt4Service;

    public TlbCommissionTraceServiceImpl(TlbCommissionTraceRepository tlbCommissionTraceRepository, TlbCommissionTraceMapper tlbCommissionTraceMapper) {
        this.tlbCommissionTraceRepository = tlbCommissionTraceRepository;
        this.tlbCommissionTraceMapper = tlbCommissionTraceMapper;
    }

    @Override
    public TlbCommissionTraceDTO save(TlbCommissionTraceDTO tlbCommissionTraceDTO) {
        TlbCommissionTrace tlbCommissionTrace = tlbCommissionTraceMapper.toEntity(tlbCommissionTraceDTO);
        TlbCommissionTrace result=tlbCommissionTraceRepository.save(tlbCommissionTrace);
        return tlbCommissionTraceMapper.toDto(result);
    }

    @Override
    public Page<TlbCommissionTraceDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");
        String commissionId = formParams.get("commissionId") == null ? "" : formParams.get("commissionId").toString();

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" SELECT\n" +
                "\ttct.*, tt.i_lots lots,\n" +
                "\ttt.c_order_type orderType,\n" +
                "\ttb.c_account_name accountName,\n" +
                "\ttb.c_email email,\n" +
                "\ttb.c_user_id userId,\n" +
                "\tu.login login,\n" +
                "\tu.department department,\n" +
                "\tu.c_mobile mobile  ");

        StringBuilder cond = new StringBuilder(" FROM\n" +
                "\tt_tlb_commission_trace tct,\n" +
                "\tt_tlb_trade tt,\n" +
                "\tt_tlb_account tb,\n" +
                "\tjhi_user u\n" +
                "WHERE\n" +
                "\ttct.i_order_no = tt.i_order_no\n" +
                "AND tb.c_account = tt.c_account\n" +
                "AND u.id=tb.c_user_id ");

        if (StringUtils.isNotBlank(commissionId)) {
            cond.append(" AND tct.t_commission_id = '"+commissionId+"' ");
        }

       String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(TlbCommissionTrace.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TlbCommissionTraceDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy,  page_number, page_size, TlbCommissionTraceDTO.class);
        return page;
    }
}
