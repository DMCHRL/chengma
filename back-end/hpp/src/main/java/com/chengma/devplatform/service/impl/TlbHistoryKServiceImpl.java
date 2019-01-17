package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.TlbHistoryK;
import com.chengma.devplatform.repository.TlbHistoryKRepository;
import com.chengma.devplatform.service.TlbHistoryKService;
import com.chengma.devplatform.service.dto.TlbHistoryKDTO;
import com.chengma.devplatform.service.mapper.TlbHistoryKMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class TlbHistoryKServiceImpl implements TlbHistoryKService {

    private final Logger log = LoggerFactory.getLogger(TlbHistoryKServiceImpl.class);

    private final TlbHistoryKRepository tlbHistoryKRepository;

    private final TlbHistoryKMapper tlbHistoryKMapper;



    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private DBService dbService;

    public TlbHistoryKServiceImpl(TlbHistoryKRepository tlbHistoryKRepository, TlbHistoryKMapper tlbHistoryKMapper) {
        this.tlbHistoryKRepository = tlbHistoryKRepository;
        this.tlbHistoryKMapper = tlbHistoryKMapper;
    }

    @Override
    public TlbHistoryKDTO save(TlbHistoryKDTO tlbHistoryKDTO) {
        tlbHistoryKDTO.setTime(new Date());
        TlbHistoryK tlbHistoryK =tlbHistoryKMapper.toEntity(tlbHistoryKDTO);
        tlbHistoryKRepository.save(tlbHistoryK);
        return tlbHistoryKMapper.toDto(tlbHistoryK);
    }

    @Override
    public Page<TlbHistoryKDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String bTime = formParams.get("bTime") == null ? "" : formParams.get("bTime").toString();
        String eTime = formParams.get("eTime") == null ? "" : formParams.get("eTime").toString();
        String symbol = formParams.get("symbol") == null ? "" : formParams.get("symbol").toString();
        String cycle = formParams.get("cycle") == null ? "" : formParams.get("cycle").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_tlb_history_k ");

        if (StringUtils.isNotBlank(symbol)) {
            cond.append(" and c_symbol = '"+symbol+"' ");
        }
        if (StringUtils.isNotBlank(symbol)) {
            cond.append(" and c_cycle LIKE '"+cycle+"' ");
        }
        if (StringUtils.isNotBlank(bTime)) {
            cond.append(" and d_time >= '"+bTime+"' ");
        }
        if (StringUtils.isNotBlank(eTime)) {
            cond.append(" and d_time <= '"+eTime+"' ");
        }

        String orderBy  = " order by d_time desc ";

        Page<TlbHistoryKDTO> page = pageCommon.execPage(column.toString(), cond.toString().replaceFirst("and","where"), orderBy,  page_number, page_size, TlbHistoryKDTO.class);
        return page;
    }

    @Override
    public TlbHistoryKDTO findOne(String id) {
        return tlbHistoryKMapper.toDto(tlbHistoryKRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        tlbHistoryKRepository.delete(id);
    }

}
