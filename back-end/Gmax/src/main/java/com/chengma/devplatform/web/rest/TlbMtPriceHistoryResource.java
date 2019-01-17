package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.TlbMtPriceHistory;
import com.chengma.devplatform.service.TlbMtPriceHistoryService;
import com.chengma.devplatform.service.dto.TlbMtPriceDTO;
import com.chengma.devplatform.service.dto.TlbMtPriceHistoryDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
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
 * REST controller for managing TlbMtPrice.
 */
@RestController
@RequestMapping("/api")
public class TlbMtPriceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TlbMtPriceHistoryResource.class);

    private static final String ENTITY_NAME = "tlbMtPriceHistory";

    private final TlbMtPriceHistoryService tlbMtPriceHistoryService;

    public TlbMtPriceHistoryResource(TlbMtPriceHistoryService tlbMtPriceHistoryService) {
        this.tlbMtPriceHistoryService = tlbMtPriceHistoryService;
    }



    @PostMapping("/tlb-mt-price-history/findList")
    @Timed
    public ResponseEntity<ResponseResult> findAll(@RequestBody HashMap<String , Object> params) {
        log.debug("REST request to get a page of TlbMtPrices");
        List<TlbMtPriceHistoryDTO> tlbMtPriceHistoryDTOList = tlbMtPriceHistoryService.findListByType(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbMtPriceHistoryDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
