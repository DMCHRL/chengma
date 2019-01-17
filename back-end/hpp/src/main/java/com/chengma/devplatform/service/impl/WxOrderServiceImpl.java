package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.repository.BankInfoRepository;
import com.chengma.devplatform.repository.WxOrderRepository;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.WxOrderService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.service.dto.WxOrderDTO;
import com.chengma.devplatform.service.mapper.BankInfoMapper;
import com.chengma.devplatform.service.mapper.WxOrderMapper;
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
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class WxOrderServiceImpl implements WxOrderService {

    private final WxOrderRepository wxOrderRepository;

    private final WxOrderMapper wxOrderMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    public WxOrderServiceImpl(WxOrderRepository wxOrderRepository, WxOrderMapper wxOrderMapper){
        this.wxOrderRepository=wxOrderRepository;
        this.wxOrderMapper=wxOrderMapper;
    }

    @Override
    public Page<WxOrderDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String object = formParams.get("object") == null ? "" : formParams.get("object").toString();
        String outTradeNo = formParams.get("outTradeNo") == null ? "" : formParams.get("outTradeNo").toString();
        String body = formParams.get("body") == null ? "" : formParams.get("body").toString();
        String bodyDetail = formParams.get("bodyDetail") == null ? "" : formParams.get("bodyDetail").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String payType = formParams.get("payType") == null ? "" : formParams.get("payType").toString();
        String startTime = formParams.get("startTime") == null ? "" : formParams.get("startTime").toString();
        String endTime = formParams.get("endTime") == null ? "" : formParams.get("endTime").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_wx_order where 1=1 ");

        //非超级管理员
       /* User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and t.c_user_id='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(object)){
            cond.append(" and c_object like '%"+object+"%' ");
        }

        if(StringUtils.isNotBlank(body)){
            cond.append(" and c_body ='"+body+"' ");
        }

        if(StringUtils.isNotBlank(status)){
            cond.append(" and c_status ='"+status+"' ");
        }

        if(StringUtils.isNotBlank(outTradeNo)){
            cond.append(" and c_out_trade_no like '%"+outTradeNo+"%' ");
        }

        if(StringUtils.isNotBlank(payType)){
            cond.append(" and c_pay_type ='"+payType+"' ");
        }

        if(StringUtils.isNotBlank(bodyDetail)){
            cond.append(" and c_body_detail like '%"+bodyDetail+"%' ");
        }
        if(StringUtils.isNotBlank(startTime)){
            cond.append(" and TO_DAYS(d_time_start)>=TO_DAYS('"+startTime+"') ");
        }

        if(StringUtils.isNotBlank(endTime)){
            cond.append(" and TO_DAYS(d_time_start)<=TO_DAYS('"+endTime+"') ");
        }
        cond.append("  order by d_time_start desc ");
        /*String orderBy="";
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "h."+ ReflectUtils.getColumnName(HppVideo.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }*/
        Page<WxOrderDTO> page = pageCommon.execPage(column.toString(), cond.toString(),page_number, page_size, WxOrderDTO.class);
        return page;
    }

    @Override
    public WxOrderDTO save(WxOrderDTO wxOrderDTO) {
        return wxOrderMapper.toDto(wxOrderRepository.save(wxOrderMapper.toEntity(wxOrderDTO)));
    }

    @Override
    public WxOrderDTO createWxOrderDTO(WxOrderDTO wxOrderDTO) {
        //添加
        if(StringUtils.isBlank(wxOrderDTO.getId())){

        }else{
            //修改
        }

        return wxOrderMapper.toDto(wxOrderRepository.save(wxOrderMapper.toEntity(wxOrderDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateWxOrderDTO(WxOrderDTO wxOrderDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public WxOrderDTO findOne(String id) {
        return wxOrderMapper.toDto(wxOrderRepository.findOne(id));
    }

    @Override
    public List<WxOrderDTO> findAll() {
        return wxOrderMapper.toDto(wxOrderRepository.findAll());
    }

    @Override
    public void delete(String id) {
        wxOrderRepository.delete(id);
    }


    @Override
    public WxOrderDTO countNum(String bodyParentId) {
        String sql = "SELECT\n" +
                "\ta.total,\n" +
                "\tb.month_total\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(*) AS total\n" +
                "\t\tFROM\n" +
                "\t\t\tt_wx_order\n" +
                "\t\tWHERE\n" +
                "\t\t\tc_body_parent_id = '"+bodyParentId+"'\n" +
                "\t\tAND c_status = 'Y'\n" +
                "\t) a,\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(*) AS month_total\n" +
                "\t\tFROM\n" +
                "\t\t\tt_wx_order\n" +
                "\t\tWHERE\n" +
                "\t\t\tDATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(d_time_start)\n" +
                "\t\tAND c_body_parent_id = '"+bodyParentId+"'\n" +
                "\t\tAND c_status = 'Y'\n" +
                "\t) b\n";
        List<WxOrderDTO> list = baseDao.findListBySql(sql,WxOrderDTO.class);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
}