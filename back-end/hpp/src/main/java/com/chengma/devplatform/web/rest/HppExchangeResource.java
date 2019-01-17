package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppExchangeService;
import com.chengma.devplatform.service.dto.HppExchangeDTO;
import com.chengma.devplatform.service.dto.HppIntegralViewDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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
 * REST controller for managingHppExchange.
 */
@RestController
@RequestMapping("/api")
public class HppExchangeResource {

    private final Logger log = LoggerFactory.getLogger(HppExchangeResource.class);

    private static final String ENTITY_NAME = "hppExchange";

    private final HppExchangeService hppExchangeService;

    public HppExchangeResource(HppExchangeService hppExchangeService) {
        this.hppExchangeService =hppExchangeService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_exchange/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppExchange");
        Page<HppExchangeDTO> page = hppExchangeService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_exchange");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * 兑换视频(app)
     * @param params
     * @return
     * @throws URISyntaxException
     *//*
    @PostMapping("/hpp_exchange/exchange")
    @Timed
    public ResponseEntity<ResponseResult> exchange(@RequestBody HashMap<String, Object> params){
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap=hppExchangeService.checkExchange(params);
        if(!ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            json.setMsgCode(retMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        HppExchangeDTO hppExchangeDTO=hppExchangeService.exchange(params);
        log.debug("REST request to save HppExchange : {}", hppExchangeDTO);
        json.setData(hppExchangeDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/

    /**
     * app 兑换明细
     * @return
     */
    @GetMapping("/hpp_exchange/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList() {
        ResponseResult json = new ResponseResult();
        List<HppIntegralViewDTO> list = hppExchangeService.findList();
        log.debug("REST request to findList HppExchange : {}");
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
