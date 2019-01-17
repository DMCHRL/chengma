package com.chengma.devplatform.web.rest;

import com.alibaba.fastjson.JSON;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.config.llpay.config.PartnerConfig;
import com.chengma.devplatform.config.llpay.utils.LLPayUtil;
import com.chengma.devplatform.config.llpay.vo.PayDataBean;
import com.chengma.devplatform.config.llpay.vo.RetBean;
import com.chengma.devplatform.domain.UsdtOrder;
import com.chengma.devplatform.service.OrderService;
import com.chengma.devplatform.service.dto.OrderDTO;
import com.chengma.devplatform.service.dto.UsdtOrderDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/order/createPayOrder")
    @Timed
    public ResponseEntity<ResponseResult> createOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = orderService.createOrderDTO(orderDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(result.get("statusCode"))){
            json.setMsgCode(result.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to createPayOrder : {}", orderDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result.get("data"));
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/order/createUsdtPayOrder")
    @Timed
    public ResponseEntity<ResponseResult> createUsdtPayOrder(@RequestBody UsdtOrderDTO usdtOrderDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = orderService.createUsdtOrderDTO(usdtOrderDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(result.get("statusCode"))){
            json.setMsgCode(result.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to createPayOrder : {}", usdtOrderDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result.get("data"));
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/order/loadMyOrder")
    @Timed
    public ResponseEntity<ResponseResult> loadMyOrder()  {
        log.debug("REST request to loadMyOrder ");
        ResponseResult json = new ResponseResult();
        List<OrderDTO> list = orderService.loadMyOrder();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/order/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id)  {
        log.debug("REST delete to order ");
        ResponseResult json = new ResponseResult();
        orderService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @PostMapping("/order/notifyUrl")
    @Timed
    public void notifyUrl(HttpServletRequest req, HttpServletResponse resp)  throws Exception {
        resp.setCharacterEncoding("UTF-8");
        System.out.println("进入支付异步通知数据接收处理");
        RetBean retBean = new RetBean();
        String reqStr = LLPayUtil.readReqStr(req);
        if (LLPayUtil.isnull(reqStr))
        {
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            return;
        }
        System.out.println("接收支付异步通知数据：【" + reqStr + "】");
        try
        {
            if (!LLPayUtil.checkSign(reqStr, PartnerConfig.YT_PUB_KEY,
                    ""))
            {
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
                System.out.println("支付异步通知验签失败");
                return;
            }
        } catch (Exception e)
        {
            System.out.println("异步通知报文解析异常：" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            return;
        }
        retBean.setRet_code("0000");
        retBean.setRet_msg("交易成功");
        resp.getWriter().write(JSON.toJSONString(retBean));
        resp.getWriter().flush();
        System.out.println("支付异步通知数据接收处理成功");
        // 解析异步通知对象
        PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
        // TODO:更新订单，发货等后续处理



    }



}
