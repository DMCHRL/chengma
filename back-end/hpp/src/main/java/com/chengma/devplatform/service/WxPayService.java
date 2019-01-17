package com.chengma.devplatform.service;

import com.alibaba.fastjson.JSON;
import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.PayCommonUtil;
import com.chengma.devplatform.config.WxPayProperties;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.WxOrderRepository;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.WxOrderMapper;
import com.chengma.devplatform.web.rest.WxOrderResource;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by Administrator on 2018/9/19.
 */
@Service
@Transactional
public class WxPayService {

    private final Logger log = LoggerFactory.getLogger(WxPayService.class);

    @Autowired
    private PayCommonUtil payCommonUtil;

    @Autowired
    private WxPayProperties wxPayProperties;

    @Autowired
    private HppCourseMealService hppCourseMealService;

    @Autowired
    private HppCourseService hppCourseService;

    @Autowired
    private HppTrainApplyService hppTrainApplyService;

    @Autowired
    private HppExamService hppExamService;

    @Autowired
    private HppExamApplyService hppExamApplyService;

    @Autowired
    private HppVideoService hppVideoService;

    @Autowired
    private HppIntegralService hppIntegralService;

    @Autowired
    private HppIntegralDetailService hppIntegralDetailService;

    @Autowired
    private HppVideoTypeService hppVideoTypeService;

    @Autowired
    private HppExchangeService hppExchangeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    private final WxOrderRepository wxOrderRepository;

    private final WxOrderMapper wxOrderMapper;

    public WxPayService(WxOrderRepository wxOrderRepository,WxOrderMapper wxOrderMapper) {
        this.wxOrderRepository = wxOrderRepository;
        this.wxOrderMapper = wxOrderMapper;
    }


    public HashMap<String,Object> checkPay(HashMap<String, Object> params) {
        HashMap<String,Object> retMap =new HashMap<>();
        String body = params.get("body") == null ? "" : (String) params.get("body");
        String id = params.get("id") == null ? "" : (String) params.get("id");
        String payType = params.get("payType") == null ? "" : (String) params.get("payType");
        User user = userService.getUserWithAuthorities();
        if(user == null || user.getMobile() == null ) {
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录...");
            return retMap;
        }

        if(wxOrderRepository.findByBodyIdEqualsAndStatusEqualsAndObjectEquals(id,"N",user.getMobile()) != null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "已有订单,请先行处理...");
            return retMap;
        }

        //检查积分兑换条件
        HppIntegralDTO hppIntegralDTO =hppIntegralService.findByMobileNum(user.getMobile());
        Double price = hppIntegralDTO.getBalance();

        if (body.equals(DevplatformConstants.BODY_TYPE_COURSE)) {
            HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(id);
            if (hppCourseMealDTO == null || hppCourseMealDTO.getPrice() < 0) {
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "付款金额错误!");
                return retMap;
            }
            if(payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL)) {
                if (hppCourseMealDTO.getPrice() > price) {
                    retMap.put("statusCode", ResponseResult.FAIL_CODE);
                    retMap.put("msg", "积分余额不足");
                    return retMap;
                }
            }
        }else if (body.equals(DevplatformConstants.BODY_TYPE_VIDEO)) {
            HppVideoDTO hppVideoDTO = hppVideoService.findOne(id);
            if (hppVideoDTO == null || hppVideoDTO.getPrice() < 0) {
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "付款金额错误!");
                return retMap;
            }
            if(payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL)) {
                if (hppVideoDTO.getPrice() > price) {
                    retMap.put("statusCode", ResponseResult.FAIL_CODE);
                    retMap.put("msg", "积分余额不足");
                    return retMap;
                }
            }
        }else if (body.equals(DevplatformConstants.BODY_TYPE_EXAM)) {
            HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(id);
            if (hppCourseMealDTO == null || hppCourseMealDTO.getPrice() < 0) {
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "付款金额错误!");
                return retMap;
            }
            if(payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL)) {
                if (hppCourseMealDTO.getPrice() > price) {
                    retMap.put("statusCode", ResponseResult.FAIL_CODE);
                    retMap.put("msg", "积分余额不足");
                    return retMap;
                }
            }
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }
    /**
     * 积分兑换，微信支付入口，若是微信支付则拉起预付单
     * @param request
     * @param params
     * @return
     */
    public HashMap<String,Object> getWXPay(HttpServletRequest request, HashMap<String, Object> params) {
        HashMap<String, Object> result = new HashMap<>();
        String body = params.get("body") == null ? "" : (String) params.get("body");
        String id = params.get("id") == null ? "" : (String) params.get("id");
        String payType = params.get("payType") == null ? "" : (String) params.get("payType");

        User user = userService.getUserWithAuthorities();
        String mobile = user.getMobile();

        WxOrderDTO wxOrderDTO = new WxOrderDTO(); // 订单

        String bodyName=null;

        //处理支付金额
        Double pay =0.0;      //总金金额
        Double totalFee =0.0; //微信支付金额
        Double integral =0.0; //积分支付金额

        if (body.equals(DevplatformConstants.BODY_TYPE_COURSE)) {
            HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(id);
            pay=hppCourseMealDTO.getPrice();
            HppCourseDTO hppCourseDTO = hppCourseService.findByCourseMealId(id);
            wxOrderDTO.setBodyName(hppCourseDTO.getTrainName());
            wxOrderDTO.setBodyImg(hppCourseDTO.getImg());
            wxOrderDTO.setBodyParentId(hppCourseDTO.getId());
            wxOrderDTO.setBodyDetail(hppCourseMealDTO.getName());
            wxOrderDTO.setBodyId(hppCourseMealDTO.getId()); //套餐id

            bodyName = hppCourseDTO.getTrainName();

            if (payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL) || payType.equals(DevplatformConstants.PAY_TYPE_MIX) ) {
                HppIntegralDTO hppIntegralDTO =hppIntegralService.findByMobileNum(user.getMobile());
                integral = hppIntegralDTO.getBalance(); //用户积分余额

                //课程最高可抵扣
                payType = DevplatformConstants.PAY_TYPE_MIX;
                if(pay >= 0 && pay < DevplatformConstants.FLAG_COURSE_C){
                    if(integral >= DevplatformConstants.MIX_COURSE_C) integral = DevplatformConstants.MIX_COURSE_C;
                }else if(pay >DevplatformConstants.FLAG_COURSE_C && pay < DevplatformConstants.FLAG_COURSE_B ){
                    if(integral >= DevplatformConstants.MIX_COURSE_B) integral = DevplatformConstants.MIX_COURSE_B;
                }else if(pay >DevplatformConstants.FLAG_COURSE_B && pay < DevplatformConstants.FLAG_COURSE_A){
                    if(integral >= DevplatformConstants.MIX_COURSE_A) integral = DevplatformConstants.MIX_COURSE_A;
                }
                if(pay >= integral){
                    totalFee = pay - integral;
                }else{
                    totalFee = pay;
                    integral = 0.0;
                }
                if (payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL)){

                    //扣减积分
                    hppIntegralService.reduceIntegral(mobile, integral);
                    HppIntegralDetailDTO out = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_OUT, DevplatformConstants.INTEGRAL_DETAIL_TYPE_COURSE, integral, null);
                    hppIntegralDetailService.createHppIntegralDetailDTO(out);

                    //保存个人兑换信息
                    HppExchangeDTO hppExchangeDTO = new HppExchangeDTO();
                    int exchangeNum = hppCourseDTO.getExchangeNum() == null ? 0 : hppCourseDTO.getExchangeNum();
                    hppCourseDTO.setExchangeNum(exchangeNum + 1);
                    hppCourseService.save(hppCourseDTO);

                    hppExchangeDTO.setBodyId(hppCourseDTO.getId());
                    hppExchangeDTO.setCreateAt(new Date());
                    hppExchangeDTO.setMobileNum(mobile);
                    hppExchangeDTO.setBody(DevplatformConstants.BODY_TYPE_COURSE);
                    hppExchangeDTO.setPrice(pay);
                    hppExchangeService.save(hppExchangeDTO);

                    //积分可抵现金购买【交易面对面】线下课程，并再获得2000分积分。
                    hppIntegralService.addIntegral(mobile, DevplatformConstants.INTEGRAL_COURSE);
                    HppIntegralDetailDTO in = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_IN, DevplatformConstants.INTEGRAL_DETAIL_TYPE_COURSE, DevplatformConstants.INTEGRAL_COURSE, null);
                    hppIntegralDetailService.createHppIntegralDetailDTO(in);
                }
            }else{
                totalFee = pay;
            }

        } else if (body.equals(DevplatformConstants.BODY_TYPE_VIDEO)) {
            HppVideoDTO hppVideoDTO = hppVideoService.findOne(id);
            pay=hppVideoDTO.getPrice();
            wxOrderDTO.setBodyImg(hppVideoDTO.getImg());
            wxOrderDTO.setBodyParentId(hppVideoDTO.getVideoTypeId());
            wxOrderDTO.setBodyName(hppVideoTypeService.findOne(hppVideoDTO.getVideoTypeId()).getVideoTypeName());
            wxOrderDTO.setBodyDetail(hppVideoDTO.getVideoName());
            wxOrderDTO.setBodyId(hppVideoDTO.getId());

            bodyName = hppVideoDTO.getVideoName();

            if (payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL) || payType.equals(DevplatformConstants.PAY_TYPE_MIX)) {
                HppIntegralDTO hppIntegralDTO =hppIntegralService.findByMobileNum(user.getMobile());
                integral = hppIntegralDTO.getBalance(); //用户积分余额

                if(integral >= pay){
                    payType = DevplatformConstants.PAY_TYPE_INTEGRAL;
                    totalFee = 0.0;
                    integral = pay;
                }else{
                    totalFee = pay - integral;              //需要支付的金额
                }
                if (payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL)){

                    hppIntegralService.reduceIntegral(mobile, integral);
                    HppIntegralDetailDTO out = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_OUT, DevplatformConstants.INTEGRAL_DETAIL_TYPE_VIDEO, integral, null);
                    hppIntegralDetailService.createHppIntegralDetailDTO(out);

                    //保存个人兑换信息
                    HppExchangeDTO hppExchangeDTO=new HppExchangeDTO();
                    int exchangeNum = hppVideoDTO.getExchangeNum() == null ? 0 : hppVideoDTO.getExchangeNum();
                    hppVideoDTO.setExchangeNum(exchangeNum+1);
                    hppVideoService.save(hppVideoDTO);

                    hppExchangeDTO.setBodyId(hppVideoDTO.getId());
                    hppExchangeDTO.setCreateAt(new Date());
                    hppExchangeDTO.setMobileNum(mobile);
                    hppExchangeDTO.setBody(DevplatformConstants.BODY_TYPE_VIDEO);
                    hppExchangeDTO.setPrice(pay);
                    hppExchangeService.save(hppExchangeDTO);

                    //积分可购买【交易战法视频】，并再获得50分积分。
                    hppIntegralService.addIntegral(mobile,DevplatformConstants.INTEGRAL_VIDEO);
                    HppIntegralDetailDTO in  = new HppIntegralDetailDTO(mobile,new Date(), DevplatformConstants.INTEGRAL_IN,DevplatformConstants.INTEGRAL_DETAIL_TYPE_VIDEO,DevplatformConstants.INTEGRAL_VIDEO,null);
                    hppIntegralDetailService.createHppIntegralDetailDTO(in);
                }
            }else{
                totalFee = pay;
            }
        } else if (body.equals(DevplatformConstants.BODY_TYPE_EXAM)) {
            HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(id);
            pay=hppCourseMealDTO.getPrice();
            HppExamDTO hppExamDTO = hppExamService.findByCourseMealId(id);
            wxOrderDTO.setBodyName(hppExamDTO.getTrainName());
            wxOrderDTO.setBodyImg(hppExamDTO.getImg());
            wxOrderDTO.setBodyParentId(hppExamDTO.getId());
            wxOrderDTO.setBodyDetail(hppCourseMealDTO.getName());
            wxOrderDTO.setBodyId(hppCourseMealDTO.getId()); //套餐id

            bodyName = hppExamDTO.getTrainName();

            if (payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL) || payType.equals(DevplatformConstants.PAY_TYPE_MIX) ) {
                HppIntegralDTO hppIntegralDTO =hppIntegralService.findByMobileNum(user.getMobile());
                integral = hppIntegralDTO.getBalance(); //用户积分余额

                //课程最高可抵扣1000
                payType = DevplatformConstants.PAY_TYPE_MIX;
                if(integral >= DevplatformConstants.MIX_EXAM){
                    integral = DevplatformConstants.MIX_EXAM;
                }
                if(pay >= integral){
                    totalFee = pay - integral;
                }else{
                    totalFee = pay;
                    integral = 0.0;
                }
                if (payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL)) {
                    hppIntegralService.reduceIntegral(mobile, integral);
                    HppIntegralDetailDTO out = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_OUT, DevplatformConstants.INTEGRAL_DETAIL_TYPE_EXAM, integral, null);
                    hppIntegralDetailService.createHppIntegralDetailDTO(out);

                    //保存个人兑换信息
                    HppExchangeDTO hppExchangeDTO = new HppExchangeDTO();
                    int exchangeNum = hppExamDTO.getExchangeNum() == null ? 0 : hppExamDTO.getExchangeNum();
                    hppExamDTO.setExchangeNum(exchangeNum + 1);
                    hppExamService.save(hppExamDTO);

                    hppExchangeDTO.setBodyId(hppExamDTO.getId());
                    hppExchangeDTO.setCreateAt(new Date());
                    hppExchangeDTO.setMobileNum(mobile);
                    hppExchangeDTO.setBody(DevplatformConstants.BODY_TYPE_EXAM);
                    hppExchangeDTO.setPrice(pay);
                }
            }else{
                totalFee = pay;
            }
        }

        wxOrderDTO.setBody(body);
        wxOrderDTO.setObject(mobile);
        wxOrderDTO.setTotalFee(totalFee);
        wxOrderDTO.setIntegral(integral);
        wxOrderDTO.setPay(pay);
        wxOrderDTO.setOutTradeNo(body + payCommonUtil.getDateStr());
        wxOrderDTO.setTimeStart(new Date());
        wxOrderDTO.setSpbillCreateIp(getRemortIP(request));

        if (payType.equals(DevplatformConstants.PAY_TYPE_WXCHAT) || payType.equals(DevplatformConstants.PAY_TYPE_MIX)) {
            wxOrderDTO.setPayType(payType);   //标记支付类型
            wxOrderDTO.setStatus(DevplatformConstants.PAY_STATUS_N);   //未支付
            try {
                String totalAmount = new Integer((int)(totalFee*100)).toString();
                SortedMap<Object, Object> parameters = payCommonUtil.getWXPrePayID(); // 获取预付单，此处已做封装，需要工具类
                parameters.put("body", bodyName);
                parameters.put("spbill_create_ip", getRemortIP(request));
                /*parameters.put("spbill_create_ip", "127.0.0.1"); 本地测试*/
                parameters.put("out_trade_no", wxOrderDTO.getOutTradeNo()); // 订单id这里我的订单id生成规则是订单id+时间
                parameters.put("total_fee", totalAmount); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
                // parameters.put("total_fee", orders.getOrderAmount()*100+""); // 上线后，将此代码放开

                // 设置签名
                String sign = payCommonUtil.createSign("UTF-8", parameters);
                parameters.put("sign", sign);
                // 封装请求参数结束
                String requestXML = payCommonUtil.getRequestXml(parameters); // 获取xml结果
                // 调用统一下单接口
                String result1 = payCommonUtil.httpsRequest(wxPayProperties.getPayURL(), "POST",
                        requestXML);
                SortedMap<Object, Object> parMap = payCommonUtil.startWXPay(result1);
                if (parMap != null) {
                    wxOrderDTO.setWxPayOrderString(JSON.toJSONString(parMap));
                } else {
                    result.put("statusCode", ResponseResult.FAIL_CODE);
                    result.put("msg", "支付出错!");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(payType.equals(DevplatformConstants.PAY_TYPE_INTEGRAL)){
            wxOrderDTO.setPayType(DevplatformConstants.PAY_TYPE_INTEGRAL);   //标记为积分支付
            wxOrderDTO.setStatus(DevplatformConstants.PAY_STATUS_Y);   //已支付
            wxOrderDTO.setTimeExpire(new Date());
        }

        wxOrderRepository.save(wxOrderMapper.toEntity(wxOrderDTO));
        result.put("statusCode", ResponseResult.SUCCESS_CODE);
        result.put("data", wxOrderDTO);
        return result;
    }

    /**
     * 获取本机IP地址
     * @return IP
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }



    public WxOrder findByOutTradeNoEquals(String outTradeNo){
        return wxOrderRepository.findByOutTradeNoEquals(outTradeNo);
    }

    public WxOrder save(WxOrder wxOrder){
        return wxOrderRepository.save(wxOrder);
    }

   /* public List<WxOrderDTO> findNoPayOrder(){
        User user = userService.getUserWithAuthorities();
        String sql="SELECT * from t_wx_order where c_object='"+user.getMobile()+"'and c_status='N'";
        return baseDao.findListBySql(sql,WxOrderDTO.class);
    }*/

    public List<WxOrderDTO> findPayOrder(String status){
        User user = userService.getUserWithAuthorities();
        StringBuffer sql=new StringBuffer("SELECT * from t_wx_order where c_object='"+user.getMobile()+"'");
        if (!status.equals("ALL")) {
            sql.append(" and c_status = '"+status+"'");
        }
        sql.append(" order by d_time_start desc");
        return baseDao.findListBySql(sql.toString(),WxOrderDTO.class);
    }


    public HashMap<String, Object> findNoPayOrderById(String id){
        HashMap<String, Object>  retMap = new HashMap<>();
        WxOrder wxOrder = wxOrderRepository.findOne(id);
        Date now = new Date();
        if((now.getTime()-wxOrder.getTimeStart().getTime())*1.0/(60*60*1000) > 1){
            wxOrder.setStatus(DevplatformConstants.PAY_STATUS_DISABLE);
            wxOrderRepository.save(wxOrder);
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "订单已失效,请重新购买...");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        retMap.put("data",wxOrder);
        return retMap;
    }

    public void delete(String id){
        WxOrder wxOrder = wxOrderRepository.findOne(id);
        if(wxOrder.getBody().equals(DevplatformConstants.BODY_TYPE_EXAM)){
            hppExamApplyService.deleteByOutTradeNo(wxOrder.getOutTradeNo());
        }else if(wxOrder.getBody().equals(DevplatformConstants.BODY_TYPE_COURSE)){
            hppTrainApplyService.deleteByOutTradeNo(wxOrder.getOutTradeNo());
        }
        wxOrderRepository.delete(id);
    }
}
