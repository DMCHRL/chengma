package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbEaGroupService;
import com.chengma.devplatform.service.dto.TlbEaGroupDTO;
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
 * REST controller for managing TlbEaGroup.
 */
@RestController
@RequestMapping("/api")
public class TlbEaGroupResource {

    private final Logger log = LoggerFactory.getLogger(TlbEaGroupResource.class);

    private static final String ENTITY_NAME = "tlbEaGroup";

    private final TlbEaGroupService tlbEaGroupService;

    public TlbEaGroupResource(TlbEaGroupService tlbEaGroupService) {
        this.tlbEaGroupService = tlbEaGroupService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbEaGroupDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-ea-group/createTlbEaGroup")
    @Timed
    public ResponseEntity<ResponseResult> createTlbEaGroup(@RequestBody TlbEaGroupDTO tlbEaGroupDTO) throws URISyntaxException {
        /*log.debug("REST request to save TlbEaGroup : {}", tlbEaGroupDTO);
        if (tlbEaGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new TlbEaGroup cannot create already have an ID")).body(null);
        }
        TlbEaGroupDTO  tlbEaGroupDTO1 = tlbEaGroupService.save(tlbEaGroupDTO);*/
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
//        json.setData(tlbEaGroupDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @PostMapping("/tlb-ea-group/updateTlbEaGroup")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbEaGroup(@RequestBody TlbEaGroupDTO tlbEaGroupDTO) throws URISyntaxException {
    /*    log.debug("REST request to update TlbEaGroup : {}", tlbEaGroupDTO);
        if (tlbEaGroupDTO.getId() == null) {
            return createTlbEaGroup(tlbEaGroupDTO);
        }
        TlbEaGroupDTO tlbEaGroupDTO1 = tlbEaGroupService.save(tlbEaGroupDTO);;*/
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
//        json.setData(tlbEaGroupDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-ea-group/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbEaGroups");
        Page<TlbEaGroupDTO> page = tlbEaGroupService.pageList(params);
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
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/tlb-ea-group/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll() {
        log.debug("REST request to get a page of TlbEaGroups");
        List<TlbEaGroupDTO> tlbEaGroupDTOList = tlbEaGroupService.findAll();
        ResponseResult json = new ResponseResult();
        json.setData(tlbEaGroupDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * GET  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tlb-ea-group/getTlbEaGroup/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getTlbEaGroup(@PathVariable String id) {
        log.debug("REST request to get TlbEaGroup : {}", id);
        ResponseResult json = new ResponseResult();

        try {
            TlbEaGroupDTO sysComponentDTO = tlbEaGroupService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }

        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



    @DeleteMapping("/tlb-ea-group/deleteTlbEaGroup/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteTlbEaGroup(@PathVariable String id) {
        log.debug("REST request to delete TlbEaGroup : {}", id);
        ResponseResult json = new ResponseResult();
        /*HashMap<String, Object> retMap = tlbEaGroupService.checkDeleteUser(id);
        if(null != retMap.get("msg") ){
            json.setMsgCode(retMap.get("msg").toString());
        }else {
            tlbEaGroupService.delete(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }*/
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-ea-group/approved/{id}")
    @Timed
    public ResponseEntity<ResponseResult> approvedTlbEaGroup(@PathVariable String id, @RequestBody HashMap<String, Object> params) {
        log.debug("REST request to delete TlbEaGroup : {}", id);
        ResponseResult json = new ResponseResult();
        /*HashMap retMap = tlbEaGroupService.approved(id, params);

        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))) {
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }else{
            json.setMsgCode(retMap.get("msg").toString());
        }*/
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-ea-group/createAccount")
    @Timed
    public ResponseEntity<ResponseResult> createAccount(@RequestBody HashMap<String, Object> params) {

        String password = params.get("password") != null ? params.get("password").toString() : "";

        ResponseResult json = new ResponseResult();
        /*if("".equals(password)) {
            json.setMsgCode("请输入套利宝帐号密码");
        }else{
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
            json.setData(tlbEaGroupService.createAccount(password));
        }*/
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
