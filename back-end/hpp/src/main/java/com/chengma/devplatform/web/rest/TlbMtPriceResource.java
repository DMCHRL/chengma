package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbMtPriceService;
import com.chengma.devplatform.service.dto.TlbMtPriceDTO;
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

/**
 * REST controller for managing TlbMtPrice.
 */
@RestController
@RequestMapping("/api")
public class TlbMtPriceResource {

    private final Logger log = LoggerFactory.getLogger(TlbMtPriceResource.class);

    private static final String ENTITY_NAME = "tlbMtPrice";

    private final TlbMtPriceService tlbMtPriceService;

    public TlbMtPriceResource(TlbMtPriceService tlbMtPriceService) {
        this.tlbMtPriceService = tlbMtPriceService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbMtPriceDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-mt-price/createTlbMtPrice")
    @Timed
    public ResponseEntity<ResponseResult> createTlbMtPrice(@RequestBody TlbMtPriceDTO tlbMtPriceDTO) throws URISyntaxException {
        log.debug("REST request to save TlbMtPrice : {}", tlbMtPriceDTO);
        if (tlbMtPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new TlbMtPrice cannot create already have an ID")).body(null);
        }
        TlbMtPriceDTO  tlbMtPriceDTO1 = tlbMtPriceService.save(tlbMtPriceDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbMtPriceDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-mt-price/updateTlbMtPrice")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbMtPrice(@RequestBody TlbMtPriceDTO tlbMtPriceDTO) throws URISyntaxException {
        log.debug("REST request to update TlbMtPrice : {}", tlbMtPriceDTO);
        if (tlbMtPriceDTO.getId() == null) {
            return createTlbMtPrice(tlbMtPriceDTO);
        }
        TlbMtPriceDTO tlbMtPriceDTO1 = tlbMtPriceService.save(tlbMtPriceDTO);;
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbMtPriceDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-mt-price/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbMtPrices");
        Page<TlbMtPriceDTO> page = tlbMtPriceService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tlb-mt-price");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    @GetMapping("/tlb-mt-price/findLastPrice")
    @Timed
    public ResponseEntity<ResponseResult> findLastPrice() {
        log.debug("REST request to get a page of TlbMtPrices");
        String symbol = "EURUSD";
        TlbMtPriceDTO tlbMtPriceDTO = tlbMtPriceService.findBySymbol(symbol);
        ResponseResult json = new ResponseResult();
        json.setData(tlbMtPriceDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
