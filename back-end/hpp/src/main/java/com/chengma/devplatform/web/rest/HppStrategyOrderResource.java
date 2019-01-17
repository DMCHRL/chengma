package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppStrategyOrderService;
import com.chengma.devplatform.service.dto.HppStrategyDTO;
import com.chengma.devplatform.service.dto.HppStrategyOrderDTO;
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

/**
 * REST controller for .HppStrategyOrder
 */
@RestController
@RequestMapping("/api")
public class HppStrategyOrderResource {

    private final Logger log = LoggerFactory.getLogger(HppStrategyOrderResource.class);

    private static final String ENTITY_NAME = "hppStrategyOrder";

    private final HppStrategyOrderService hppStrategyOrderService;

    public HppStrategyOrderResource(HppStrategyOrderService hppStrategyOrderService) {
        this.hppStrategyOrderService =hppStrategyOrderService;
    }

    /**
     * 申请跟单(app)
     * @param hppStrategyOrderDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_strategy_order/follow")
    @Timed
    public ResponseEntity<ResponseResult> follow(@RequestBody HppStrategyOrderDTO hppStrategyOrderDTO) {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppStrategyOrderService.checkApply(hppStrategyOrderDTO, DevplatformConstants.STRATEGY_IN);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save HppStrategyOrder : {}", hppStrategyOrderDTO);
        HppStrategyOrderDTO hppStrategyOrderDTO1 = hppStrategyOrderService.follow(hppStrategyOrderDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppStrategyOrderDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 申请解绑(app)
     * @param hppStrategyOrderDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_strategy_order/relieve")
    @Timed
    public ResponseEntity<ResponseResult> relieve(@RequestBody HppStrategyOrderDTO hppStrategyOrderDTO) {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppStrategyOrderService.checkApply(hppStrategyOrderDTO, DevplatformConstants.STRATEGY_OUT);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save HppStrategyOrder : {}", hppStrategyOrderDTO);
        HppStrategyOrderDTO hppStrategyOrderDTO1 = hppStrategyOrderService.relieve(hppStrategyOrderDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppStrategyOrderDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 处理申请单
     * @param params
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_strategy_order/approve")
    @Timed
    public ResponseEntity<ResponseResult> approve(@RequestBody HashMap<String,Object> params) {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppStrategyOrderService.checkApprove(params);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to approve HppStrategyOrder : {}");
        HppStrategyOrderDTO hppStrategyOrderDTO1 = hppStrategyOrderService.approve(params);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppStrategyOrderDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_strategy_order/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppStrategyDTO");
        Page<HppStrategyOrderDTO> page = hppStrategyOrderService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_strategy_order");
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
     * Post  .
     *根据策略Id获取跟单着具体数据
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_strategy_order/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppStrategyDTO");
        Page<HppStrategyOrderDTO> page = hppStrategyOrderService.findList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_strategy_order");
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
     * Post  .
     *设置mt4状态
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_strategy_order/setMt4Status")
    @Timed
    public ResponseEntity<ResponseResult> setMt4Status(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to setMt4Status of HppStrategyOrderDTO");
        hppStrategyOrderService.setMt4Status(params);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
