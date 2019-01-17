package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.DigitalCurrencyService;
import com.chengma.devplatform.service.dto.DigitalCurrencyDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing DigitalCurrency.
 */
@RestController
@RequestMapping("/api")
public class DigitalCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(DigitalCurrencyResource.class);

    private static final String ENTITY_NAME = "digitalCurrency";

    private final DigitalCurrencyService digitalCurrencyService;

    public DigitalCurrencyResource(DigitalCurrencyService digitalCurrencyService) {
        this.digitalCurrencyService = digitalCurrencyService;
    }

    @PostMapping("/digital_currency/saveDigitalCurrency")
    @Timed
    public ResponseEntity<ResponseResult> saveHppNotice(@RequestBody DigitalCurrencyDTO digitalCurrencyDTO)  {
        ResponseResult json = new ResponseResult();
        log.debug("REST request to save digitalCurrencyDTO : {}", digitalCurrencyDTO);
        DigitalCurrencyDTO digitalCurrencyDTO1 = digitalCurrencyService.createDigitalCurrencyDTO(digitalCurrencyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(digitalCurrencyDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/digital_currency/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to delete DigitalCurrency : {}", id);
        DigitalCurrencyDTO digitalCurrencyDTO = digitalCurrencyService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(digitalCurrencyDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/digital_currency/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll DigitalCurrency : {}");
        List<DigitalCurrencyDTO> list = digitalCurrencyService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取最新收款人
     * @return
     */
    @GetMapping("/digital_currency/findNew")
    @Timed
    public ResponseEntity<ResponseResult> findListRandom() {
        log.debug("REST request to findNew DigitalCurrency : {}");
        DigitalCurrencyDTO digitalCurrencyDTO = digitalCurrencyService.findNew();
        ResponseResult json = new ResponseResult();
        json.setData(digitalCurrencyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/digital_currency/changFlag/{id}")
    @Timed
    public ResponseEntity<ResponseResult> changFlag(@PathVariable String id) {
        log.debug("REST request to changFlag DigitalCurrency : {}");
        digitalCurrencyService.changeFlag(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/digital_currency/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete DigitalCurrency : {}");
        digitalCurrencyService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
