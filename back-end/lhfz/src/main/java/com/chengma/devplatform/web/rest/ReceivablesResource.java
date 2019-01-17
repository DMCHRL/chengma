package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.ReceivablesService;
import com.chengma.devplatform.service.dto.ReceivablesDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing Receivables.
 */
@RestController
@RequestMapping("/api")
public class ReceivablesResource {

    private final Logger log = LoggerFactory.getLogger(ReceivablesResource.class);

    private static final String ENTITY_NAME = "receivables";

    private final ReceivablesService receivablesService;

    public ReceivablesResource(ReceivablesService receivablesService) {
        this.receivablesService = receivablesService;
    }

    @PostMapping("/receivables/saveReceivables")
    @Timed
    public ResponseEntity<ResponseResult> saveHppNotice(@RequestBody ReceivablesDTO receivablesDTO)  {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = receivablesService.checkCreateReceivablesDTO(receivablesDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save receivablesDTO : {}", receivablesDTO);
        ReceivablesDTO receivablesDTO1 = receivablesService.createReceivablesDTO(receivablesDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(receivablesDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to delete Receivables : {}", id);
        ReceivablesDTO receivablesDTO = receivablesService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(receivablesDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll Receivables : {}");
        List<ReceivablesDTO> list = receivablesService.findAll();
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
    @GetMapping("/receivables/findNew")
    @Timed
    public ResponseEntity<ResponseResult> findListRandom() {
        log.debug("REST request to findNew Receivables : {}");
        ReceivablesDTO receivablesDTO = receivablesService.findNew();
        ResponseResult json = new ResponseResult();
        json.setData(receivablesDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables/changFlag/{id}")
    @Timed
    public ResponseEntity<ResponseResult> changFlag(@PathVariable String id) {
        log.debug("REST request to changFlag Receivables : {}");
        receivablesService.changeFlag(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/receivables/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete Receivables : {}");
        receivablesService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
