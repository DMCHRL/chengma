package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppStrategyBalHistoryService;
import com.chengma.devplatform.service.dto.HppStrategyBalHistoryDTO;
import com.chengma.devplatform.service.dto.HppStrategyViewDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppStrategyBalHistory.
 */
@RestController
@RequestMapping("/api")
public class HppStrategyBalHistoryResource {

    private final Logger log = LoggerFactory.getLogger(HppStrategyBalHistoryResource.class);

    private static final String ENTITY_NAME = "hppStrategyBalHistory";

    private final HppStrategyBalHistoryService hppStrategyBalHistoryService;

    public HppStrategyBalHistoryResource(HppStrategyBalHistoryService hppStrategyBalHistoryService) {
        this.hppStrategyBalHistoryService =hppStrategyBalHistoryService;
    }


    /**
     * 获取余额增长表(app)
     * @param params
     * @return
     */
    @PostMapping("/hpp_strategy_bal_history/findByAccount")
    @Timed
    public ResponseEntity<ResponseResult> findByMobile(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to find balance of account : {}", params);
        ResponseResult json = new ResponseResult();
        List<HppStrategyViewDTO> list= hppStrategyBalHistoryService.findGrowthRate(params);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取收益率(app)
     * @param params
     * @return
     */
    @PostMapping("/hpp_strategy_bal_history/findGainRate")
    @Timed
    public ResponseEntity<ResponseResult> findGainRate(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to find gainRate of account : {}", params);
        ResponseResult json = new ResponseResult();
        List<HppStrategyViewDTO> list= hppStrategyBalHistoryService.findProfitRate(params);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
