package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.UsdtOrderService;
import com.chengma.devplatform.service.dto.UsdtOrderDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class UsdtOrderResource {

    private final Logger log = LoggerFactory.getLogger(UsdtOrderResource.class);

    private static final String ENTITY_NAME = "order";

    private final UsdtOrderService usdtOrderService;

    public UsdtOrderResource(UsdtOrderService usdtOrderService) {
        this.usdtOrderService = usdtOrderService;
    }


    @PostMapping("/usdtOrder/createOrder")
    @Timed
    public ResponseEntity<ResponseResult> createUsdtPayOrder(@RequestBody UsdtOrderDTO usdtOrderDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = usdtOrderService.checkUsdtOrderDTO(usdtOrderDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        HashMap<String,Object> result = usdtOrderService.createUsdtOrderDTO(usdtOrderDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(result.get("statusCode"))){
            json.setMsgCode(result.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to createPayOrder : {}", usdtOrderDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result.get("data"));
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/usdtOrder/notifyUrl",method = {RequestMethod.POST,RequestMethod.GET})
    @Timed
    public void notifyUrl(@RequestParam(value = "UserID",required = false) Integer UserID,@RequestParam(value="OderUsername",required = false) String OderUsername,@RequestParam(value="OrderID",required = false) String OrderID,@RequestParam(value="Result",required = false) Integer Result,@RequestParam(value="OrderMoney",required = false) Float OrderMoney,@RequestParam(value="SuccTime",required = false) String SuccTime,@RequestParam(value="PostKey",required = false) String PostKey)  throws Exception {
        log.info("UserID= "+UserID+"======OrderID= "+OrderID+"=====OderUsername ="+OderUsername+"=====Result= "+Result+"=====OrderMoney= "+OrderMoney+"======SuccTime "+SuccTime+"======PostKey= "+PostKey);
        HashMap<String,Object> params = new HashMap<>();
        params.put("UserID",UserID);
        params.put("OderUsername",OderUsername);
        params.put("OrderID",OrderID);
        params.put("Result",Result);
        params.put("OrderMoney",OrderMoney);
        params.put("SuccTime",SuccTime);
        params.put("PostKey",PostKey);
        usdtOrderService.notifyUrl(params);
    }



}
