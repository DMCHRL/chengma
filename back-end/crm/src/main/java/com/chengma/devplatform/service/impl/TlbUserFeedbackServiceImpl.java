package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbUserFeedback;
import com.chengma.devplatform.repository.TlbUserFeedbackRepository;
import com.chengma.devplatform.service.TlbUserFeedbackService;
import com.chengma.devplatform.service.dto.TlbUserFeedbackDTO;
import com.chengma.devplatform.service.mapper.TlbUserFeedbackMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class TlbUserFeedbackServiceImpl implements TlbUserFeedbackService {

    private final Logger log = LoggerFactory.getLogger(TlbUserFeedbackServiceImpl.class);

    private final TlbUserFeedbackRepository tlbUserFeedbackRepository;

    private final TlbUserFeedbackMapper tlbUserFeedbackMapper;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private DBService dbService;

    public TlbUserFeedbackServiceImpl(TlbUserFeedbackRepository tlbUserFeedbackRepository, TlbUserFeedbackMapper tlbUserFeedbackMapper) {
        this.tlbUserFeedbackRepository = tlbUserFeedbackRepository;
        this.tlbUserFeedbackMapper = tlbUserFeedbackMapper;    }


    @Override
    public TlbUserFeedbackDTO save(TlbUserFeedbackDTO tlbUserFeedbackDTO) {
        log.debug("Request to save UserFeedBack : {}", tlbUserFeedbackDTO);
        tlbUserFeedbackDTO.setCreateAt(new Date());
        TlbUserFeedback tlbUserFeedback = tlbUserFeedbackMapper.toEntity(tlbUserFeedbackDTO);
        tlbUserFeedback = tlbUserFeedbackRepository.save(tlbUserFeedback);
        return tlbUserFeedbackMapper.toDto(tlbUserFeedback);
    }

    @Override
    public Page<TlbUserFeedbackDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String userName = formParams.get("userName") == null ? "" : formParams.get("userName").toString();
        String tlbAccount = formParams.get("tlbAccount") == null ? "" : formParams.get("tlbAccount").toString();
        String feedbackType = formParams.get("feedbackType") == null ? "" : formParams.get("feedbackType").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_tlb_user_feedback ");

        if (StringUtils.isNotBlank(userName)) {
            cond.append(" and c_username LIKE '%"+userName+"%' ");
        }
        if (StringUtils.isNotBlank(tlbAccount)) {
            cond.append(" and c_tlb_account LIKE '%"+tlbAccount+"%' ");
        }
        if (StringUtils.isNotBlank(feedbackType)) {
            cond.append(" and c_feedback_type LIKE '%"+feedbackType+"%' ");
        }
        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(TlbUserFeedback.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TlbUserFeedbackDTO> page = pageCommon.execPage(column.toString(), cond.toString().replaceFirst("and","where"),orderBy.toString(), page_number, page_size, TlbUserFeedbackDTO.class);
        return page;

    }

    @Override
    public List<TlbUserFeedbackDTO> findAll(HashMap<String, Object> params) {

        List<TlbUserFeedback> tlbUserFeedbacks = tlbUserFeedbackRepository.findAll();

        List<TlbUserFeedbackDTO> tlbUserFeedbackDTOS = new ArrayList<>();
        for(TlbUserFeedback tlbUserFeedback : tlbUserFeedbacks){
            tlbUserFeedbackDTOS.add(tlbUserFeedbackMapper.toDto(tlbUserFeedback));
        }

        return tlbUserFeedbackDTOS;
    }


    @Override
    public TlbUserFeedbackDTO findOne(String id) {
        return tlbUserFeedbackMapper.toDto(tlbUserFeedbackRepository.findOne(id));
    }

    @Override
    public void delete(String id) {

    }
}
