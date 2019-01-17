package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.util.ExeclUtil;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
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



    /**
     * 导出手机用户信息
     * @param response
     * @param params
     *//*
    @PostMapping("/export/mobileUser")
    @ResponseBody
    public void exportSiteDeclareList(HttpServletResponse response, @RequestBody HashMap<String,Object> params){
        String[] headArray = {"昵称","联系方式","推荐码","推荐人数","分享次数","当前积分","跟单用户","消费用户","开户用户"};
        List<HppMobileUserDTO> list =  hppMobileUserService.pageList(params).getContent();
        List<Object[]> contentList = new ArrayList<>();
        if(list.size()>0) {
            for (HppMobileUserDTO entity : list) {
                Object[] o = {
                        entity.getUserName(),
                        entity.getPhone(),
                        entity.getRecommendation(),
                        entity.getRecommendationTotal(),
                        entity.getShareTotal(),
                        entity.getIntegral(),
                        entity.getFollowFlag().equals("N")  ? "否" : "是",
                        entity.getBuyFlag().equals("N")  ? "否" : "是",
                        entity.getOpenFlag().equals("N")  ? "否" : "是",
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
    }*/


}
