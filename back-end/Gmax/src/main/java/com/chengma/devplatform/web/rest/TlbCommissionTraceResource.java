package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbCommissionTraceService;
import com.chengma.devplatform.service.dto.TlbCommissionTraceDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * REST controller for managing TlbCommissionTrace.
 */
@RestController
@RequestMapping("/api")
public class TlbCommissionTraceResource {

    private final Logger log = LoggerFactory.getLogger(TlbCommissionTraceResource.class);

    private static final String ENTITY_NAME = "tlbCommissionTrace";

    private final TlbCommissionTraceService tlbCommissionTraceService;

    public TlbCommissionTraceResource(TlbCommissionTraceService tlbCommissionTraceService) {
        this.tlbCommissionTraceService = tlbCommissionTraceService;
    }


    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-commission-trace/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbCommissionTraces");
        Page<TlbCommissionTraceDTO> page = tlbCommissionTraceService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tlb-commission-trace");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }


}
