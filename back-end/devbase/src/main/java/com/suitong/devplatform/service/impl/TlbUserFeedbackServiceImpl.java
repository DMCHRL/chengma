package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.domain.TlbUserFeedback;
import com.suitong.devplatform.repository.TlbUserFeedbackRepository;
import com.suitong.devplatform.service.TlbUserFeedbackService;
import com.suitong.devplatform.service.dto.TlbUserFeedbackDTO;
import com.suitong.devplatform.service.mapper.TlbUserFeedbackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public HashMap<String, Object> save(TlbUserFeedbackDTO tlbUserFeedbackDTO) {
        log.debug("Request to save SysForm : {}", tlbUserFeedbackDTO);
        HashMap<String,Object> response = new HashMap<>();
        try {
            TlbUserFeedback tlbUserFeedback = tlbUserFeedbackMapper.toEntity(tlbUserFeedbackDTO);
            tlbUserFeedback = tlbUserFeedbackRepository.save(tlbUserFeedback);

            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    @Override
    public Page<TlbUserFeedbackDTO> pageList(Pageable pageable) {
        return null;
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
    public TlbUserFeedbackDTO findOne(Long id) {
        return tlbUserFeedbackMapper.toDto(tlbUserFeedbackRepository.findOne(id));
    }

    @Override
    public void delete(Long id) {

    }
}
