package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbFundApplyService;
import com.chengma.devplatform.service.dto.TlbFundApplyDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing SysComponent.
 */
@RestController
@RequestMapping("/api")
public class TlbFundApplyResource {

    private final Logger log = LoggerFactory.getLogger(TlbFundApplyResource.class);

    private static final String ENTITY_NAME = "tlbFundApply";

    private final TlbFundApplyService tlbFundApplyService;

    public TlbFundApplyResource(TlbFundApplyService tlbFundApplyService) {
        this.tlbFundApplyService = tlbFundApplyService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbFundApplyDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-fund-apply/createTlbFundApply")
    @Timed
    public ResponseEntity<ResponseResult> createTlbFundApply(@RequestBody TlbFundApplyDTO tlbFundApplyDTO) throws URISyntaxException {
        log.debug("REST request to save TlbFundApply : {}", tlbFundApplyDTO);
        if (tlbFundApplyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysComponent cannot already have an ID")).body(null);
        }
        TlbFundApplyDTO accountDTO = tlbFundApplyService.save(tlbFundApplyDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(accountDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/updateTlbFundApply")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbUser(@RequestBody TlbFundApplyDTO tlbFundApplyDTO) throws URISyntaxException {
        log.debug("REST request to update TlbFundApply : {}", tlbFundApplyDTO);
        if (tlbFundApplyDTO.getId() == null) {
            return createTlbFundApply(tlbFundApplyDTO);
        }
        TlbFundApplyDTO tlbFundApplyDTO1 = tlbFundApplyService.save(tlbFundApplyDTO);;
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbFundApplyDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-fund-apply/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbFundApply");
        Page<TlbFundApplyDTO> page = tlbFundApplyService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbl-user");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        //json.setData(page.getContent());
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-fund-apply/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbFundApply");
        List<TlbFundApplyDTO> tlbFundApplyDTOList = tlbFundApplyService.findAll(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * 提现
     * @param params
     * @return
     */

    @PostMapping("/tlb-fund-apply/withdrawals")
    @Timed
    public ResponseEntity<ResponseResult> withdrawals(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to withdrawals from TlbFundApply");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = tlbFundApplyService.checkWithdrawals(params);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.withdrawals(params);
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/recharge")
    @Timed
    public ResponseEntity<ResponseResult> recharge(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.recharge(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/inner")
    @Timed
    public ResponseEntity<ResponseResult> inner(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbFundApply");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = tlbFundApplyService.checkInner(params);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.inner(params);
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/pass/{id}")
    @Timed
    public ResponseEntity<ResponseResult> pass(@PathVariable String id) {
        log.debug("REST request to pass recharge TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.pass(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/reject/{id}")
    @Timed
    public ResponseEntity<ResponseResult> reject(@PathVariable String id) {
        log.debug("REST request to pass recharge TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.reject(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }


    /**
     * GET  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tlb-fund-apply/getTlbFundApply/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysComponent(@PathVariable String id) {
        log.debug("REST request to get TlbFundApply : {}", id);
        ResponseResult json = new ResponseResult();

        try {
            TlbFundApplyDTO sysComponentDTO = tlbFundApplyService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }

        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/tlb-fund-apply/deleteTlbFundApply/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysUser(@PathVariable String id) {
        log.debug("REST request to delete TlbFundApply : {}", id);
        tlbFundApplyService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/passRecharge/{id}")
    @Timed
    public ResponseEntity<ResponseResult> passRecharge(@PathVariable String id) {
        log.debug("REST request to pass recharge TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.passRecharge(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/rejectRecharge/{id}")
    @Timed
    public ResponseEntity<ResponseResult> rejectRecharge(@PathVariable String id) {
        log.debug("REST request to reject recharge TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.rejectRecharge(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/passWithdrawals/{id}")
    @Timed
    public ResponseEntity<ResponseResult> passWithdrawals(@PathVariable String id) {
        log.debug("REST request to pass withdrawals TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.passWithdrawals(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/passInner/{id}")
    @Timed
    public ResponseEntity<ResponseResult> passInner(@PathVariable String id) {
        log.debug("REST request to pass inner TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.passInner(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/rejectInner/{id}")
    @Timed
    public ResponseEntity<ResponseResult> rejectInner(@PathVariable String id) {
        log.debug("REST request to reject inner TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.rejectInner(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/tlb-fund-apply/rejectWithdrawals/{id}")
    @Timed
    public ResponseEntity<ResponseResult> rejectWithdrawals(@PathVariable String id) {
        log.debug("REST request to reject withdrawals TlbFundApply");
        TlbFundApplyDTO tlbFundApplyDTO = tlbFundApplyService.rejectWithdrawals(id);
        ResponseResult json = new ResponseResult();
        json.setData(tlbFundApplyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/tlb-fund-apply/history/{account}")
    @Timed
    public ResponseEntity<ResponseResult> history(@PathVariable String account) {
        log.debug("REST request to history TlbFundApply");
        List<TlbFundApplyDTO> list = tlbFundApplyService.findTlbFundHistoryList(account, DevplatformConstants.FUND_APPLY_STATUS_PASSED);
        ResponseResult json = new ResponseResult();
        json.setData(list);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}
