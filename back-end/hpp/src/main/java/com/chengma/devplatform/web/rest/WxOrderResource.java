package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.WxOrderService;
import com.chengma.devplatform.service.dto.WxOrderDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * REST controller for managing WxOrder.
 */
@RestController
@RequestMapping("/api")
public class WxOrderResource {

    private final Logger log = LoggerFactory.getLogger(WxOrderResource.class);

    private static final String ENTITY_NAME = "wxOrder";

    private final WxOrderService wxOrderService;

    public WxOrderResource(WxOrderService wxOrderService) {
        this.wxOrderService = wxOrderService;
    }

    /**
     * Post  .
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/wx_order/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of WxOrder");
        Page<WxOrderDTO> page = wxOrderService.pageList(params);
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
