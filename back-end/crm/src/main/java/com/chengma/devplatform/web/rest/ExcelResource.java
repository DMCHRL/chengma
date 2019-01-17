package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.ExeclUtil;
import com.chengma.devplatform.service.TlbTradeService;
import com.chengma.devplatform.service.dto.TlbTradeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing BankInfo.
 */
@RestController
@RequestMapping("/api")
public class ExcelResource {

    private final Logger log = LoggerFactory.getLogger(ExcelResource.class);

    @Autowired
    private TlbTradeService tlbTradeService;

    @PostMapping("/export")
    @ResponseBody
    public void exportSiteDeclareList(HttpServletResponse response, @RequestBody HashMap<String,Object> params){
        String[] headArray = {"运单号","理赔情况","申报网点名称","省区","申报类型","责任类型",
                "仲裁登记时间","寄件日期","仲裁审核日期","分单人","分单时间"};
        HashMap<String,Object>  tlbTrades = tlbTradeService.pageList(params);
        Page<TlbTradeDTO> page = (Page<TlbTradeDTO>)tlbTrades.get("page");
        List<TlbTradeDTO> list = page.getContent();
        List<Object[]> contentList = new ArrayList<>();
        if(list.size()>0) {
            for (TlbTradeDTO entity : list) {
                Object[] o = {
                        entity.getAccount(),//"运单号
                        entity.getCalcSum(),//理赔情况
                        entity.getClosed(),//申报网点名称
                        entity.getClosePrice(),//省区
                        entity.getCloseTime(),//申报类型
                        entity.getGain(),//责任类型
                        entity.getLots(),//仲裁登记时间
                        entity.getOrderType(),//寄件日期
                        entity.getOrderType(),//仲裁审核日期
                        entity.getSymbol(),//分单人
                        entity.getSl()//分单时间
                };
                contentList.add(o);
            }
        }else{
            return;
        }
        try {
            ExeclUtil.ExportExcel(response, headArray, contentList, "SubmenuList.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
