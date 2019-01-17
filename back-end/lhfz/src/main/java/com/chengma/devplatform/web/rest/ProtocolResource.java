package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.ProtocolService;
import com.chengma.devplatform.service.dto.ProtocolDTO;
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
 * REST controller for managingProtocol.
 */
@RestController
@RequestMapping("/api")
public class ProtocolResource {

    private final Logger log = LoggerFactory.getLogger(ProtocolResource.class);

    private static final String ENTITY_NAME = "protocol";

    private final ProtocolService protocolService;

    public ProtocolResource(ProtocolService protocolService) {
        this.protocolService =protocolService;
    }


    /**
     * 保存协议信息
     * @param protocolDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/protocol/saveProtocol")
    @Timed
    public ResponseEntity<ResponseResult> saveProtocol(@RequestBody ProtocolDTO protocolDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = protocolService.checkSave(protocolDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save Protocol : {}", protocolDTO);
        ProtocolDTO protocolDTO1 = protocolService.save(protocolDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(protocolDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @GetMapping("/protocol/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete Protocol : {}", id);
        ResponseResult json = new ResponseResult();
        protocolService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/protocol/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get a Protocol : {}", id);
        ResponseResult json = new ResponseResult();
        ProtocolDTO protocolDTO= protocolService.findOne(id);
        json.setData(protocolDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/protocol/getType/{type}")
    @Timed
    public ResponseEntity<ResponseResult> getType(@PathVariable String type) {
        log.debug("REST request to get a Protocol : {}", type);
        ResponseResult json = new ResponseResult();
        ProtocolDTO protocolDTO= protocolService.findType(type);
        json.setData(protocolDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @GetMapping("/protocol/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList() {
        log.debug("REST request to get a list of Protocol : {}");
        ResponseResult json = new ResponseResult();
        List<ProtocolDTO> list= protocolService.findList();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
