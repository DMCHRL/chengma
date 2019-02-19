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

    @Autowired
    private HppMobileUserService hppMobileUserService;

    @Autowired
    private HppStrategyOrderService hppStrategyOrderService;

    @Autowired
    private HppTrainApplyService hppTrainApplyService;

    @Autowired
    private HppExamApplyService hppExamApplyService;

    @Autowired
    private HppUserService hppUserService;

    @Autowired
    private  WxOrderService wxOrderService;

    /**
     * 导出手机用户信息
     * @param response
     * @param params
     */
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
    }

    /**
     * 导出课程报名信息
     * @param response
     * @param params
     */
    @PostMapping("/export/courseApply")
    @ResponseBody
    public void courseApply(HttpServletResponse response, @RequestBody HashMap<String,Object> params){
        response.setHeader("Access-Control-Allow-Origin", "*");
       /* response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setHeader("Access-Control-Allow-Methods", "POST");*/
        String[] headArray = {"姓名","手机","邮箱","报名时间","培训名称","套餐","套餐包含","价格","支付方式","支付状态"};
        List<HppTrainApplyDTO> list =  hppTrainApplyService.pageList(params).getContent();
        List<Object[]> contentList = new ArrayList<>();
        if(list.size()>0) {
            for (HppTrainApplyDTO entity : list) {
                Object[] o = {
                        entity.getUsername(),
                        entity.getPhone(),
                        entity.getEmail(),
                        entity.getCreateAt(),
                        entity.getTrainName(),
                        entity.getMealName(),
                        entity.getMealInclude(),
                        entity.getMealPrice(),
                        entity.getPayType().equals("weChat") ? "微信支付":"积分兑换",
                        entity.getStatus().equals("Y") ? "已支付":"未支付",
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

    /**
     * 导出课程报名信息
     * @param response
     * @param params
     */
    @PostMapping("/export/examApply")
    @ResponseBody
    public void examApply(HttpServletResponse response, @RequestBody HashMap<String,Object> params){
        String[] headArray = {"姓名","手机","邮箱","报名时间","考证名称","套餐","套餐包含","价格","支付方式","支付状态"};
        List<HppExamApplyDTO> list =  hppExamApplyService.pageList(params).getContent();
        List<Object[]> contentList = new ArrayList<>();
        if(list.size()>0) {
            for (HppExamApplyDTO entity : list) {
                Object[] o = {
                        entity.getUsername(),
                        entity.getPhone(),
                        entity.getEmail(),
                        entity.getCreateAt(),
                        entity.getExamName(),
                        entity.getMealName(),
                        entity.getMealInclude(),
                        entity.getMealPrice(),
                        entity.getPayType().equals("weChat") ? "微信支付":"积分兑换",
                        entity.getStatus().equals("Y") ? "已支付":"未支付",
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

    /**
     * 导出开户审核信息
     * @param response
     * @param params
     */
    @PostMapping("/export/hppUserApply")
    @ResponseBody
    public void hppUserApply(HttpServletResponse response, @RequestBody HashMap<String,Object> params){
        String[] headArray = {"姓名","手机号","邮箱","推荐码","身份证明号码","其他证件类型","银行卡号","开户银行","银行支行","汇商","状态","申请时间","处理人","处理时间",};
        List<HppUserDTO> list =  hppUserService.pageList(params).getContent();
        List<Object[]> contentList = new ArrayList<>();
        if(list.size()>0) {
            for (HppUserDTO entity : list) {
                //driver charge passport
                String type="";
                if(entity.getSecondType() != null){
                    switch (entity.getSecondType()){
                        case "driver": type = "驾驶证";break;
                        case "charge" : type = "水费或电费发票";break;
                        default:type = "护照及其他";
                    }
                }

                String status="";
                if(entity.getStatus() != null){
                    switch (entity.getStatus()){
                        case "APPLYING": status = "申请中";break;
                        case "PASSED" : status = "已通过";break;
                        default:status = "拒绝";
                    }
                }
                Object[] o = {
                        entity.getUsername(),
                        entity.getPhone(),
                        entity.getEmail(),
                        entity.getRecommendation(),
                        entity.getIdNumber(),
                        type,
                        entity.getCardNumber(),
                        entity.getOpeningBank(),
                        entity.getBranch(),
                        entity.getSinksName(),
                        status,
                        entity.getCreateAt(),
                        entity.getApprovedname(),
                        entity.getApprovedAt(),
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

    /**
     * 导出策略跟单解绑信息
     * @param response
     * @param params
     */
    @PostMapping("/export/hppStrategyOrder")
    @ResponseBody
    public void hppStrategyOrder(HttpServletResponse response, @RequestBody HashMap<String,Object> params){
        String[] headArray = {"名称","类型","联系方式","账号","时间","状态"};
        List<HppStrategyOrderDTO> list =  hppStrategyOrderService.pageList(params).getContent();
        List<Object[]> contentList = new ArrayList<>();
        if(list.size()>0) {
            for (HppStrategyOrderDTO entity : list) {
                //IN or OUT
                String type="";
                if(entity.getType() != null){
                    switch (entity.getType()){
                        case "IN": type = "跟单";break;
                        case "OUT" : type = "解绑";break;
                        default:type = "不明";
                    }
                }

                String status="";
                if(entity.getStatus() != null){
                    switch (entity.getStatus()){
                        case "APPLYING": status = "申请中";break;
                        case "PASSED" : status = "已通过";break;
                        default:status = "拒绝";
                    }
                }
                Object[] o = {
                        entity.getStrategyName(),
                        type,
                        entity.getMail(),
                        entity.getAccount(),
                        entity.getCreateAt(),
                        status,
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

    /**
     * 导出订单记录
     * @param response
     * @param params
     */
    @PostMapping("/export/wxOrder")
    @ResponseBody
    public void wxOrder(HttpServletResponse response, @RequestBody HashMap<String,Object> params){
        String[] headArray = {"订单号","商品类别","商品名称","商品详情","应付价格(￥)","积分抵扣","实付(￥)","下单时间","联系方式","支付方式","支付状态"};
        List<WxOrderDTO> list =  wxOrderService.pageList(params).getContent();
        List<Object[]> contentList = new ArrayList<>();
        if(list.size()>0) {
            for (WxOrderDTO entity : list) {
                //IN or OUT
                String body="";
                if(entity.getBody() != null){
                    switch (entity.getBody()){
                        case "video": body = "交易战法视频";break;
                        case "course" : body = "交易面对面";break;
                        case "exam" : body = "交易师考证";break;
                        default:body = "不明";
                    }
                }

                String status="";
                if(entity.getStatus() != null){
                    switch (entity.getStatus()){
                        case "Y": status = "已支付";break;
                        case "N" : status = "未支付";break;
                        default:status = "不明";
                    }
                }

                String payType="";
                if(entity.getPayType() != null){
                    switch (entity.getPayType()){
                        case "weChat": payType = "微信支付";break;
                        case "integral" : payType = "积分兑换";break;
                        case "mix" : payType = "积分抵扣";break;
                        default:payType = "不明";
                    }
                }
                Object[] o = {
                        entity.getOutTradeNo(),
                        body,
                        entity.getBodyName(),
                        entity.getBodyDetail(),
                        entity.getPay(),
                        entity.getIntegral(),
                        entity.getTotalFee(),
                        entity.getTimeStart(),
                        entity.getObject(),
                        payType,
                        status,
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
