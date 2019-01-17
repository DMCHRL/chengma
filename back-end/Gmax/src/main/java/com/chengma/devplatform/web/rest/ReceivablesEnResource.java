package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.ReceivablesEnService;
import com.chengma.devplatform.service.dto.ReceivablesEnDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing ReceivablesEn.
 */
@RestController
@RequestMapping("/api")
public class ReceivablesEnResource {

    private final Logger log = LoggerFactory.getLogger(ReceivablesEnResource.class);

    private static final String ENTITY_NAME = "receivablesEn";

    private final ReceivablesEnService receivablesEnService;

    public ReceivablesEnResource(ReceivablesEnService receivablesEnService) {
        this.receivablesEnService = receivablesEnService;
    }

    @PostMapping("/receivables_en/saveReceivablesEn")
    @Timed
    public ResponseEntity<ResponseResult> saveHppNotice(@RequestBody ReceivablesEnDTO receivablesEnDTO)  {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = receivablesEnService.checkCreateReceivablesEnDTO(receivablesEnDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save receivablesEnDTO : {}", receivablesEnDTO);
        ReceivablesEnDTO receivablesEnDTO1 = receivablesEnService.createReceivablesEnDTO(receivablesEnDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(receivablesEnDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables_en/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to delete ReceivablesEn : {}", id);
        ReceivablesEnDTO receivablesEnDTO = receivablesEnService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(receivablesEnDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables_en/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll ReceivablesEn : {}");
        List<ReceivablesEnDTO> list = receivablesEnService.findAll();
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
    @GetMapping("/receivables_en/findNew")
    @Timed
    public ResponseEntity<ResponseResult> findListRandom() {
        log.debug("REST request to findNew ReceivablesEn : {}");
        ReceivablesEnDTO receivablesEnDTO = receivablesEnService.findNew();
        ResponseResult json = new ResponseResult();
        json.setData(receivablesEnDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables_en/changFlag/{id}")
    @Timed
    public ResponseEntity<ResponseResult> changFlag(@PathVariable String id) {
        log.debug("REST request to changFlag ReceivablesEn : {}");
        receivablesEnService.changeFlag(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables_en/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete ReceivablesEn : {}");
        receivablesEnService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
