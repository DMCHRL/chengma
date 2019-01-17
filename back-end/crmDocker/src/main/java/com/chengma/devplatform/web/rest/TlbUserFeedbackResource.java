package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbUserFeedbackService;
import com.chengma.devplatform.service.dto.TlbUserFeedbackDTO;
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
 * REST controller for managing TlbUserFeedback.
 */
@RestController
@RequestMapping("/api")
public class TlbUserFeedbackResource {

    private final Logger log = LoggerFactory.getLogger(TlbUserFeedbackResource.class);

    private static final String ENTITY_NAME = "tlbUserFeedback";

    private final TlbUserFeedbackService tlbUserFeedbackService;

    public TlbUserFeedbackResource(TlbUserFeedbackService tlbUserFeedbackService) {
        this.tlbUserFeedbackService = tlbUserFeedbackService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbUserFeedbackDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-user-feedback/createTlbUserFeedback")
    @Timed
    public ResponseEntity<ResponseResult> createTlbUserFeedback(@RequestBody TlbUserFeedbackDTO tlbUserFeedbackDTO) throws URISyntaxException {
        log.debug("REST request to save TlbUserFeedback : {}", tlbUserFeedbackDTO);
        if (tlbUserFeedbackDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysComponent cannot already have an ID")).body(null);
        }
        TlbUserFeedbackDTO userFeedbackDTO = tlbUserFeedbackService.save(tlbUserFeedbackDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(userFeedbackDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-user-feedback/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbUserFeedbacks");
        Page<TlbUserFeedbackDTO> page = tlbUserFeedbackService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tlb-user-feedback");
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("list", page.getContent());
        response.put("total", page.getTotalElements());
        response.put("totalPage", page.getTotalPages());
        ResponseResult json = new ResponseResult();
        json.setData(response);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-user-feedback/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbUserFeedbacks");
        List<TlbUserFeedbackDTO> tlbUserFeedbackDTOList = tlbUserFeedbackService.findAll(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbUserFeedbackDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }


    /**
     * GET  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tlb-user-feedback/getTlbUserFeedback/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getTlbUserFeedback(@PathVariable String id) {
        log.debug("REST request to get TlbUserFeedback : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            TlbUserFeedbackDTO sysComponentDTO = tlbUserFeedbackService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
