package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.repository.BindBankRepository;
import com.chengma.devplatform.service.BindBankService;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.mapper.BindBankMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class BindBankServiceImpl implements BindBankService {

    private final Logger log = LoggerFactory.getLogger(BindBankServiceImpl.class);

    private final BindBankRepository bindBankRepository;

    private final BindBankMapper bindBankMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;

    public BindBankServiceImpl(BindBankRepository bindBankRepository, BindBankMapper bindBankMapper) {
        this.bindBankRepository = bindBankRepository;
        this.bindBankMapper = bindBankMapper;
    }

    /**
     * Save a bindBank.
     */
    @Override
    public BindBankDTO save(BindBankDTO bindBankDTO) {
        ResponseResult response = new ResponseResult();
        BindBank bindBank = bindBankMapper.toEntity(bindBankDTO);
        bindBank = bindBankRepository.save(bindBank);
        if (null != bindBank) {
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        } else {
            response.setMsgCode("default");
        }
        return bindBankMapper.toDto(bindBank);
    }

    /**
     * findAll
     */
    @Override
    @Transactional(readOnly = true)
    public HashMap<String, Object> findAll(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String outTradeNo = formParams.get("outTradeNo") == null ? "" : formParams.get("outTradeNo").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();


        StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        if (StringUtils.isNotBlank(outTradeNo)) {
            cond.append(" and c_out_trade_no like '%" + outTradeNo + "%' ");
        }
       /* cond.append(" order by c_out_trade_no desc ");
        String orderBy = " order by c_out_trade_no desc ";*/

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(BindBank.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<BindBankDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, BindBankDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("list", page.getContent());
        response.put("page_number", page_number);
        response.put("page_size", page_size);

        return response;
    }

    /**
     * Get one bindBank by id.
     */
    @Override
    @Transactional(readOnly = true)
    public BindBankDTO findOne(String id) {
        BindBank bindBank = bindBankRepository.findOne(id);
        return bindBankMapper.toDto(bindBank);
    }

    /**
     * findByCode
     */
    @Override
    @Transactional(readOnly = true)
    public BindBankDTO findByCode(BindBankDTO bindBankDTO) {
    /*    StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        cond.append(" and c_out_trade_no = '" + bindBankDTO.getOutTradeNo() + "' ");

        List<BindBankDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), BindBankDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;*/
        return null;
    }

    @Override
    public BindBankDTO findOneByParam(HashMap<String, Object> params) {
        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_bind_bank where 1 = 1 ");

        if(null != params.get("banksn")){
            cond.append(" and c_banksn = '" + params.get("banksn") + "' ");
        }
        if(null != params.get("idcardsn")){
            cond.append(" and c_idcardsn = '" + params.get("idcardsn") + "' ");
        }
        if(null != params.get("mobile")){
            cond.append(" and c_mobile = '" + params.get("mobile") + "' ");
        }

        List<BindBankDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), BindBankDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * delete
     */
    @Override
    public void delete(String id) {
        bindBankRepository.delete(id);
    }
}
