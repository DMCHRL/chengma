package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppProtocolService;
import com.chengma.devplatform.service.dto.HppProtocolDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppProtocol.
 */
@RestController
@RequestMapping("/api")
public class HppProtocolResource {

    private final Logger log = LoggerFactory.getLogger(HppProtocolResource.class);

    private static final String ENTITY_NAME = "hppProtocol";

    private final HppProtocolService hppProtocolService;

    public HppProtocolResource(HppProtocolService hppProtocolService) {
        this.hppProtocolService =hppProtocolService;
    }


    /**
     * 保存协议信息
     * @param hppProtocolDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_protocol/saveHppProtocol")
    @Timed
    public ResponseEntity<ResponseResult> saveHppProtocol(@RequestBody HppProtocolDTO hppProtocolDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppProtocolService.checkSave(hppProtocolDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save HppProtocol : {}", hppProtocolDTO);
        HppProtocolDTO hppProtocolDTO1 = hppProtocolService.save(hppProtocolDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppProtocolDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @GetMapping("/hpp_protocol/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppProtocol : {}", id);
        ResponseResult json = new ResponseResult();
        hppProtocolService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_protocol/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get a HppProtocol : {}", id);
        ResponseResult json = new ResponseResult();
        HppProtocolDTO hppProtocolDTO= hppProtocolService.findOne(id);
        json.setData(hppProtocolDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_protocol/getType/{type}")
    @Timed
    public ResponseEntity<ResponseResult> getType(@PathVariable String type) {
        log.debug("REST request to get a HppProtocol : {}", type);
        ResponseResult json = new ResponseResult();
        HppProtocolDTO hppProtocolDTO= hppProtocolService.findType(type);
        json.setData(hppProtocolDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @GetMapping("/hpp_protocol/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList() {
        log.debug("REST request to get a list of HppProtocol : {}");
        ResponseResult json = new ResponseResult();
        List<HppProtocolDTO> list= hppProtocolService.findList();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
