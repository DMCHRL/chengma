package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbHistoryKService;
import com.chengma.devplatform.service.dto.TlbHistoryKDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
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

import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * REST controller for managing TlbHistoryK.
 */
@RestController
@RequestMapping("/api")
public class TlbHistoryKResource {

    private final Logger log = LoggerFactory.getLogger(TlbHistoryKResource.class);

    private static final String ENTITY_NAME = "tlbHistoryK";

    private final TlbHistoryKService tlbHistoryKService;

    public TlbHistoryKResource(TlbHistoryKService tlbHistoryKService) {
        this.tlbHistoryKService = tlbHistoryKService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbHistoryKDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-history-k/createTlbHistoryK")
    @Timed
    public ResponseEntity<ResponseResult> createTlbHistoryK(@RequestBody TlbHistoryKDTO tlbHistoryKDTO) throws URISyntaxException {
        log.debug("REST request to save TlbHistoryK : {}", tlbHistoryKDTO);
        if (tlbHistoryKDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new TlbHistoryK cannot create already have an ID")).body(null);
        }
        TlbHistoryKDTO  tlbHistoryKDTO1 = tlbHistoryKService.save(tlbHistoryKDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbHistoryKDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-history-k/updateTlbHistoryK")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbHistoryK(@RequestBody TlbHistoryKDTO tlbHistoryKDTO) throws URISyntaxException {
        log.debug("REST request to update TlbHistoryK : {}", tlbHistoryKDTO);
        if (tlbHistoryKDTO.getId() == null) {
            return createTlbHistoryK(tlbHistoryKDTO);
        }
        TlbHistoryKDTO tlbHistoryKDTO1 = tlbHistoryKService.save(tlbHistoryKDTO);;
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbHistoryKDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-history-k/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbHistoryKs");
        Page<TlbHistoryKDTO> page = tlbHistoryKService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tlb-history-k");
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
