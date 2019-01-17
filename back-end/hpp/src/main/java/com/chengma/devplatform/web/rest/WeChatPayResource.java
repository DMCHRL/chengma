package com.chengma.devplatform.web.rest;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import com.alibaba.fastjson.JSON;
import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.PayCommonUtil;
import com.chengma.devplatform.common.util.XMLUtil;
import com.chengma.devplatform.config.WxPayProperties;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managingHppNotice.
 */
@RestController
@RequestMapping("/api")
public class WeChatPayResource {

    private final Logger log = LoggerFactory.getLogger(WeChatPayResource.class);
    
    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private WxPayProperties wxPayProperties;

    @Autowired
    private PayCommonUtil payCommonUtil;

    @Autowired
    private HppIntegralService hppIntegralService;

    @Autowired
    private HppIntegralDetailService hppIntegralDetailService;

    @Autowired
    private HppMobileUserService hppMobileUserService;

    @Autowired
    private HppExamApplyService hppExamApplyService;

    @Autowired
    private HppTrainApplyService hppTrainApplyService;



    /**
     * 如果是微信支付则,拉取微信预付单
     */
    @PostMapping("/pay/createOrder")
    @Timed
    public ResponseEntity<ResponseResult> getWXPay(HttpServletRequest request,@RequestBody HashMap<String, Object> params) {
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> checkMap =wxPayService.checkPay(params);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        HashMap<String,Object> result = wxPayService.getWXPay(request,params);
        if(!ResponseResult.SUCCESS_CODE.equals(result.get("statusCode"))){
            json.setMsgCode(result.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save createOrder : {}", params);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result.get("data"));
        return new ResponseEntity<>(json, null, HttpStatus.OK);

    }

    /**
     * 微信异步通知
     */
    @RequestMapping("/wxpay/notifyUrl")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException
    {
        String result = payCommonUtil.reciverWx(request); // 接收到异步的参数
        Map<String, String> m = new HashMap<String, String>();// 解析xml成map
        if (m != null && !"".equals(m))
        {
            m = XMLUtil.doXMLParse(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext())
        {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue)
            {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml = "";
        if (payCommonUtil.isTenpaySign("UTF-8", packageParams))
        {
            if ("SUCCESS".equals((String) packageParams.get("return_code")))
            {
                // 如果返回成功
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                // String transaction_id = (String)
                // packageParams.get("transaction_id"); // 微信支付订单号
                // 查询订单 根据订单号查询订单
              /*  String orderId = out_trade_no.substring(0, out_trade_no.length() - payCommonUtil.TIME.length());
                Orders orders = ordersMapper.selectByPrimaryKey(Integer.parseInt(orderId));*/
                WxOrder wxOrder = wxPayService.findByOutTradeNoEquals(out_trade_no);

                // 验证商户ID 和 价格 以防止篡改金额
                if (wxPayProperties.getMchId().equals(mch_id) && wxOrder != null
                    // &&
                    // total_fee.trim().toString().equals(orders.getOrderAmount())
                    // // 实际项目中将此注释删掉，以保证支付金额相等
                        )
                {
                    /** 这里是我项目里的消费状态
                     * 1.待付款=0 2.付款完成=1
                     * 3.消费成功=2
                     * 4.取消=-1
                     * 5.发起退款=-2
                     * 6.退款成功=-3
                     * 7.退款失败=3（由于商户拒绝退款或其他原因导致退款失败）
                     */
                   /* insertWxNotice(packageParams);
                    orders.setPayWay("1"); // 变更支付方式为wx
                    orders.setOrderState("1"); // 订单状态为已付款*/
                    wxOrder.setFeeType("CNY");

                    String mobile = wxOrder.getObject(); //客户手机号

                    if(wxOrder.getStatus().equals(DevplatformConstants.PAY_STATUS_N)){
                        //标记为消费用户
                        hppMobileUserService.buyFlagY(mobile);
                        //加积分
                        if(wxOrder.getBody().equals(DevplatformConstants.BODY_TYPE_VIDEO)){
                            if(wxOrder.getPayType().equals(DevplatformConstants.PAY_TYPE_MIX)){
                                hppIntegralService.reduceIntegral(mobile, wxOrder.getIntegral());
                                HppIntegralDetailDTO out = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_OUT, DevplatformConstants.INTEGRAL_DETAIL_TYPE_VIDEO, wxOrder.getIntegral(), null);
                                hppIntegralDetailService.createHppIntegralDetailDTO(out);
                            }

                            hppIntegralService.addIntegral(mobile,DevplatformConstants.WECHAT_VIDEO);
                            HppIntegralDetailDTO in = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_IN, DevplatformConstants.INTEGRAL_DETAIL_TYPE_VIDEO, DevplatformConstants.WECHAT_VIDEO, null);
                            hppIntegralDetailService.createHppIntegralDetailDTO(in);
                        }else if(wxOrder.getBody().equals(DevplatformConstants.BODY_TYPE_COURSE)){
                            if(wxOrder.getPayType().equals(DevplatformConstants.PAY_TYPE_MIX)){
                                hppIntegralService.reduceIntegral(mobile, wxOrder.getIntegral());
                                HppIntegralDetailDTO out = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_OUT, DevplatformConstants.INTEGRAL_DETAIL_TYPE_COURSE, wxOrder.getIntegral(), null);
                                hppIntegralDetailService.createHppIntegralDetailDTO(out);
                            }

                            hppIntegralService.addIntegral(wxOrder.getObject(),DevplatformConstants.WECHAT_COURSE);
                            HppIntegralDetailDTO in = new HppIntegralDetailDTO(wxOrder.getObject(), new Date(), DevplatformConstants.INTEGRAL_IN, DevplatformConstants.INTEGRAL_DETAIL_TYPE_COURSE, DevplatformConstants.WECHAT_COURSE, null);
                            hppIntegralDetailService.createHppIntegralDetailDTO(in);

                            HppTrainApplyDTO hppTrainApplyDTO = hppTrainApplyService.findByOutTradeNo(wxOrder.getOutTradeNo());
                            hppTrainApplyDTO.setStatus(DevplatformConstants.PAY_STATUS_Y);
                            hppTrainApplyService.save(hppTrainApplyDTO);
                        }else if(wxOrder.getBody().equals(DevplatformConstants.BODY_TYPE_EXAM)){
                            if(wxOrder.getPayType().equals(DevplatformConstants.PAY_TYPE_MIX)){
                                hppIntegralService.reduceIntegral(mobile, wxOrder.getIntegral());
                                HppIntegralDetailDTO out = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_OUT, DevplatformConstants.INTEGRAL_DETAIL_TYPE_EXAM, wxOrder.getIntegral(), null);
                                hppIntegralDetailService.createHppIntegralDetailDTO(out);
                            }

                            HppExamApplyDTO hppExamApplyDTO = hppExamApplyService.findByOutTradeNo(wxOrder.getOutTradeNo());
                            hppExamApplyDTO.setStatus(DevplatformConstants.PAY_STATUS_Y);
                            hppExamApplyService.save(hppExamApplyDTO);
                        }
                    }
                    wxOrder.setStatus(DevplatformConstants.PAY_STATUS_Y);
                    wxOrder.setTimeExpire(new Date());
                    wxPayService.save(wxOrder);
                    //ordersMapper.updateByPrimaryKeySelective(orders); // 变更数据库中该订单状态
                    // ordersMapper.updatePayStatus(Integer.parseInt(orderId));
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                }
            } else {// 如果微信返回支付失败，将错误信息返回给微信
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }

        // 处理业务完毕，将业务结果通知给微信
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    @GetMapping("/pay/orderquery/{order}")
    @Timed
    public ResponseEntity<ResponseResult> orderquery(@PathVariable String order) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid",wxPayProperties.getAppId());
        parameters.put("mch_id", wxPayProperties.getMchId());
        parameters.put("out_trade_no", order);
        parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
        String sign = payCommonUtil.createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        String requestXML = payCommonUtil.getRequestXml(parameters); // 获取xml结果
        String result1 = payCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/orderquery", "POST",
                requestXML);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 返回未支付订单
     * @return
     *//*
    @GetMapping("/pay/findNoPayOrder")
    @Timed
    public ResponseEntity<ResponseResult> findNoPayOrder() {
        ResponseResult json = new ResponseResult();
        List<WxOrderDTO> list = wxPayService.findNoPayOrder();
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("list",list);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(resultMap);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/

    /**
     * 返回支付订单
     * @return
     */
    @GetMapping("/pay/findPayOrder/{status}")
    @Timed
    public ResponseEntity<ResponseResult> findPayOrder(@PathVariable String status) {
        ResponseResult json = new ResponseResult();
        List<WxOrderDTO> list = wxPayService.findPayOrder(status);
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("list",list);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(resultMap);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * 拉取未支付订单
     * @return
     */
    @GetMapping("/pay/rePay/{id}")
    @Timed
    public ResponseEntity<ResponseResult> findNoPayOrderById(@PathVariable String id) {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap  = wxPayService.findNoPayOrderById(id);
        HashMap<String,Object> resultMap = new HashMap<>();
        if(!ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            json.setMsgCode(retMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(retMap.get("data"));
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * 取消订单
     * @return
     */
    @GetMapping("/pay/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        ResponseResult json = new ResponseResult();
        wxPayService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
