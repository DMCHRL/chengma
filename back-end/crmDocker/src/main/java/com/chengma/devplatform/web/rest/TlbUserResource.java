package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbUserService;
import com.chengma.devplatform.service.dto.TlbUserDTO;
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
 * REST controller for managing TlbUser.
 */
@RestController
@RequestMapping("/api")
public class TlbUserResource {

    private final Logger log = LoggerFactory.getLogger(TlbUserResource.class);

    private static final String ENTITY_NAME = "tlbUser";

    private final TlbUserService tlbUserService;

    public TlbUserResource(TlbUserService tlbUserService) {
        this.tlbUserService = tlbUserService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbUserDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-user/createTlbUser")
    @Timed
    public ResponseEntity<ResponseResult> createTlbUser(@RequestBody TlbUserDTO tlbUserDTO) throws URISyntaxException {
        log.debug("REST request to save TlbUser : {}", tlbUserDTO);
        if (tlbUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new TlbUser cannot create already have an ID")).body(null);
        }
        TlbUserDTO  tlbUserDTO1 = tlbUserService.save(tlbUserDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbUserDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @PostMapping("/tlb-user/updateTlbUser")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbUser(@RequestBody TlbUserDTO tlbUserDTO) throws URISyntaxException {
        log.debug("REST request to update TlbUser : {}", tlbUserDTO);
        if (tlbUserDTO.getId() == null) {
            return createTlbUser(tlbUserDTO);
        }
        TlbUserDTO tlbUserDTO1 = tlbUserService.save(tlbUserDTO);;
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbUserDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-user/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbUsers");
        Page<TlbUserDTO> page = tlbUserService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbl-user");
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
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-user/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbUsers");
        List<TlbUserDTO> tlbUserDTOList = tlbUserService.findAll(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbUserDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * GET  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tlb-user/getTlbUser/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getTlbUser(@PathVariable String id) {
        log.debug("REST request to get TlbUser : {}", id);
        ResponseResult json = new ResponseResult();

        try {
            TlbUserDTO sysComponentDTO = tlbUserService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }

        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



    @DeleteMapping("/tlb-user/deleteTlbUser/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteTlbUser(@PathVariable String id) {
        log.debug("REST request to delete TlbUser : {}", id);
        HashMap<String, Object> retMap = tlbUserService.checkDeleteUser(id);
        ResponseResult json = new ResponseResult();
        if(null != retMap.get("msg") ){
            json.setMsgCode(retMap.get("msg").toString());
        }else {
            tlbUserService.delete(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-user/approved/{id}")
    @Timed
    public ResponseEntity<ResponseResult> approvedTlbUser(@PathVariable String id, @RequestBody HashMap<String, Object> params) {
        log.debug("REST request to delete TlbUser : {}", id);
        HashMap retMap = tlbUserService.approved(id, params);
        ResponseResult json = new ResponseResult();
        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))) {
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }else{
            json.setMsgCode(retMap.get("msg").toString());
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-user/createAccount")
    @Timed
    public ResponseEntity<ResponseResult> createAccount(@RequestBody HashMap<String, Object> params) {

        String password = params.get("password") != null ? params.get("password").toString() : "";

        ResponseResult json = new ResponseResult();
        if("".equals(password)) {
            json.setMsgCode("请输入套利宝帐号密码");
        }else{
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
            json.setData(tlbUserService.createAccount(password));
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
