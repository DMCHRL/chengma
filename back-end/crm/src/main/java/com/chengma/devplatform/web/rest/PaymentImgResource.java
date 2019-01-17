package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.PaymentImgService;
import com.chengma.devplatform.service.dto.PaymentImgDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing PaymentImg.
 */
@RestController
@RequestMapping("/api")
public class PaymentImgResource {

    private final Logger log = LoggerFactory.getLogger(PaymentImgResource.class);

    private static final String ENTITY_NAME = "paymentImg";

    private final PaymentImgService paymentImgService;

    public PaymentImgResource(PaymentImgService paymentImgService) {
        this.paymentImgService = paymentImgService;
    }

    /**
     * 保存支付圖片
     * @param paymentImgDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/payment_img/save")
    @Timed
    public ResponseEntity<ResponseResult> save(@RequestBody PaymentImgDTO paymentImgDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> checkMap=paymentImgService.checkPaymentImgDTO(paymentImgDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        PaymentImgDTO paymentImgDTO1 = paymentImgService.save(paymentImgDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(paymentImgDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 獲支付圖片實體
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/payment_img/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> findOne(@PathVariable String id) {
        ResponseResult json = new ResponseResult();
        PaymentImgDTO paymentImgDTO1 = paymentImgService.findOne(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(paymentImgDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     *刪除支付圖片實體
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/payment_img/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        ResponseResult json = new ResponseResult();
        paymentImgService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     *獲取支付圖片實體列表
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/payment_img/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        ResponseResult json = new ResponseResult();
        List<PaymentImgDTO> list = paymentImgService.findAll();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     *設置為最新支付圖
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/payment_img/setImgFlag/{id}")
    @Timed
    public ResponseEntity<ResponseResult> setImgFlag(@PathVariable String id) {
        ResponseResult json = new ResponseResult();
        PaymentImgDTO paymentImgDTO = paymentImgService.setImgFlag(id);
        json.setData(paymentImgDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     *獲取最新支付圖
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/payment_img/findNewImg")
    @Timed
    public ResponseEntity<ResponseResult> findNewImg() {
        ResponseResult json = new ResponseResult();
        PaymentImgDTO paymentImgDTO = paymentImgService.findNewImg();
        HashMap<String, Object> retMap = new HashMap<>();
        json.setData(paymentImgDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
