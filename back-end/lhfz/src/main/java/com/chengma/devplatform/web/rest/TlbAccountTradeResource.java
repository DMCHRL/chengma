package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.TlbAccountTrade;
import com.chengma.devplatform.service.AllotRuleService;
import com.chengma.devplatform.service.TlbAccountTradeService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.TlbAccountTradeDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
@RestController
@RequestMapping("/api")
public class TlbAccountTradeResource {

    private final Logger log = LoggerFactory.getLogger(TlbAccountTradeResource.class);

    private static final String ENTITY_NAME = "tlbAccountTrade";

    private final TlbAccountTradeService tlbAccountTradeService;


    public TlbAccountTradeResource(TlbAccountTradeService tlbAccountTradeService) {
        this.tlbAccountTradeService = tlbAccountTradeService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb_account_trade/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbAccountTrade",params);
        HashMap<String,Object> result = tlbAccountTradeService.pageList(params);
        Page<TlbAccountTradeDTO> page = (Page<TlbAccountTradeDTO> )result.get("page");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
