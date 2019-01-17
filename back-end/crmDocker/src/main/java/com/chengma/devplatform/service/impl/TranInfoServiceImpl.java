package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TranInfo;
import com.chengma.devplatform.repository.TranInfoRepository;
import com.chengma.devplatform.service.TranInfoService;
import com.chengma.devplatform.service.dto.TranInfoDTO;
import com.chengma.devplatform.service.mapper.TranInfoMapper;
import com.chengma.devplatform.xxpay.XXPayApi;
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
public class TranInfoServiceImpl implements TranInfoService {

    private final Logger log = LoggerFactory.getLogger(TranInfoServiceImpl.class);

    private final TranInfoRepository tranInfoRepository;

    private final TranInfoMapper tranInfoMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;

    public TranInfoServiceImpl(TranInfoRepository tranInfoRepository, TranInfoMapper tranInfoMapper) {
        this.tranInfoRepository = tranInfoRepository;
        this.tranInfoMapper = tranInfoMapper;
    }

    /**
     * Save a tranInfo.
     */
    @Override
    public ResponseResult save(TranInfoDTO tranInfoDTO) {
        ResponseResult response = new ResponseResult();
        TranInfo tranInfo = tranInfoMapper.toEntity(tranInfoDTO);
        tranInfo = tranInfoRepository.save(tranInfo);
        if (null != tranInfo) {
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        } else {
            response.setMsgCode("default");
        }
        return response;
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
        //cond.append(" order by c_out_trade_no desc ");
        //String orderBy = " order by c_out_trade_no desc ";

        String orderBy="";
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(TranInfo.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TranInfoDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TranInfoDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("list", page.getContent());
        response.put("page_number", page_number);
        response.put("page_size", page_size);
        return response;
    }

    /**
     * Get one tranInfo by id.
     */
    @Override
    @Transactional(readOnly = true)
    public TranInfoDTO findOne(String id) {
        TranInfo tranInfo = tranInfoRepository.findOne(id);
        return tranInfoMapper.toDto(tranInfo);
    }

    /**
     * findByCode
     */
    @Override
    @Transactional(readOnly = true)
    public TranInfoDTO findByCode(TranInfoDTO tranInfoDTO) {
        StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        cond.append(" and c_out_trade_no = '" + tranInfoDTO.getOutTradeNo() + "' ");

        List<TranInfoDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TranInfoDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public TranInfoDTO findByParams(HashMap<String, Object> params) {
        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        if(null != params.get("payOrderId")){
            cond.append(" and c_transaction_id = '" + params.get("payOrderId") + "' ");
        }

        cond.append(" and c_appid = '" + XXPayApi.xxPayApi.xxpayProperties.getAppId() + "' ");
        cond.append(" and c_mch_id = '" + XXPayApi.xxPayApi.xxpayProperties.getMchId() + "' ");

        List<TranInfoDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TranInfoDTO.class);
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
        tranInfoRepository.delete(id);
    }
}

