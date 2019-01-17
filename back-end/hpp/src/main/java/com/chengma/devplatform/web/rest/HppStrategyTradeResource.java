package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppStrategyTradeService;
import com.chengma.devplatform.service.dto.HppStrategyTradeDTO;
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
 * REST controller for managingHppStrategyTrade.
 */
@RestController
@RequestMapping("/api")
public class HppStrategyTradeResource {

    private final Logger log = LoggerFactory.getLogger(HppStrategyTradeResource.class);

    private static final String ENTITY_NAME = "hppStrategyTrade";

    private final HppStrategyTradeService hppStrategyTradeService;

    public HppStrategyTradeResource(HppStrategyTradeService hppStrategyTradeService) {
        this.hppStrategyTradeService =hppStrategyTradeService;
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/hpp_strategy_trade/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a pageList of hppStrategyTradeDTO : {}", params);
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> response = hppStrategyTradeService.pageList(params);
        Page<HppStrategyTradeDTO> page=(Page<HppStrategyTradeDTO>)response.get("page");
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        retMap.put("totalLots", response.get("totalLots"));
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }




    /**
     * 根据账号获取全部历史交易(app)
     * @param account
     * @return
     */
    @GetMapping("/hpp_strategy_trade/findTradeByAccount/{account}")
    @Timed
    public ResponseEntity<ResponseResult> findTradeByAccount(@PathVariable String account) {
        log.debug("REST request to get a list of hppStrategyTradeDTO : {}", account);
        ResponseResult json = new ResponseResult();
        List<HppStrategyTradeDTO> list= hppStrategyTradeService.findTradeByAccount(account);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取历史交易(app)近三条
     * @param account
     * @return
     */
    @GetMapping("/hpp_strategy_trade/findTradeByAccountLimit/{account}")
    @Timed
    public ResponseEntity<ResponseResult> findTradeByAccountLimit(@PathVariable String account) {
        log.debug("REST request to get a list of hppStrategyTradeDTO : {}", account);
        ResponseResult json = new ResponseResult();
        List<HppStrategyTradeDTO> list= hppStrategyTradeService.findTradeByAccountLimit(account);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取交易类别比例(app饼图数据)
     * @param account
     * @return
     */
    @GetMapping("/hpp_strategy_trade/findTradeByRatio/{account}")
    @Timed
    public ResponseEntity<ResponseResult> findTradeByRatio(@PathVariable String account) {
        log.debug("REST request to get a list of hppStrategyTradeDTO : {}", account);
        ResponseResult json = new ResponseResult();
        List<HppStrategyTradeDTO> list= hppStrategyTradeService.findTradeByRatio(account);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);

        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
