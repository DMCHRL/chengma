package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.impl.XXPayServiceImpl;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing users.
 * <p>
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 */
@RestController
@RequestMapping("/api")
public class XXPayResource {

    private final Logger log = LoggerFactory.getLogger(XXPayResource.class);

    @Autowired
    private XXPayServiceImpl xxPayService;

    /**
     * 绑卡
     * @param params
     * @return
     */
    @PostMapping("/xxpay/bind")
    @Timed
    public ResponseEntity<ResponseResult> bind(@RequestBody HashMap<String, Object> params) {
        log.info("bind bank card :" + params.toString());
        Map ret = xxPayService.bind(params);

        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(ret);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 绑卡确认
     * @param params
     * @return
     */
    @PostMapping("/xxpay/bind_confirm")
    @Timed
    public ResponseEntity<ResponseResult> bindConfirm(@RequestBody HashMap<String, Object> params) {
        log.info("bind_confirm bank card :" + params.toString());

        Map ret = xxPayService.bindConfirm(params);

        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(ret);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * 下单
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysRoles in body
     */
    @PostMapping("/xxpay/createOrder")
    @Timed
    public ResponseEntity<ResponseResult> createOrder(@RequestBody HashMap<String, Object> params) {
        log.info("createOrder :" + params.toString());

        Map ret = xxPayService.createOrder(params);

        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(ret);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 支付确认
     * @param params
     * @return
     */
    @PostMapping("/xxpay/pay_confirm")
    @Timed
    public ResponseEntity<ResponseResult> payConfirm(@RequestBody HashMap<String, Object> params) {
        log.info("payConfirm :" + params.toString());

        Map ret = xxPayService.payConfirm(params);

        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(ret);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/xxpay/queryOrder")
    @Timed
    public ResponseEntity<ResponseResult> queryOrder(@RequestBody HashMap<String, Object> params) {
        log.info("query order :" + params.toString());

        Map ret = xxPayService.queryOrder(params);

        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(ret);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @RequestMapping(value = "/xxpay/bind_callback")
    @ResponseBody
    public String bindCallback(HttpServletRequest request) {
        HashMap<String, Object> params = getParams(request);
        log.info("bind_callback funtion data :" + params.toString());

        if( null != params.get("status") && "100".equals(params.get("status"))){
            System.out.print("绑卡回调通知测试成功");
            xxPayService.updateBindBank(params);
        }
        return "success";
    }

    @RequestMapping(value = "/xxpay/pay_callback")
    @ResponseBody
    public String payCallback(HttpServletRequest request) {
        HashMap<String, Object> params = getParams(request);
        log.info("pay_callback funtion data :" + params.toString());
        if( null != params.get("payOrderId") && !"".equals(params.get("payOrderId")) && "2".equals(params.get("status"))){
            System.out.print("付款成功");
            xxPayService.updateTranInfo(params);
        }
        return "success";
    }

    private HashMap<String, Object> getParams(HttpServletRequest request){
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, String[]> paramMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = "";
            String[] paramValueArr = entry.getValue();
            for (int i = 0; paramValueArr != null && i < paramValueArr.length; i++) {
                if (i == paramValueArr.length - 1) {
                    paramValue += paramValueArr[i];
                } else {
                    paramValue += paramValueArr[i] + ",";
                }
            }
            returnMap.put(paramName, paramValue);
        }
        return returnMap;
    }
}
